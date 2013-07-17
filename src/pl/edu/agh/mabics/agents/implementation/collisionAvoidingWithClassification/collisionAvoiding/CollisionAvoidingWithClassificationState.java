package pl.edu.agh.mabics.agents.implementation.collisionAvoidingWithClassification.collisionAvoiding;

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
public class CollisionAvoidingWithClassificationState {
    public static final int INITIAL_STATE_REWARD = 0;
    private int agentDistanceToCollisionPoint;
    private int collisionAgentDistanceToCollisionPoint;
    private int agentSpeed;
    private int collisionAgentSpeed;
    private int distanceToTarget;
    private int reward;

    public CollisionAvoidingWithClassificationState(PlatformRequest request, int reward) {
        calculateCollisionDistances(request);
        calculateDistanceToTarget(request);
        this.reward = reward;
        if (agentDistanceToCollisionPoint == -1) { //no more collisions possible (in the range of agent)
            //teach using all as positive examples
        } else {
            //put state to backlog, will be used later
        }
    }

    private void calculateDistanceToTarget(PlatformRequest request) {
        DistanceType type = getCollisionDistanceType(currentRobot(request.getRobots(), request.getPosition()));
        Coordinates destination = request.getDestination().get(0);
        this.distanceToTarget = destination.distance(request.getPosition(), type);
    }

    private void calculateCollisionDistances(PlatformRequest request) {
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
            //TODO add 123 line of Physic runner adding speed to robot, fix robotCOnverter, uncomment code below
//            this.agentSpeed = robot.getSpeed();
//            this.collisionAgentSpeed = closestRobot.getSpeed();
        }
    }

    private void collisionImpossibleState() {
        this.agentDistanceToCollisionPoint = -1;
        this.agentSpeed = -1;
        this.collisionAgentSpeed = -1;
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

    //TODO: replace with method getStateToReduce returning map of fields and their names
    public Double[] getStateAsDouble() {
        return new Double[]{(double) this.agentSpeed, (double) this.collisionAgentSpeed, (double) this.agentDistanceToCollisionPoint, (double) this.collisionAgentDistanceToCollisionPoint};
    }
}