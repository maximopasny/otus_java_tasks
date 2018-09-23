package department.common.observer.event;

import department.atm.ATMMachineWithCaretaker;

import java.util.Map;

public class LoadMoneyEvent implements Event {
    private final String currency;
    private final Map<Integer, Integer> banknotesWithCount;

    private LoadMoneyEvent() {
        this.currency = null;
        this.banknotesWithCount = null;
    }

    public LoadMoneyEvent(String currency, Map<Integer, Integer> banknotesWithCount) {
        this.currency = currency;
        this.banknotesWithCount = banknotesWithCount;
    }

    @Override
    public Object[] execute(ATMMachineWithCaretaker atmMachine) {
        atmMachine.takeBanknotes(currency, banknotesWithCount);
        return null;
    }

    @Override
    public EventType getEventType() {
        return EventType.LOAD_MONEY;
    }
}
