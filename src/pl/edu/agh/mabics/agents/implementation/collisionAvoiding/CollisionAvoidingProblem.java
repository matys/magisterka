package pl.edu.agh.mabics.agents.implementation.collisionAvoiding;

import pl.edu.agh.mabics.agents.implementation.Point3D;
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
public class CollisionAvoidingProblem implements ProblemDiscreteAction {

    private Action currentAction;
    private TRStep currentStep;
    private CollisionAvoidingState currentState;
    public static int AGENT_RANGE; //if collision point is farther, we ignore it
    List<Action> actions = new ArrayList<Action>();
    private boolean endEpisode;

    public CollisionAvoidingProblem(Integer agentRange, Integer maxSpeedChange) {
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
        TRStep step = new TRStep(new double[]{currentState.getDistanceToTarget(), currentState
                .getAgentDistanceToCollisionPoint(), currentState.getCollisionAgentDistanceToCollisionPoint()},
                currentState.getReward());
        return step;
    }

    @Override
    public TRStep step(Action action) {
        this.currentState = null;
        System.out.println("Action chosen");
        this.currentAction = action; //can be set to null from outside after being read.
        while (this.currentState == null) {
            try {
                System.out.println("waiting for state");
                Thread.currentThread().sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("action and its state available");
        this.currentAction = null;
        //process current state, check reward
        System.out.println("action " + action.toString());
        System.out.println("state " + currentState.toString());

        if (endEpisode) {
            forceEndEpisode();
        } else {
            currentStep = new TRStep(currentStep, action, new double[]{currentState.getDistanceToTarget(), currentState
                    .getAgentDistanceToCollisionPoint(), currentState.getCollisionAgentDistanceToCollisionPoint()},
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

    public void setCurrentState(CollisionAvoidingState collisionAvoidingState) {
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
        final Point3D size = size();
        final BVector projection = new BVector(size.x * size.y * size.z);
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
                if (obs != null && obs[0] != -1 && obs[1] != -1 && obs[2] != -1) projection.setOn((int) (obs[0] *
                        size.y * size.z +
                        obs[1] * size.y + obs[2]));
                return projection;
            }
        };
    }

    public void setEndEpisode(boolean endEpisode) {
        this.endEpisode = endEpisode;
    }
}

