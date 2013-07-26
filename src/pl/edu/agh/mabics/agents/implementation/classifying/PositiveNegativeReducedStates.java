package pl.edu.agh.mabics.agents.implementation.classifying;

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz
 * Date: 16.07.13
 * Time: 21:49
 */
public enum PositiveNegativeReducedStates implements IReducedStatesEnum {

    POSITIVE("positive", 1.0),
    NEGATIVE("negative", 0.0);
    private String stringRepresentation;
    private double doubleRepresentation;


    PositiveNegativeReducedStates(String stringValue, double doubleValue) {
        this.stringRepresentation = stringValue;
        this.doubleRepresentation = doubleValue;

    }

    public String getStringRepresentation() {
        return stringRepresentation;
    }

    public PositiveNegativeReducedStates getValueForString(String value) {
        return (PositiveNegativeReducedStates) EnumHelper.getByString(value, this.values());
    }

    public double getDoubleRepresentation() {
        return doubleRepresentation;
    }

}
