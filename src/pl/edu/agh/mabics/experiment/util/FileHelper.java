package pl.edu.agh.mabics.experiment.util;

import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz
 * Date: 28.02.13
 * Time: 12:43
 */
@Service
public class FileHelper {

    public BufferedWriter createBufferedReader(String filePath) {
        try {
            FileWriter writer = new FileWriter(filePath);
            return new BufferedWriter(writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void closeBufferedWriters(BufferedWriter[] bufferedWriters) {
        for (BufferedWriter writer : bufferedWriters) {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
