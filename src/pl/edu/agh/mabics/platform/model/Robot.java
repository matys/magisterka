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

    public Robot(Coordinates position, Vector velocity) {
        this.position = position;
        this.velocity = velocity;
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
}
