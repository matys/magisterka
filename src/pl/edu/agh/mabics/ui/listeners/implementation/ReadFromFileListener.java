package pl.edu.agh.mabics.ui.listeners.implementation;

import pl.edu.agh.mabics.ui.listeners.helpers.ISerializationHelper;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz
 * Date: 11.02.13
 * Time: 14:22
 */
public class ReadFromFileListener implements ActionListener {

    private static final String SERIALIZATION_FILE_DESCRIPTION = ".xml - file with data in xml format";
    private static final String SERIALIZATION_FILE_FILE_EXTENSIONS = "xml";
    private static final String SERIALIZATION_FILE_DEFAULT_DIRECTORY = "C:\\Users\\Mateusz\\Desktop\\nauka\\Studia";
    private ISerializationHelper serializationHelper;
    private Component fileChooserParent;
    private JFileChooser fc;

    public ReadFromFileListener(ISerializationHelper serializationHelper, Component fileChooserParent, JFileChooser fc) {
        this.serializationHelper = serializationHelper;
        this.fileChooserParent = fileChooserParent;
        this.fc = fc;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        fc.setFileFilter(new FileNameExtensionFilter(SERIALIZATION_FILE_DESCRIPTION, SERIALIZATION_FILE_FILE_EXTENSIONS));
        fc.setCurrentDirectory(new File(SERIALIZATION_FILE_DEFAULT_DIRECTORY));
        int returnVal = fc.showOpenDialog(fileChooserParent);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            String chosenFilePath = file.getPath();
            serializationHelper.deserializeFormBean(chosenFilePath);
        }
    }
}
