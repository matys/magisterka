package pl.edu.agh.mabics.ui.util.serialization;

import com.thoughtworks.xstream.XStream;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz
 * Date: 11.02.13
 * Time: 00:27
 */
@Service
public abstract class XMLSerializer {

    protected void serializeObject(Object object, String outputFileDirectory) {
        XStream xstream = new XStream();
        loadMapping(xstream);
        String xml = xstream.toXML(object);
        writeToFile(outputFileDirectory, xml);
    }

    protected void writeToFile(String outputFileDirectory, String text) {
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new FileWriter(outputFileDirectory));
            out.write(text);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            assert out != null;
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    protected void loadMapping(XStream xstream) {
        Map<String, Class> mapping = getMapping();
        for (String name : mapping.keySet()) {
            xstream.alias(name, mapping.get(name));
        }

    }

    protected abstract Map<String, Class> getMapping();


}
