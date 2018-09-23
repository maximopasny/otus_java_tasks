package department;

import java.util.Map;

public interface ATMDepartment {
    Map<Integer, Integer> getDepartmentBalance(String currency);
    void resetDepartmentToInitialState();
    void massiveUndo();
    void loadMoney(String currency, Map<Integer, Integer> banknotesWithCount);
}
