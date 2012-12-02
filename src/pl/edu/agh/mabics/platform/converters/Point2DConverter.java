package pl.edu.agh.mabics.platform.converters;

import org.springframework.stereotype.Service;
import pl.edu.agh.mabics.platform.Point2D;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz
 * Date: 01.12.12
 * Time: 16:41
 * To change this template use File | Settings | File Templates.
 */
@Service
public class Point2DConverter implements IConverter<Point2D, List<Integer>> {
    @Override
    public Point2D convert(List<Integer> input) {
        return new Point2D(input.get(0), input.get(1));
    }
}
