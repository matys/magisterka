package pl.edu.agh.mabics.agents.implementation;

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz
 * Date: 12.06.13
 * Time: 18:37
 */
public class PossibleValue {
    private String name;
    private Double value;

    public PossibleValue(String name, Double value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
