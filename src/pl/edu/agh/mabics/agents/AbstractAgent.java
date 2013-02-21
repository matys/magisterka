package pl.edu.agh.mabics.agents;

import org.mortbay.jetty.Request;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.handler.AbstractHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.mabics.experiment.controllers.CollisionController;
import pl.edu.agh.mabics.platform.JSONHelper;
import pl.edu.agh.mabics.platform.model.MoveState;
import pl.edu.agh.mabics.platform.model.PlatformRequest;
import pl.edu.agh.mabics.platform.model.PlatformResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
public abstract class AbstractAgent extends AbstractHandler {

    private JSONHelper jsonHelper;
    private CollisionController collisionController;
    private int port;
    private String id;

    @Autowired
    public void setJsonHelper(JSONHelper jsonHelper) {
        this.jsonHelper = jsonHelper;
    }

    @Autowired
    public void setCollisionController(CollisionController collisionController) {
        this.collisionController = collisionController;
    }

    public abstract PlatformResponse getNextMove(PlatformRequest request);

    public void handle(String target, HttpServletRequest request,
                       HttpServletResponse response, int dispatch) throws IOException,
            ServletException {
        String content = request.getReader().readLine();
        PlatformRequest parsedRequest = jsonHelper.parseRequest(content);
        prepareResponseToSend(response);
        makeMove((Request) request, response, parsedRequest);
    }

    private void makeMove(Request request, HttpServletResponse response, PlatformRequest parsedRequest) throws IOException {
        PlatformResponse nextMove = getNextMove(parsedRequest);
        collisionController.acceptMove(id, nextMove.getMove());
//        while (nextMove.getMove().getState().equals(MoveState.NOT_PROCESSED)) {
//            sleep();
//        }
        if (!nextMove.getMove().getState().equals(MoveState.ACCEPTED)) {
            response.getOutputStream().print(jsonHelper.responseToJSON(nextMove));
            request.setHandled(true);
        } else {
            removeMove(parsedRequest, nextMove);
            makeMove(request, response, parsedRequest);
        }
    }

    private void removeMove(PlatformRequest parsedRequest, PlatformResponse nextMove) {
        parsedRequest.getAllowedMoves().remove(nextMove.getMove()); //TODO: check!
    }

    private void sleep() {
        try {
            Thread.currentThread().sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void prepareResponseToSend(HttpServletResponse response) {
        response.setContentType("text/plain");
        response.setHeader("Content-type", "text/plain");
        response.setStatus(200);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    public void startAgent(Integer port, String id) {
        this.port = port;
        this.id = id;
        Server server = new Server(port);
        server.setHandler(this);
        try {
            System.out.println("Starting server on port: " + port.toString());
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getPort() {
        return port;
    }
}