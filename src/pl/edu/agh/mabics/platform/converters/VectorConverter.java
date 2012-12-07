package pl.edu.agh.mabics.platform.converters;

import org.springframework.stereotype.Service;
import pl.edu.agh.mabics.platform.model.Vector;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz
 * Date: 01.12.12
 * Time: 16:46
 */
@Service
public class VectorConverter implements IConverter<Vector, List<Integer>> {
    @Override
    public Vector convert(List<Integer> input) {
        return new Vector(input.get(0), input.get(1));
    }
}
