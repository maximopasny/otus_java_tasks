package department.atm.machine;

import java.util.Map;

public interface ATMMachine {
    Map<Integer, Integer> getBalance(String currency);
    Map<String, Map<Integer, Integer>> getBalance();
    Map<Integer, Integer> giveBanknotes(Long amount, String currency);
    void takeBanknotes(String currency, Map<Integer, Integer> banknotesWithCount);
    void applyState(Map<String, Map<Integer, Integer>> cells);
}
