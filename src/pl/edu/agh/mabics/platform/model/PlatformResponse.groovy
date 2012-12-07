package pl.edu.agh.mabics.platform.model

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz
 * Date: 01.12.12
 * Time: 23:14
 * To change this template use File | Settings | File Templates.
 */
class PlatformResponse {

    private Move move;
    private int speed;

    public PlatformResponse(move, speed) {
        this.move = move;
        this.speed = speed;
    }

    Move getMove() {
        return move
    }

    void setMove(Move move) {
        this.move = move
    }

    int getSpeed() {
        return speed
    }

    void setSpeed(int speed) {
        this.speed = speed
    }

}

