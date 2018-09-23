package department;

import department.common.observer.ATMObservable;
import department.common.observer.Observer;
import department.common.observer.event.GetBalanceEvent;
import department.common.observer.event.LoadMoneyEvent;
import department.common.observer.event.ResetEvent;
import department.common.observer.event.UndoEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ATMDepartmentImpl implements ATMDepartment, ATMObservable {
    private List<Observer> departmentATMsList;

    public ATMDepartmentImpl() {
        departmentATMsList = new ArrayList<>();
    }

    public Map<Integer, Integer> getDepartmentBalance(String currency) {
        GetBalanceEvent getBalanceEvent = new GetBalanceEvent(currency);
        return departmentATMsList.stream()
                .flatMap(atm -> GetBalanceEvent.readResultAsBanknoteMappings(atm.doEvent(getBalanceEvent)).entrySet().stream())
                .collect(Collectors.toMap(entry -> entry.getKey(), entry -> entry.getValue(), Integer::sum));
    }

    public void resetDepartmentToInitialState() {
        ResetEvent resetEvent = new ResetEvent();
        departmentATMsList.stream().forEach(atm -> atm.doEvent(resetEvent));
    }

    @Override
    public void massiveUndo() {
        UndoEvent undoEvent = new UndoEvent();
        departmentATMsList.stream().forEach(atm -> atm.doEvent(undoEvent));
    }

    @Override
    public void loadMoney(String currency, Map<Integer, Integer> banknotesWithCount) {
        LoadMoneyEvent loadMoneyEvent = new LoadMoneyEvent(currency, banknotesWithCount);
        departmentATMsList.stream().forEach(atm -> atm.doEvent(loadMoneyEvent));
    }

    @Override
    public void register(Observer observer) {
        departmentATMsList.add(observer);
    }
}
