package pl.edu.agh.mabics.platform.model;

import pl.edu.agh.mabics.ui.datamodel.util.Coordinates;

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz
 * Date: 14.04.13
 * Time: 17:54
 */
public class Robot {

    private Coordinates position;
    private Vector velocity;
    private int speed;

    public Robot(Coordinates position, Vector velocity, Integer speed) {
        this.position = position;
        this.velocity = velocity;
        this.speed = speed;
    }

    public Coordinates getPosition() {
        return position;
    }

    public void setPosition(Coordinates position) {
        this.position = position;
    }

    public Vector getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector velocity) {
        this.velocity = velocity;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
