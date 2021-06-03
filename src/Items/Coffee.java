package Items;

public class Coffee extends Item {
    private String coffeeType;
    private char cupSize;

    public Coffee(String name, String description, double purchasingPrice, double sellingPrice, int availableItems, String coffeeType, char cupSize) {
        super(name, description, purchasingPrice, sellingPrice, availableItems);
        this.coffeeType = coffeeType;
        this.cupSize = cupSize;
    }

    public String getCoffeeType() {
        return coffeeType;
    }

    public void setCoffeeType(String coffeeType) {
        this.coffeeType = coffeeType;
    }

    public char getCupSize() {
        return cupSize;
    }

    public void setCupSize(char cupSize) {
        this.cupSize = cupSize;
    }

    @Override
    public void calculateProfit(double purchasingPrice, double sellingPrice) {
        switch (this.getCupSize()) {
            case 's':
                this.setProfit((purchasingPrice - sellingPrice) * 1);
                break;
            case 'm':
                this.setProfit((purchasingPrice - sellingPrice) * 2);
                break;
            case 'l':
                this.setProfit((purchasingPrice - sellingPrice) * 3);
                break;
            default:
                System.out.println("Unrecognized cup size");
        }


    }
}
