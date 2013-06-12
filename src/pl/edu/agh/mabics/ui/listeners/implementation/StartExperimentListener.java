package pl.edu.agh.mabics.ui.listeners.implementation;

import pl.edu.agh.mabics.experiment.controllers.ExperimentRunner;
import pl.edu.agh.mabics.experiment.controllers.ParametersSearchRunner;
import pl.edu.agh.mabics.ui.datamodel.beans.FormBean;
import pl.edu.agh.mabics.ui.listeners.helpers.IExperimentRunnerHelper;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz
 * Date: 13.02.13
 * Time: 17:52
 */
public class StartExperimentListener implements ActionListener {

    private ExperimentRunner experimentRunner;
    private ParametersSearchRunner parametersSearchRunner;
    private IExperimentRunnerHelper experimentRunnerHelper;

    public StartExperimentListener(IExperimentRunnerHelper experimentRunnerHelper, ExperimentRunner experimentRunner,
                                   ParametersSearchRunner parametersSearchRunner) {
        this.experimentRunner = experimentRunner;
        this.experimentRunnerHelper = experimentRunnerHelper;
        this.parametersSearchRunner = parametersSearchRunner;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        FormBean data = experimentRunnerHelper.getDataFromForm();
        if (data.isPerformParametersSearch()) {
            parametersSearchRunner.searchBestParameters(data);
        } else {
            experimentRunner.startExperiment(data);
        }
    }
}
