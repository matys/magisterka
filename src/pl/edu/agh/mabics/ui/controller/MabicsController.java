package pl.edu.agh.mabics.ui.controller;

import com.intellij.uiDesigner.core.GridConstraints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import pl.edu.agh.mabics.MabicsGUI;
import pl.edu.agh.mabics.agents.implementation.AgentType;
import pl.edu.agh.mabics.experiment.controllers.ExperimentRunner;
import pl.edu.agh.mabics.ui.datamodel.beans.FormBean;
import pl.edu.agh.mabics.ui.listeners.helpers.IExperimentRunnerHelper;
import pl.edu.agh.mabics.ui.listeners.helpers.IIntersectionConfigurationHelper;
import pl.edu.agh.mabics.ui.listeners.helpers.ISerializationHelper;
import pl.edu.agh.mabics.ui.listeners.implementation.*;
import pl.edu.agh.mabics.ui.util.serialization.FormBeanSerializer;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseListener;

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz
 * Date: 07.02.13
 * Time: 17:32
 */
@Controller
public class MabicsController
        implements ISerializationHelper, IIntersectionConfigurationHelper, IExperimentRunnerHelper {

    private FormBeanSerializer formBeanSerializer;
    private FormBean formBean;
    private JFrame frame;
    private MabicsGUI mabicsGUI;
    private ExperimentRunner experimentRunner;


    public MabicsController() {
        initGUI();
        refreshListeners();
    }

    private void refreshListeners() {
        removeOldListeners();
        initListeners();
    }

    private void initListeners() {
        mabicsGUI.getIntersectionFileChooseButton()
                .addMouseListener(new IntersectionImageChoiceListener(mabicsGUI.getFc(), mabicsGUI.getParent(), this));
        mabicsGUI.getIntersectionFileShowButton().addMouseListener(new ShowIntersectionListener(this));
        mabicsGUI.getNumberOfAgentsLeftTextField().getDocument().addDocumentListener(
                new NumberOfAgentsChangeListener(mabicsGUI.getAgentsPanelLeft(),
                        mabicsGUI.getNumberOfAgentsLeftTextField(), "Left"));
        mabicsGUI.getNumberOfAgentsDownTextField().getDocument().addDocumentListener(
                new NumberOfAgentsChangeListener(mabicsGUI.getAgentsPanelDown(),
                        mabicsGUI.getNumberOfAgentsDownTextField(), "Down"));
        mabicsGUI.getGenerateRandomButtonLeft().addMouseListener(
                new GenerateRandomAgentsListener(mabicsGUI.getAgentsPanelLeft(),
                        mabicsGUI.getLeftTopCornerLeftTextField(), mabicsGUI.getRightDownCornerLeftTextField()));
        mabicsGUI.getGenerateRandomButtonDown().addMouseListener(
                new GenerateRandomAgentsListener(mabicsGUI.getAgentsPanelDown(),
                        mabicsGUI.getLeftTopCornerDownTextField(), mabicsGUI.getRightDownCornerDownTextField()));
        mabicsGUI.getWriteToFileButton()
                .addActionListener(new WriteToFileListener(this, mabicsGUI.getParent(), mabicsGUI.getFc()));
        mabicsGUI.getReadFromFileButton()
                .addActionListener(new ReadFromFileListener(this, mabicsGUI.getParent(), mabicsGUI.getFc()));
        mabicsGUI.getRunButton().addActionListener(new StartExperimentListener(this, experimentRunner));
        mabicsGUI.getAgentImplementationLeftComboBox().addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                AgentType newAgentType = (AgentType) e.getItem();
                mabicsGUI.initParametersSearchTab(newAgentType.getParameters(), newAgentType.getDescription());
            }
        });
    }

    private void removeOldListeners() {
        removeOldMouseListeners(mabicsGUI.getIntersectionFileChooseButton(), IntersectionImageChoiceListener.class);
        removeOldActionListeners(mabicsGUI.getRunButton(), StartExperimentListener.class);
        removeOldMouseListeners(mabicsGUI.getIntersectionFileShowButton(), ShowIntersectionListener.class);
        removeOldMouseListeners(mabicsGUI.getGenerateRandomButtonDown(), GenerateRandomAgentsListener.class);
        removeOldMouseListeners(mabicsGUI.getGenerateRandomButtonLeft(), GenerateRandomAgentsListener.class);
        removeOldActionListeners(mabicsGUI.getWriteToFileButton(), WriteToFileListener.class);
        removeOldActionListeners(mabicsGUI.getReadFromFileButton(), ReadFromFileListener.class);
    }

    private void removeOldActionListeners(JButton button, Class listenerClass) {
        ActionListener[] listeners = button.getActionListeners();
        for (ActionListener listener : listeners) {
            if (listenerClass.isInstance(listener)) button.removeActionListener(listener);
        }
    }

    private void removeOldMouseListeners(JButton button, Class listenerClass) {
        MouseListener[] listeners = button.getMouseListeners();
        for (MouseListener listener : listeners) {
            if (listenerClass.isInstance(listener)) button.removeMouseListener(listener);
        }
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

    public void serializeFormBean(String filePath) {
        formBeanSerializer.serialize(mabicsGUI.getData(formBean), filePath);
    }

    @Override
    public void deserializeFormBean(String filePath) {
        FormBean formBean = formBeanSerializer.deserialize(filePath);
        formBean.copyDataTo(this.formBean);
        mabicsGUI.setData(this.formBean);
        refreshListeners();
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

    @Autowired
    public void setExperimentRunner(ExperimentRunner experimentRunner) {
        this.experimentRunner = experimentRunner;
    }

    @Override
    public FormBean getDataFromForm() {
        mabicsGUI.getData(formBean);
        return formBean;
    }
}

