//package pl.edu.agh.mabics.agents.implementation.ml;
//
//import rlpark.plugin.rltoys.envio.actions.Action;
//import rlpark.plugin.rltoys.envio.observations.Legend;
//import rlpark.plugin.rltoys.envio.rl.TRStep;
//import rlpark.plugin.rltoys.problems.ProblemDiscreteAction;
//
///**
// * Created with IntelliJ IDEA.
// * User: Mateusz
// * Date: 25.03.13
// * Time: 11:03
// */
//public class CollisionAvoidingProblem implements ProblemDiscreteAction {
//
//    public static final int INITIAL_STATE_REWARD = 0;
//    private Action currentAction;
//    private TRStep currentStep;
//
//    //action should be go faster/slower or exact values of speed?   some probabilty to make it indeterministic
//    @Override
//    public Action[] actions() {
//        return new Action[0];  //To change body of implemented methods use File | Settings | File Templates.
//    }
//
//    @Override
//    public TRStep initialize() {
//        TRStep step = new TRStep(new double[]{(distance_from_collision_point, distance_of_collision_agent_to_collision_point)},
//        INITIAL_STATE_REWARD);
//        return step;
//    }
//
//    @Override
//    public TRStep step(Action action) {
//        this.currentState = null;
//        this.currentAction = action; //can be set to null from outside after being read.
//        while (currentState == null) {
//            try {
//                Thread.currentThread().sleep(10);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//        this.currentAction = null;
//        //process current state, check reward
//        currentStep = new TRStep(currentStep, action, new double[]{newN}, reward);
//        return currentStep;
//
//    }
//
//    @Override
//    public TRStep forceEndEpisode() {
//        return null;  //To change body of implemented methods use File | Settings | File Templates.
//    }
//
//    @Override
//    public TRStep lastStep() {
//        return null;  //To change body of implemented methods use File | Settings | File Templates.
//    }
//
//    @Override
//    public Legend legend() {
//        return null;  //To change body of implemented methods use File | Settings | File Templates.
//    }
//}
//
