package pl.edu.agh.mabics.experiment.util;

import java.io.BufferedWriter;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz
 * Date: 10.05.13
 * Time: 12:17
 */
public abstract class Physic {

    public abstract void writePhysicToFile(BufferedWriter writer) throws IOException;

    protected String stateAsString(int speed, int velocity_x, int velocity_y) {
        return "(" + speed + ", (" + velocity_x + "," + velocity_y + "))";
    }

    protected String moveAsString(int pos_x, int pos_y, int vel_x, int vel_y, int speed) {
        return "((" + pos_x + "," + pos_y + "), (" + vel_x + "," + vel_y + "), " + speed + ")";
    }
}
