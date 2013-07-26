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
public class RobotConverter implements IConverter<Robot, List> {
    private Point2DConverter point2DConverter;
    private VectorConverter vectorConverter;

    @Override
    public Robot convert(List input) {
        List<Integer> position = (List<Integer>) input.get(0);
        List<Integer> velocity = (List<Integer>) input.get(1);
        Integer speed = (Integer) input.get(2);
        return new Robot(this.point2DConverter.convert(position), this.vectorConverter.convert(velocity), speed);
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
