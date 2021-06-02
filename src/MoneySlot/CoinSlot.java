package MoneySlot;

import Money.Coin;

public class CoinSlot {
    private CoinsBalance coinsBalance;

    public CoinSlot() {
        this.coinsBalance = new CoinsBalance();
    }

    public CoinsBalance getCoinsBalance() {
        return coinsBalance;
    }

    public void setCoinsBalance(CoinsBalance coinsBalance) {
        this.coinsBalance = coinsBalance;
    }

    public void updateCoinsBalance(Coin coin) {

        switch (coin.getCategory()) {
            case 'c':
                switch (coin.getCoinValue()) {
                    case 10:
                        this.coinsBalance.setNumberOf10c(this.getCoinsBalance().getNumberOf10c() + 1);
                        break;
                    case 20:
                        this.coinsBalance.setNumberOf20c(this.getCoinsBalance().getNumberOf20c() + 1);
                        break;
                    case 50:
                        this.coinsBalance.setNumberOf50c(this.getCoinsBalance().getNumberOf50c() + 1);
                        break;
                }
                break;
            case '$': {
                this.coinsBalance.setNumberOf1Dollar(this.coinsBalance.getNumberOf1Dollar() + 1);
                break;
            }

        }

    }
}
