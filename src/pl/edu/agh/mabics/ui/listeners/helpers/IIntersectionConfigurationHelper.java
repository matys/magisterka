package pl.edu.agh.mabics.ui.listeners.helpers;

import javax.swing.*;

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz
 * Date: 11.02.13
 * Time: 15:03
 */
public interface IIntersectionConfigurationHelper {
    String getIntersectionFilePath();

    void replaceIntersectionImage(JLabel picLabel);

    void setIntersectionFilePath(String path);
}
