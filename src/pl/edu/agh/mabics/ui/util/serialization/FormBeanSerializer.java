package pl.edu.agh.mabics.ui.util.serialization;

import org.springframework.stereotype.Service;
import pl.edu.agh.mabics.agents.implementation.AlgorithmParameter;
import pl.edu.agh.mabics.ui.datamodel.beans.*;
import pl.edu.agh.mabics.ui.datamodel.util.Coordinates;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz
 * Date: 11.02.13
 * Time: 00:48
 */
@Service
public class FormBeanSerializer extends XMLSerializer {

    private static final Map<String, Class> mapping = new HashMap<String, Class>();

    static {
        mapping.put("form", FormBean.class);
        mapping.put("agent_data", AgentData.class);
        mapping.put("agents", AgentsConfiguration.class);
        mapping.put("experiment", ExperimentConfiguration.class);
        mapping.put("intersection", IntersectionConfiguration.class);
        mapping.put("one_side", OneSideConfiguration.class);
        mapping.put("coordinates", Coordinates.class);
        mapping.put("parameters", ParametersSearchConfiguration.class);
        mapping.put("parameter", AlgorithmParameter.class);
    }

    public void serialize(final FormBean form, String filePath) {
        serializeObject(form, filePath);
    }

    public FormBean deserialize(String filePath) {
        return (FormBean) deserializeObject(filePath);
    }

    @Override
    protected Map<String, Class> getMapping() {
        return mapping;
    }


}




