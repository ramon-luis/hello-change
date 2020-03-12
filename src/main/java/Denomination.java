import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public enum Denomination {
    ONE(1),
    TWO(2),
    FIVE(5),
    TEN(10),
    TWENTY(20);

    private static Map<Integer, Denomination> VALUE_MAP = new HashMap<>();
    static {
        for (Denomination denomination: Denomination.values()) {
            VALUE_MAP.put(denomination.value, denomination);
        }
        VALUE_MAP = Collections.unmodifiableMap(VALUE_MAP);
    }

    private final int value;

    Denomination(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static Denomination of(int value) {
        return VALUE_MAP.get(value);
    }
}
