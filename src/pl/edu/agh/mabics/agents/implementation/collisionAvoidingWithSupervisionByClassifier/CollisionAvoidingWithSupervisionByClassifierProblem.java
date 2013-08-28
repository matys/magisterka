package pl.edu.agh.mabics.agents.implementation.collisionAvoidingWithSupervisionByClassifier;

import pl.edu.agh.mabics.agents.implementation.classifying.*;
import rlpark.plugin.rltoys.algorithms.functions.states.Projector;
import rlpark.plugin.rltoys.envio.actions.Action;
import rlpark.plugin.rltoys.envio.actions.ActionArray;
import rlpark.plugin.rltoys.envio.observations.Legend;
import rlpark.plugin.rltoys.envio.rl.TRStep;
import rlpark.plugin.rltoys.math.vector.RealVector;
import rlpark.plugin.rltoys.math.vector.implementations.BVector;
import rlpark.plugin.rltoys.problems.ProblemDiscreteAction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz
 * Date: 25.03.13
 * Time: 11:03
 */
/*
TODO: generalization needed - this class is exactly the same as CollisionAvoidingWithClassificationProblem
use spring conf to do that
*/

public class CollisionAvoidingWithSupervisionByClassifierProblem implements ProblemDiscreteAction {

    public static final int UPDATE_FREQUENCY_IN_NBR_OF_EXAMPLES = 100;
    public static final Class<PositiveNegativeReducedStates> REDUCED_STATES_CLASS = PositiveNegativeReducedStates.class;
    private Action currentAction;
    private TRStep currentStep;
    private CollisionAvoidingWithSupervisionByClassifierState currentState;
    public static int AGENT_RANGE; //if collision point is farther, we ignore it
    List<Action> actions = new ArrayList<Action>();
    private boolean endEpisode;
    private MyClassifier SLClassifier;
    private MyClassifier RLClassifier;
    private boolean collisionHappened = false;
    private Integer gameCounter = 0;

    public CollisionAvoidingWithSupervisionByClassifierProblem(Integer agentRange, Integer maxSpeedChange) {
        SLClassifier = new MyClassifier(REDUCED_STATES_CLASS, WekaClassifiers.C45,
                new PositiveNegativeStateComparator(getNegativeStatePriority(0)),
                CollisionAvoidingWithSupervisionByClassifierState.NUMBER_OF_STATE_ATTRIBUTES_TO_REDUCE + 1,
                //one for action
                UPDATE_FREQUENCY_IN_NBR_OF_EXAMPLES);
        RLClassifier = new MyClassifier(REDUCED_STATES_CLASS, WekaClassifiers.C45,
                new PositiveNegativeStateComparator(getNegativeStatePriority(0)),
                CollisionAvoidingWithSupervisionByClassifierState.NUMBER_OF_STATE_ATTRIBUTES_TO_REDUCE,
                //one for action
                UPDATE_FREQUENCY_IN_NBR_OF_EXAMPLES);
        this.AGENT_RANGE = agentRange;
        for (int i = -maxSpeedChange; i <= maxSpeedChange; i++) {
            actions.add(new ActionArray(i));
        }
    }

    //action should be go faster/slower or exact values of speed?   some probability to make it indeterministic
    @Override
    public Action[] actions() {
        Action actionsArray[] = new Action[actions.size()];
        for (int i = 0; i < actions.size(); i++) {
            actionsArray[i] = actions.get(i);
        }
        return actionsArray;

    }

    @Override
    public TRStep initialize() {
        System.out.println("initialization of problem");
        gameCounter = 0;
        return new TRStep(new double[]{currentState.getDistanceToTarget(), getReducedState()},
                currentState.getReward());
    }

    @Override
    public TRStep step(Action action) {
        SLClassifier.setComparator(new PositiveNegativeStateComparator(getNegativeStatePriority(gameCounter++)));
        this.currentAction = superviseAction(this.currentState, action); //can be set to null from outside after being
        SLClassifier.addState(concatActionToState(currentState.getReducableState(), this.currentAction));
        this.currentState = null;
        System.out.println("Action chosen");
        // read.
        while (this.currentState == null) {
            try {
//                System.out.println("waiting for state");
                Thread.currentThread().sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        RLClassifier.addState(currentState.getReducableState());
        System.out.println("action and its state available");
        this.currentAction = null;
        //process current state, check reward
        System.out.println("action " + action.toString());
        System.out.println("state " + currentState.toString());

        if (collisionHappened) {
            SLClassifier.usePreviousExamplesAs(PositiveNegativeReducedStates.NEGATIVE);
            RLClassifier.usePreviousExamplesAs(PositiveNegativeReducedStates.NEGATIVE);
            System.out.println("Teaching as negative examples on collision");
            collisionHappened = false;
        }

        if (endEpisode) {
            System.out.println("Teaching as positive examples on the end of game");
            SLClassifier.usePreviousExamplesAs(PositiveNegativeReducedStates.POSITIVE);
            RLClassifier.usePreviousExamplesAs(PositiveNegativeReducedStates.POSITIVE);
            forceEndEpisode();
        } else {
            currentStep = new TRStep(currentStep, action,
                    new double[]{currentState.getDistanceToTarget(), getReducedState()}, currentState.getReward());
        }
        return currentStep;

    }

    private Action superviseAction(CollisionAvoidingWithSupervisionByClassifierState currentState, Action action) {
        PositiveNegativeReducedStates supervisorDecision;
        try {
            supervisorDecision = (PositiveNegativeReducedStates) SLClassifier
                    .reduce(concatActionToState(currentState.getReducableState(), this.currentAction));
        } catch (MyClassifier.ClassifierException e) {
            supervisorDecision = PositiveNegativeReducedStates.POSITIVE;
        }
        switch (supervisorDecision) {
            case POSITIVE:
                return action;
            case NEGATIVE:
                return chooseTheBestActionAccordingToSupervisor(currentState);
            default:
                return action;
        }
    }

    private Action chooseTheBestActionAccordingToSupervisor(CollisionAvoidingWithSupervisionByClassifierState state) {
        List<Double[]> possibleDecisions = new ArrayList<Double[]>();
        for (Action action : actions()) {
            possibleDecisions.add(concatActionToState(state.getReducableState(), action));
        }
        try {
            return new ActionArray(
                    SLClassifier.chooseTheBest(possibleDecisions, PositiveNegativeReducedStates.POSITIVE)[state
                            .getReducableState().length]);
        } catch (Exception e) {   //cannot happen because if we are here, we already had to use classifier
            e.printStackTrace();
            return null;
        }
    }

    private Double[] concatActionToState(Double[] state, Action currentAction) {
        Double[] stateWithAction = new Double[state.length + 1];
        for (int i = 0; i < state.length; i++) {
            stateWithAction[i] = state[i];
        }
        if (currentAction != null) {
            stateWithAction[state.length] = ((ActionArray) currentAction).actions[0];
        } else {
            stateWithAction[state.length] = 0.0;
        }
        return stateWithAction;
    }

    private double getReducedState() {
        Double[] reducableState = currentState.getReducableState();
        IReducedStatesEnum reducedState;
        try {
            reducedState = RLClassifier.reduce(reducableState);
            System.out.println(reducedState.getStringRepresentation());
        } catch (MyClassifier.ClassifierException e) {
            reducedState = EnumHelper.getRandomValue(REDUCED_STATES_CLASS);
        }
        return reducedState.getDoubleRepresentation();
    }

    @Override
    public TRStep forceEndEpisode() {
        endEpisode = false;
        currentStep = currentStep.createEndingStep();
        return currentStep;
    }

    @Override
    public TRStep lastStep() {
        return currentStep;
    }


    @Override
    public Legend legend() {
        return null;
    }

    public Integer size() {
        return AGENT_RANGE;
    }

    public void setCurrentState(CollisionAvoidingWithSupervisionByClassifierState collisionAvoidingState) {
        this.currentState = collisionAvoidingState;
    }

    public Action getCurrentAction() {
        return currentAction;
    }

    public void setCurrentAction(Action action) {
        this.currentAction = action;
    }

    @SuppressWarnings("serial")
    public Projector getMarkovProjector() {
        final Integer size = size();
        final BVector projection = new BVector(size);
        return new Projector() {
            @Override
            public int vectorSize() {
                return projection.size;
            }

            @Override
            public double vectorNorm() {
                return 1;
            }

            @Override
            public RealVector project(double[] obs) {
                projection.clear();
                if (obs != null && obs[0] != -1) projection.setOn((int) (obs[0]));
                return projection;
            }
        };
    }

    public void setEndEpisode(boolean endEpisode) {
        this.endEpisode = endEpisode;
    }

    public void collisionHappened() {
        this.collisionHappened = true;
    }

    public void resetExperienceFromLastGame() {
        SLClassifier.removeNotClassifiedExamples();
    }


    public double getNegativeStatePriority(Integer gameCounter) {
        if (gameCounter < 10) {
            return 1;
        } else if (gameCounter < 200) {
            return 2;
        } else if (gameCounter < 400) {
            return 3;
        } else if (gameCounter < 600) {
            return 4;
        } else {
            return 5;
        }
    }
}

