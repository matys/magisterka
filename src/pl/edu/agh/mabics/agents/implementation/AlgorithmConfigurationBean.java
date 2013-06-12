package pl.edu.agh.mabics.agents.implementation;

import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz
 * Date: 12.06.13
 * Time: 18:32
 */
@Component
public class AlgorithmConfigurationBean {

    private Set<PossibleValue> possibleValues;

    public void setPossibleValues(Set<PossibleValue> possibleValues) {
        this.possibleValues = possibleValues;
    }

    public Set<PossibleValue> getPossibleValues() {
        return possibleValues;
    }

    public void setConfiguration(Set<Object> possibleValues) {
        this.possibleValues = new HashSet<PossibleValue>();
        for (Object o : possibleValues) {
            this.possibleValues.add((PossibleValue) o);
        }
    }
}
