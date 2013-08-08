package pl.edu.agh.mabics.experiment.datamodel;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz
 * Date: 13.02.13
 * Time: 18:13
 */
public class GameResult {
    private Integer timeOfLast;
    private Integer numberOfCollisions;
    private Float averageTime;
    private Integer timeOfFirst;
    private Map<String, Integer> timePerAgent;
    private Map<String, Integer> collisionsPerAgent;

    public GameResult(int numberOfCollisions, int timeOfLast, float averageTime, Integer timeOfFirst,
                      Map<String, Integer> timePerAgent, Map<String, Integer> collisionsPerAgent) {
        this.timeOfLast = timeOfLast;
        this.numberOfCollisions = numberOfCollisions;
        this.averageTime = averageTime;
        this.timeOfFirst = timeOfFirst;
        this.timePerAgent = timePerAgent;
        this.collisionsPerAgent = collisionsPerAgent;
    }

    public Integer getTimeOfLast() {
        return timeOfLast;
    }

    public Integer getNumberOfCollisions() {
        return numberOfCollisions;
    }

    public Float getAverageTime() {
        return averageTime;
    }

    public Integer getTimeOfFirst() {
        return timeOfFirst;
    }

    public Map<String, Integer> getTimePerAgent() {
        return timePerAgent;
    }

    public Map<String, Integer> getCollisionsPerAgent() {
        return collisionsPerAgent;
    }
}
