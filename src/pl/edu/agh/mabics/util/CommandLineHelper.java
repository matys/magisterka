package pl.edu.agh.mabics.util;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz
 * Date: 07.12.12
 * Time: 22:19
 */
@Service
public class CommandLineHelper {

    public Process runCommand(String[] commands, boolean waitForProcess) {
        CommandBuilder builder = new CommandBuilder();
        for (String command : commands) {
            builder = builder.addCommand(command);
        }
        Process p = null;
        try {
            System.out.println(builder.getCommand());
            p = Runtime.getRuntime().exec(builder.getCommand());
            if (waitForProcess) {
                System.out.println("waiting for process");
                p.waitFor();
            }
            System.out.println("after waiting for process");
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
//            String line = reader.readLine();
//            while (line != null) {
//                System.out.println(line);
//                line = reader.readLine();
//            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return p;
        }
    }


}
