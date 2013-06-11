package pl.edu.agh.mabics.agents.implementation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz
 * Date: 18.02.13
 * Time: 19:30
 */
public enum AgentType {

    //TODO add more parameters as Rewards, etc..
    RANDOM("randomly choose move", "randomAgent", new ArrayList<AlgorithmParameter>()),
    STRAIGHT("going straight forward", "straightAgent", new ArrayList<AlgorithmParameter>()),
    AVOIDING_COLLISION_STRAIGHT("going straight forward and avoiding collisions", "avoidingCollisionsStraightAgent",
            initAvoidingCollisionStraightParameters()),
    TARGET_AND_SPEED("going straight forward, " + "avoiding taking distance to target into consideration",
            "targetFocusedStraightAgent", initTargetAndSpeedParamters());

    private static List<AlgorithmParameter> initTargetAndSpeedParamters() {
        List<AlgorithmParameter> parameters = new ArrayList<AlgorithmParameter>();
        parameters.add(new AlgorithmParameter("alpha", 0.45));
        parameters.add(new AlgorithmParameter("gamma", 1.0));
        parameters.add(new AlgorithmParameter("lambda", 0.9));
        parameters.add(new AlgorithmParameter("epsilon", 0.2));
        return parameters;
    }

    private static List<AlgorithmParameter> initAvoidingCollisionStraightParameters() {
        List<AlgorithmParameter> parameters = new ArrayList<AlgorithmParameter>();
        parameters.add(new AlgorithmParameter("alpha", 0.45));
        parameters.add(new AlgorithmParameter("gamma", 1.0));
        parameters.add(new AlgorithmParameter("lambda", 0.9));
        parameters.add(new AlgorithmParameter("epsilon", 0.2));
        return parameters;
    }
    //new String[]{"alpha", "gamma", "lambda", "epsilon"}


    private String description;
    private String beanName;
    private List<AlgorithmParameter> parameters;


    AgentType(String description, String beanName, List<AlgorithmParameter> parameters) {
        this.description = description;
        this.beanName = beanName;
        this.parameters = parameters;
    }

    public String getDescription() {
        return description;
    }

    public String getBeanName() {
        return beanName;
    }

    public List<AlgorithmParameter> getParameters() {
        return parameters;
    }

}
