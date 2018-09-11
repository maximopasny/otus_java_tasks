package atm.machine;

import java.util.Map;

public interface ATMMachine {
    Map<Integer, Integer> getBalance(String currency);
    Map<Integer, Integer> giveBanknotes(Long amount, String currency);
    void takeBanknotes(String currency, Map<Integer, Integer> banknotesWithCount);
}
