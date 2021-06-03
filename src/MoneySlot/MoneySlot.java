package MoneySlot;

import Money.Money;
import Money.Coin;
import Money.Note;

public class MoneySlot {
    private String currency;
    private CoinSlot coinSlot;
    private NoteSlot noteSlot;
    private CardSLot cardSLot;
    private double balanceInUSD;

    public MoneySlot(String currency) {
        this.currency = currency;
        this.balanceInUSD = 0.0;
        this.coinSlot = new CoinSlot();
        this.noteSlot = new NoteSlot();
        this.cardSLot = new CardSLot();

    }

    public CardSLot getCardSLot() {
        return cardSLot;
    }

    public void setCardSLot(CardSLot cardSLot) {
        this.cardSLot = cardSLot;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public CoinSlot getCoinSlot() {
        return coinSlot;
    }

    public void setCoinSlot(CoinSlot coinSlot) {
        this.coinSlot = coinSlot;
    }

    public NoteSlot getNoteSlot() {
        return noteSlot;
    }

    public void setNoteSlot(NoteSlot noteSlot) {
        this.noteSlot = noteSlot;
    }

    public double getBalanceInUSD() {
        return balanceInUSD;
    }

    public void setBalanceInUSD(double balanceInUSD) {
        this.balanceInUSD = balanceInUSD;
    }

    public void updateBalance(Money money) {
        if (money instanceof Coin) {
            Coin c = (Coin) money;
            this.coinSlot.updateCoinsBalance(c);
        } else if (money instanceof Note) {
            Note n = (Note) money;
            this.noteSlot.updateNoteBalance(n);
        }
        this.calculateBalanceInUSD();
    }

    public void calculateBalanceInUSD() {
        double coinsSum = ((
                this.getCoinSlot().getCoinsBalance().getNumberOf1Dollar() * 1)
                + (this.getCoinSlot().getCoinsBalance().getNumberOf10c() * 0.1)
                + (this.getCoinSlot().getCoinsBalance().getNumberOf20c() * 0.2)
                + (this.getCoinSlot().getCoinsBalance().getNumberOf50c() * 0.5));

        double noteSum = ((
                this.noteSlot.getNoteBalance().getNumberOf20Dollars() * 20.0)
                + (this.noteSlot.getNoteBalance().getNumberOf50Dollars() * 50.0));

        this.setBalanceInUSD(coinsSum + noteSum);


    }
}
