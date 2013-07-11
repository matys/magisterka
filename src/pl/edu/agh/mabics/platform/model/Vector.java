package pl.edu.agh.mabics.platform.model;

public class Vector {

    int x;
    int y;

    public Vector(int x, int y) {
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
    public boolean equals(Object obj) {
        if (obj instanceof Vector) {
            Vector otherVector = (Vector) obj;
            if (this.getX() == otherVector.getX() && this.getY() == otherVector.getY()) {
                return true;
            }
        }
        return false;
    }
}
