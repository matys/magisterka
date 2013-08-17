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

    RANDOM("randomly choose move", "randomAgent", initEmptyParametersList()),
    STRAIGHT("going straight forward", "straightAgent", initEmptyParametersList()),
    STRAIGHT_CONSTANT("going straight forward with constant speed", "straightConstantAgent", initEmptyParametersList()),
    AVOIDING_COLLISION_STRAIGHT("going straight forward and avoiding collisions", "avoidingCollisionsStraightAgent",
            initAvoidingCollisionStraightParameters()),
    TARGET_AND_SPEED("going straight forward, " + "taking distance to target into consideration",
            "targetFocusedStraightAgent", initTargetAndSpeedParameters()),
    AVOIDING_COLLISION_WITH_CLASSIFICATION_STRAIGHT("going straight forward, " + "avoiding collisions and taking " +
            "distance " +
            "to target " +
            "into consideration and using classification to reduce " +
            "number of states", "avoidingCollisionsWithClassificationStraightAgent",
            initAvoidingCollisionsWithClassificationParameters()),

    AVOIDING_COLLISION_WITH_SPEED_CLASSIFICATION_STRAIGHT("going straight forward, " +
            "" + "avoiding coliisions and taking distance to " +
            "target " +
            "into consideration and using classification to reduce " +
            "number of states", "avoidingCollisionsWithSpeedClassificationStraightAgent",
            initAvoidingCollisionsWithSpeedClassificationParameters()

    ),

    AVOIDING_COLLISION_WITH_SAFETY_MODE(
            "Checks first if collision possible, if not RL is making decision, " + "slowing down otherwise",
            "avoidingCollisionsWithSafetyModeAgent", initEmptyParametersList());

    private static ArrayList<AlgorithmParameter> initEmptyParametersList() {
        return new ArrayList<AlgorithmParameter>();
    }


    private static List<AlgorithmParameter> initAvoidingCollisionsWithClassificationParameters() {
        List<AlgorithmParameter> parameters = initEmptyParametersList();
        parameters.add(new AlgorithmParameter("alpha", 0.45));
        parameters.add(new AlgorithmParameter("gamma", 1.0));
        parameters.add(new AlgorithmParameter("lambda", 0.9));
        parameters.add(new AlgorithmParameter("epsilon", 0.2));
        parameters.add(new AlgorithmParameter("gettingToTargetReward", 100d));
        parameters.add(new AlgorithmParameter("collisionReward", -100d));
        parameters.add(new AlgorithmParameter("stepReward", -20d));
        return parameters;
    }


    private static List<AlgorithmParameter> initAvoidingCollisionsWithSpeedClassificationParameters() {
        List<AlgorithmParameter> parameters = initEmptyParametersList();
        parameters.add(new AlgorithmParameter("alpha", 0.45));
        parameters.add(new AlgorithmParameter("gamma", 1.0));
        parameters.add(new AlgorithmParameter("lambda", 0.9));
        parameters.add(new AlgorithmParameter("epsilon", 0.2));
        parameters.add(new AlgorithmParameter("gettingToTargetReward", 100d));
        parameters.add(new AlgorithmParameter("collisionReward", -100d));
        parameters.add(new AlgorithmParameter("stepReward", -20d));
        return parameters;
    }


    private static List<AlgorithmParameter> initTargetAndSpeedParameters() {
        List<AlgorithmParameter> parameters = initEmptyParametersList();
        parameters.add(new AlgorithmParameter("alpha", 0.45));
        parameters.add(new AlgorithmParameter("gamma", 1.0));
        parameters.add(new AlgorithmParameter("lambda", 0.9));
        parameters.add(new AlgorithmParameter("epsilon", 0.2));
        parameters.add(new AlgorithmParameter("gettingToTargetReward", 100d));
        parameters.add(new AlgorithmParameter("collisionReward", -100d));
        parameters.add(new AlgorithmParameter("stepReward", -20d));
        return parameters;
    }

    private static List<AlgorithmParameter> initAvoidingCollisionStraightParameters() {
        List<AlgorithmParameter> parameters = initEmptyParametersList();
        parameters.add(new AlgorithmParameter("alpha", 0.45));
        parameters.add(new AlgorithmParameter("gamma", 1.0));
        parameters.add(new AlgorithmParameter("lambda", 0.9));
        parameters.add(new AlgorithmParameter("epsilon", 0.2));
        parameters.add(new AlgorithmParameter("gettingToTargetReward", 100d));
        parameters.add(new AlgorithmParameter("collisionReward", -100d));
        parameters.add(new AlgorithmParameter("stepReward", -20d));
        return parameters;
    }


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
