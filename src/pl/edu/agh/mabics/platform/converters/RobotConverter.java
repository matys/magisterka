package pl.edu.agh.mabics.platform.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.mabics.platform.model.Robot;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz
 * Date: 14.04.13
 * Time: 17:53
 */
@Service
public class RobotConverter implements IConverter<Robot, List<List<Integer>>> {
    private Point2DConverter point2DConverter;
    private VectorConverter vectorConverter;

    @Override
    public Robot convert(List<List<Integer>> input) {
        return new Robot(this.point2DConverter.convert(input.get(0)), this.vectorConverter.convert(input.get(1)));
    }


    @Autowired
    public void setPoint2DConverter(Point2DConverter point2DConverter) {
        this.point2DConverter = point2DConverter;
    }

    @Autowired
    public void setVectorConverter(VectorConverter vectorConverter) {
        this.vectorConverter = vectorConverter;
    }
}
