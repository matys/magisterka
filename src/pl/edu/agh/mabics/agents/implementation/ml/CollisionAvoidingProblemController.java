//package pl.edu.agh.mabics.agents.implementation.ml;
//
//import rlpark.plugin.rltoys.agents.rl.LearnerAgentFA;
//import rlpark.plugin.rltoys.algorithms.control.ControlLearner;
//import rlpark.plugin.rltoys.algorithms.control.acting.EpsilonGreedy;
//import rlpark.plugin.rltoys.algorithms.control.qlearning.QLearning;
//import rlpark.plugin.rltoys.algorithms.control.qlearning.QLearningControl;
//import rlpark.plugin.rltoys.algorithms.functions.stateactions.TabularAction;
//import rlpark.plugin.rltoys.algorithms.functions.states.Projector;
//import rlpark.plugin.rltoys.algorithms.traces.RTraces;
//import rlpark.plugin.rltoys.envio.policy.Policy;
//import rlpark.plugin.rltoys.experiments.helpers.Runner;
//import rlpark.plugin.rltoys.experiments.helpers.Runner.RunnerEvent;
//import rlpark.plugin.rltoys.math.vector.implementations.PVector;
//import zephyr.plugin.core.api.Zephyr;
//import zephyr.plugin.core.api.signals.Listener;
//import zephyr.plugin.core.api.synchronization.Clock;
//
//import java.util.Random;
//
///**
// * Created with IntelliJ IDEA.
// * User: Mateusz
// * Date: 25.03.13
// * Time: 11:12
// */
//public class CollisionAvoidingProblemController implements Runnable {
//    private  CollisionAvoidingProblem problem = null;//Problems.createProblem(100000);
//    private  ControlLearner control;
//    private  Clock clock = new Clock("Main");
//    private  Projector projector;
//    private  PVector occupancy;
//    private  LearnerAgentFA agent;
//
//    public void init() {
//        projector = problem.getMarkovProjector();
//        occupancy = new PVector(projector.vectorSize());
//        TabularAction toStateAction = new TabularAction(problem.actions(),
//                projector.vectorNorm(), projector.vectorSize());
//        double alpha = .15 / projector.vectorNorm();
//        double gamma = 1.0;
//        double lambda = 0.6;
//        QLearning qlearning = new QLearning(problem.actions(), alpha, gamma,
//                lambda, toStateAction, new RTraces());
//        double epsilon = 0.2;
//        Policy acting = new EpsilonGreedy(new Random(0), problem.actions(),
//                toStateAction, qlearning, epsilon);
//        control = new QLearningControl(acting, qlearning);
//        agent = new LearnerAgentFA(control, projector);
//        Zephyr.advertise(clock, this);
//    }
//
//    @Override
//    public void run() {
//        Runner runner = new Runner(problem, agent);
//        runner.onEpisodeEnd.connect(new Listener<Runner.RunnerEvent>() {
//            @Override
//            public void listen(RunnerEvent eventInfo) {
//                System.out.println(String.format("Episode %d: %d steps",
//                        eventInfo.nbEpisodeDone, eventInfo.step.time));
//            }
//        });
//        while (clock.tick()) {
//            runner.step();
//            occupancy.addToSelf(agent.lastState());
//        }
//    }
//}
