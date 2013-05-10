package pl.edu.agh.mabics.ui.datamodel.beans;

import org.springframework.stereotype.Component;
import pl.edu.agh.mabics.experiment.util.PhysicType;

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz
 * Date: 24.12.12
 * Time: 14:48
 */
@Component
public class IntersectionConfiguration {
    private String intersectionFilePath;
    private PhysicType physic;
    private Integer maxSpeed;
    private Integer maxSpeedChange;
    private Integer agentRange;

    public String getIntersectionFilePath() {
        return intersectionFilePath;
    }

    public void setIntersectionFilePath(String intersectionFilePath) {
        this.intersectionFilePath = intersectionFilePath;
    }

    public void copyDataTo(IntersectionConfiguration intersectionConfiguration) {
        intersectionConfiguration.setIntersectionFilePath(getIntersectionFilePath());
        intersectionConfiguration.setAgentRange(getAgentRange());
        intersectionConfiguration.setMaxSpeed(getMaxSpeed());
        intersectionConfiguration.setMaxSpeedChange(getMaxSpeedChange());
        intersectionConfiguration.setPhysic(getPhysic());
    }

    public PhysicType getPhysic() {
        return physic;
    }

    public void setPhysic(PhysicType physic) {
        this.physic = physic;
    }

    public Integer getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(Integer maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public Integer getMaxSpeedChange() {
        return maxSpeedChange;
    }

    public void setMaxSpeedChange(Integer maxSpeedChange) {
        this.maxSpeedChange = maxSpeedChange;
    }

    public Integer getAgentRange() {
        return agentRange;
    }

    public void setAgentRange(Integer agentRange) {
        this.agentRange = agentRange;
    }
}

