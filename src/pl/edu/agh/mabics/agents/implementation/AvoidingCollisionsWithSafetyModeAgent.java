package pl.edu.agh.mabics.agents.implementation;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.mabics.agents.AbstractAgent;
import pl.edu.agh.mabics.agents.implementation.classifying.*;
import pl.edu.agh.mabics.agents.implementation.collisionAvoidingWithSafetyMode.CollisionAvoidingWithSafetyModeState;
import pl.edu.agh.mabics.agents.implementation.collisionAvoidingWithSpeedClassification.CollisionAvoidingWithSpeedClassificationProblemController;
import pl.edu.agh.mabics.agents.implementation.collisionAvoidingWithSpeedClassification.CollisionAvoidingWithSpeedClassificationState;
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
public class AvoidingCollisionsWithSafetyModeAgent extends AbstractAgent {

    private CollisionAvoidingWithSpeedClassificationProblemController problemController;
    private boolean firstCall = true;
    private MovesFilter movesFilter;
    private MyClassifier classifier;
    private static final Integer CLASSIFIER_UPDATE_FREQUENCY = 100;
    private Integer NEGATIVE_STATE_PRIORITY = 2;
    private Action GO_FASTER_ACTION = new ActionArray(1);
    private Action GO_SLOWER_ACTION = new ActionArray(-1);
    private int gameCounter = 0;
    private int stepCounter = 0;


    @Override
    public PlatformResponse getNextMove(PlatformRequest request) {
        classifier.setComparator(new PositiveNegativeStateComparator(getNegativeStatePriority()));
        CollisionAvoidingWithSpeedClassificationState state = new CollisionAvoidingWithSpeedClassificationState(request,
                0);

        if (stepCounter > 2) {
            // because first state always the same - doesn't influence future
            classifier.addState(state.getReducableState()); //only if not first state,
        }
        stepCounter++;
        PositiveNegativeReducedStates reducedState = null;
//        if (state.isBeginningState()) {
//            reducedState = PositiveNegativeReducedStates.POSITIVE;
//        } else {
        try {
            reducedState = (PositiveNegativeReducedStates) classifier.reduce(state.getReducableState());
        } catch (MyClassifier.ClassifierException e) {
            reducedState = (PositiveNegativeReducedStates) EnumHelper
                    .getRandomValue(PositiveNegativeReducedStates.class);
        }
//        }
        System.out.println(super.getAgentId());
        System.out.println(state.toString());
        System.out.println(reducedState);
        switch (reducedState) {
            case POSITIVE:
//               return generateNextStepUsingRL(request);
                return generateFasterMove(request);
            case NEGATIVE:
                return generateSlowerMove(request);
            default:
                return generateNextStepUsingRL(request);
        }
    }

    private PlatformResponse generateSlowerMove(PlatformRequest request) {
        return responseFromAction(request, GO_SLOWER_ACTION);
    }

    private PlatformResponse generateFasterMove(PlatformRequest request) {
        return responseFromAction(request, GO_FASTER_ACTION);
    }

    @Deprecated // becuase we cannot just call this method to get response, we need also to give reward in NEXT step!
    //we should keep in here as field next reward after that state and use it in next call
    private PlatformResponse generateNextStepUsingRL(PlatformRequest request) {
        if (firstCall) {
            while (problemController.getCurrentAction() == null) {
                try {
//                    System.out.println("waiting for action to be chosen by controller (first time)");
                    Thread.currentThread().sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            Action currentAction = problemController.getCurrentAction();
            problemController.setCurrentAction(null);
            firstCall = false;
            return responseFromAction(request, currentAction);
        }
        problemController.onStep();
        System.out.println("setting state");
        problemController.setCurrentState(
                new CollisionAvoidingWithSpeedClassificationState(request, problemController.getReward()));
        problemController.resetReward();
        while (problemController.getCurrentAction() == null) {
            try {
//                System.out.println("waiting for action to be chosen by controller");
                Thread.currentThread().sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("setting chosen action");
        Action currentAction = problemController.getCurrentAction();
        problemController.setCurrentAction(null);
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
        stepCounter = 0;
        problemController.onAgentGetsToTarget();
        classifier.usePreviousExamplesAs(PositiveNegativeReducedStates.POSITIVE);
    }

    @Override
    public void onCollision() {
        classifier.usePreviousExamplesAs(PositiveNegativeReducedStates.NEGATIVE);
        problemController.onAgentCollision();
    }

    @Override
    protected void onRestartBecausePlatformHanged() {
        stepCounter = 0;
        classifier.removeNotClassifiedExamples();
        problemController.resetExperienceFromLastGame();
    }

    public void initIt() {
        problemController = new CollisionAvoidingWithSpeedClassificationProblemController(intersectionConfiguration);
        initParameters(problemController, CollisionAvoidingWithSpeedClassificationProblemController.class);
        problemController.init();
        Thread controllerThread = new Thread(problemController);
        controllerThread.start();
        gameCounter = 0;
        stepCounter = 0;
        classifier = new MyClassifier(PositiveNegativeReducedStates.class, WekaClassifiers.C45,
                new PositiveNegativeStateComparator(NEGATIVE_STATE_PRIORITY),
                CollisionAvoidingWithSafetyModeState.NUMBER_OF_STATE_ATTRIBUTES_TO_REDUCE, CLASSIFIER_UPDATE_FREQUENCY);
    }

    @Override
    protected void onNextGame() {
        this.firstCall = true;
        stepCounter = 0;
    }

    @Autowired
    public void setMovesFilter(MovesFilter movesFilter) {
        this.movesFilter = movesFilter;
    }

    public double getNegativeStatePriority() {
        if (gameCounter < 100) {
            return 1;
        } else if (gameCounter < 300) {
            return 2;
        } else {
            return 3;
        }
    }
}
