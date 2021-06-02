package Items;

public class Snack extends Item {
    private int weightInGM;


    public Snack(String name, String description, double purchasingPrice, double sellingPrice, int availableItems, int weightInGM) {
        super(name, description, purchasingPrice, sellingPrice, availableItems);
        this.weightInGM = weightInGM;
    }

    public int getWeightInGM() {
        return weightInGM;
    }

    public void setWeightInGM(int weightInGM) {
        this.weightInGM = weightInGM;
    }

    @Override
    public void calculateProfit(double purchasingPrice, double sellingPrice) {
        switch (this.getWeightInGM()) {
            case 20:
                this.setProfit((purchasingPrice - sellingPrice) * 1);
            case 30:
                this.setProfit((purchasingPrice - sellingPrice) * 2);
            case 50:
                this.setProfit((purchasingPrice - sellingPrice) * 3);
            default:
                System.out.println("Unrecognized snack weight");
        }
    }
}
