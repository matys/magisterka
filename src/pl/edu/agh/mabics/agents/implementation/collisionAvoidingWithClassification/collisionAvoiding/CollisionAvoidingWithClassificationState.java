package pl.edu.agh.mabics.agents.implementation.collisionAvoidingWithClassification.collisionAvoiding;

import pl.edu.agh.mabics.agents.util.StateHelper;
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
public class CollisionAvoidingWithClassificationState {
    public static final int INITIAL_STATE_REWARD = 0;
    private int agentDistanceToCollisionPoint;
    private int collisionAgentDistanceToCollisionPoint;
    private int agentSpeed;
    private int collisionAgentSpeed;
    private int distanceToTarget;
    private int reward;
    public static final Integer NUMBER_OF_STATE_ATTRIBUTES_TO_REDUCE = 2;

    public CollisionAvoidingWithClassificationState(PlatformRequest request, int reward) {
        calculateCollisionDistances(request);
        calculateDistanceToTarget(request);
        this.reward = reward;
    }

    private void calculateDistanceToTarget(PlatformRequest request) {
        DistanceType type = StateHelper
                .getCollisionDistanceType(StateHelper.currentRobot(request.getRobots(), request.getPosition()));
        Coordinates destination = request.getDestination().get(0);
        this.distanceToTarget = destination.distance(request.getPosition(), type.revert());
    }

    private void calculateCollisionDistances(PlatformRequest request) {
        List<Robot> robots = request.getRobots();
        Robot robot = StateHelper.currentRobot(robots, request.getPosition());
        robots = StateHelper.filterOutTheSameDirectionRobots(robots, robot.getVelocity());
        robots = StateHelper.filterOutRobotsWhichAreNotDangerous(robots, robot);
        if (robots.isEmpty()) {
            collisionImpossibleState();
        } else {
            Robot closestRobot = StateHelper.getClosestRobot(robots, robot.getPosition());
            this.agentDistanceToCollisionPoint = robot.getPosition()
                    .distance(closestRobot.getPosition(), StateHelper.getCollisionDistanceType(closestRobot));
            this.collisionAgentDistanceToCollisionPoint = closestRobot.getPosition()
                    .distance(robot.getPosition(), StateHelper.getCollisionDistanceType(robot));
            this.agentSpeed = robot.getSpeed();
            this.collisionAgentSpeed = closestRobot.getSpeed();
        }
    }

    private void collisionImpossibleState() {
        this.agentDistanceToCollisionPoint = -1;
        this.agentSpeed = -1;
        this.collisionAgentSpeed = -1;
        this.collisionAgentDistanceToCollisionPoint = -1;
    }


    public CollisionAvoidingWithClassificationState() {
        agentDistanceToCollisionPoint = -1;
        collisionAgentDistanceToCollisionPoint = -1;
        reward = INITIAL_STATE_REWARD;
    }

    public int getReward() {
        return reward;
    }

    public int getDistanceToTarget() {
        return distanceToTarget;
    }

    public int getAgentDistanceToCollisionPoint() {
        return agentDistanceToCollisionPoint;
    }

    public int getCollisionAgentDistanceToCollisionPoint() {
        return collisionAgentDistanceToCollisionPoint;
    }

    public int getAgentSpeed() {
        return agentSpeed;
    }

    public int getCollisionAgentSpeed() {
        return collisionAgentSpeed;
    }

    @Override
    public String toString() {
        return "(" + this.distanceToTarget + " (" + this.agentDistanceToCollisionPoint + "," +
                "" + this.collisionAgentDistanceToCollisionPoint +
                "))" + "" +
                " reward: " + this.reward;
    }

    public Double[] getReducableState() {
        //return new Double[]{(double) this.agentSpeed, (double) this.collisionAgentSpeed,
        //        (double) this.agentDistanceToCollisionPoint, (double) this.collisionAgentDistanceToCollisionPoint};
        return new Double[]{(double) this.agentDistanceToCollisionPoint, (double) this.collisionAgentDistanceToCollisionPoint};
    }
}
