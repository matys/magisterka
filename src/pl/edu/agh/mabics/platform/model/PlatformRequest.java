package pl.edu.agh.mabics.platform.model;

import java.util.List;

public class PlatformRequest {

    private int speed;
    private Vector velocity;
    private String id;
    private Point2D position;
    private List<Point2D> robots;
    private List<Move> allowedMoves;
    private List<Point2D> destination;

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

    public Point2D getPosition() {
        return position;
    }

    public void setPosition(Point2D position) {
        this.position = position;
    }

    public List<Point2D> getRobots() {
        return robots;
    }

    public void setRobots(List<Point2D> robots) {
        this.robots = robots;
    }

    public List<Move> getAllowedMoves() {
        return allowedMoves;
    }

    public void setAllowedMoves(List<Move> allowedMoves) {
        this.allowedMoves = allowedMoves;
    }

    public List<Point2D> getDestination() {
        return destination;
    }

    public void setDestination(List<Point2D> destination) {
        this.destination = destination;
    }

}
