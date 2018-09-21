package department.common.memento;

import java.util.Map;


public class Memento {
    private final Map<String, Map<Integer, Integer>> cellsState;

    public Memento(Map<String, Map<Integer, Integer>> cellsState) {
        this.cellsState = cellsState;
    }

    public Map<String, Map<Integer, Integer>> getCellsState() {
        return cellsState;
    }
}
