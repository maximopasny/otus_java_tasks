package department;

import java.util.Map;

public interface ATMDepartment {
    Map<Integer, Integer> getDepartmentBalance(String currency);
    void resetDepartmentToInitialState();
}
