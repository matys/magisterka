package pl.edu.agh.mabics.util;

import org.springframework.stereotype.Service;
import pl.edu.agh.mabics.experiment.datamodel.GameResult;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz
 * Date: 03.03.13
 * Time: 23:33
 */
@Service
public class ReflectionHelper {

    public Double getValue(GameResult obj, String methodName) {
        try {
            Method method = obj.getClass().getMethod(methodName);
            Object value = method.invoke(obj);
            if (value instanceof Double)
                return (Double) value;
            if (value instanceof Integer)
                return new Double((Integer) value);
            if (value instanceof Float)
                return new Double((Float) value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }
}
