package pl.edu.agh.mabics.agents.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.mabics.agents.AbstractAgent;
import pl.edu.agh.mabics.agents.implementation.collisionAvoiding.CollisionAvoidingProblemController;
import pl.edu.agh.mabics.agents.implementation.collisionAvoiding.CollisionAvoidingState;
import pl.edu.agh.mabics.agents.util.MovesFilter;
import pl.edu.agh.mabics.platform.model.PlatformRequest;
import pl.edu.agh.mabics.platform.model.PlatformResponse;
import pl.edu.agh.mabics.platform.model.Vector;
import pl.edu.agh.mabics.util.AgentSite;
import rlpark.plugin.rltoys.envio.actions.Action;
import rlpark.plugin.rltoys.envio.actions.ActionArray;

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz
 * Date: 25.03.13
 * Time: 09:53
 */
@Service
public class AvoidingCollisionsStraightAgent extends AbstractAgent {

    private CollisionAvoidingProblemController collisionAvoidingProblemController;
    private boolean firstCall = true;
    private MovesFilter movesFilter;


    @Override
    public PlatformResponse getNextMove(PlatformRequest request) {
        if (firstCall) {
            while (collisionAvoidingProblemController.getCurrentAction() == null) {
                try {
                    //  System.out.println("waiting for action to be chosen by controller");
                    Thread.currentThread().sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            Action currentAction = collisionAvoidingProblemController.getCurrentAction();
            collisionAvoidingProblemController.setCurrentAction(null);
            firstCall = false;
            return responseFromAction(request, currentAction);
        }
        collisionAvoidingProblemController.onStep();
        System.out.println("setting state");
        collisionAvoidingProblemController
                .setCurrentState(new CollisionAvoidingState(request, collisionAvoidingProblemController.getReward()));
        collisionAvoidingProblemController.resetReward();
        while (collisionAvoidingProblemController.getCurrentAction() == null) {
            try {
                //System.out.println("waiting for action to be chosen by controller 2");
                Thread.currentThread().sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("setting chosen action");
        Action currentAction = collisionAvoidingProblemController.getCurrentAction();
        collisionAvoidingProblemController.setCurrentAction(null);
        return responseFromAction(request, currentAction);
    }

    private PlatformResponse responseFromAction(PlatformRequest request, Action action) {

        filterStraightMoves(request);
        double[] actions = ((ActionArray) action).actions;
        int wantedSpeed = (int) (request.getSpeed() + actions[0]);
        PlatformResponse response = new PlatformResponse();
        response.setSpeed(wantedSpeed);
        if (request.getAllowedMoves().size() == 0) {
            response.setMove(getStayInPlaceMove(request.getPosition()));
            response.setSpeed(0);
        } else {
            response.setMove(request.getAllowedMoves().get(0));//all movements in that physic model should be to the
            // same point
        }
        return response;
    }

    private void filterStraightMoves(PlatformRequest request) {
        Vector desiredVelocity = null;
        if (agentSite == AgentSite.LEFT) {
            desiredVelocity = new Vector(1, 0);
        } else {
            desiredVelocity = new Vector(0, 1);
        }
        movesFilter.filterOutMovesWithVelocityDifferentThan(request, desiredVelocity);
    }

    @Override
    public void onComplete() {
        collisionAvoidingProblemController.onAgentGetsToTarget();
    }

    @Override
    public void onCollision() {
        collisionAvoidingProblemController.onAgentCollision();
    }

    @Override
    protected void onNextGame() {
        //this.firstCall = true;
    }

    public void initIt() {
        collisionAvoidingProblemController = new CollisionAvoidingProblemController(intersectionConfiguration);
        initParameters(collisionAvoidingProblemController, CollisionAvoidingProblemController.class);
        collisionAvoidingProblemController.init();
        Thread controllerThread = new Thread(collisionAvoidingProblemController);
        controllerThread.start();
    }

    @Autowired
    public void setMovesFilter(MovesFilter movesFilter) {
        this.movesFilter = movesFilter;
    }
}
