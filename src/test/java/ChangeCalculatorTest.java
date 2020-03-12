import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class ChangeCalculatorTest {

    private ChangeCalculator changeCalculator = new ChangeCalculator();
    private int target;
    private List<Integer> values;

    @Test
    public void shouldGetChangeWhenOnlyLargestDenominationPossible() {
        target = 20;
        values = new ArrayList<>(Arrays.asList(20, 5, 2, 1, 1, 1));

        Optional<List<Integer>> change = changeCalculator.getChange(target, values);

        int changeSum = change.get().stream().mapToInt(Integer::intValue).sum();
        assertThat(changeSum).isEqualTo(target);
    }

    @Test
    public void shouldGetChangeWhenOnlySmallestDenominationPossible() {
        target = 3;
        values = new ArrayList<>(Arrays.asList(1, 1, 1, 5, 10, 20));

        Optional<List<Integer>> change = changeCalculator.getChange(target, values);

        int changeSum = change.get().stream().mapToInt(Integer::intValue).sum();
        assertThat(changeSum).isEqualTo(target);
    }

    @Test
    public void shouldGetChangeWhenAllDenominationsRequired() {
        target = 38; // 20 + 10 + 5 + 2 +1 = 38
        values = new ArrayList<>(Arrays.asList(20, 20, 10, 5, 2, 1, 1, 1));

        Optional<List<Integer>> change = changeCalculator.getChange(target, values);

        int changeSum = change.get().stream().mapToInt(Integer::intValue).sum();
        assertThat(changeSum).isEqualTo(target);
    }

    @Test
    public void shouldGetChangeWhenSomeDenominationsRequired() {
        target = 13; // 10 + 1 + 1 + 1 = 13
        values = new ArrayList<>(Arrays.asList(20, 20, 20, 10, 5, 1, 1, 1));

        Optional<List<Integer>> change = changeCalculator.getChange(target, values);

        int changeSum = change.get().stream().mapToInt(Integer::intValue).sum();
        assertThat(changeSum).isEqualTo(target);
    }

    @Test
    public void shouldNotGetChangeWhenNotPossible() {
        target = 100;
        values = new ArrayList<>(Arrays.asList(5, 2, 2, 2, 1, 1, 1, 1, 1));

        Optional<List<Integer>> actualChange = changeCalculator.getChange(target, values);
        assertThat(actualChange).isEmpty();
    }
}
