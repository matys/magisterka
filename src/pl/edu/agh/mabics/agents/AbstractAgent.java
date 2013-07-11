package pl.edu.agh.mabics.agents;

import org.mortbay.jetty.Request;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.handler.AbstractHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.mabics.agents.implementation.AlgorithmConfigurationBean;
import pl.edu.agh.mabics.agents.implementation.PossibleValue;
import pl.edu.agh.mabics.experiment.controllers.CollisionController;
import pl.edu.agh.mabics.experiment.controllers.EndGameController;
import pl.edu.agh.mabics.experiment.controllers.IGameRunner;
import pl.edu.agh.mabics.experiment.datamodel.AgentStatistics;
import pl.edu.agh.mabics.platform.JSONHelper;
import pl.edu.agh.mabics.platform.model.*;
import pl.edu.agh.mabics.ui.datamodel.beans.IntersectionConfiguration;
import pl.edu.agh.mabics.ui.datamodel.util.Coordinates;
import pl.edu.agh.mabics.util.AgentSite;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

@Service
public abstract class AbstractAgent extends AbstractHandler {

    private JSONHelper jsonHelper;
    private CollisionController collisionController;
    private EndGameController endPointController;
    private IGameRunner gameRunner;
    private int port;
    private String id;
    private AgentStatistics statistics = new AgentStatistics();
    private boolean collision = false;
    private Server server;
    private boolean finished;
    protected IntersectionConfiguration intersectionConfiguration;
    private AgentFactory agentFactory;
    private boolean stopped = false;
    private AlgorithmConfigurationBean algorithmConfigurationBean;
    protected AgentSite agentSite;

    public abstract PlatformResponse getNextMove(PlatformRequest request);

    public abstract void onComplete();

    public abstract void onCollision();

    public void handle(String target, HttpServletRequest request, HttpServletResponse response, int dispatch)
            throws IOException, ServletException {
        if (!stopped) {
            System.out.println("agent " + id + " got request");
            String content = request.getReader().readLine();
            PlatformRequest parsedRequest = jsonHelper.parseRequest(content);
            prepareResponseToSend(response);
            if (endPointController.isAgentInEndPoint(id, parsedRequest.getPosition(), parsedRequest.getDestination())) {
                finished = true;
                onComplete();
            }
            makeMove((Request) request, response, parsedRequest);
            afterStep();
        }
    }

    private void afterStep() {
        updateStatistics();
        informController();
    }

    private void informController() {
        gameRunner.afterAgentStep(statistics);
    }

    private void updateStatistics() {
        if (!finished) {
            if (collision) {
                statistics.numberOfCollisions++;
            }
            statistics.numberOfSteps++;
            collision = false;
        }
    }

    private void makeMove(Request request, HttpServletResponse response, PlatformRequest parsedRequest)
            throws IOException {
        PlatformResponse nextMove = getNextMove(parsedRequest);
        verify(nextMove);
        collisionController.acceptMove(id, nextMove.getMove());
        while (nextMove.getMove().getState().equals(MoveState.NOT_PROCESSED)) {
            sleep();
        }
        if (nextMove.getMove().getState().equals(MoveState.ACCEPTED)) {
            System.out.println("move approved " + id);
            collision = false;
            response.getOutputStream().print(jsonHelper.responseToJSON(nextMove));
            request.setHandled(true);
        } else {
            System.out.println("move not approved " + id);
            collision = true;
            onCollision();
            removeMove(parsedRequest, nextMove);
            sleep(); //to wait as other not accepted agents removes their positions
            System.out.println("finding new move... old one: " + nextMove.getMove().getPoint().toString());
            nextMove.setMove(collisionController.getPossibleMove(nextMove.getMove(), parsedRequest.getPosition(), id));
            System.out.println("new move found... new one: " + nextMove.getMove().getPoint().toString());
            sendFinalMove(nextMove, response, request);
        }
    }

    private void verify(PlatformResponse nextMove) {
        int wantedSpeed = nextMove.getSpeed();
        int maxSpeed = intersectionConfiguration.getMaxSpeed();
        if (wantedSpeed > maxSpeed) {
            System.out.println("WARNING: illegal value of wanted speed: " + " wanted: " + wantedSpeed + " max: " +
                    maxSpeed);
            wantedSpeed = maxSpeed;
        }
        if (wantedSpeed < 0) {
            System.out.println("WARNING: illegal value of wanted speed: " + " wanted: " + wantedSpeed + " min: " +
                    maxSpeed);
            wantedSpeed = 0;
        }
        nextMove.setSpeed(wantedSpeed);
    }

    private void sendFinalMove(PlatformResponse nextMove, HttpServletResponse response, Request request)
            throws IOException {
        while (nextMove.getMove().getState().equals(MoveState.NOT_PROCESSED)) {
//            System.out.println("waiting for all moves");
            sleep();
        }
        if (nextMove.getMove().getState().equals(MoveState.ACCEPTED)) {
            response.getOutputStream().print(jsonHelper.responseToJSON(nextMove));
            request.setHandled(true);
        }
    }


    private void removeMove(PlatformRequest parsedRequest, PlatformResponse nextMove) {
        parsedRequest.getAllowedMoves().remove(nextMove.getMove()); //TODO: check!
    }

    private void sleep() {
        try {
            Thread.currentThread().sleep(100);
        } catch (InterruptedException e) {
            throw new IllegalStateException();
        }
    }

    private void prepareResponseToSend(HttpServletResponse response) {
        response.setContentType("text/plain");
        response.setHeader("Content-type", "text/plain");
        response.setStatus(200);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    public void startAgent(Integer port, String id, AgentSite site) {
        onNextGame();
        boolean portAlreadyInUse;
        do {
            this.port = port;
            this.id = id;
            this.agentSite = site;
            this.finished = false;
            server = new Server(port);
            server.setHandler(this);
            portAlreadyInUse = false;
            try {
                System.out.println("Starting server on port: " + port.toString());
                server.start();
            } catch (java.net.BindException e) {
                portAlreadyInUse = true;
                port = agentFactory.nextPort();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } while (portAlreadyInUse);
        restartStatistics();
    }

    public void restartAgent() {
        onNextGame();
        this.stopped = false;
        this.finished = false;
        restartStatistics();
    }

    protected abstract void onNextGame();


    protected void restartStatistics() {
        statistics = new AgentStatistics();
    }

    public int getPort() {
        return port;
    }

    protected Move getStayInPlaceMove(Coordinates position) {
        return new Move(position, new Vector(0, 0));
    }

    @Autowired
    public void setJsonHelper(JSONHelper jsonHelper) {
        this.jsonHelper = jsonHelper;
    }


    protected void initParameters(Object controller, Class controllerClass) {
        Set<PossibleValue> possibleValues = algorithmConfigurationBean.getPossibleValues();
        if (possibleValues != null && !possibleValues.isEmpty()) {
            for (PossibleValue possibleValue : possibleValues) {
                Method method = BeanUtils
                        .findDeclaredMethod(controllerClass, "set" + capitalizeFirstLetter(possibleValue.getName()),
                                new Class[]{Double.class});
                try {
                    method.invoke(controller, possibleValue.getValue());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    @Autowired
    public void setAlgorithmConfigurationBean(AlgorithmConfigurationBean algorithmConfigurationBean) {
        this.algorithmConfigurationBean = algorithmConfigurationBean;
    }

    private String capitalizeFirstLetter(String name) {
        Character first = Character.toUpperCase(name.charAt(0));
        return first + name.substring(1);
    }


    public void stopAgent() {
        try {
            stopped = true;
//            server.stop();
//            super.stop();
        } catch (Exception e) {
            stopAgent();
        }
    }

    @Autowired
    public void setCollisionController(CollisionController collisionController) {
        this.collisionController = collisionController;
    }

    @Autowired
    public void setEndPointController(EndGameController endPointController) {
        this.endPointController = endPointController;
    }

    @Autowired
    public void setGameRunner(IGameRunner gameRunner) {
        this.gameRunner = gameRunner;
    }

    public String getId() {
        return id;
    }

    public AgentStatistics getStatistics() {
        return statistics;
    }

    @Autowired
    public void setIntersectionConfiguration(IntersectionConfiguration configuration) {
        this.intersectionConfiguration = configuration;
    }

    @Autowired
    public void setAgentFactory(AgentFactory agentFactory) {
        this.agentFactory = agentFactory;
    }

    public AgentSite getAgentSite() {
        return agentSite;
    }
}