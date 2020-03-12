import java.util.Arrays;

public class UserInput {

    private static final int ARGUMENT_INDEX = 1;
    private static final int COMMAND_INDEX = 0;
    private static final String EMPTY_STRING = "";
    private static final String SPACE_DELIMITER = " ";

    private String command;
    private String argument;

    private UserInput(String command, String argument){
        this.command = command;
        this.argument = argument;
    }

    public static UserInput fromLine(String line) {
        String command = getCommandFromLine(line);
        String argument = getArgumentFromLine(line);
        return new UserInput(command, argument);
    }

    public String getCommandFromLine() {
        return this.command;
    }

    public String getArgumentFromLine() {
        return this.argument;
    }

    private static String getCommandFromLine(String line) {
        if (line == null || line.isEmpty()) {
            return line;
        }
        String[] components = line.split(SPACE_DELIMITER);
        return components[COMMAND_INDEX];
    }

    private static String getArgumentFromLine(String line) {
        if (line == null || line.isEmpty()) {
            return line;
        }
        String[] components = line.split(SPACE_DELIMITER, 2);
        System.out.println(Arrays.toString(components));
        if (components.length == 1) {
            return EMPTY_STRING;
        }
        return components[ARGUMENT_INDEX];
    }
}
