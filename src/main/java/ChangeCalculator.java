import java.util.*;

public final class ChangeCalculator {

    private int target;
    private int[] values;
    private boolean[][] isValueCombinationTable;

    public Optional<List<Integer>> getChange(int target, List<Integer> values) {
        this.target = target;
        this.values = values.stream().sorted().mapToInt(i->i).toArray();
        setValueCombinationTable();

        if (!isValueCombination()) {
            return Optional.empty();
        }
        return Optional.of(getValueCombination());
    }

    private void setValueCombinationTable() {
        int valuesCount = values.length;
        isValueCombinationTable = new boolean[valuesCount+1][target+1];
        for (int row = 1; row <= valuesCount; row++) {
            for (int col = 1; col <= target; col++) {
                if ((col - values[row-1]) >= 0) {
                    isValueCombinationTable[row][col] =
                        isValueCombinationTable[row-1][col]
                        || values[row-1] == col
                        || isValueCombinationTable[row-1][col-values[row-1]];
                } else {
                    isValueCombinationTable[row][col] =
                        isValueCombinationTable[row-1][col]
                        || values[row-1] == col;
                }
            }
        }
    }

    private boolean isValueCombination() {
        return isValueCombinationTable[values.length][target];
    }

    private List<Integer> getValueCombination() {
        List<Integer> valueCombination = new ArrayList<>();
        int row = values.length;
        int col = target;
        int currentTarget = target;
        while (row > 0 && col > 0 && currentTarget > 0) {
            if (isValueCombinationTable[row][col] && isValueCombinationTable[row-1][col]) {
                row--;
            } else {
                int value = values[row - 1];
                valueCombination.add(value);
                row--;
                col -= value;
                currentTarget -= value;
            }
        }
        return valueCombination;
    }
}
