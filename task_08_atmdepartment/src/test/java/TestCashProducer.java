import department.atm.exception.NoCashAvailableException;
import department.atm.operation.cash.ProduceCashToClientCalculator;
import department.atm.operation.cash.ProduceCashToClientCalculatorImpl;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestCashProducer {
    @Test
    public void When_9x100_9x10_9x1_InATMAnd999Passed_ThenReturnEmptyHolderMap() {
        Map<Integer, Integer> atmBalance = new HashMap<>();
        atmBalance.put(100, 9);
        atmBalance.put(1, 9);
        atmBalance.put(10, 9);
        ProduceCashToClientCalculator produceCashToClientCalculator = new ProduceCashToClientCalculatorImpl();
        var resultPair = produceCashToClientCalculator.produceCash(999l, atmBalance);
        atmBalance = resultPair.getAfterOperationATMBalance();
        var cashToGive = resultPair.getCashToGive();
        assertEquals(Integer.valueOf(0), atmBalance.get(100));
        assertEquals(Integer.valueOf(0), atmBalance.get(10));
        assertEquals(Integer.valueOf(0), atmBalance.get(1));

        assertEquals(Integer.valueOf(9), cashToGive.get(100));
        assertEquals(Integer.valueOf(9), cashToGive.get(10));
        assertEquals(Integer.valueOf(9), cashToGive.get(1));
    }

    @Test
    public void When_8x100_11x10_90x1_InATMAnd999Passed_ThenReturn1x1InHolderMap() {
        Map<Integer, Integer> atmBalance = new HashMap<>();
        atmBalance.put(10, 11);
        atmBalance.put(1, 90);
        atmBalance.put(100, 8);
        ProduceCashToClientCalculator produceCashToClientCalculator = new ProduceCashToClientCalculatorImpl();
        var resultPair = produceCashToClientCalculator.produceCash(999l, atmBalance);
        atmBalance = resultPair.getAfterOperationATMBalance();
        var cashToGive = resultPair.getCashToGive();
        assertEquals(Integer.valueOf(0), atmBalance.get(100));
        assertEquals(Integer.valueOf(0), atmBalance.get(10));
        assertEquals(Integer.valueOf(1), atmBalance.get(1));

        assertEquals(Integer.valueOf(8), cashToGive.get(100));
        assertEquals(Integer.valueOf(11), cashToGive.get(10));
        assertEquals(Integer.valueOf(89), cashToGive.get(1));
    }

    @Test
    public void When_8x100_11x10_90x1_InATMAnd1001Passed_ThenThrowNoCashAvailableException() {
        Map<Integer, Integer> cashHolder = new HashMap<>();
        cashHolder.put(100, 8);
        cashHolder.put(1, 90);
        cashHolder.put(10, 11);
        ProduceCashToClientCalculator produceCashToClientCalculator = new ProduceCashToClientCalculatorImpl();
        assertThrows(NoCashAvailableException.class, () -> produceCashToClientCalculator.produceCash(1001l, cashHolder));
    }
}
