package department.common.observer.event;

import department.atm.ATMMachineWithCaretaker;

import java.util.Map;

public class GetBalanceEvent implements Event {
    private final String currency;

    public GetBalanceEvent(String currency) {
        this.currency = currency;
    }

    @Override
    public Object[] execute(ATMMachineWithCaretaker atmMachine) {
        Map<Integer, Integer> balance = atmMachine.getBalance(currency);
        return new Map[]{balance};
    }

    @Override
    public EventType getEventType() {
        return EventType.GET_BALANCE;
    }

    public static Map<Integer, Integer> readResultAsBanknoteMappings(Object[] result) {
        return (Map<Integer, Integer>) result[0];
    }
}
