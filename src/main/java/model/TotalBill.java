package model;

public class TotalBill {
    private String date;

    private Double total_money;

    public TotalBill() {
    }

    public TotalBill(String date, Double total_money) {
        this.date = date;
        this.total_money = total_money;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getTotal_money() {
        return total_money;
    }

    public void setTotal_money(Double total_money) {
        this.total_money = total_money;
    }

    @Override
    public String toString() {
        return "TotalBill{" +
                "date='" + date + '\'' +
                ", total_money=" + total_money +
                '}';
    }
}
