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

    public PlatformResponse() {
    }


    public PlatformResponse(Move move, int speed) {
        this.move = move;
        this.speed = speed;
    }

    public Move getMove() {
        return move;
    }

    public void setMove(Move move) {
        this.move = move;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

}

