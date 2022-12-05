package tab;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Bill;
import model.Dish;

import java.util.ArrayList;

public class BillDetailsController {
    @FXML
    private Label customer;

    @FXML
    private Label date;

    @FXML
    private TableColumn<Dish, Integer> dishAmountColumn;

    @FXML
    private TableColumn<Dish, String> dishIdColumn;

    @FXML
    private TableColumn<Dish, String> dishNameColumn;

    @FXML
    private TableColumn<Dish, Double> dishPriceColumn;

    @FXML
    private TableView<Dish> dishTable;

    @FXML
    private Label employee;

    @FXML
    private Label status;

    @FXML
    private Label tableId;

    @FXML
    private Label totalBill;

    @FXML
    private TableColumn<Dish, Double> totalPriceColumn;

    ObservableList<Dish> dishList = FXCollections.observableArrayList();

    public void setData(Bill bill) {
        tableId.setText(bill.getTable_id());
        employee.setText(bill.getEmployeeName());
        customer.setText(bill.getCustomerName());
        date.setText(bill.getDate());
        status.setText(bill.getStatus());
        totalBill.setText(bill.getTotal().toString());
        dishList.clear();
        dishList.addAll(bill.getDishList());
        //set column
        dishIdColumn.setCellValueFactory(new PropertyValueFactory<>("dish_id"));
        dishNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        dishPriceColumn.setCellValueFactory(new PropertyValueFactory<>("dish_price"));
        dishAmountColumn.setCellValueFactory(new PropertyValueFactory<>("qty"));
        totalPriceColumn.setCellValueFactory(new PropertyValueFactory<>("total_price"));
        dishTable.setItems(dishList);
    }

}
