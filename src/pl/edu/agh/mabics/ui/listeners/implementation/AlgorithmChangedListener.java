package pl.edu.agh.mabics.ui.listeners.implementation;

import pl.edu.agh.mabics.MabicsGUI;
import pl.edu.agh.mabics.agents.implementation.AgentType;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz
 * Date: 12.06.13
 * Time: 01:27
 */
public class AlgorithmChangedListener implements ItemListener {

    MabicsGUI mabicsGUI;

    public AlgorithmChangedListener(MabicsGUI mabicsGUI) {
        this.mabicsGUI = mabicsGUI;
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        AgentType newAgentType = (AgentType) e.getItem();
        mabicsGUI.initParametersSearchTab(newAgentType.getParameters(), newAgentType.getDescription());
    }
}
