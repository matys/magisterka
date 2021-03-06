package pl.edu.agh.mabics.agents.implementation.collisionAvoidingWithClassification;

import pl.edu.agh.mabics.agents.implementation.Point2D;
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
public class CollisionAvoidingWithClassificationProblem implements ProblemDiscreteAction {

    public static final int UPDATE_FREQUENCY_IN_NBR_OF_EXAMPLES = 10;
    public static final Class<PositiveNegativeReducedStates> REDUCED_STATES_CLASS = PositiveNegativeReducedStates.class;
    public static final int NEGATIVE_STATE_PRIORITY = 5;
    private Action currentAction;
    private TRStep currentStep;
    private CollisionAvoidingWithClassificationState currentState;
    public static int AGENT_RANGE; //if collision point is farther, we ignore it
    List<Action> actions = new ArrayList<Action>();
    private boolean endEpisode;
    private MyClassifier classifier;
    private boolean collisionHappened = false;

    public CollisionAvoidingWithClassificationProblem(Integer agentRange, Integer maxSpeedChange) {
        classifier = new MyClassifier(REDUCED_STATES_CLASS, WekaClassifiers.C45,
                new PositiveNegativeStateComparator(NEGATIVE_STATE_PRIORITY),
                CollisionAvoidingWithClassificationState.NUMBER_OF_STATE_ATTRIBUTES_TO_REDUCE,
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
        return new TRStep(new double[]{currentState.getDistanceToTarget(), getReducedState()},
                currentState.getReward());

    }

    @Override
    public TRStep step(Action action) {
        this.currentState = null;
        System.out.println("Action chosen");
        this.currentAction = action; //can be set to null from outside after being read.
        while (this.currentState == null) {
            try {
//                System.out.println("waiting for state");
                Thread.currentThread().sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        classifier.addState(currentState.getReducableState());
        System.out.println("action and its state available");
        this.currentAction = null;
        //process current state, check reward
        System.out.println("action " + action.toString());
        System.out.println("state " + currentState.toString());

        if (collisionHappened) {
            classifier.usePreviousExamplesAs(PositiveNegativeReducedStates.NEGATIVE);
            System.out.println("Teaching as negative examples on collision");
            collisionHappened = false;
        }

        if (endEpisode) {
            System.out.println("Teaching as positive examples on the end of game");
            classifier.usePreviousExamplesAs(PositiveNegativeReducedStates.POSITIVE);
            forceEndEpisode();
        } else {
            currentStep = new TRStep(currentStep, action,
                    new double[]{currentState.getDistanceToTarget(), getReducedState()}, currentState.getReward());
        }
        return currentStep;

    }

    private double getReducedState() {
        Double[] reducableState = currentState.getReducableState();
        IReducedStatesEnum reducedState;
        try {
            reducedState = classifier.reduce(reducableState);
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

    public Point2D size() {
        return new Point2D(AGENT_RANGE, CollisionAvoidingWithClassificationState.NUMBER_OF_STATE_ATTRIBUTES_TO_REDUCE);
    }

    public void setCurrentState(CollisionAvoidingWithClassificationState collisionAvoidingState) {
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
        final Point2D size = size();
        final BVector projection = new BVector(size.x * size.y);
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
                if (obs != null && obs[0] != -1 && obs[1] != -1) projection.setOn((int) (obs[0] + obs[1] * size.x));
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
        classifier.removeNotClassifiedExamples();
    }
}

