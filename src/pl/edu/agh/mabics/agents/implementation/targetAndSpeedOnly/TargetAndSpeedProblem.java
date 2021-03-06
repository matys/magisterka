package pl.edu.agh.mabics.agents.implementation.targetAndSpeedOnly;

import pl.edu.agh.mabics.agents.implementation.Point3D;
import rlpark.plugin.rltoys.algorithms.functions.states.Projector;
import rlpark.plugin.rltoys.envio.actions.Action;
import rlpark.plugin.rltoys.envio.actions.ActionArray;
import rlpark.plugin.rltoys.envio.observations.Legend;
import rlpark.plugin.rltoys.envio.rl.TRStep;
import rlpark.plugin.rltoys.math.vector.RealVector;
import rlpark.plugin.rltoys.math.vector.implementations.BVector;
import rlpark.plugin.rltoys.problems.ProblemDiscreteAction;

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz
 * Date: 25.03.13
 * Time: 11:03
 */
public class TargetAndSpeedProblem implements ProblemDiscreteAction {

    private Action currentAction;
    private TRStep currentStep;
    private TargetAndSpeedState currentState;
    public static final int AGENT_RANGE = 50; //if collision point is farther, we ignore it - state (-1, -1)
    public static final ActionArray Slower = new ActionArray(-1);
    public static final ActionArray Faster = new ActionArray(1);
    public static final ActionArray theSame = new ActionArray(0);
    static final public Action[] Actions = {Slower, Faster, theSame};
    private boolean endEpisode;

    //action should be go faster/slower or exact values of speed?   some probability to make it nondeterministic
    @Override
    public Action[] actions() {
        return Actions;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public TRStep initialize() {
        System.out.println("initialization of problem");
        TRStep step = new TRStep(new double[]{currentState.getDistanceToTarget()}, currentState.getReward());
        return step;
    }

    @Override
    public TRStep step(Action action) {
        this.currentState = null;
//        System.out.println("Action chosen");
        this.currentAction = action; //can be set to null from outside after being read.
        while (this.currentState == null) {
            try {
                // System.out.println("waiting for state");
                Thread.currentThread().sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
//        System.out.println("action and its state available");
        this.currentAction = null;
        //process current state, check reward
        System.out.println("action " + action.toString());
        System.out.println("state " + currentState.toString());

        if (endEpisode) {
            forceEndEpisode();
        } else {
            currentStep = new TRStep(currentStep, action, new double[]{currentState.getDistanceToTarget()},
                    currentState.getReward());
        }

        return currentStep;

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

    public Point3D size() {
        return new Point3D(AGENT_RANGE, AGENT_RANGE, AGENT_RANGE);
    }

    public void setCurrentState(TargetAndSpeedState targetAndSpeedState) {
        this.currentState = targetAndSpeedState;
    }

    public Action getCurrentAction() {
        return currentAction;
    }

    public void setCurrentAction(Action action) {
        this.currentAction = action;
    }

    @SuppressWarnings("serial")
    public Projector getMarkovProjector() {
        final Point3D size = size();
        final BVector projection = new BVector(size.x);
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
                if (obs != null && obs[0] != -1) projection.setOn((int) obs[0]);
                return projection;
            }
        };
    }

    public void setEndEpisode(boolean endEpisode) {
        this.endEpisode = endEpisode;
    }
}

