import department.ATMDepartment;
import department.ATMDepartmentImpl;
import department.atm.ATMMachineWithCaretaker;
import department.atm.ATMMachineWithCaretakerImpl;
import department.common.observer.ATMObservable;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestATMDepartment {
    @Test
    public void When_TwoATMsCreated_1x1000_1x100_PassedToEachOne_ThenTotalBalanceIs_2x1000_2x100() {
        ATMDepartment atmDepartment = new ATMDepartmentImpl();
        Map<String, Set<Integer>> moneyRules = new HashMap<>();
        moneyRules.put("RUB", Set.of(100, 1000));
        ATMMachineWithCaretaker machine1 = new ATMMachineWithCaretakerImpl(moneyRules, (ATMObservable) atmDepartment);
        ATMMachineWithCaretaker machine2 = new ATMMachineWithCaretakerImpl(moneyRules, (ATMObservable) atmDepartment);
        Map<Integer, Integer> rublesToLoad = new HashMap<>();
        rublesToLoad.put(1000, 1);
        rublesToLoad.put(100, 1);
        atmDepartment.loadMoney("RUB", rublesToLoad);
        Map<Integer, Integer> rubleDepartmentBalance = atmDepartment.getDepartmentBalance("RUB");
        assertEquals(Integer.valueOf(2), rubleDepartmentBalance.get(100));
        assertEquals(Integer.valueOf(2), rubleDepartmentBalance.get(1000));
    }


    @Test
    public void When_TwoATMsCreated_1x1000_1x100_PassedToEachOne_1x1000_1x100_PassedAgain_UndoDone_ThenTotalBalanceIs_1x1000_1x100() {
        ATMDepartment atmDepartment = new ATMDepartmentImpl();
        Map<String, Set<Integer>> moneyRules = new HashMap<>();
        moneyRules.put("RUB", Set.of(100, 1000));
        ATMMachineWithCaretaker machine1 = new ATMMachineWithCaretakerImpl(moneyRules, (ATMObservable) atmDepartment);
        ATMMachineWithCaretaker machine2 = new ATMMachineWithCaretakerImpl(moneyRules, (ATMObservable) atmDepartment);
        Map<Integer, Integer> rublesToLoad = new HashMap<>();
        rublesToLoad.put(1000, 1);
        rublesToLoad.put(100, 1);
        atmDepartment.loadMoney("RUB", rublesToLoad);
        atmDepartment.loadMoney("RUB", rublesToLoad);
        atmDepartment.massiveUndo();
        Map<Integer, Integer> rubleDepartmentBalance = atmDepartment.getDepartmentBalance("RUB");
        assertEquals(Integer.valueOf(2), rubleDepartmentBalance.get(100));
        assertEquals(Integer.valueOf(2), rubleDepartmentBalance.get(1000));
    }

    @Test
    public void When_TwoATMsCreated_1x1000_1x100_PassedToEachOne_ResetToInitialStateDone_ThenTotalBalanceIs_0x1000_0x100() {
        ATMDepartment atmDepartment = new ATMDepartmentImpl();
        Map<String, Set<Integer>> moneyRules = new HashMap<>();
        moneyRules.put("RUB", Set.of(100, 1000));
        ATMMachineWithCaretaker machine1 = new ATMMachineWithCaretakerImpl(moneyRules, (ATMObservable) atmDepartment);
        ATMMachineWithCaretaker machine2 = new ATMMachineWithCaretakerImpl(moneyRules, (ATMObservable) atmDepartment);
        Map<Integer, Integer> rublesToLoad = new HashMap<>();
        rublesToLoad.put(1000, 1);
        rublesToLoad.put(100, 1);
        atmDepartment.loadMoney("RUB", rublesToLoad);
        atmDepartment.resetDepartmentToInitialState();
        Map<Integer, Integer> rubleDepartmentBalance = atmDepartment.getDepartmentBalance("RUB");
        assertEquals(Integer.valueOf(0), rubleDepartmentBalance.get(100));
        assertEquals(Integer.valueOf(0), rubleDepartmentBalance.get(1000));
    }
}
