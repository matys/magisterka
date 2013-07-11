package pl.edu.agh.mabics.experiment.datamodel;

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

    public GameResult(int numberOfCollisions, int timeOfLast, float averageTime, Integer timeOfFirst) {
        this.timeOfLast = timeOfLast;
        this.numberOfCollisions = numberOfCollisions;
        this.averageTime = averageTime;
        this.timeOfFirst = timeOfFirst;
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
}
