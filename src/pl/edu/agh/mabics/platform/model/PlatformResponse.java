package pl.edu.agh.mabics.platform.model;

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz
 * Date: 01.12.12
 * Time: 23:14
 */
public class PlatformResponse {

    private Move move;
    private int speed;

    public PlatformResponse(Move move, int speed) {
        this.move = move;
        this.speed = speed;
    }

    Move getMove() {
        return move;
    }

    void setMove(Move move) {
        this.move = move;
    }

    int getSpeed() {
        return speed;
    }

    void setSpeed(int speed) {
        this.speed = speed;
    }

}

