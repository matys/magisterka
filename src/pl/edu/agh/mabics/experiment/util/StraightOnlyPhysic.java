package pl.edu.agh.mabics.experiment.util;

import java.io.BufferedWriter;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz
 * Date: 26.03.13
 * Time: 12:19
 */
public class StraightOnlyPhysic extends Physic {

    private int maxSpeed;
    private int maxSpeedChange;
    private static final String STATE_SEPARATOR = "##";

    public StraightOnlyPhysic(Integer maxSpeed, Integer maxSpeedChange) {
        this.maxSpeed = maxSpeed;
        this.maxSpeedChange = maxSpeedChange;
    }

    public void writePhysicToFile(BufferedWriter writer) throws IOException {
        //possible movements from beginning state
        writer.write(stateAsString(0, 0, 0));
        writer.newLine();
        for (int speed = 0; speed <= maxSpeedChange; speed++) {
            writer.write(moveAsString(0, 0, 1, 0, speed)); //right
            writer.newLine();
            writer.write(moveAsString(0, 0, 0, 1, speed)); //up
            writer.newLine();
        }
        for (int speed = 0; speed <= maxSpeed; speed++) {
            writer.write(STATE_SEPARATOR);
            writer.newLine();
            writer.write(stateAsString(speed, 1, 0));
            writer.newLine();
            for (int dv = -maxSpeedChange; dv <= maxSpeedChange; dv++) {
                int newSpeed = speed + dv;
                if (newSpeed >= 0 && newSpeed <= maxSpeed) {
                    writer.write(moveAsString(speed, 0, 1, 0, newSpeed));
                    writer.newLine();
                }
            }
        }
        for (int speed = 0; speed <= maxSpeed; speed++) {
            writer.write(STATE_SEPARATOR);
            writer.newLine();
            writer.write(stateAsString(speed, 0, 1));
            writer.newLine();
            for (int dv = -maxSpeedChange; dv <= maxSpeedChange; dv++) {
                int newSpeed = speed + dv;
                if (newSpeed >= 0 && newSpeed <= maxSpeed) {
                    writer.write(moveAsString(0, speed, 0, 1, newSpeed));
                    writer.newLine();
                }
            }
        }

    }

    public PhysicType getType() {
        return PhysicType.StraightOnly;
    }

}
