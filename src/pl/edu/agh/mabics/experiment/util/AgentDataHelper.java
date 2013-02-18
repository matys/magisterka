package pl.edu.agh.mabics.experiment.util;

import org.springframework.stereotype.Service;
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

    public Collection<Coordinates> generateHorizontalEndlinePoints(Integer position, Integer start, Integer finish) {
        Collection<Coordinates> points = new ArrayList<Coordinates>();
        for (int i = start; i <= finish; i++) {
            points.add(new Coordinates(i, position));
        }
        return points;
    }

    public Collection<Coordinates> generateVerticalEndlinePoints(Integer position, Integer start, Integer finish) {
        Collection<Coordinates> points = new ArrayList<Coordinates>();
        for (int i = start; i <= finish; i++) {
            points.add(new Coordinates(position, i));
        }
        return points;
    }

}
