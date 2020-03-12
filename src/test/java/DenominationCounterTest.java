import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class DenominationCounterTest {

    private DenominationCounter denominationCounter;

    @Mock
    private ChangeCalculator changeCalculator;

    @Before
    public void setup() {
        denominationCounter = new DenominationCounter();
    }

    @Test
    public void shouldInitializeWithZeroCountForEachDenomination() {
        assertThat(denominationCounter.getDenominationCountMap().get(Denomination.TWENTY)).isZero();
        assertThat(denominationCounter.getDenominationCountMap().get(Denomination.TEN)).isZero();
        assertThat(denominationCounter.getDenominationCountMap().get(Denomination.FIVE)).isZero();
        assertThat(denominationCounter.getDenominationCountMap().get(Denomination.TWO)).isZero();
        assertThat(denominationCounter.getDenominationCountMap().get(Denomination.ONE)).isZero();
    }

    @Test
    public void shouldAdd() {
        int twentyCount = 1;
        int tenCount = 2;
        int fiveCount = 3;
        int twoCount = 4;
        int oneCount = 5;
        String denominationStringToAdd = String.format("%d %d %d %d %d",
                twentyCount, tenCount, fiveCount, twoCount, oneCount);

        DenominationCounter denominationCounterToAdd = DenominationCounter.fromString(denominationStringToAdd);
        denominationCounter.add(denominationCounterToAdd);

        assertThat(denominationCounter.getDenominationCountMap().get(Denomination.TWENTY)).isEqualTo(twentyCount);
        assertThat(denominationCounter.getDenominationCountMap().get(Denomination.TEN)).isEqualTo(tenCount);
        assertThat(denominationCounter.getDenominationCountMap().get(Denomination.FIVE)).isEqualTo(fiveCount);
        assertThat(denominationCounter.getDenominationCountMap().get(Denomination.TWO)).isEqualTo(twoCount);
        assertThat(denominationCounter.getDenominationCountMap().get(Denomination.ONE)).isEqualTo(oneCount);
    }

    @Test
    public void shouldRemoveIfPossible() {
        int startingCount = 5;
        fillEachDenominationToStartingCount(denominationCounter, startingCount);

        int twentyCount = 1;
        int tenCount = 2;
        int fiveCount = 3;
        int twoCount = 4;
        int oneCount = 5;
        String denominationStringToRemove = String.format("%d %d %d %d %d",
                twentyCount, tenCount, fiveCount, twoCount, oneCount);

        DenominationCounter denominationCounterToRemove = DenominationCounter.fromString(denominationStringToRemove);
        denominationCounter.remove(denominationCounterToRemove);

        assertThat(denominationCounter.getDenominationCountMap().get(Denomination.TWENTY))
                .isEqualTo(startingCount - twentyCount);
        assertThat(denominationCounter.getDenominationCountMap().get(Denomination.TEN))
                .isEqualTo(startingCount - tenCount);
        assertThat(denominationCounter.getDenominationCountMap().get(Denomination.FIVE))
                .isEqualTo(startingCount - fiveCount);
        assertThat(denominationCounter.getDenominationCountMap().get(Denomination.TWO))
                .isEqualTo(startingCount - twoCount);
        assertThat(denominationCounter.getDenominationCountMap().get(Denomination.ONE))
                .isEqualTo(startingCount - oneCount);
    }

    @Test
    public void shouldNotRemoveIfNotPossible() {
        int startingCount = 1;
        fillEachDenominationToStartingCount(denominationCounter, startingCount);

        int twentyCount = 1;
        int tenCount = 1;
        int fiveCount = 1;
        int twoCount = 5;  // Too big - not enough existing to remove.
        int oneCount = 1;
        String denominationStringToRemove = String.format("%d %d %d %d %d",
                twentyCount, tenCount, fiveCount, twoCount, oneCount);

        DenominationCounter denominationCounterToRemove = DenominationCounter.fromString(denominationStringToRemove);
        denominationCounter.remove(denominationCounterToRemove);

        assertThat(denominationCounter.getDenominationCountMap().get(Denomination.TWENTY)).isEqualTo(startingCount);
        assertThat(denominationCounter.getDenominationCountMap().get(Denomination.TEN)).isEqualTo(startingCount);
        assertThat(denominationCounter.getDenominationCountMap().get(Denomination.FIVE)).isEqualTo(startingCount);
        assertThat(denominationCounter.getDenominationCountMap().get(Denomination.TWO)).isEqualTo(startingCount);
        assertThat(denominationCounter.getDenominationCountMap().get(Denomination.ONE)).isEqualTo(startingCount);
    }

    @Test
    public void shouldReturnTrueIfContains() {
        int startingCount = 5;
        fillEachDenominationToStartingCount(denominationCounter, startingCount);

        int twentyCount = 1;
        int tenCount = 2;
        int fiveCount = 3;
        int twoCount = 4;
        int oneCount = 5;
        String denominationsToCheckString = String.format("%d %d %d %d %d",
                twentyCount, tenCount, fiveCount, twoCount, oneCount);

        DenominationCounter denominationsToCheckCounter = DenominationCounter.fromString(denominationsToCheckString);
        boolean result = denominationCounter.contains(denominationsToCheckCounter);
        assertThat(result).isTrue();
    }

    @Test
    public void shouldReturnFalseIfDoesNotContains() {
        int startingCount = 4;
        fillEachDenominationToStartingCount(denominationCounter, startingCount);

        int twentyCount = 1;
        int tenCount = 2;
        int fiveCount = 3;
        int twoCount = 4;
        int oneCount = 5;
        String denominationsToCheckString = String.format("%d %d %d %d %d",
                twentyCount, tenCount, fiveCount, twoCount, oneCount);

        DenominationCounter denominationsToCheckCounter = DenominationCounter.fromString(denominationsToCheckString);
        boolean result = denominationCounter.contains(denominationsToCheckCounter);
        assertThat(result).isFalse();
    }

    @Test
    public void shouldGetChangeIfPossible() {
        int startingCount = 3;
        fillEachDenominationToStartingCount(denominationCounter, startingCount);

        String expectedString = "0 0 0 0 3";  // 2 five-denominations and 3 one-denominations.

        int changeRequest = 3;
        Optional<DenominationCounter> change = denominationCounter.getChange(changeRequest);

        // Compare as String for simplicity. Alternatively, consider implementing custom equals().
        String changeAsString = change.get().denominationCountAsString();
        assertThat(changeAsString).isEqualTo(expectedString);
    }

    @Test
    public void shouldReturnEmptyOptionalIfChangeIfNotPossible() {
        int changeRequest = 3;
        Optional<DenominationCounter> result = denominationCounter.getChange(changeRequest);

        assertThat(result).isEmpty();
    }

    @Test
    public void shouldRemoveChangeFromCounterForSuccessfulChangeRequest() {
        int startingCount = 3;
        fillEachDenominationToStartingCount(denominationCounter, startingCount);

        int changeRequest = 3;
        denominationCounter.getChange(changeRequest);

        assertThat(denominationCounter.getDenominationCountMap().get(Denomination.TWENTY)).isEqualTo(startingCount);
        assertThat(denominationCounter.getDenominationCountMap().get(Denomination.TEN)).isEqualTo(startingCount);
        assertThat(denominationCounter.getDenominationCountMap().get(Denomination.FIVE)).isEqualTo(startingCount);
        assertThat(denominationCounter.getDenominationCountMap().get(Denomination.TWO)).isEqualTo(startingCount);
        assertThat(denominationCounter.getDenominationCountMap().get(Denomination.ONE))
                .isEqualTo(startingCount - changeRequest);
    }


    private void fillEachDenominationToStartingCount(DenominationCounter denominationCounter, int startingCount) {
        String denominationStringToAdd = String.format("%d %d %d %d %d",
                startingCount, startingCount, startingCount, startingCount, startingCount);
        DenominationCounter denominationCounterToAdd = DenominationCounter.fromString(denominationStringToAdd);
        denominationCounter.add(denominationCounterToAdd);
    }
}
