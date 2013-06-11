package pl.edu.agh.mabics.ui.datamodel.beans;

import org.springframework.stereotype.Component;
import pl.edu.agh.mabics.agents.implementation.AlgorithmParameter;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz
 * Date: 11.06.13
 * Time: 23:30
 */
@Component
public class ParametersSearchConfiguration {

    List<AlgorithmParameter> parameters;

    public List<AlgorithmParameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<AlgorithmParameter> parameters) {
        this.parameters = parameters;
    }

    public void copyDataTo(ParametersSearchConfiguration parametersConfiguration) {
        parametersConfiguration.setParameters(getParameters());
    }
}
