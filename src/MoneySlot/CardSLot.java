package MoneySlot;

public class CardSLot {
    private CardBalance cardBalance;

    public CardSLot( ) {
        this.cardBalance = new CardBalance();
    }

    public CardBalance getCardBalance() {
        return cardBalance;
    }

    public void setCardBalance(CardBalance cardBalance) {
        this.cardBalance = cardBalance;
    }
}
