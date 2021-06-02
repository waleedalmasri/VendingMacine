package Items;

public abstract class Item {
    private String name;
    private String description;
    private double purchasingPrice;
    private double sellingPrice;
    private double profit;
    private int availableItems;

    public Item(String name, String description, double purchasingPrice, double sellingPrice, int availableItems) {
        this.name = name;
        this.description = description;
        this.purchasingPrice = purchasingPrice;
        this.sellingPrice = sellingPrice;
        this.availableItems = availableItems;
    }

    public int getAvailableItems() {
        return availableItems;
    }

    public void setAvailableItems(int availableItems) {
        this.availableItems = availableItems;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPurchasingPrice() {
        return purchasingPrice;
    }

    public void setPurchasingPrice(double purchasingPrice) {
        this.purchasingPrice = purchasingPrice;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }
    public abstract void calculateProfit(double purchasingPrice,double sellingPrice);
}
