package pl.edu.agh.mabics.agents.implementation

import org.springframework.stereotype.Service
import pl.edu.agh.mabics.agents.AbstractAgent
import pl.edu.agh.mabics.platform.model.DistanceType
import pl.edu.agh.mabics.platform.model.PlatformRequest
import pl.edu.agh.mabics.platform.model.PlatformResponse
import pl.edu.agh.mabics.platform.model.Robot
import pl.edu.agh.mabics.ui.datamodel.util.Coordinates

//TODO shouldn't it be Bean instead of Service?
@Service
class RandomAgent extends AbstractAgent {

    private Random random = new Random();

    @Override
    PlatformResponse getNextMove(PlatformRequest request) {
        def move = request.getAllowedMoves().get(Math.abs(random.nextInt()) % request.getAllowedMoves().size());
        Thread.currentThread().sleep(100);
        int speed = request.getSpeed();
        int d_speed = Math.abs(random.nextInt() % 3) - 1; //find random number from set {-1,0,1}
        speed += d_speed;
        if (speed < 0) {
            speed = 0;
        }
        if (speed > 2) {
            speed = 2;
        }
//        if (getRobotDirection(currentRobot(request.getRobots(), request.getPosition())).equals(DistanceType
//                .HORIZONTAL)){
//            move.set
//        }
        return new PlatformResponse(move, speed);
    }

    private Robot currentRobot(List<Robot> robots, Coordinates position) {
        for (Robot robot : robots) {
            if (robot.getPosition().equals(position)) {
                return robot;
            }
        }
        return null;
    }

    @Override
    void onComplete() {
        //TODO complete

    }

    @Override
    void onCollision() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    protected void onNextGame() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    private DistanceType getRobotDirection(Robot robot) {
        if (robot.getVelocity().getX() == 0) return DistanceType.VERTICAL;
        else return DistanceType.HORIZONTAL;
    }
}
