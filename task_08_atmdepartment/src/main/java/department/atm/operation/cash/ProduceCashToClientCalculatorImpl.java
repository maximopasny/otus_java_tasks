package department.atm.operation.cash;

import department.atm.exception.NoCashAvailableException;
import department.atm.model.ProduceCashToClientCalculatorResult;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class ProduceCashToClientCalculatorImpl implements ProduceCashToClientCalculator {
    public ProduceCashToClientCalculatorResult produceCash(Long amount, Map<Integer, Integer> currentATMBalance) {
        Map<Integer, Integer> cashToProduce = new HashMap<>();

        currentATMBalance = currentATMBalance.entrySet().stream()
                .sorted(Map.Entry.<Integer, Integer>comparingByKey().reversed())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        Iterator<Map.Entry<Integer, Integer>> itr = currentATMBalance.entrySet().iterator();
        
        while (itr.hasNext()) {
            Map.Entry<Integer, Integer> bundle = itr.next();
            Integer banknote = bundle.getKey();
            Integer numberOfBanknotesToHold = bundle.getValue();
            Integer numberOfBanknotesToReturn = 0;
            while (amount >= banknote && numberOfBanknotesToHold > 0) {
                numberOfBanknotesToReturn++;
                numberOfBanknotesToHold--;
                amount = amount - banknote;
            }

            if (numberOfBanknotesToReturn > 0)  {
                cashToProduce.put(banknote, numberOfBanknotesToReturn);
                bundle.setValue(numberOfBanknotesToHold);
            }
        }

        if (amount != 0) {
            throw new NoCashAvailableException();
        }

        return new ProduceCashToClientCalculatorResult(cashToProduce, currentATMBalance);
    }
}
