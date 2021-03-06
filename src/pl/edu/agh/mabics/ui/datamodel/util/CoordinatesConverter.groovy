package pl.edu.agh.mabics.ui.datamodel.util;


import org.springframework.stereotype.Service
import pl.edu.agh.mabics.platform.converters.IConverter

import javax.swing.JTextField

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz
 * Date: 24.12.12
 * Time: 15:27
 */
@Service
public class CoordinatesConverter implements IConverter<Coordinates, String> {
    def final CoordinatesPatter = ~/\(\s*(\d+)\s*,\s*(\d+)\s*\)/

    @Override
    public Coordinates convert(String input) {
        def matcher = (input =~ CoordinatesPatter)
        return new Coordinates(new Integer(matcher[0][1]), new Integer(matcher[0][2]))
    }

    //TODO refactor
    public static Coordinates convert(JTextField input) {
        def coordinatesPatter = ~/\(\s*(\d+)\s*,\s*(\d+)\s*\)/
        def matcher = (input.getText() =~ coordinatesPatter)
        return new Coordinates(new Integer(matcher[0][1]), new Integer(matcher[0][2]))
    }


}
