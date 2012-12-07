package pl.edu.agh.mabics.platform.model;

public class Move {
    Point2D point;
    Vector velocity;

    public Move(Point2D point, Vector velocity) {
        this.point = point;
        this.velocity = velocity;
    }

    public Point2D getPoint() {
        return point;
    }

    public Vector getVelocity() {
        return velocity;
    }

}
