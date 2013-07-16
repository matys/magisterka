package pl.edu.agh.mabics.agents.implementation.classifying;

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz
 * Date: 16.07.13
 * Time: 23:30
 */
public class EnumHelper {

    public static IReducedStatesEnum getByString(String code, IReducedStatesEnum[] values) {
        for (IReducedStatesEnum e : values) {
            if (e.getStringRepresentation().equalsIgnoreCase(code)) {
                return e;
            }
        }
        return null;
    }
}
