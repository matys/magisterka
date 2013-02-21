package pl.edu.agh.mabics.platform.model;

public class Point2D {

    int x;
    int y;

    public Point2D(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Point2D) {
            Point2D anotherPoint = (Point2D) o;
            if (anotherPoint.x == x && anotherPoint.y == y) {
                return true;
            }
        }
        return false;
    }

}

