import java.util.Scanner;

public final class Application {

    public static final String WELCOME = "Cash register application.\n"
            + "Available commands:\n"
            + " > show\n"
            + " > put <# # # # #>\n"
            + " > take <# # # # #>\n"
            + " > change <# # # # #>\n"
            + " > quit\n"
            + "Ready for command...\n";

    private final static CashRegister CASH_REGISTER = new CashRegister();

    public static void run() {
        System.out.println(WELCOME);

        Scanner scanner = new Scanner(System.in);

        boolean shouldExit = false;
        while (!shouldExit) {
            UserInput input = UserInput.fromLine(scanner.nextLine());
            String commandName = input.getCommandFromLine();
            if (!Command.VALID_COMMAND_NAMES.contains(commandName)) {
                System.out.println(String.format("Received invalid command [%s].", commandName));
                continue;
            }
            Command command = Command.valueOf(commandName.toUpperCase());
            String argument = input.getArgumentFromLine();
            switch (command) {
                case SHOW:
                    System.out.println(CASH_REGISTER.show());
                    break;
                case PUT:
                    System.out.println(CASH_REGISTER.put(argument));
                    break;
                case TAKE:
                    System.out.println(CASH_REGISTER.take(argument));
                    break;
                case CHANGE:
                    System.out.println(CASH_REGISTER.change(argument));
                    break;
                case QUIT:
                    shouldExit = true;
                    break;
            }
        }
    }
}
