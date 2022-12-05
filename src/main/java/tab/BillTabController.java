package tab;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import model.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class BillTabController implements Initializable {


    @FXML
    private TableView<Bill> billTable;

    @FXML
    private TableColumn<Bill, String> bill_id;

    @FXML
    private TableColumn<Bill, String> customerName;

    @FXML
    private TableColumn<Bill, String> date;

    @FXML
    private TableColumn<Bill, String> employeeName;

    @FXML
    private JFXButton refreshButton;

    @FXML
    private TextField searchTextField;

    @FXML
    private TableColumn<Bill, String> status;

    @FXML
    private TableColumn<Bill, String> table_id;

    @FXML
    private TableColumn<Bill, Double> total_bill;

    @FXML
    private DatePicker dateField;

    @FXML
    void refreshTable(ActionEvent event) {
        billList.clear();
        dateField.setValue(null);
        searchTextField.setText("");
        BillDB billDB = new BillDB();
        employeeDB employeeDB = new employeeDB();
        CustomerDB customerDB = new CustomerDB();
        ArrayList<Employee> emps = employeeDB.getAllEmployee();
        ArrayList<Bill> bills = billDB.getAllBill();
        ArrayList<Customer> customers = customerDB.getAllCustomer();
        bills.forEach(bill->{
            emps.forEach(emp->{
                if (bill.getEmployee_id().equals(Integer.toString(emp.getUserid()))){
                    bill.setEmployeeName(emp.getName());
                }
            });
            if (bill.getEmployeeName() == null) {
                bill.setEmployeeName("admin");
            }
            customers.forEach(customer -> {
                if (bill.getClient_id().equals(customer.getClient_id())){
                    bill.setCustomerName(customer.getName());
                }
            });
        });
        billList.addAll(bills);
        billTable.refresh();
    }



    @FXML
    void viewBillDetails(ActionEvent event) {
        //open dialog pane bill details
        Bill bill = billTable.getSelectionModel().getSelectedItem();
        if (bill != null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/billDetails.fxml"));
            DialogPane dialogPane = null;
            try {
                dialogPane = loader.load();
            } catch (Exception e) {
                e.printStackTrace();
            }
            BillDetailsController billDetailsController = loader.getController();
            billDetailsController.setData(bill);
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);
            dialog.setHeaderText("Bill Details");
            dialog.show();
        }

    }

    private final ObservableList<Bill> billList = FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //set table data
        bill_id.setCellValueFactory(new PropertyValueFactory<>("bill_id"));
        table_id.setCellValueFactory(new PropertyValueFactory<>("table_id"));
        employeeName.setCellValueFactory(new PropertyValueFactory<>("employeeName"));
        total_bill.setCellValueFactory(new PropertyValueFactory<>("total"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        customerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        refreshButton.fire();
        //dynamic search table
        FilteredList<Bill> filteredData = new FilteredList<>(billList, b -> true);
        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(bill -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (bill.getEmployeeName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (bill.getStatus().contains(lowerCaseFilter)) {
                    return true;
                } else return bill.getCustomerName().contains(lowerCaseFilter);
            });
        });
        dateField.valueProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(bill -> {
                if (newValue == null) {
                    return true;
                }
                String lowerCaseFilter = newValue.toString();
                return bill.getDate().contains(lowerCaseFilter);
            });
        });
        SortedList<Bill> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(billTable.comparatorProperty());
        billTable.setItems(sortedData);

    }
    

}
