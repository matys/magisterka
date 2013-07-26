package pl.edu.agh.mabics.agents.implementation.classifying;

import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz
 * Date: 16.07.13
 * Time: 23:30
 */
public class EnumHelper {

    private static Random randomGenerator = new Random();

    public static IReducedStatesEnum getByString(String code, IReducedStatesEnum[] values) {
        for (IReducedStatesEnum e : values) {
            if (e.getStringRepresentation().equalsIgnoreCase(code)) {
                return e;
            }
        }
        return null;
    }

    public static <E extends IReducedStatesEnum> IReducedStatesEnum getRandomValue(Class<E> enumData) {
        int numberOfEnums = enumData.getEnumConstants().length;
        IReducedStatesEnum[] indexToClassMap = new IReducedStatesEnum[numberOfEnums];
        int i = 0;
        for (E clazz : enumData.getEnumConstants()) {
            indexToClassMap[i] = clazz;
            i++;
        }
        int j = Math.abs(randomGenerator.nextInt()) % numberOfEnums;
        return indexToClassMap[j];
    }

}
