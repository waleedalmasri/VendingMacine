package MoneySlot;

public class CardBalance {
    private double collectedFromCard;

    public CardBalance() {
        this.collectedFromCard = 0.0;
    }

    public double getCollectedFromCard() {
        return collectedFromCard;
    }

    public void setCollectedFromCard(double collectedFromCard) {
        this.collectedFromCard = collectedFromCard;
    }

    @Override
    public String toString() {
        return "CardBalance{\n" +
                "\ncollectedFromCard=" + collectedFromCard +
                '}';
    }
}
