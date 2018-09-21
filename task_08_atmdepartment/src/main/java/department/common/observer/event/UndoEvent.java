package department.common.observer.event;

import department.atm.ATMMachineWithCaretaker;

public class UndoEvent implements Event {
    @Override
    public Object[] execute(ATMMachineWithCaretaker atmMachine) {
        atmMachine.undo();
        return null;
    }

    @Override
    public EventType getEventType() {
        return EventType.UNDO;
    }
}
