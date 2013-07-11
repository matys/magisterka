package pl.edu.agh.mabics.agents.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.mabics.agents.AbstractAgent;
import pl.edu.agh.mabics.agents.implementation.targetAndSpeedOnly.TargetAndSpeedProblemController;
import pl.edu.agh.mabics.agents.implementation.targetAndSpeedOnly.TargetAndSpeedState;
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
public class TargetFocusedStraightAgent extends AbstractAgent {

    private TargetAndSpeedProblemController targetAndSpeedProblemController;
    private boolean firstCall = true;
    private MovesFilter movesFilter;

    @Override
    public PlatformResponse getNextMove(PlatformRequest request) {
        if (firstCall) {
            while (targetAndSpeedProblemController.getCurrentAction() == null) {
                try {
                    //  System.out.println("waiting for action to be chosen by controller");
                    Thread.currentThread().sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            Action currentAction = targetAndSpeedProblemController.getCurrentAction();
            targetAndSpeedProblemController.setCurrentAction(null);
            firstCall = false;
            return responseFromAction(request, currentAction);
        }
        targetAndSpeedProblemController.onStep();
        System.out.println("setting state");
        targetAndSpeedProblemController
                .setCurrentState(new TargetAndSpeedState(request, targetAndSpeedProblemController.getReward()));
        targetAndSpeedProblemController.resetReward();
        while (targetAndSpeedProblemController.getCurrentAction() == null) {
            try {
                //System.out.println("waiting for action to be chosen by controller 2");
                Thread.currentThread().sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("setting chosen action");
        Action currentAction = targetAndSpeedProblemController.getCurrentAction();
        targetAndSpeedProblemController.setCurrentAction(null);
        return responseFromAction(request, currentAction);
    }

    //TODO: refactor, generalization needed
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
        targetAndSpeedProblemController.onAgentGetsToTarget();
    }

    @Override
    public void onCollision() {
        targetAndSpeedProblemController.onAgentCollision();
    }

    @Override
    protected void onNextGame() {
        //this.firstCall = true;
    }

    public void initIt() {
        targetAndSpeedProblemController = new TargetAndSpeedProblemController();
        initParameters(targetAndSpeedProblemController, TargetAndSpeedProblemController.class);
        targetAndSpeedProblemController.init();
        Thread controllerThread = new Thread(targetAndSpeedProblemController);
        controllerThread.start();
    }

    @Autowired
    public void setMovesFilter(MovesFilter movesFilter) {
        this.movesFilter = movesFilter;
    }
}
