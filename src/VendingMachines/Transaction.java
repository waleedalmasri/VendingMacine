package VendingMachines;

import java.util.Date;

public class Transaction {
    private String description;
    private double profit;
    private Date date;

    public Transaction(String description, double profit, Date date) {
        this.description = description;
        this.profit = profit;
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
