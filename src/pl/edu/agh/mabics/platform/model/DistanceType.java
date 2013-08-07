package pl.edu.agh.mabics.platform.model;

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz
 * Date: 14.04.13
 * Time: 18:19
 */
public enum DistanceType {

    HORIZONTAL,
    VERTICAL;

    public DistanceType revert() {
        if (this.equals(HORIZONTAL)) {
            return VERTICAL;
        } else {
            return HORIZONTAL;
        }
    }
}
