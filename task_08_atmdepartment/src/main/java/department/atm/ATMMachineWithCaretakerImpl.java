package department.atm;

import department.atm.machine.ATMMachine;
import department.atm.machine.ATMMachineImpl;
import department.common.memento.Memento;
import department.common.observer.ATMObservable;
import department.common.observer.event.Event;

import java.util.HashMap;
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
        return giveBanknotesActualImpl(amount, currency);
    }

    public Map<Integer, Integer> giveBanknotesActualImpl(Long amount, String currency) {
        return atmMachine.giveBanknotes(amount, currency);
    }

    @Override
    public void takeBanknotes(String currency, Map<Integer, Integer> banknotesWithCount) {
        saveState();
        takeBanknotesActualImpl(currency, banknotesWithCount);
    }

    public void takeBanknotesActualImpl(String currency, Map<Integer, Integer> banknotesWithCount) {
        atmMachine.takeBanknotes(currency, banknotesWithCount);
    }

    @Override
    public void applyState(Map<String, Map<Integer, Integer>> cells) {
        saveState();
        applyStateActualImpl(cells);
    }

    public void applyStateActualImpl(Map<String, Map<Integer, Integer>> cells) {
        atmMachine.applyState(cells);
    }

    private void saveState() {
        Map<String, Map<Integer, Integer>> balanceCopy
                = atmMachine.getBalance().entrySet().stream()
                .collect(Collectors.toMap(entry -> entry.getKey(), entry -> new HashMap<>(entry.getValue())));
        Memento stateToSave = new Memento(balanceCopy);
        savedStates.push(stateToSave);
    }

    @Override
    public void undo() {
        Memento prevState = savedStates.pop();
        applyStateActualImpl(prevState.getCellsState());
    }

    @Override
    public void applyInitialState() {
        saveState();
        applyInitialStateActualImpl();
    }

    public void applyInitialStateActualImpl() {
        Map<String, Map<Integer, Integer>> mapToSet = initialState.entrySet().stream()
                .collect(Collectors.toMap(entry -> entry.getKey(),
                        entry -> entry.getValue().stream().
                                collect(Collectors.toMap(Function.identity(),
                                        banknote -> 0))));
        atmMachine.applyState(mapToSet);
    }

    @Override
    public Object[] doEvent(Event event) {
        return event.execute(this);
    }
}
