package pl.edu.agh.mabics.experiment.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.mabics.util.CommandLineHelper;

import java.io.BufferedWriter;
import java.io.File;
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

    private CommandLineHelper commandLineHelper;

    public BufferedWriter createBufferedWriter(String filePath) {
        removeFile(filePath);
        try {
            FileWriter writer = new FileWriter(filePath);
            return new BufferedWriter(writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void removeFile(String filePath) {
        File f = new File(filePath);
        if (f.exists()) {
            f.delete();
        }
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

    public void closeBufferedWriter(BufferedWriter fileWriter) {
        closeBufferedWriters(new BufferedWriter[]{fileWriter});
    }

    public void initDirectory(String dirName) {
        commandLineHelper.runCommand(new String[]{"rmdir /q /s " + dirName}, true);
        commandLineHelper.runCommand(new String[]{"mkdir " + dirName}, true);
    }

    @Autowired
    public void setCommandLineHelper(CommandLineHelper commandLineHelper) {
        this.commandLineHelper = commandLineHelper;
    }

    public String removeExtension(String fileName) {
//        return fileName.split(".")[0];
        return fileName.substring(0, fileName.length() - 4);
    }
}
