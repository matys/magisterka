package pl.edu.agh.mabics.experiment.util;

import org.springframework.stereotype.Service;
import pl.edu.agh.mabics.ui.datamodel.beans.OneSideConfiguration;
import pl.edu.agh.mabics.ui.datamodel.util.Coordinates;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz
 * Date: 18.02.13
 * Time: 17:47
 */
@Service
public class AgentDataHelper {

    private static final int MAX_SPEED = 2; //TODO move to configuration

    public Collection<Coordinates> generateHorizontalEndlinePoints(Integer position, Integer start, Integer finish) {
        Collection<Coordinates> points = new ArrayList<Coordinates>();
        for (int j = 0; j < MAX_SPEED; j++) {
            for (int i = start; i <= finish; i++) {
                points.add(new Coordinates(i, position + j));
            }
        }
        return points;
    }

    public Collection<Coordinates> generateVerticalEndlinePoints(Integer position, Integer start, Integer finish) {
        Collection<Coordinates> points = new ArrayList<Coordinates>();
        for (int j = 0; j < MAX_SPEED; j++) {
            for (int i = start; i <= finish; i++) {
                points.add(new Coordinates(position + j, i));
            }
        }
        return points;
    }

    public Collection<Coordinates> generateDownEndlinePoints(OneSideConfiguration data) {
        return generateHorizontalEndlinePoints(data.getEndLine(), data.getLeftTopCornerCoordinates().getX(),
                data.getRightDownCornerCoordinates().getX());
    }

    public Collection<? extends Coordinates> generateLeftEndlinePoints(OneSideConfiguration data) {
        return generateVerticalEndlinePoints(data.getEndLine(), data.getRightDownCornerCoordinates().getY(),
                data.getLeftTopCornerCoordinates().getY());
    }
}
