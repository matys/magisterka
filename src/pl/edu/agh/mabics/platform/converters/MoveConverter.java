package pl.edu.agh.mabics.platform.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.mabics.platform.model.Move;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz
 * Date: 01.12.12
 * Time: 21:55
 */
@Service
public class MoveConverter implements IConverter<Move, List<List<Integer>>> {

    private Point2DConverter point2DConverter;
    private VectorConverter vectorConverter;

    @Autowired
    void setPoint2DConverter(Point2DConverter point2DConverter) {
        this.point2DConverter = point2DConverter;
    }

    @Autowired
    void setVectorConverter(VectorConverter vectorConverter) {
        this.vectorConverter = vectorConverter;
    }

    @Override
    public Move convert(List<List<Integer>> input) {
        return new Move(this.point2DConverter.convert(input.get(0)), this.vectorConverter.convert(input.get(1)));
    }
}
