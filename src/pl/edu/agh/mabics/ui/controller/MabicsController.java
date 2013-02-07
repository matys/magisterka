package pl.edu.agh.mabics.ui.controller;

import com.intellij.uiDesigner.core.GridConstraints;
import org.springframework.stereotype.Controller;
import pl.edu.agh.mabics.MabicsGUI;
import pl.edu.agh.mabics.ui.datamodel.beans.FormBean;
import pl.edu.agh.mabics.ui.listeners.GenerateRandomAgentsListener;
import pl.edu.agh.mabics.ui.listeners.NumberOfAgentsChangeListener;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz
 * Date: 07.02.13
 * Time: 17:32
 */
@Controller
public class MabicsController {

    private static final String INTERSECTION_FILE_DESCRIPTION = ".bmp - bitmap with intersection";
    private static final String INTERSECTION_FILE_EXTENSIONS = "bmp";
    private static final String INTERSECTION_FILE_DEFAULT_DIRECTORY = "C:\\Users\\Mateusz\\Desktop\\nauka\\Studia";
    private FormBean formBean;
    private JFrame frame;
    private MabicsGUI mabicsGUI;

    public MabicsController() {
        formBean = new FormBean();
        initGUI();
        initListeners();
    }

    private void initListeners() {
        initIntersectionFileChooser();
        initIntersectionFileShowButton();
        mabicsGUI.getNumberOfAgentsLeftTextField().getDocument().addDocumentListener(new NumberOfAgentsChangeListener(mabicsGUI.getAgentsPanelLeft(), mabicsGUI.getNumberOfAgentsLeftTextField()));
        mabicsGUI.getNumberOfAgentsDownTextField().getDocument().addDocumentListener(new NumberOfAgentsChangeListener(mabicsGUI.getAgentsPanelDown(), mabicsGUI.getNumberOfAgentsDownTextField()));
        mabicsGUI.getGenerateRandomButtonLeft().addMouseListener(new GenerateRandomAgentsListener(mabicsGUI.getAgentsPanelLeft()));
    }

    private void initIntersectionFileShowButton() {
        mabicsGUI.getIntersectionFileShowButton().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String filePath = mabicsGUI.getIntersectionFilePath().getText();
                //run python image creation script, find created file etc...
                BufferedImage myPicture = null;
                try {
                    myPicture = ImageIO.read(new File(filePath));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                JLabel picLabel = new JLabel(new ImageIcon(myPicture));
                mabicsGUI.getIntersectionImage().removeAll();
                mabicsGUI.getIntersectionImage().add(picLabel, new GridConstraints());
                mabicsGUI.getIntersectionImage().revalidate();

            }
        });
    }

    private void initIntersectionFileChooser() {
        mabicsGUI.getIntersectionFileChooseButton().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JFileChooser fc = mabicsGUI.getFc();
                fc.setFileFilter(new FileNameExtensionFilter(INTERSECTION_FILE_DESCRIPTION, INTERSECTION_FILE_EXTENSIONS));
                fc.setCurrentDirectory(new File(INTERSECTION_FILE_DEFAULT_DIRECTORY));
                int returnVal = fc.showOpenDialog(mabicsGUI.getParent());
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    mabicsGUI.getIntersectionFilePath().setText(file.getPath());
                }
            }
        });
    }


    private void initGUI() {
        frame = new JFrame("MabicsGUI");
        mabicsGUI = new MabicsGUI();
        frame.setContentPane(mabicsGUI.Main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
