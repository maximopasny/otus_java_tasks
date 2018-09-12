package atm.machine;

import atm.exception.InvalidBanknotePassedException;
import atm.exception.UnsupportedCurrencyException;
import atm.operation.cash.ProduceCashToClientCalculator;
import atm.operation.cash.ProduceCashToClientCalculatorImpl;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ATMMachineImpl implements ATMMachine {

    private Map<String, Map<Integer, Integer>> cells = new HashMap<>();
    private ProduceCashToClientCalculator produceCashToClientCalculator = new ProduceCashToClientCalculatorImpl();

    public ATMMachineImpl(Map<String, Set<Integer>> currenciesSupported) {
        currenciesSupported.entrySet().stream()
                .forEach(currencyWithBanknotesSupported ->
                        storeBanknotesNewCurrency(currencyWithBanknotesSupported.getKey(),
                                currencyWithBanknotesSupported.getValue(),
                                new HashMap<>()));
    }

    @Override
    public Map<Integer, Integer> giveBanknotes(Long amount, String currency) {
        if (!cells.containsKey(currency)) {
            throw new UnsupportedCurrencyException();
        }
        var produceCashToClientCalculatorResult =
                produceCashToClientCalculator.produceCash(amount, cells.get(currency));
        cells.replace(currency, produceCashToClientCalculatorResult.getAfterOperationATMBalance());
        return produceCashToClientCalculatorResult.getCashToGive();
    }

    @Override
    public void takeBanknotes(String currency, Map<Integer, Integer> banknotesWithCount) {
        if (!cells.containsKey(currency)) {
            throw new UnsupportedCurrencyException();
        }

        storeBanknotesExistingCurrency(currency, banknotesWithCount);
    }

    @Override
    public Map<Integer, Integer> getBalance(String currency) {
        if (!cells.containsKey(currency)) {
            throw new UnsupportedCurrencyException();
        }

        return cells.get(currency);
    }

    private void storeBanknotesExistingCurrency(String currency, Map<Integer, Integer> banknotesWithCount) {
        Map<Integer, Integer> currencyCells = cells.get(currency);
        Map<Integer, Integer> currencyBalanceAfterUpdate = storeBanknotesToSpecificCells(currencyCells, banknotesWithCount);
        cells.replace(currency, currencyBalanceAfterUpdate);
    }

    private void storeBanknotesNewCurrency(String currency, Set<Integer> possibleBanknotes,
                                           Map<Integer, Integer> banknotesWithCount) {
        Map<Integer, Integer> initialCellBalance = possibleBanknotes.stream().collect(Collectors.toMap(Function.identity(), x -> 0));
        Map<Integer, Integer> currencyBalance = storeBanknotesToSpecificCells(initialCellBalance, banknotesWithCount);
        cells.put(currency, currencyBalance);
    }

    private Map<Integer, Integer> storeBanknotesToSpecificCells(Map<Integer, Integer> currentCellsBalance,
                                               Map<Integer, Integer> banknotesToStoreWithCount) {
        boolean cashPassedToStoreContainsUnknownBanknote =
                banknotesToStoreWithCount.keySet().stream()
                        .anyMatch(banknote -> !currentCellsBalance.keySet().contains(banknote));

        if (cashPassedToStoreContainsUnknownBanknote) {
            throw new InvalidBanknotePassedException();
        }

        banknotesToStoreWithCount.entrySet()
                .stream().forEach(entry -> currentCellsBalance.replace(entry.getKey(),
                entry.getValue() + currentCellsBalance.get(entry.getKey())));

        return currentCellsBalance;
    }
}
