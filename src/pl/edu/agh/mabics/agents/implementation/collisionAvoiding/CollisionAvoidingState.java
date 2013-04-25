package pl.edu.agh.mabics.agents.implementation.collisionAvoiding;

import pl.edu.agh.mabics.platform.model.DistanceType;
import pl.edu.agh.mabics.platform.model.PlatformRequest;
import pl.edu.agh.mabics.platform.model.Robot;
import pl.edu.agh.mabics.platform.model.Vector;
import pl.edu.agh.mabics.ui.datamodel.util.Coordinates;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz
 * Date: 11.04.13
 * Time: 16:10
 */
public class CollisionAvoidingState {
    public static final int INITIAL_STATE_REWARD = 0;
    private int agentDistanceToCollisionPoint;
    private int collisionAgentDistanceToCollisionPoint;
    private int reward;

    public CollisionAvoidingState(PlatformRequest request, int reward) {
        calculateDistances(request);
        this.reward = reward;
    }

    private void calculateDistances(PlatformRequest request) {
        List<Robot> robots = request.getRobots();
        Robot robot = currentRobot(robots, request.getPosition());
        robots = filterOutTheSameDirectionRobots(robots, robot.getVelocity());
        robots = filterOutRobotsWhichAreNotDangerous(robots, robot);
        if (robots.isEmpty()) {
            collisionImpossibleState();
        } else {
            Robot closestRobot = getClosestRobot(robots, robot.getPosition());
            this.agentDistanceToCollisionPoint = robot.getPosition()
                    .distance(closestRobot.getPosition(), getCollisionDistanceType(robot));
            this.collisionAgentDistanceToCollisionPoint = closestRobot.getPosition()
                    .distance(robot.getPosition(), getCollisionDistanceType(closestRobot));
        }
    }

    private void collisionImpossibleState() {
        this.agentDistanceToCollisionPoint = -1;
        this.collisionAgentDistanceToCollisionPoint = -1;
    }

    private List<Robot> filterOutRobotsWhichAreNotDangerous(List<Robot> robots, Robot robot) {
        List<Robot> dangerousRobots = new ArrayList<Robot>();
        if (robot.getVelocity().getX() == 0) {
            for (Robot r : robots) {
                if (r.getPosition().getX() <= robot.getPosition().getX()) {
                    dangerousRobots.add(r);
                }
            }
        } else if (robot.getVelocity().getY() == 0) {
            for (Robot r : robots) {
                if (r.getPosition().getY() <= robot.getPosition().getY()) {
                    dangerousRobots.add(r);
                }
            }
        }
        return dangerousRobots;
    }

    private Robot getClosestRobot(List<Robot> robots, Coordinates position) {
        Robot closestRobot = null;
        Integer actualSmallestDistance = 1000;
        for (Robot robot : robots) {
            Integer distance = position.distance(robot.getPosition(), getCollisionDistanceType(robot));
            if (distance < actualSmallestDistance) {
                actualSmallestDistance = distance;
                closestRobot = robot;
            }
        }
        return closestRobot;
    }

    private DistanceType getCollisionDistanceType(Robot robot) {
        if (robot.getVelocity().getX() == 0) return DistanceType.VERTICAL;
        else return DistanceType.HORIZONTAL;
    }

    private List<Robot> filterOutTheSameDirectionRobots(List<Robot> robots, Vector velocity) {
        List<Robot> otherDirectionRobots = new ArrayList<Robot>();
        for (Robot robot : robots) {
            if (!robot.getVelocity().equals(velocity)) {
                otherDirectionRobots.add(robot);
            }
        }
        return otherDirectionRobots;
    }

    private Robot currentRobot(List<Robot> robots, Coordinates position) {
        for (Robot robot : robots) {
            if (robot.getPosition().equals(position)) {
                return robot;
            }
        }
        return null;
    }

    public CollisionAvoidingState() {
        agentDistanceToCollisionPoint = -1;
        collisionAgentDistanceToCollisionPoint = -1;
        reward = INITIAL_STATE_REWARD;
    }

    public int getAgentDistanceToCollisionPoint() {
        return agentDistanceToCollisionPoint;
    }

    public int getCollisionAgentDistanceToCollisionPoint() {
        return collisionAgentDistanceToCollisionPoint;
    }

    public int getReward() {
        return reward;
    }

    @Override
    public String toString() {
        return "(" + this.agentDistanceToCollisionPoint + "," + this.collisionAgentDistanceToCollisionPoint + ")" + "" +
                " reward: " + this.reward;
    }

}
