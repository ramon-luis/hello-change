import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public enum Command {
    CHANGE("change"),
    PUT("put"),
    QUIT("quit"),
    SHOW("show"),
    TAKE("take");

    public static Set<String> VALID_COMMAND_NAMES = new HashSet<>();
    static {
        for (Command command : Command.values()) {
            VALID_COMMAND_NAMES.add(command.getName());
        }
        VALID_COMMAND_NAMES = Collections.unmodifiableSet(VALID_COMMAND_NAMES);
    }

    private final String name;

    Command(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
