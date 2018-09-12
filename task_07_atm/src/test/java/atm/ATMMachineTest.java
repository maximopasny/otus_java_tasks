package atm;

import atm.machine.ATMMachine;
import atm.machine.ATMMachineImpl;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ATMMachineTest {
    @Test
    public void When_4x100_5x10_LoadedInATM_ThenGive4x100_4x10ToClient_AndThenBalanceIs1x10() {
        Map<String, Set<Integer>> currencySupported = new HashMap<>();
        Set<Integer> rubBanknotes = new HashSet<>();
        rubBanknotes.addAll(Arrays.asList(10, 50, 100, 200, 500, 1000, 2000, 5000));
        currencySupported.put("RUB", rubBanknotes);
        ATMMachine atm = new ATMMachineImpl(currencySupported);
        Map<Integer, Integer> banknotesLoadedToATM = new HashMap<>();
        banknotesLoadedToATM.put(10, 5);
        banknotesLoadedToATM.put(100, 4);
        atm.takeBanknotes("RUB", banknotesLoadedToATM);
        atm.giveBanknotes(440l, "RUB");
        Map<Integer, Integer> balance = atm.getBalance("RUB");
        assertTrue(balance.get(10).equals(1));
        assertTrue(balance.get(100).equals(0));
    }
}
