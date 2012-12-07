package pl.edu.agh.mabics.util;

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz
 * Date: 07.12.12
 * Time: 22:21
 */
public class CommandBuilder {

    private String command;

    public CommandBuilder() {
        this.command = "cmd /c ";
    }

    public CommandBuilder addCommand(String command) {
        this.command = this.command + command + " && ";
        return this;
    }

    public String getCommand() {
        if (command.endsWith(" && ")) {
            command = command.substring(0, command.length() - 3);
        }
        return command;
    }
}
