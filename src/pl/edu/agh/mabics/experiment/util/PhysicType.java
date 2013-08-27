package pl.edu.agh.mabics.experiment.util;

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz
 * Date: 09.05.13
 * Time: 13:29
 */
public enum PhysicType {

    StraightOnly("straight only", "straightOnlyPhysic.txt"),
    MyOwnPhysic("my own physic", "physic.txt");

    private String physicFile;
    private String description;

    PhysicType(String description, String physicFile) {
        this.physicFile = physicFile;
        this.description = description;
    }

    public String getPhysicFile() {
        return physicFile;
    }

    public void setPhysicFile(String physicFile) {
        this.physicFile = physicFile;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

