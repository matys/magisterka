package pl.edu.agh.mabics.platform.converters;

import org.springframework.stereotype.Service;
import pl.edu.agh.mabics.ui.datamodel.util.Coordinates;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz
 * Date: 07.12.12
 * Time: 17:05
 */
@Service
public class Point2DReverseConverter implements IConverter<List<Integer>, Coordinates> {

    @Override
    public List<Integer> convert(Coordinates input) {
        List<Integer> vector = new ArrayList<Integer>();
        vector.add(input.getX());
        vector.add(input.getY());
        return vector;
    }
}
