package department.atm.operation.cash;


import department.atm.model.ProduceCashToClientCalculatorResult;

import java.util.Map;

public interface ProduceCashToClientCalculator {
    ProduceCashToClientCalculatorResult produceCash(Long amount, Map<Integer, Integer> currentATMBalance);
}
