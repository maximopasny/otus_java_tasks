package department.atm;

import department.atm.machine.ATMMachine;
import department.atm.machine.ATMMachineImpl;
import department.common.memento.Memento;
import department.common.observer.ATMObservable;
import department.common.observer.event.Event;

import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ATMMachineWithCaretakerImpl implements ATMMachineWithCaretaker {

    private final ATMMachine atmMachine;
    private final Stack<Memento> savedStates = new Stack<>();
    private final Map<String, Set<Integer>> initialState;

    public ATMMachineWithCaretakerImpl(Map<String, Set<Integer>> currenciesSupported,
                                       ATMObservable atmDepartment) {
        atmMachine = new ATMMachineImpl(currenciesSupported);
        initialState = currenciesSupported;
        atmDepartment.register(this);
    }

    private ATMMachineWithCaretakerImpl() {
        atmMachine = null;
        initialState = null;
    }

    @Override
    public Map<Integer, Integer> getBalance(String currency) {
        return atmMachine.getBalance(currency);
    }

    @Override
    public Map<String, Map<Integer, Integer>> getBalance() {
        return atmMachine.getBalance();
    }

    @Override
    public Map<Integer, Integer> giveBanknotes(Long amount, String currency) {
        saveState();
        return atmMachine.giveBanknotes(amount, currency);
    }

    @Override
    public void takeBanknotes(String currency, Map<Integer, Integer> banknotesWithCount) {
        saveState();
        atmMachine.takeBanknotes(currency, banknotesWithCount);
    }

    @Override
    public void applyState(Map<String, Map<Integer, Integer>> cells) {
        saveState();
        atmMachine.applyState(cells);
    }

    private void saveState() {
        Memento stateToSave = new Memento(atmMachine.getBalance());
        savedStates.push(stateToSave);
    }

    @Override
    public void undo() {
        Memento prevState = savedStates.pop();
        atmMachine.applyState(prevState.getCellsState());
    }

    @Override
    public void applyInitialState() {
        saveState();
        Map<String, Map<Integer, Integer>> mapToSet = initialState.entrySet().stream()
                .collect(Collectors.toMap(entry -> entry.getKey(),
                        entry -> entry.getValue().stream().
                                collect(Collectors.toMap(Function.identity(),
                                        banknote -> 0))));
        atmMachine.applyState(mapToSet);
    }

    @Override
    public Object[] doEvent(Event event) {
        saveState();
        return event.execute(this);
    }
}
