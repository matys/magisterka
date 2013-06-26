package pl.edu.agh.mabics.experiment.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import pl.edu.agh.mabics.agents.implementation.AlgorithmConfigurationBean;
import pl.edu.agh.mabics.agents.implementation.AlgorithmParameter;
import pl.edu.agh.mabics.agents.implementation.PossibleValue;
import pl.edu.agh.mabics.ui.datamodel.beans.FormBean;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz
 * Date: 12.06.13
 * Time: 01:18
 */
@Controller
public class ParametersSearchRunner {

    private ExperimentRunner experimentRunner;
    private AlgorithmConfigurationBean algorithmConfigurationBean;

    public void searchBestParameters(FormBean data) {
        String baseOutputDir = data.getExperimentConfiguration().getOutputDirName();
        List<AlgorithmParameter> parameters = data.getParametersConfiguration();
        List<Set<PossibleValue>> possibleValues = generatePossibleValues(parameters);
        Set<Set<Object>> configurations = cartesianProduct(possibleValues);
        for (Set<Object> conf : configurations) {
            algorithmConfigurationBean.setConfiguration(conf);
            prepareOutputDirForExperiment(data, conf, baseOutputDir);
            experimentRunner.startExperiment(data);
        }
    }

    private void prepareOutputDirForExperiment(FormBean data, Set<Object> conf, String baseOutputDir) {
        String configurationOutputDirName = "";
        for (Object o : conf) {
            PossibleValue value = (PossibleValue) o;
            configurationOutputDirName = configurationOutputDirName + value.getName() + value.getValue();
        }
        configurationOutputDirName.replace(".", ",");
        data.getExperimentConfiguration().setOutputDirName(baseOutputDir +
                "\\" + configurationOutputDirName);
    }

    private List<Set<PossibleValue>> generatePossibleValues(List<AlgorithmParameter> parameters) {
        List<Set<PossibleValue>> generatedValues = new ArrayList<Set<PossibleValue>>();
        for (AlgorithmParameter param : parameters) {
            Set<PossibleValue> possibleValues = new HashSet<PossibleValue>();
            Double step = param.getStep();
            Double fromValue = param.getFromValue();
            Double toValue = param.getToValue();
            if (step != 0) {
                for (Double value = fromValue; value <= toValue; value += step) {
                    possibleValues.add(new PossibleValue(param.getName(), value));
                }
            } else {
                possibleValues.add(new PossibleValue(param.getName(), fromValue));
            }
            generatedValues.add(possibleValues);
        }
        return generatedValues;
    }

    public Set<Set<Object>> cartesianProduct(List<Set<PossibleValue>> sets) {
        if (sets.size() < 2) throw new IllegalArgumentException("Can't have a product of fewer than two sets (got " +
                sets.size() + ")");
        return _cartesianProduct(0, sets);
    }

    private Set<Set<Object>> _cartesianProduct(int index, List<Set<PossibleValue>> sets) {
        Set<Set<Object>> ret = new HashSet<Set<Object>>();
        if (index == sets.size()) {
            ret.add(new HashSet<Object>());
        } else {
            for (Object obj : sets.get(index)) {
                for (Set<Object> set : _cartesianProduct(index + 1, sets)) {
                    set.add(obj);
                    ret.add(set);
                }
            }
        }
        return ret;
    }

    @Autowired
    public void setExperimentRunner(ExperimentRunner experimentRunner) {
        this.experimentRunner = experimentRunner;
    }

    @Autowired
    public void setAlgorithmConfigurationBean(AlgorithmConfigurationBean algorithmConfigurationBean) {
        this.algorithmConfigurationBean = algorithmConfigurationBean;
    }
}
