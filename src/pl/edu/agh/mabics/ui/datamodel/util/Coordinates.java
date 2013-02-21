package pl.edu.agh.mabics.ui.datamodel.util;

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz
 * Date: 24.12.12
 * Time: 15:24
 */
public class Coordinates {

    private Integer x;
    private Integer y;

    public Coordinates(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Coordinates) {
            Coordinates anotherCoordinates = (Coordinates) o;
            if (anotherCoordinates.x.equals(x) && anotherCoordinates.y.equals(y)) {
                return true;
            }
        }
        return false;
    }

}
