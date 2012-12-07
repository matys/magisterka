package pl.edu.agh.mabics.platform.converters;

import org.springframework.stereotype.Service;
import pl.edu.agh.mabics.platform.Vector;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz
 * Date: 07.12.12
 * Time: 16:48
 * To change this template use File | Settings | File Templates.
 */
@Service
public class VectorReverseConverter implements IConverter<List<Integer>, Vector>  {

    @Override
    public List<Integer> convert(Vector input) {
        List<Integer> vector = new ArrayList<Integer>();
        vector.add(input.getX());
        vector.add(input.getY());
        return vector;
    }
}
