package pl.edu.agh.mabics.agents.implementation;

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz
 * Date: 18.02.13
 * Time: 19:30
 */
public enum AgentType {

    RANDOM("randomly choose move", "randomAgent"),
    STRAIGHT("going straight forward", "straightAgent"),
    AVOIDING_COLLISION_STRAIGHT("going straight forward and avoiding collisions", "avoidingCollisionsStraightAgent");


    private String description;
    private String beanName;


    AgentType(String description, String beanName) {
        this.description = description;
        this.beanName = beanName;
    }

    public String getDescription() {
        return description;
    }

    public String getBeanName() {
        return beanName;
    }
}
