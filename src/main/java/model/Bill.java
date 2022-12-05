package model;

import java.util.ArrayList;

public class Bill {

    private String bill_id;
    private String table_id;
    private String employee_id;

    private String employeeName;
    private Double total;
    private String status;
    private ArrayList<Dish> dishList;
    private String customerName;
    private String customerPhone;
    private String date;

    private String client_id;

    public Bill() {
    }

    public Bill(String table_id, String employee_id, Double total, String status,
                ArrayList<Dish> dishList, String customerName, String customerPhone) {
        this.table_id = table_id;
        this.employee_id = employee_id;
        this.total = total;
        this.status = status;
        this.dishList = dishList;
        this.customerName = customerName;
        this.customerPhone = customerPhone;
    }

    public String getTable_id() {
        return table_id;
    }

    public void setTable_id(String table_id) {
        this.table_id = table_id;
    }

    public String getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(String employee_id) {
        this.employee_id = employee_id;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<Dish> getDishList() {
        return dishList;
    }

    public void setDishList(ArrayList<Dish> dishList) {
        this.dishList = dishList;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getBill_id() {
        return bill_id;
    }

    public void setBill_id(String bill_id) {
        this.bill_id = bill_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    @Override
    public String toString() {
        return "Bill{" +
                "bill_id='" + bill_id + '\'' +
                ", table_id='" + table_id + '\'' +
                ", employee_id='" + employee_id + '\'' +
                ", employeeName='" + employeeName + '\'' +
                ", total=" + total +
                ", status='" + status + '\'' +
                ", dishList=" + dishList +
                ", customerName='" + customerName + '\'' +
                ", customerPhone='" + customerPhone + '\'' +
                ", date='" + date + '\'' +
                ", client_id='" + client_id + '\'' +
                '}';
    }
}
