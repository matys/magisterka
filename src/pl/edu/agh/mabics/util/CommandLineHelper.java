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

    public void runCommand(String[] commands) {
        CommandBuilder builder = new CommandBuilder();
        for (String command : commands) {
            builder = builder.addCommand(command);
        }
        try {
            System.out.println(builder.getCommand());
            Process p = Runtime.getRuntime().exec(builder.getCommand());
            p.waitFor();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = reader.readLine();
            while (line != null) {
                System.out.println(line);
                line = reader.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
