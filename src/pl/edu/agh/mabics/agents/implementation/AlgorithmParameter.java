package pl.edu.agh.mabics.agents.implementation;

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz
 * Date: 11.06.13
 * Time: 21:50
 */
public class AlgorithmParameter {

    private String name;
    private Double fromValue;
    private Double toValue;
    private Double step;


    public AlgorithmParameter(String name, Double defaultValue) {
        this.name = name;
        this.fromValue = defaultValue;
        this.toValue = defaultValue;
        this.step = 0.0;
    }

    public AlgorithmParameter(String name, Double fromValue, Double toValue, Double step) {
        this.name = name;
        this.fromValue = fromValue;
        this.toValue = toValue;
        this.step = step;
    }

    public AlgorithmParameter() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getFromValue() {
        return fromValue;
    }

    public void setFromValue(Double fromValue) {
        this.fromValue = fromValue;
    }

    public Double getToValue() {
        return toValue;
    }

    public void setToValue(Double toValue) {
        this.toValue = toValue;
    }

    public Double getStep() {
        return step;
    }

    public void setStep(Double step) {
        this.step = step;
    }
}
