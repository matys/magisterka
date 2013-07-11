package pl.edu.agh.mabics.agents.util;

import org.springframework.stereotype.Service;
import pl.edu.agh.mabics.platform.model.Move;
import pl.edu.agh.mabics.platform.model.PlatformRequest;
import pl.edu.agh.mabics.platform.model.Vector;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz
 * Date: 11.07.13
 * Time: 20:53
 */
@Service
public class MovesFilter {

    /**
     * removes from request's available moves moves that have different velocity than desiredVelocity
     *
     * @param request
     * @param desiredVelocity
     */
    public void filterOutMovesWithVelocityDifferentThan(PlatformRequest request, Vector desiredVelocity) {
        List<Move> moves = new ArrayList<Move>();
        for (Move move : request.getAllowedMoves()) {
            if (move.getVelocity().equals(desiredVelocity)) {
                moves.add(move);
            }
        }
        request.setAllowedMoves(moves);
    }
}
