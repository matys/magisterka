package pl.edu.agh.mabics.ui.datamodel.util;

import org.springframework.stereotype.Service;
import pl.edu.agh.mabics.platform.converters.IConverter;

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz
 * Date: 24.12.12
 * Time: 15:26
 */
@Service
public class CoordinatesReverseConverter implements IConverter<String, Coordinates> {

    @Override
    public String convert(Coordinates input) {
        return "(" + input.getX().toString() + "," + input.getY().toString() + ")";
    }
}
