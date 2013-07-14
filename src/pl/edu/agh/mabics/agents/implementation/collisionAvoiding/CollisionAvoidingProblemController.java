package pl.edu.agh.mabics.agents.implementation.collisionAvoiding;

import pl.edu.agh.mabics.ui.datamodel.beans.IntersectionConfiguration;
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
public class CollisionAvoidingProblemController implements Runnable {
    private CollisionAvoidingProblem problem = null;
    private ControlLearner control;
    private Clock clock = new Clock("CollisionAvoidingProblemController");
    private Projector projector;
    private PVector occupancy;
    private LearnerAgentFA agent;
    private double reward = 0;

    private QLearning qlearning;
    private IntersectionConfiguration intersectionConfiguration;

    /*
    configurable parameters
     */ double epsilon = 0.2;        //how many actions should be chosen on random (instead of taking best)
    double gamma = 1.0;                              //discount factor  (how important is future value)
    double lambda = 0.9;                             //how much less important is every next step action (lambda *
    // rt+1 + lambda^2 rt+2 + lambda^3 rt+3...
    double alpha = .45;   //learning rate
    private double gettingToTargetReward = 100;
    private double collisionReward = -100;
    private double stepReward = -20;

    public CollisionAvoidingProblemController(IntersectionConfiguration intersectionConfiguration) {
        this.intersectionConfiguration = intersectionConfiguration;
    }


    public void init() {
        problem = new CollisionAvoidingProblem(intersectionConfiguration.getAgentRange(),
                intersectionConfiguration.getMaxSpeedChange());
        problem.setCurrentState(new CollisionAvoidingState());
        problem.initialize();
        projector = problem.getMarkovProjector();
        occupancy = new PVector(projector.vectorSize());
        TabularAction toStateAction = new TabularAction(problem.actions(), projector.vectorNorm(),
                projector.vectorSize());

        alpha = alpha / projector.vectorNorm();
        qlearning = new QLearning(problem.actions(), alpha, gamma, lambda, toStateAction, new RTraces());

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
//            printTheta();
            occupancy.addToSelf(agent.lastState()); //????
        }
    }

    public void setCurrentState(CollisionAvoidingState collisionAvoidingState) {
        problem.setCurrentState(collisionAvoidingState);
    }

    public void onAgentGetsToTarget() {
        reward = reward + gettingToTargetReward;
        problem.setEndEpisode(true);
    }

    public void resetReward() {
        reward = 0;
    }

    public void onAgentCollision() {
        reward = reward + collisionReward;
    }

    public void onStep() {
        reward = reward + stepReward;
    }

    public Action getCurrentAction() {
        return problem.getCurrentAction();
    }

    public void setCurrentAction(Action action) {
        problem.setCurrentAction(action);
    }

    public int getReward() {
        return (int) reward;
    }

    //TODO: debug method only, generalization needed if more important role needed
    private void printTheta() {
        System.out.println("Action -1");
        int agentRange = intersectionConfiguration.getAgentRange();
        int statesQuantityPerAction = agentRange * agentRange * agentRange;
        for (int i = 0; i < statesQuantityPerAction; i++) {
            System.out.println(qlearning.theta().getEntry(i));
        }
        System.out.println("Action 0");
        for (int i = 0; i < statesQuantityPerAction; i++) {
            System.out.println(qlearning.theta().getEntry(i + statesQuantityPerAction));
        }

        System.out.println("Action 1");
        for (int i = 0; i < statesQuantityPerAction; i++) {
            System.out.println(qlearning.theta().getEntry(i + statesQuantityPerAction * 2));
        }
    }

    public void setEpsilon(Double epsilon) {
        this.epsilon = epsilon;
    }

    public void setGamma(Double gamma) {
        this.gamma = gamma;
    }

    public void setLambda(Double lambda) {
        this.lambda = lambda;
    }

    public void setAlpha(Double alpha) {
        this.alpha = alpha;
    }

    public void setGettingToTargetReward(Double gettingToTargetReward) {
        this.gettingToTargetReward = gettingToTargetReward;
    }

    public void setCollisionReward(Double collisionReward) {
        this.collisionReward = collisionReward;
    }

    public void setStepReward(Double stepReward) {
        this.stepReward = stepReward;
    }
}
