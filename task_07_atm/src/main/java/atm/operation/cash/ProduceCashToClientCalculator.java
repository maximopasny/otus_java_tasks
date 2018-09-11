package atm.operation.cash;

import atm.model.ProduceCashToClientCalculatorResult;

import java.util.Map;

public interface ProduceCashToClientCalculator {
    ProduceCashToClientCalculatorResult produceCash(Long amount, Map<Integer, Integer> currentATMBalance);
}
