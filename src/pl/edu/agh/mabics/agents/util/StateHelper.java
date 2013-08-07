package pl.edu.agh.mabics.agents.util;

import pl.edu.agh.mabics.platform.model.DistanceType;
import pl.edu.agh.mabics.platform.model.Robot;
import pl.edu.agh.mabics.platform.model.Vector;
import pl.edu.agh.mabics.ui.datamodel.util.Coordinates;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz
 * Date: 04.08.13
 * Time: 16:13
 */
public class StateHelper {

    public static List<Robot> filterOutRobotsWhichAreNotDangerous(List<Robot> robots, Robot robot) {
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
        List<Robot> dangerousRobotsWithPositiveSpeed = new ArrayList<Robot>();
        for (Robot r : dangerousRobots) {
            if (r.getSpeed() > 0) {
                dangerousRobotsWithPositiveSpeed.add(r);
            }
        }
        return dangerousRobotsWithPositiveSpeed;
    }

    public static Robot getClosestRobot(List<Robot> robots, Coordinates position) {
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

    public static DistanceType getCollisionDistanceType(Robot collisionRobot) {
        if (collisionRobot.getVelocity().getY() == 0) return DistanceType.VERTICAL;
        else return DistanceType.HORIZONTAL;
    }

    public static List<Robot> filterOutTheSameDirectionRobots(List<Robot> robots, Vector velocity) {
        List<Robot> otherDirectionRobots = new ArrayList<Robot>();
        for (Robot robot : robots) {
            if (!robot.getVelocity().equals(velocity)) {
                otherDirectionRobots.add(robot);
            }
        }
        return otherDirectionRobots;
    }

    public static Robot currentRobot(List<Robot> robots, Coordinates position) {
        for (Robot robot : robots) {
            if (robot.getPosition().equals(position)) {
                return robot;
            }
        }
        return null;
    }
}
