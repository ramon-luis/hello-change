import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserInputTest {

    private String line;

    @Test
    public void shouldGetUserInputFromLineForShow() {
        line = "show";
        String expectedCommand = "show";
        String expectedArgument = "";

        UserInput userInput = UserInput.fromLine(line);

        String actualCommand = userInput.getCommandFromLine();
        assertThat(actualCommand).isEqualTo(expectedCommand);

        String actualArgument = userInput.getArgumentFromLine();
        assertThat(actualArgument).isEqualTo(expectedArgument);
    }

    @Test
    public void shouldGetUserInputFromLineForPut() {
        line = "put 1 2 3 4 5";
        String expectedCommand = "put";
        String expectedArgument = "1 2 3 4 5";

        UserInput userInput = UserInput.fromLine(line);

        String actualCommand = userInput.getCommandFromLine();
        assertThat(actualCommand).isEqualTo(expectedCommand);

        String actualArgument = userInput.getArgumentFromLine();
        assertThat(actualArgument).isEqualTo(expectedArgument);
    }

    @Test
    public void shouldGetUserInputFromLineForTake() {
        line = "take 1 2 3 4 5";
        String expectedCommand = "take";
        String expectedArgument = "1 2 3 4 5";

        UserInput userInput = UserInput.fromLine(line);

        String actualCommand = userInput.getCommandFromLine();
        assertThat(actualCommand).isEqualTo(expectedCommand);

        String actualArgument = userInput.getArgumentFromLine();
        assertThat(actualArgument).isEqualTo(expectedArgument);
    }

    @Test
    public void shouldGetUserInputFromLineForQuit() {
        line = "quit";
        String expectedCommand = "quit";
        String expectedArgument = "";

        UserInput userInput = UserInput.fromLine(line);

        String actualCommand = userInput.getCommandFromLine();
        assertThat(actualCommand).isEqualTo(expectedCommand);

        String actualArgument = userInput.getArgumentFromLine();
        assertThat(actualArgument).isEqualTo(expectedArgument);
    }

    @Test
    public void shouldGetUserInputFromLineForChange() {
        line = "change 11";
        String expectedCommand = "change";
        String expectedArgument = "11";

        UserInput userInput = UserInput.fromLine(line);

        String actualCommand = userInput.getCommandFromLine();
        assertThat(actualCommand).isEqualTo(expectedCommand);

        String actualArgument = userInput.getArgumentFromLine();
        assertThat(actualArgument).isEqualTo(expectedArgument);
    }

    @Test
    public void shouldGetUserInputFromNullLine() {
        line = null;

        UserInput userInput = UserInput.fromLine(line);

        String actualCommand = userInput.getCommandFromLine();
        assertThat(actualCommand).isNull();

        String actualArgument = userInput.getArgumentFromLine();
        assertThat(actualArgument).isNull();
    }

    @Test
    public void shouldGetUserInputFromEmptyLine() {
        line = "";

        UserInput userInput = UserInput.fromLine(line);

        String actualCommand = userInput.getCommandFromLine();
        assertThat(actualCommand).isEmpty();

        String actualArgument = userInput.getArgumentFromLine();
        assertThat(actualArgument).isEmpty();
    }
}
