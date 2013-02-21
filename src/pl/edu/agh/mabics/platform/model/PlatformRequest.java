package pl.edu.agh.mabics.platform.model;

import pl.edu.agh.mabics.ui.datamodel.util.Coordinates;

import java.util.List;

public class PlatformRequest {

    private int speed;
    private Vector velocity;
    private String id;
    private Coordinates position;
    private List<Coordinates> robots;
    private List<Move> allowedMoves;
    private List<Coordinates> destination;

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public Vector getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector velocity) {
        this.velocity = velocity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Coordinates getPosition() {
        return position;
    }

    public void setPosition(Coordinates position) {
        this.position = position;
    }

    public List<Coordinates> getRobots() {
        return robots;
    }

    public void setRobots(List<Coordinates> robots) {
        this.robots = robots;
    }

    public List<Move> getAllowedMoves() {
        return allowedMoves;
    }

    public void setAllowedMoves(List<Move> allowedMoves) {
        this.allowedMoves = allowedMoves;
    }

    public List<Coordinates> getDestination() {
        return destination;
    }

    public void setDestination(List<Coordinates> destination) {
        this.destination = destination;
    }

}
