package tab;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import model.*;
import table.Table;
import table.tableDB;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DashBoardTabController implements Initializable {

    @FXML
    private Label availableTable;

    @FXML
    private Label inUsedTable;
    @FXML
    private DatePicker billDate;

    @FXML
    private Label numberOfBills;

    @FXML
    private Label totalMoney;

    @FXML
    private Label employee;
    @FXML
    private LineChart<?, ?> lineChart;
    @FXML
    private NumberAxis unitY;
    @FXML
    private CategoryAxis billX;
    @FXML
    private Label dish;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        availableTable.setText("0");
        inUsedTable.setText("0");
        numberOfBills.setText("0");
        totalMoney.setText("Total: 0");
        employee.setText("0");
        dish.setText("0");
        //data
        tableDB tabledb = new tableDB();
        BillDB billDB = new BillDB();
        TotalBillDB totalBillDB = new TotalBillDB();
        DishDB dishDB = new DishDB();
        employeeDB employeeDB = new employeeDB();
        ArrayList<Dish> dishList = dishDB.getAllDish();
        ArrayList<Table> tableList = tabledb.getAllTable();
        ArrayList<Bill> billList = billDB.getAllBill();
        ArrayList<TotalBill> totalBillList = totalBillDB.getAllTotalBill();
        ArrayList<Employee> employeeList = employeeDB.getAllEmployee();

        tableList.forEach(table -> {
            if (table.getTableStatus().equalsIgnoreCase("available")) {
                availableTable.setText(String.valueOf(Integer.parseInt(availableTable.getText()) + 1));
            } else if (table.getTableStatus().equalsIgnoreCase("in used")) {
                inUsedTable.setText(String.valueOf(Integer.parseInt(inUsedTable.getText()) + 1));
            }
        });

        ArrayList<DaySales> daySales = new ArrayList<>();
        totalBillList.forEach(totalBill -> {
            DaySales daySale = new DaySales();
            daySale.setDate(totalBill.getDate());
            daySale.setTotalMoney(totalBill.getTotal_money());
            daySale.setNumberOfBills(0);
            billList.forEach(bill -> {
                if (bill.getDate().contains(totalBill.getDate())) {
                    daySale.setNumberOfBills(daySale.getNumberOfBills() + 1);
                }
            });
            daySales.add(daySale);
        });
        billDate.setOnAction(actionEvent -> {
            numberOfBills.setText("0");
            totalMoney.setText("Total: 0");
            daySales.forEach(daySale -> {
                if (daySale.getDate().contains(billDate.getValue().toString())) {
                    numberOfBills.setText(String.valueOf(daySale.getNumberOfBills()));
                    totalMoney.setText("Total: " + daySale.getTotalMoney() + "$");
                }
            });
        });
        billDate.setValue(LocalDate.now());

        if (employeeList != null && dishList != null) {
            employee.setText(String.valueOf(employeeList.size()));
            dish.setText(String.valueOf(dishList.size()));
        }
        //line chart
        lineChart.getData().clear();
        XYChart.Series set1 = new XYChart.Series<>();
        daySales.forEach(daySale -> {
            set1.getData().add(new XYChart.Data(daySale.getDate(), daySale.getTotalMoney()));
        });
        lineChart.getData().addAll(set1);


    }

}

 class DaySales {
    private String date;
    private double totalMoney;
    private Integer numberOfBills;

    public DaySales(String date, double total) {
        this.date = date;
        this.totalMoney = total;
    }

     public DaySales() {
     }

     public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getTotal() {
        return totalMoney;
    }

    public void setTotal(double total) {
        this.totalMoney = total;
    }

     public double getTotalMoney() {
         return totalMoney;
     }

     public void setTotalMoney(double totalMoney) {
         this.totalMoney = totalMoney;
     }

     public Integer getNumberOfBills() {
         return numberOfBills;
     }

     public void setNumberOfBills(Integer numberOfBills) {
         this.numberOfBills = numberOfBills;
     }

     @Override
     public String toString() {
         return "DaySales{" +
                 "date='" + date + '\'' +
                 ", totalMoney=" + totalMoney +
                 ", numberOfBills=" + numberOfBills +
                 '}';
     }
 }
