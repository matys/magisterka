package pl.edu.agh.mabics.agents.implementation.targetAndSpeedOnly;

import pl.edu.agh.mabics.platform.model.DistanceType;
import pl.edu.agh.mabics.platform.model.PlatformRequest;
import pl.edu.agh.mabics.platform.model.Robot;
import pl.edu.agh.mabics.ui.datamodel.util.Coordinates;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz
 * Date: 11.04.13
 * Time: 16:10
 */
public class TargetAndSpeedState {
    public static final int INITIAL_STATE_REWARD = 0;
    private int distanceToTarget;
    private int reward;

    public TargetAndSpeedState(PlatformRequest request, int reward) {
        calculateDistanceToTarget(request);
        this.reward = reward;
    }

    private void calculateDistanceToTarget(PlatformRequest request) {
        DistanceType type = getCollisionDistanceType(currentRobot(request.getRobots(), request.getPosition()));
        Coordinates destination = request.getDestination().get(0);
        this.distanceToTarget = destination.distance(request.getPosition(), type);
    }

    private DistanceType getCollisionDistanceType(Robot robot) {
        if (robot.getVelocity().getX() == 0) return DistanceType.VERTICAL;
        else return DistanceType.HORIZONTAL;
    }

    private Robot currentRobot(List<Robot> robots, Coordinates position) {
        for (Robot robot : robots) {
            if (robot.getPosition().equals(position)) {
                return robot;
            }
        }
        return null;
    }

    public TargetAndSpeedState() {
        reward = INITIAL_STATE_REWARD;
    }

    public int getReward() {
        return reward;
    }

    public int getDistanceToTarget() {
        return distanceToTarget;
    }

    @Override
    public String toString() {
        return "(" + this.distanceToTarget + ") " +
                " reward: " + this.reward;
    }

}
