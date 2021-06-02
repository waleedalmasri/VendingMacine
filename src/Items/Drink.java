package Items;

public class Drink extends Item {
    private int sizeInMill;

    public Drink(String name, String description, double purchasingPrice, double sellingPrice, int availableItems, int sizeInMill) {
        super(name, description, purchasingPrice, sellingPrice, availableItems);
        this.sizeInMill = sizeInMill;
    }

    public int getSizeInMill() {
        return sizeInMill;
    }

    public void setSizeInMill(int sizeInMill) {
        this.sizeInMill = sizeInMill;
    }

    @Override
    public void calculateProfit(double purchasingPrice, double sellingPrice) {
        switch (this.getSizeInMill()) {
            case 150:
                this.setProfit((purchasingPrice - sellingPrice) * 1);
            case 250:
                this.setProfit((purchasingPrice - sellingPrice) * 2);
            case 300:
                this.setProfit((purchasingPrice - sellingPrice) * 3);
            default:
                System.out.println("Unrecognized drink size");
        }
    }
}
