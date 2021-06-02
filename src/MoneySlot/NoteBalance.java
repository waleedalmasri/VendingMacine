package MoneySlot;

public class NoteBalance {
    private int numberOf20Dollars;
    private int numberOf50Dollars;

    public NoteBalance() {
        this.numberOf20Dollars = 0;
        this.numberOf50Dollars = 0;
    }

    public int getNumberOf20Dollars() {
        return numberOf20Dollars;
    }

    public void setNumberOf20Dollars(int numberOf20Dollars) {
        this.numberOf20Dollars = numberOf20Dollars;
    }

    public int getNumberOf50Dollars() {
        return numberOf50Dollars;
    }

    public void setNumberOf50Dollars(int numberOf50Dollars) {
        this.numberOf50Dollars = numberOf50Dollars;
    }

    @Override
    public String toString() {
        return "NoteBalance{" +
                "numberOf20Dollars=" + numberOf20Dollars +
                ", numberOf50Dollars=" + numberOf50Dollars +
                '}';
    }
}
