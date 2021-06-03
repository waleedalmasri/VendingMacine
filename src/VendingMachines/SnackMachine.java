package VendingMachines;

import Items.Item;
import Items.Snack;
import Money.Coin;
import Money.Money;
import Money.Note;
import MoneySlot.MoneySlot;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.Stack;

public class SnackMachine extends VendingMachine implements SnackMachineOperations {
    private Stack<Snack>[][] snacks;
    private int remainingSnacks;

    public SnackMachine(MoneySlot currentBalance, MoneySlot moneySlot) throws IOException {
        super(currentBalance, moneySlot);
        this.snacks = new Stack[4][2];
        for (int row = 0; row < 4; row++)
            for (int col = 0; col < 2; col++)
                snacks[row][col] = new Stack<Snack>();
        this.refillStock();
        this.refillBalance();

    }

    public Stack<Snack>[][] getSnacks() {
        return snacks;
    }

    public void setSnacks(Stack<Snack>[][] snacks) {
        this.snacks = snacks;
    }

    public int getRemainingSnacks() {
        return remainingSnacks;
    }

    public void setRemainingSnacks(int remainingSnacks) {
        this.remainingSnacks = remainingSnacks;
    }

    @Override
    public void refillStock() throws IOException {
        BufferedReader snacksCSVReader = new BufferedReader(new FileReader("snacks.csv"));
        String line;
        int rowIndex = 0;
        int colIndex = 0;

        while ((line = snacksCSVReader.readLine()) != null) {
            String[] data = line.split(",");
            Snack newSnack = new Snack(data[0].trim(), data[1].trim(), Double.parseDouble(data[2].trim()), Double.parseDouble(data[3].trim()), Integer.parseInt(data[4].trim()), Integer.parseInt(data[5].trim()));
            this.setRemainingSnacks(this.getRemainingSnacks() + newSnack.getAvailableItems());
            for (int i = 0; i < newSnack.getAvailableItems(); i++) {
                snacks[rowIndex][colIndex].push(newSnack);
            }

            colIndex++;
            if (colIndex == 2) {
                if (rowIndex < 4) {
                    rowIndex++;
                    colIndex = 0;
                }

            }

        }

        snacksCSVReader.close();

    }

    @Override
    public void refillBalance() throws IOException {
        BufferedReader snacksCSVReader = new BufferedReader(new FileReader("money.csv"));
        String line;

        while ((line = snacksCSVReader.readLine()) != null) {
            String[] data = line.split(",");
            if (data[1].trim().charAt(0) == 'c' || Integer.parseInt(data[2].trim()) == 1) {
                Coin newCoin = new Coin(data[0].trim(), data[1].trim().charAt(0), Integer.parseInt(data[2].trim()));
                if (validateMoney(newCoin))
                    this.getCurrentBalance().updateBalance(newCoin);
                else
                    System.out.println("USD only");
            } else {
                Note newNote = new Note(data[0].trim(), data[1].trim().charAt(0), Integer.parseInt(data[2].trim()));
                if (validateMoney(newNote))
                    this.getCurrentBalance().updateBalance(newNote);
                else
                    System.out.println("USD only");
            }

        }

        snacksCSVReader.close();

    }


    @Override
    public boolean purchase(int rowIndex, int colIndex) {
        if (validateChange()) {
            snacks[rowIndex][colIndex].peek().calculateProfit(snacks[rowIndex][colIndex].peek().getSellingPrice(), snacks[rowIndex][colIndex].peek().getPurchasingPrice());
            snacks[rowIndex][colIndex].peek().setAvailableItems(snacks[rowIndex][colIndex].peek().getAvailableItems() - 1);
            getTransactionsList().add(new Transaction(snacks[rowIndex][colIndex].peek().getName() + " Sold Successfully", snacks[rowIndex][colIndex].peek().getProfit(), new Date()));
            snacks[rowIndex][colIndex].pop();
            return true;
        }

        return false;

    }

    public boolean purchaseWithCard(int rowIndex, int colIndex) {
        this.getMoneySlot().getCardSLot().getCardBalance().setCollectedFromCard(this.getSnacks()[rowIndex][colIndex].peek().getSellingPrice());
        snacks[rowIndex][colIndex].peek().calculateProfit(snacks[rowIndex][colIndex].peek().getSellingPrice(), snacks[rowIndex][colIndex].peek().getPurchasingPrice());
        snacks[rowIndex][colIndex].peek().setAvailableItems(snacks[rowIndex][colIndex].peek().getAvailableItems() - 1);
        getTransactionsList().add(new Transaction(snacks[rowIndex][colIndex].peek().getName() + " Sold Successfully", snacks[rowIndex][colIndex].peek().getProfit(), new Date()));
        snacks[rowIndex][colIndex].pop();
        this.getCurrentBalance().getCardSLot().getCardBalance().setCollectedFromCard(this.getMoneySlot().getCardSLot().getCardBalance().getCollectedFromCard());
        return true;
    }

    @Override
    public void calculateChange(Item item) {
        if (this.getMoneySlot().getBalanceInUSD() > item.getSellingPrice())
            this.setChange(this.getMoneySlot().getBalanceInUSD() - item.getSellingPrice());
    }

    @Override
    public boolean validateMoney(Money money) {
        return money.getCurrency().equals("USD");
    }

    @Override
    public boolean acceptMoney(Item item) {
        return this.getMoneySlot().getBalanceInUSD() >= item.getSellingPrice();
    }
}
