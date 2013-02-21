package pl.edu.agh.mabics.platform.converters;

import org.springframework.stereotype.Service;
import pl.edu.agh.mabics.ui.datamodel.util.Coordinates;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz
 * Date: 01.12.12
 * Time: 16:41
 */
@Service
public class Point2DConverter implements IConverter<Coordinates, List<Integer>> {
    @Override
    public Coordinates convert(List<Integer> input) {
        return new Coordinates(input.get(0), input.get(1));
    }
}
