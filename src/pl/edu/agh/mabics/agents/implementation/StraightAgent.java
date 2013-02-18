package pl.edu.agh.mabics.agents.implementation;

import org.springframework.stereotype.Service;
import pl.edu.agh.mabics.agents.AbstractAgent;
import pl.edu.agh.mabics.platform.model.Move;
import pl.edu.agh.mabics.platform.model.PlatformRequest;
import pl.edu.agh.mabics.platform.model.PlatformResponse;
import pl.edu.agh.mabics.platform.model.Point2D;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz
 * Date: 18.02.13
 * Time: 19:20
 */
@Service
public class StraightAgent extends AbstractAgent {

    @Override
    public PlatformResponse getNextMove(PlatformRequest request) {
        PlatformResponse response = new PlatformResponse();
        response.setSpeed(1);
        Point2D position = request.getPosition();
        Direction direction = getDirection(request.getDestination());
        for (Move move : request.getAllowedMoves()) {
            if (checkIfMoveInGoodDirection(direction, move, position)) {
                response.setMove(move);
                return response;
            }
        }
        response.setMove(request.getAllowedMoves().get(0));
        return response;
    }

    private boolean checkIfMoveInGoodDirection(Direction direction, Move move, Point2D position) {
        int x = position.getX();
        int y = position.getY();
        switch (direction) {
            case TOP:
                return (move.getPoint().getX() == x && move.getPoint().getY() != y);
            case RIGHT:
                return (move.getPoint().getX() != x && move.getPoint().getY() == y);

        }
        return false;
    }

    private Direction getDirection(List<Point2D> destination) {
        int dx = destination.get(0).getX() - destination.get(1).getX();
        int dy = destination.get(0).getY() - destination.get(1).getY();
        if (dx == 0) {
            return Direction.RIGHT;
        } else if (dy == 0) {
            return Direction.TOP;
        }
        return Direction.NONE;
    }

    enum Direction {
        RIGHT,
        TOP,
        NONE;
    }
}



