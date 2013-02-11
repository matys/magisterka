package pl.edu.agh.mabics.ui.listeners.implementation;

import pl.edu.agh.mabics.ui.listeners.helpers.IIntersectionConfigurationHelper;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz
 * Date: 11.02.13
 * Time: 15:01
 */
public class ShowIntersectionListener extends MouseAdapter {

    IIntersectionConfigurationHelper intersectionConfigurationHelper;

    public ShowIntersectionListener(IIntersectionConfigurationHelper intersectionConfigurationHelper) {
        this.intersectionConfigurationHelper = intersectionConfigurationHelper;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        String filePath = intersectionConfigurationHelper.getIntersectionFilePath();
        //run python image creation script, find created file etc...
        BufferedImage myPicture = null;
        try {
            myPicture = ImageIO.read(new File(filePath));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        JLabel picLabel = new JLabel(new ImageIcon(myPicture));
        intersectionConfigurationHelper.replaceIntersectionImage(picLabel);
    }
}
