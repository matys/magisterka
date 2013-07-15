package pl.edu.agh.mabics.experiment.controllers;

import org.springframework.stereotype.Service;
import pl.edu.agh.mabics.platform.model.Move;
import pl.edu.agh.mabics.platform.model.MoveState;
import pl.edu.agh.mabics.ui.datamodel.util.Coordinates;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz
 * Date: 19.02.13
 * Time: 16:42
 */
@Service
public class CollisionController extends Thread {

    private Map<String, Move> chosenMoves = new ConcurrentHashMap<String, Move>();
    private int numberOfAgents = 1;


    @Override
    public void run() {
        while (true) {
            while (chosenMoves.size() < numberOfAgents) {
                sleep();
            }
            Map<String, Move> duplicatedMoves = getDuplicatedMoves();
            if (duplicatedMoves.isEmpty()) {
                System.out.println("all moves ok");
                acceptAllMoves();
            } else {
                System.out.println("wrong moves");
                rejectDuplicatedMoves(duplicatedMoves);
            }
        }
    }

    private void rejectDuplicatedMoves(Map<String, Move> duplicatedMoves) {
        for (String moveId : duplicatedMoves.keySet()) {
            Move move = duplicatedMoves.get(moveId);
            move.setState(MoveState.WRONG);
            chosenMoves.remove(moveId);
        }
    }

    private void acceptAllMoves() {
        for (Move move : chosenMoves.values()) {
            move.setState(MoveState.ACCEPTED);
        }
        chosenMoves.clear();
    }

    private void sleep() {
        try {
            Thread.currentThread().sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void acceptMove(String agentName, Move move) {
        move.setState(MoveState.NOT_PROCESSED);
        chosenMoves.put(agentName, move);
    }

    public void init(int numberOfAgents) {
        this.numberOfAgents = numberOfAgents;
        chosenMoves.clear();
    }

    private Map<String, Move> getDuplicatedMoves() {
        Map<String, Move> duplicatedMoves = new HashMap<String, Move>();
        Map<String, Move> checkedMoves = new HashMap<String, Move>();
        for (String moveId : chosenMoves.keySet()) {
            Move move = chosenMoves.get(moveId);
            for (String checkedMoveId : checkedMoves.keySet()) {
                Move checkedMove = checkedMoves.get(checkedMoveId);
                if (checkedMove.getPoint().equals(move.getPoint())) {
                    duplicatedMoves.put(moveId, move);
                    duplicatedMoves.put(checkedMoveId, checkedMove);
                }
            }
            checkedMoves.put(moveId, move);
        }
        return duplicatedMoves;
    }

    public synchronized Move getPossibleMove(Move wantedMove, Coordinates previousPosition, String id) {
        Move possibleMove = null;
        if (!hasMove(chosenMoves, wantedMove)) {
            possibleMove = wantedMove; //one of agents in conflicted has to release position,
            // so the second one can take it
        } else {
            possibleMove = new Move(previousPosition, wantedMove.getVelocity());
            while (hasMove(chosenMoves, possibleMove)) {
                if (wantedMove.getVelocity().getY() > 0) {
                    possibleMove.getPoint().setY(possibleMove.getPoint().getY() + 1);
                } else {
                    possibleMove.getPoint().setX(possibleMove.getPoint().getX() + 1);
                }
            }
        }
        acceptMove(id, possibleMove);
        return possibleMove;
    }

    private boolean hasMove(Map<String, Move> chosenMoves, Move moveToCheck) {
        for (String moveId : chosenMoves.keySet()) {
            Move move = chosenMoves.get(moveId);
            if (move.getPoint().equals(moveToCheck.getPoint())) {
                return true;
            }
        }
        return false;
    }
}
