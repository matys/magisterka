package pl.edu.agh.mabics.ui.controller;

import com.intellij.uiDesigner.core.GridConstraints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import pl.edu.agh.mabics.MabicsGUI;
import pl.edu.agh.mabics.ui.datamodel.beans.FormBean;
import pl.edu.agh.mabics.ui.listeners.helpers.IIntersectionConfigurationHelper;
import pl.edu.agh.mabics.ui.listeners.helpers.ISerializationHelper;
import pl.edu.agh.mabics.ui.listeners.implementation.*;
import pl.edu.agh.mabics.ui.util.serialization.FormBeanSerializer;

import javax.swing.*;

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz
 * Date: 07.02.13
 * Time: 17:32
 */
@Controller
public class MabicsController implements ISerializationHelper, IIntersectionConfigurationHelper {

    private FormBeanSerializer formBeanSerializer;
    private FormBean formBean;
    private JFrame frame;
    private MabicsGUI mabicsGUI;


    public MabicsController() {
        initGUI();
        initListeners();
    }

    public void renderForm() {
        mabicsGUI.setData(formBean);
    }

    public void executeForm() {
        mabicsGUI.getData(formBean);
    }

    private void initListeners() {
        mabicsGUI.getIntersectionFileChooseButton().addMouseListener(new IntersectionImageChoiceListener(mabicsGUI.getFc(), mabicsGUI.getParent(), this));
        mabicsGUI.getIntersectionFileShowButton().addMouseListener(new ShowIntersectionListener(this));
        mabicsGUI.getNumberOfAgentsLeftTextField().getDocument().addDocumentListener(new NumberOfAgentsChangeListener(mabicsGUI.getAgentsPanelLeft(), mabicsGUI.getNumberOfAgentsLeftTextField()));
        mabicsGUI.getNumberOfAgentsDownTextField().getDocument().addDocumentListener(new NumberOfAgentsChangeListener(mabicsGUI.getAgentsPanelDown(), mabicsGUI.getNumberOfAgentsDownTextField()));
        mabicsGUI.getGenerateRandomButtonLeft().addMouseListener(new GenerateRandomAgentsListener(mabicsGUI.getAgentsPanelLeft(), mabicsGUI.getLeftTopCornerLeftTextField(), mabicsGUI.getRightDownCornerLeftTextField()));
        mabicsGUI.getGenerateRandomButtonDown().addMouseListener(new GenerateRandomAgentsListener(mabicsGUI.getAgentsPanelDown(), mabicsGUI.getLeftTopCornerDownTextField(), mabicsGUI.getRightDownCornerDownTextField()));
        mabicsGUI.getWriteToFileButton().addActionListener(new WriteToFileListener(this, mabicsGUI.getParent(), mabicsGUI.getFc()));
    }

    private void initGUI() {
        frame = new JFrame("MabicsGUI");
        mabicsGUI = new MabicsGUI();
        frame.setContentPane(mabicsGUI.Main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    @Autowired
    public void setFormBean(FormBean formBean) {
        this.formBean = formBean;
    }

    @Autowired
    public void setFormBeanSerializer(FormBeanSerializer formBeanSerializer) {
        this.formBeanSerializer = formBeanSerializer;
    }

    public void serializeFormBean(String fileName) {
        formBeanSerializer.serialize(formBean, fileName);
    }

    @Override
    public String getIntersectionFilePath() {
        return mabicsGUI.getIntersectionFilePath().getText();
    }

    @Override
    public void replaceIntersectionImage(JLabel picLabel) {
        mabicsGUI.getIntersectionImage().removeAll();
        mabicsGUI.getIntersectionImage().add(picLabel, new GridConstraints());
        mabicsGUI.getIntersectionImage().revalidate();
    }

    @Override
    public void setIntersectionFilePath(String path) {
        mabicsGUI.getIntersectionFilePath().setText(path);
    }
}

