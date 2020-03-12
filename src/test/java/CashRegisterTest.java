import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CashRegisterTest {

    private CashRegister cashRegister;

    @Before
    public void setup() {
        cashRegister = new CashRegister();
    }

    @Test
    public void shouldShowForEmptyRegister() {
        String actual = cashRegister.show();

        String expected = "$0 0 0 0 0 0";
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void shouldShowForNonEmptyRegister() {
        String amountsToAdd = "1 2 3 4 5";
        cashRegister.put(amountsToAdd);

        String actual = cashRegister.show();

        int expectedValue = 1*20 + 2*10 + 3*5 + 4*2 + 5*1;
        String expected = String.format("$%d %s", expectedValue, amountsToAdd);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void shouldTake() {
        String amountsToAdd = "1 2 3 4 5";
        cashRegister.put(amountsToAdd);

        String amountsToTake = "1 2 3 4 0";
        String actual = cashRegister.take(amountsToTake);

        String expected = "$5 0 0 0 0 5";
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void shouldReturnErrorMessageIfCannotTake() {
        String amountsToTake = "1 2 3 4 0";
        String actual = cashRegister.take(amountsToTake);

        String requested = "$63 1 2 3 4 0";
        String expected = "Insufficient funds for requested transaction."
                        + "\nRequested to take: " + requested
                        + "\nRegister contains: $0 0 0 0 0 0";
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void shouldGiveChange() {
        String amountsToAdd = "1 0 1 3 1";
        cashRegister.put(amountsToAdd);

        String changeRequest = "11";
        String actual = cashRegister.change(changeRequest);

        String expected = "0 0 1 3 0";
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void shouldReturnErrorMessageIfCannotGiveChange() {
        String changeRequest = "11";
        String actual = cashRegister.change(changeRequest);

        String expected = "Insufficient funds for requested transaction."
                + "\nRequested change for: " + changeRequest
                + "\nRegister contains: $0 0 0 0 0 0";
        assertThat(actual).isEqualTo(expected);
    }
}
