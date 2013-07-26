package pl.edu.agh.mabics.agents.implementation.classifying;

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz
 * Date: 16.07.13
 * Time: 23:13
 */
public interface IReducedStatesEnum {

    public String getStringRepresentation();

    public IReducedStatesEnum getValueForString(String value);

    public double getDoubleRepresentation();

}
