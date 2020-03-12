import java.util.*;

public class DenominationCounter {
    private static final int TWENTY_INDEX = 0;
    private static final int TEN_INDEX = 1;
    private static final int FIVE_INDEX = 2;
    private static final int TWO_INDEX = 3;
    private static final int ONE_INDEX = 4;
    private static final String SPACE_DELIMITER = " ";

    private final Map<Denomination, Integer> denominationCountMap;
    private final ChangeCalculator changeCalculator;

    public DenominationCounter() {
        this.denominationCountMap = new HashMap<>();
        this.changeCalculator = new ChangeCalculator();
        for (Denomination denomination : Denomination.values()) {
            denominationCountMap.put(denomination, 0);
        }
    }

    private DenominationCounter(Map<Denomination, Integer> denominationCountMap) {
        this.denominationCountMap = new HashMap<>(denominationCountMap);
        this.changeCalculator = new ChangeCalculator();
    }

    private static DenominationCounter fromMap(Map<Denomination, Integer> denominationCountMap) {
        return new DenominationCounter(denominationCountMap);
    }

    private static DenominationCounter fromList(List<Integer> values) {
        DenominationCounter denominationCounter = new DenominationCounter();
        Map<Denomination, Integer> denominationCountMap = denominationCounter.getDenominationCountMap();
        for (int i = 0; i < values.size(); i++) {
            Denomination denomination = Denomination.of(values.get(i));
            int currentCount = denominationCountMap.get(denomination);
            denominationCountMap.put(denomination, currentCount + 1);
        }
        return denominationCounter;
    }

    public static DenominationCounter fromString(String denominationCount) {
        Map<Denomination, Integer> denominationCountMap = new HashMap<>();
        String[] denominationCounts = denominationCount.split(SPACE_DELIMITER);
        denominationCountMap.put(Denomination.ONE, Integer.parseInt(denominationCounts[ONE_INDEX]));
        denominationCountMap.put(Denomination.TWO, Integer.parseInt(denominationCounts[TWO_INDEX]));
        denominationCountMap.put(Denomination.FIVE, Integer.parseInt(denominationCounts[FIVE_INDEX]));
        denominationCountMap.put(Denomination.TEN, Integer.parseInt(denominationCounts[TEN_INDEX]));
        denominationCountMap.put(Denomination.TWENTY, Integer.parseInt(denominationCounts[TWENTY_INDEX]));
        return DenominationCounter.fromMap(denominationCountMap);
    }

    public Map<Denomination, Integer> getDenominationCountMap() {
        return this.denominationCountMap;
    }

    public void add(DenominationCounter denominationCounter) {
        Map<Denomination, Integer> denominationCountsToAdd = denominationCounter.getDenominationCountMap();
        for (Denomination denomination : denominationCountsToAdd.keySet()) {
            int existingCount = denominationCountMap.get(denomination);
            int countToAdd = denominationCountsToAdd.get(denomination);
            denominationCountMap.put(denomination, existingCount + countToAdd);
        }
    }

    public void remove(DenominationCounter denominationCounter) {
        if (!this.contains(denominationCounter)) {
            return;
        }

        Map<Denomination, Integer> denominationCountsToRemove = denominationCounter.getDenominationCountMap();
        for (Denomination denomination : denominationCountsToRemove.keySet()) {
            int existingCount = denominationCountMap.get(denomination);
            int countToRemove = denominationCountsToRemove.get(denomination);
            denominationCountMap.put(denomination, existingCount - countToRemove);
        }
    }

    public boolean contains(DenominationCounter denominationCounter) {
        Map<Denomination, Integer> denominationCountsRequested = denominationCounter.getDenominationCountMap();
        for (Denomination denomination : denominationCountsRequested.keySet()) {
            int existingCount = denominationCountMap.get(denomination);
            int requestedCount = denominationCountsRequested.get(denomination);
            if (existingCount < requestedCount) {
                return false;
            }
        }
        return true;
    }

    public Optional<DenominationCounter> getChange(int amount) {
        List<Integer> values = this.toList();
        Optional<List<Integer>> changeList = changeCalculator.getChange(amount, values);
        if (!changeList.isPresent()) {
            return Optional.empty();
        }
        DenominationCounter change = DenominationCounter.fromList(changeList.get());
        this.remove(change);
        return Optional.of(change);
    }

    public String denominationCountAsString() {
        return String.format("%d %d %d %d %d",
                denominationCountMap.get(Denomination.TWENTY),
                denominationCountMap.get(Denomination.TEN),
                denominationCountMap.get(Denomination.FIVE),
                denominationCountMap.get(Denomination.TWO),
                denominationCountMap.get(Denomination.ONE));
    }

    @Override
    public String toString() {
        return String.format("$%d %s",
                this.getTotalValue(),
                this.denominationCountAsString());
    }

    private int getTotalValue() {
        int totalValue = 0;
        for (Map.Entry<Denomination, Integer> entry : denominationCountMap.entrySet()) {
            int denominationValue = entry.getKey().getValue();
            int denominationCount = entry.getValue();
            totalValue += denominationValue * denominationCount;
        }
        return totalValue;
    }

    private List<Integer> toList() {
        List<Integer> values = new ArrayList<>();
        for (Map.Entry<Denomination, Integer> entry : denominationCountMap.entrySet()) {
            int denominationValue = entry.getKey().getValue();
            int denominationCount = entry.getValue();
            for (int i = 0; i < denominationCount; i++) {
                values.add(denominationValue);
            }
        }
        return values;
    }
}
