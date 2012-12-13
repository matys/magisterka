package pl.edu.agh.mabics.agents;

import org.mortbay.jetty.Request;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.handler.AbstractHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.mabics.platform.JSONHelper;
import pl.edu.agh.mabics.platform.model.PlatformRequest;
import pl.edu.agh.mabics.platform.model.PlatformResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
public abstract class AbstractAgent extends AbstractHandler {

    private JSONHelper jsonHelper;

    @Autowired
    public void setJsonHelper(JSONHelper jsonHelper) {
        this.jsonHelper = jsonHelper;
    }

    public abstract PlatformResponse getNextMove(PlatformRequest request);

    public void handle(String target, HttpServletRequest request,
                       HttpServletResponse response, int dispatch) throws IOException,
            ServletException {
        String content = request.getReader().readLine();
        PlatformRequest parsedRequest = jsonHelper.parseRequest(content);
        prepareResponseToSend(response);
        PlatformResponse nextMove = getNextMove(parsedRequest);
        response.getOutputStream().print(jsonHelper.responseToJSON(nextMove));
        ((Request) request).setHandled(true);
    }

    private void prepareResponseToSend(HttpServletResponse response) {
        response.setContentType("text/plain");
        response.setHeader("Content-type", "text/plain");
        response.setStatus(200);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    public void startAgent(Integer port) {
        Server server = new Server(port);
        server.setHandler(this);
        try {
            System.out.println("Starting server on port: " + port.toString());
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}