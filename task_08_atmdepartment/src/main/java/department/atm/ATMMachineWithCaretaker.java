package department.atm;

import department.atm.machine.ATMMachine;
import department.common.memento.Caretaker;
import department.common.observer.Observer;

public interface ATMMachineWithCaretaker extends ATMMachine, Caretaker, Observer {
}
