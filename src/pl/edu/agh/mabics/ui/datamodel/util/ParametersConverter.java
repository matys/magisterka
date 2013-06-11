package pl.edu.agh.mabics.ui.datamodel.util;

import org.springframework.stereotype.Service;
import pl.edu.agh.mabics.agents.implementation.AlgorithmParameter;
import pl.edu.agh.mabics.platform.converters.IConverter;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz
 * Date: 07.02.13
 * Time: 19:35
 */
@Service
public class ParametersConverter implements IConverter<List<AlgorithmParameter>, JPanel> {

    @Override
    public List<AlgorithmParameter> convert(JPanel input) {
        List<AlgorithmParameter> parameters = new ArrayList<AlgorithmParameter>();
        Component[] components = ((JPanel) input.getComponent(0)).getComponents();

        for (int i = 1; i < components.length; i++) {
            Component component = components[i];
            if (component instanceof JLabel) {
                AlgorithmParameter parameter = new AlgorithmParameter();
                parameter.setName(((JLabel) component).getText());
                parameter.setFromValue(new Double(((JTextField) components[i + 1]).getText()));
                parameter.setToValue(new Double(((JTextField) components[i + 3]).getText()));
                parameter.setStep(new Double(((JTextField) components[i + 6]).getText()));
                parameters.add(parameter);
                i = i + 6;
            }
        }
        return parameters; //:(
    }
}
