package pl.edu.agh.mabics.ui.listeners.helpers;

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz
 * Date: 11.02.13
 * Time: 14:25
 */
public interface ISerializationHelper {
    void serializeFormBean(String filePath);

    void deserializeFormBean(String filePath);
}
