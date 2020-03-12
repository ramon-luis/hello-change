import java.util.Optional;

public final class CashRegister {

    private static final String INSUFFICIENT_FUNDS = "Insufficient funds for requested transaction.";

    private final DenominationCounter denominationCounter;

    public CashRegister(){
        this.denominationCounter = new DenominationCounter();
    }

    public String show() {
        return denominationCounter.toString();
    }

    public String put(String denominationCounts) {
        DenominationCounter denominationsToAdd = DenominationCounter.fromString(denominationCounts);
        denominationCounter.add(denominationsToAdd);
        return denominationCounter.toString();
    }

    public String take(String denominationCounts) {
        DenominationCounter denominationsToRemove = DenominationCounter.fromString(denominationCounts);
        if (!denominationCounter.contains(denominationsToRemove)) {
            String errorMsg = String.format("%s\nRequested to take: %s\nRegister contains: %s",
                    INSUFFICIENT_FUNDS, denominationsToRemove.toString(), denominationCounter.toString());
            return errorMsg;
        }
        denominationCounter.remove(denominationsToRemove);
        return denominationCounter.toString();
    }

    public String change(String amount) {
        Optional<DenominationCounter> change = denominationCounter.getChange(Integer.parseInt(amount));
        if (!change.isPresent()) {
            String errorMsg = String.format("%s\nRequested change for: %s\nRegister contains: %s",
                    INSUFFICIENT_FUNDS, amount.toString(), denominationCounter.toString());
            return errorMsg;
        }
        return change.get().denominationCountAsString();
    }

    @Override
    public String toString() {
        return denominationCounter.toString();
    }
}
