package pl.edu.agh.mabics.ui.datamodel.beans;

import pl.edu.agh.mabics.ui.datamodel.util.Coordinates;

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz
 * Date: 07.02.13
 * Time: 19:22
 */
public class AgentData {
    private String name;
    private Coordinates location;

    public AgentData(String name, Coordinates location) {
        this.name = name;
        this.location = location;
    }

    public AgentData() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coordinates getLocation() {
        return location;
    }

    public void setLocation(Coordinates location) {
        this.location = location;
    }
}
