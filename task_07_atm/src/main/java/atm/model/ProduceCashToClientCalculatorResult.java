package atm.model;

import java.util.Map;

public class ProduceCashToClientCalculatorResult {
    private final Map<Integer, Integer> cashToGive;
    private final Map<Integer, Integer> afterOperationATMBalance;

    public ProduceCashToClientCalculatorResult(Map<Integer, Integer> cashToGive,
                                               Map<Integer, Integer> afterOperationATMBalance) {
        this.cashToGive = cashToGive;
        this.afterOperationATMBalance = afterOperationATMBalance;
    }

    public Map<Integer, Integer> getCashToGive() {
        return cashToGive;
    }

    public Map<Integer, Integer> getAfterOperationATMBalance() {
        return afterOperationATMBalance;
    }
}
