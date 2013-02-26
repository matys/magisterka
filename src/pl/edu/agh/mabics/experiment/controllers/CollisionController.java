package pl.edu.agh.mabics.experiment.controllers;

import org.springframework.stereotype.Service;
import pl.edu.agh.mabics.platform.model.Move;
import pl.edu.agh.mabics.platform.model.MoveState;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz
 * Date: 19.02.13
 * Time: 16:42
 */
@Service
public class CollisionController extends Thread {

    private Map<String, Move> chosenMoves = new HashMap<String, Move>();
    private int numberOfAgents = 1;


    //TODO turn on as only platform changed
    @Override
    public void run() {
        while (true) {
//            while (chosenMoves.size() < numberOfAgents) {
            sleep();
//                System.out.println("waiting for decision of agents");
//            }
//            Map<String, Move> duplicatedMoves = getDuplicatedMoves();
//            if (duplicatedMoves.isEmpty()) {
//                System.out.println("all moves ok");
//                acceptAllMoves();
//            } else {
//                System.out.println("wrong moves");
//                rejectDuplicatedMoves(duplicatedMoves);
//            }
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

    public Map<String, Move> getDuplicatedMoves() {
        Map<String, Move> duplicatedMoves = new HashMap<String, Move>();
        Map<String, Move> checkedMoves = new HashMap<String, Move>();
        for (String moveId : chosenMoves.keySet()) {
            Move move = chosenMoves.get(moveId);
            for (String checkedMoveId : checkedMoves.keySet()) {
                Move checkedMove = checkedMoves.get(checkedMoveId);
                if (checkedMove.getPoint().equals(move.getPoint())) {
                    duplicatedMoves.put(moveId, move);
                    duplicatedMoves.put(moveId, checkedMove);
                }
            }
            checkedMoves.put(moveId, move);
        }
        return duplicatedMoves;
    }
}
