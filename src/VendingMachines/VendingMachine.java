package VendingMachines;

import Items.Item;
import Money.Money;
import MoneySlot.MoneySlot;
import MoneySlot.NoteBalance;
import MoneySlot.CoinsBalance;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;

public abstract class VendingMachine {
    private MoneySlot currentBalance;
    private boolean ready;
    private double change;
    private MoneySlot moneySlot;
    private ArrayList<Transaction> transactionsList;

    public VendingMachine(MoneySlot currentBalance, MoneySlot moneySlot) {
        this.currentBalance = currentBalance;
        this.currentBalance.calculateBalanceInUSD();
        this.moneySlot = moneySlot;
        this.ready = isReady();
        this.transactionsList = new ArrayList<Transaction>();
        this.change = 0.0;
    }

    public boolean isReady() {
        return currentBalance.getBalanceInUSD() >= 0.5;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    public double getChange() {
        return change;
    }

    public void setChange(double change) {
        this.change = change;
    }

    public MoneySlot getMoneySlot() {
        return moneySlot;
    }

    public void setMoneySlot(MoneySlot moneySlot) {
        this.moneySlot = moneySlot;
    }

    public ArrayList<Transaction> getTransactionsList() {
        return transactionsList;
    }

    public void setTransactionsList(ArrayList<Transaction> transactionsList) {
        this.transactionsList = transactionsList;
    }

    public MoneySlot getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(MoneySlot currentBalance) {
        this.currentBalance = currentBalance;
    }


    public boolean validateChange() {

        if (currentBalance.getBalanceInUSD() >= this.change && this.getChange() > 0) {
            double remainingChange = this.getChange();
            MoneySlot tempSlot = new MoneySlot("USD");
            tempSlot.getNoteSlot().getNoteBalance().setNumberOf50Dollars(currentBalance.getNoteSlot().getNoteBalance().getNumberOf50Dollars());
            tempSlot.getNoteSlot().getNoteBalance().setNumberOf20Dollars(currentBalance.getNoteSlot().getNoteBalance().getNumberOf20Dollars());

            tempSlot.getCoinSlot().getCoinsBalance().setNumberOf1Dollar(currentBalance.getCoinSlot().getCoinsBalance().getNumberOf1Dollar());
            tempSlot.getCoinSlot().getCoinsBalance().setNumberOf50c(currentBalance.getCoinSlot().getCoinsBalance().getNumberOf50c());
            tempSlot.getCoinSlot().getCoinsBalance().setNumberOf20c(currentBalance.getCoinSlot().getCoinsBalance().getNumberOf20c());
            tempSlot.getCoinSlot().getCoinsBalance().setNumberOf10c(currentBalance.getCoinSlot().getCoinsBalance().getNumberOf10c());


            NoteBalance currentNoteBalance = tempSlot.getNoteSlot().getNoteBalance();
            CoinsBalance currentCoinBalance = tempSlot.getCoinSlot().getCoinsBalance();
            int numberOfUnitsToBeRemoved = 0;
            double valueOfUnitsToBeRemoved = 0.0;
            System.out.println(this.currentBalance.getCoinSlot().getCoinsBalance().toString());
            //-------------------------------------------------

            numberOfUnitsToBeRemoved = (int) (remainingChange / 50.0);
            if (currentNoteBalance.getNumberOf50Dollars() >= numberOfUnitsToBeRemoved) {
                valueOfUnitsToBeRemoved = numberOfUnitsToBeRemoved * 50.0;
                currentNoteBalance.setNumberOf50Dollars(currentNoteBalance.getNumberOf50Dollars() - numberOfUnitsToBeRemoved);
                remainingChange -= valueOfUnitsToBeRemoved;
            } else if (numberOfUnitsToBeRemoved > currentNoteBalance.getNumberOf50Dollars()) {
                valueOfUnitsToBeRemoved = currentNoteBalance.getNumberOf50Dollars() * 50.0;
                currentNoteBalance.setNumberOf50Dollars(0);
                remainingChange -= valueOfUnitsToBeRemoved;
            }
            //-------------------------------------------------

            numberOfUnitsToBeRemoved = (int) (remainingChange / 20.0);
            if (currentNoteBalance.getNumberOf20Dollars() >= numberOfUnitsToBeRemoved) {
                valueOfUnitsToBeRemoved = numberOfUnitsToBeRemoved * 20.0;
                currentNoteBalance.setNumberOf20Dollars(currentNoteBalance.getNumberOf20Dollars() - numberOfUnitsToBeRemoved);
                remainingChange -= valueOfUnitsToBeRemoved;
            } else if (numberOfUnitsToBeRemoved > currentNoteBalance.getNumberOf20Dollars()) {
                valueOfUnitsToBeRemoved = currentNoteBalance.getNumberOf20Dollars() * 20.0;
                currentNoteBalance.setNumberOf20Dollars(0);
                remainingChange -= valueOfUnitsToBeRemoved;
            }

            //-----------------------------------------------
            numberOfUnitsToBeRemoved = (int) (remainingChange / 1.0);
            if (currentCoinBalance.getNumberOf1Dollar() >= numberOfUnitsToBeRemoved) {
                valueOfUnitsToBeRemoved = numberOfUnitsToBeRemoved * 1.0;
                currentCoinBalance.setNumberOf1Dollar(currentCoinBalance.getNumberOf1Dollar() - numberOfUnitsToBeRemoved);
                remainingChange -= valueOfUnitsToBeRemoved;
            } else if (numberOfUnitsToBeRemoved > currentCoinBalance.getNumberOf1Dollar()) {
                valueOfUnitsToBeRemoved = currentCoinBalance.getNumberOf1Dollar() * 1.0;
                currentCoinBalance.setNumberOf1Dollar(0);
                remainingChange -= valueOfUnitsToBeRemoved;

            }


            //-----------------------------------------------
            numberOfUnitsToBeRemoved = (int) (remainingChange / 0.5);
            if (currentCoinBalance.getNumberOf50c() >= numberOfUnitsToBeRemoved) {
                valueOfUnitsToBeRemoved = numberOfUnitsToBeRemoved * 0.5;
                currentCoinBalance.setNumberOf50c(currentCoinBalance.getNumberOf50c() - numberOfUnitsToBeRemoved);
                remainingChange -= valueOfUnitsToBeRemoved;
            } else if (numberOfUnitsToBeRemoved > currentCoinBalance.getNumberOf50c()) {
                valueOfUnitsToBeRemoved = currentCoinBalance.getNumberOf50c() * 0.5;
                currentCoinBalance.setNumberOf50c(0);
                remainingChange -= valueOfUnitsToBeRemoved;

            }

            //-----------------------------------------------
            numberOfUnitsToBeRemoved = (int) (remainingChange / 0.2);
            if (currentCoinBalance.getNumberOf20c() >= numberOfUnitsToBeRemoved) {
                valueOfUnitsToBeRemoved = numberOfUnitsToBeRemoved * 0.2;
                currentCoinBalance.setNumberOf20c(currentCoinBalance.getNumberOf20c() - numberOfUnitsToBeRemoved);
                remainingChange -= valueOfUnitsToBeRemoved;
            } else if (numberOfUnitsToBeRemoved > currentCoinBalance.getNumberOf20c()) {
                valueOfUnitsToBeRemoved = currentCoinBalance.getNumberOf20c() * 0.2;
                currentCoinBalance.setNumberOf20c(0);
                remainingChange -= valueOfUnitsToBeRemoved;

            }

            //-----------------------------------------------
            numberOfUnitsToBeRemoved = (int) (remainingChange / 0.1);
            if (currentCoinBalance.getNumberOf10c() >= numberOfUnitsToBeRemoved) {
                valueOfUnitsToBeRemoved = numberOfUnitsToBeRemoved * 0.1;
                currentCoinBalance.setNumberOf10c(currentCoinBalance.getNumberOf10c() - numberOfUnitsToBeRemoved);
                remainingChange -= valueOfUnitsToBeRemoved;
            } else if (numberOfUnitsToBeRemoved > currentCoinBalance.getNumberOf10c()) {
                valueOfUnitsToBeRemoved = currentCoinBalance.getNumberOf10c() * 0.1;
                currentCoinBalance.setNumberOf10c(0);
                remainingChange -= valueOfUnitsToBeRemoved;

            }


            if (remainingChange == 0.0) {
                this.currentBalance = tempSlot;
                this.currentBalance.calculateBalanceInUSD();
                this.setChange(0.0);
                tempSlot.calculateBalanceInUSD();
                return true;
            }
        }
        if (this.getChange() == 0) {
            setReady(true);
            return true;
        }
        setReady(false);
        return false;
    }

    public void returnMoney() {
        this.setChange(0.0);
        NoteBalance currentNoteBalance = this.getCurrentBalance().getNoteSlot().getNoteBalance();
        CoinsBalance currentCoinBalance = this.getCurrentBalance().getCoinSlot().getCoinsBalance();

        NoteBalance toBeCanceledNotes = this.getMoneySlot().getNoteSlot().getNoteBalance();
        CoinsBalance toBeCanceledCoins = this.getMoneySlot().getCoinSlot().getCoinsBalance();

        if (currentNoteBalance.getNumberOf20Dollars() >= toBeCanceledNotes.getNumberOf20Dollars())
            currentNoteBalance.setNumberOf20Dollars(currentNoteBalance.getNumberOf20Dollars() - toBeCanceledNotes.getNumberOf20Dollars());

        if (currentNoteBalance.getNumberOf50Dollars() >= toBeCanceledNotes.getNumberOf50Dollars())
            currentNoteBalance.setNumberOf50Dollars(currentNoteBalance.getNumberOf50Dollars() - toBeCanceledNotes.getNumberOf50Dollars());

        if (currentCoinBalance.getNumberOf1Dollar() > toBeCanceledCoins.getNumberOf1Dollar())
            currentCoinBalance.setNumberOf1Dollar(currentCoinBalance.getNumberOf1Dollar() - toBeCanceledCoins.getNumberOf1Dollar());

        if (currentCoinBalance.getNumberOf50c() > toBeCanceledCoins.getNumberOf50c())
            currentCoinBalance.setNumberOf50c(currentCoinBalance.getNumberOf50c() - toBeCanceledCoins.getNumberOf50c());

        if (currentCoinBalance.getNumberOf20c() > toBeCanceledCoins.getNumberOf20c())
            currentCoinBalance.setNumberOf20c(currentCoinBalance.getNumberOf20c() - toBeCanceledCoins.getNumberOf20c());

        if (currentCoinBalance.getNumberOf10c() > toBeCanceledCoins.getNumberOf10c())
            currentCoinBalance.setNumberOf10c(currentCoinBalance.getNumberOf10c() - toBeCanceledCoins.getNumberOf10c());

        this.clearMoneySlot();

        this.currentBalance.calculateBalanceInUSD();
        this.moneySlot.calculateBalanceInUSD();
    }

    public void clearMoneySlot() {
        NoteBalance toBeCanceledNotes = this.getMoneySlot().getNoteSlot().getNoteBalance();
        CoinsBalance toBeCanceledCoins = this.getMoneySlot().getCoinSlot().getCoinsBalance();
        toBeCanceledNotes.setNumberOf50Dollars(0);
        toBeCanceledNotes.setNumberOf20Dollars(0);
        toBeCanceledCoins.setNumberOf1Dollar(0);
        toBeCanceledCoins.setNumberOf50c(0);
        toBeCanceledCoins.setNumberOf20c(0);
        toBeCanceledCoins.setNumberOf10c(0);
    }

    public abstract boolean purchase(int rowIndex, int colIndex);

    public abstract void calculateChange(Item item);

    public abstract boolean validateMoney(Money money);

    public abstract boolean acceptMoney(Item item);
}
