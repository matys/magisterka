package pl.edu.agh.mabics.platform.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.mabics.platform.Move;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz
 * Date: 07.12.12
 * Time: 16:56
 * To change this template use File | Settings | File Templates.
 */
@Service
public class MoveReverseConverter implements IConverter<List<List<Integer>>, Move> {

    private Point2DReverseConverter point2DConverter;
    private VectorReverseConverter vectorConverter;

    @Autowired
    public void setPoint2DConverter(Point2DReverseConverter point2DConverter) {
        this.point2DConverter = point2DConverter;
    }

    @Autowired
    public void setVectorConverter(VectorReverseConverter vectorConverter) {
        this.vectorConverter = vectorConverter;
    }

    @Override
    public List<List<Integer>> convert(Move input) {
        List<List<Integer>> move = new ArrayList<List<Integer>>();
        move.add(point2DConverter.convert(input.getPoint()));
        move.add(vectorConverter.convert(input.getVelocity()));
        return move;
    }
}
