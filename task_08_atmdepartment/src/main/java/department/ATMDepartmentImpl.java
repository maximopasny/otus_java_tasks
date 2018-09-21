package department;

import department.common.observer.ATMObservable;
import department.common.observer.Observer;
import department.common.observer.event.GetBalanceEvent;
import department.common.observer.event.ResetEvent;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ATMDepartmentImpl implements ATMDepartment, ATMObservable {
    private final List<Observer> departmentATMsList;

    public ATMDepartmentImpl(List<Observer> departmentATMsList) {
        this.departmentATMsList = departmentATMsList;
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
    public void register(Observer observer) {
        departmentATMsList.add(observer);
    }
}
