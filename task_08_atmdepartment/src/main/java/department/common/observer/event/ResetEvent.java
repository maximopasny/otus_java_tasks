package department.common.observer.event;

import department.atm.ATMMachineWithCaretaker;

public class ResetEvent implements Event {
    @Override
    public Object[] execute(ATMMachineWithCaretaker atmMachine) {
        atmMachine.applyInitialState();
        return null;
    }

    @Override
    public EventType getEventType() {
        return EventType.RESET;
    }
}
