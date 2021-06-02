package Money;

public class Coin extends Money {
    private int coinValue;

    public Coin(String currency, char category, int coinValue) {
        super(currency, category);
        this.coinValue = coinValue;
    }

    public int getCoinValue() {
        return coinValue;

    }

    public void setCoinValue(int coinValue) {
        this.coinValue = coinValue;
    }
}
