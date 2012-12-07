package pl.edu.agh.mabics.platform.converters;

import org.springframework.stereotype.Service;
import pl.edu.agh.mabics.platform.Point2D;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz
 * Date: 07.12.12
 * Time: 17:05
 * To change this template use File | Settings | File Templates.
 */
@Service
public class Point2DReverseConverter implements IConverter<List<Integer>, Point2D> {

    @Override
    public List<Integer> convert(Point2D input) {
        List<Integer> vector = new ArrayList<Integer>();
        vector.add(input.getX());
        vector.add(input.getY());
        return vector;
    }
}
