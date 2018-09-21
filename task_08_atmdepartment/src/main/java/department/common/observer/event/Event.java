package department.common.observer.event;

import department.atm.ATMMachineWithCaretaker;

public interface Event {
    Object[] execute(ATMMachineWithCaretaker atmMachine);
    EventType getEventType();
}
