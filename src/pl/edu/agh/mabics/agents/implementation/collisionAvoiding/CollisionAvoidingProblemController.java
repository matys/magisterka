package pl.edu.agh.mabics.agents.implementation.collisionAvoiding;

import org.springframework.stereotype.Service;
import rlpark.plugin.rltoys.agents.rl.LearnerAgentFA;
import rlpark.plugin.rltoys.algorithms.control.ControlLearner;
import rlpark.plugin.rltoys.algorithms.control.acting.EpsilonGreedy;
import rlpark.plugin.rltoys.algorithms.control.qlearning.QLearning;
import rlpark.plugin.rltoys.algorithms.control.qlearning.QLearningControl;
import rlpark.plugin.rltoys.algorithms.functions.stateactions.TabularAction;
import rlpark.plugin.rltoys.algorithms.functions.states.Projector;
import rlpark.plugin.rltoys.algorithms.traces.RTraces;
import rlpark.plugin.rltoys.envio.actions.Action;
import rlpark.plugin.rltoys.envio.policy.Policy;
import rlpark.plugin.rltoys.experiments.helpers.Runner;
import rlpark.plugin.rltoys.experiments.helpers.Runner.RunnerEvent;
import rlpark.plugin.rltoys.math.vector.implementations.PVector;
import zephyr.plugin.core.api.Zephyr;
import zephyr.plugin.core.api.signals.Listener;
import zephyr.plugin.core.api.synchronization.Clock;

import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz
 * Date: 25.03.13
 * Time: 11:12
 */
@Service
public class CollisionAvoidingProblemController implements Runnable {
    private CollisionAvoidingProblem problem = null;
    private ControlLearner control;
    private Clock clock = new Clock("CollisionAvoidingProblemController");
    private Projector projector;
    private PVector occupancy;
    private LearnerAgentFA agent;
    private int reward = 0;
    private static final int GETTING_TARGET_REWARD = 100;
    private static final int COLLISION_REWARD = -100;
    private static final int STEP_REWARD = -10;


    public void init() {
        problem = new CollisionAvoidingProblem();
        problem.setCurrentState(new CollisionAvoidingState());
        problem.initialize();
        projector = problem.getMarkovProjector();
        occupancy = new PVector(projector.vectorSize());
        TabularAction toStateAction = new TabularAction(problem.actions(), projector.vectorNorm(),
                projector.vectorSize());
        double alpha = .15 / projector.vectorNorm();     //learning rate
        double gamma = 1.0;                              //discount factor  (how important is future value)
        double lambda = 0.6;                             //how much less important is every next step action (lambda *
        // rt+1 + lambda^2 rt+2 + lambda^3 rt+3...
        QLearning qlearning = new QLearning(problem.actions(), alpha, gamma, lambda, toStateAction, new RTraces());
        double epsilon = 0.2;        //how many actions should be chosen on random (instead of taking best)
        Policy acting = new EpsilonGreedy(new Random(), problem.actions(), toStateAction, qlearning, epsilon);
        control = new QLearningControl(acting, qlearning);
        agent = new LearnerAgentFA(control, projector);
        Zephyr.advertise(clock, this);
    }

    @Override
    public void run() {
        Runner runner = new Runner(problem, agent);
        runner.onEpisodeEnd.connect(new Listener<Runner.RunnerEvent>() {
            @Override
            public void listen(RunnerEvent eventInfo) {
                System.out.println(String.format("Episode %d: %d steps", eventInfo.nbEpisodeDone, eventInfo.step.time));
            }
        });
        while (clock.tick()) {
            runner.step();
            occupancy.addToSelf(agent.lastState()); //????
        }
    }

    public void setCurrentState(CollisionAvoidingState collisionAvoidingState) {
        problem.setCurrentState(collisionAvoidingState);
    }

    public void onAgentGetsToTarget() {
        reward = reward + GETTING_TARGET_REWARD;
    }

    public void resetReward() {
        reward = 0;
    }

    public void onAgentCollision() {
        reward = reward + COLLISION_REWARD;
    }

    public void onStep() {
        reward = reward + STEP_REWARD;
    }

    public Action getCurrentAction() {
        return problem.getCurrentAction();
    }

    public void setCurrentAction(Action action) {
        problem.setCurrentAction(action);
    }

    public int getReward() {
        return reward;
    }
}
