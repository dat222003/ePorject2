package tab;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.util.converter.IntegerStringConverter;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class NewBillController implements Initializable {


    @FXML
    private JFXButton createBillButton;

    @FXML
    private Label dateNow;

    @FXML
    private Label employeeId;

    @FXML
    private Label tableId;

    @FXML
    private Label totalBill;

    @FXML
    private TableView<Dish> dishTable;

    @FXML
    private TableColumn<Dish, ?> imageColumn;

    @FXML
    private TableColumn<Dish, String> nameColumn;

    @FXML
    private TableColumn<Dish, String> priceColumn;

    @FXML
    private TableColumn<Dish, String> dishIdColumn;

    @FXML
    private TableColumn<Dish, String> categoryColumn;

    @FXML
    private TextField searchTextField;

    @FXML
    private TableView<Dish> addedDishTable;

    @FXML
    private TableColumn<Dish, String> addedDishId;

    @FXML
    private TableColumn<Dish, String> addedDish;

    @FXML
    private TableColumn<Dish, Integer> qty;

    @FXML
    private TableColumn<Dish, Double> price;

    @FXML
    private TextField customerName;

    @FXML
    private TextField customerPhone;

    @FXML
    private HBox tableIdBox;

    @FXML
    private Label billId;

    @FXML
    private JFXButton payBillButton;


    @FXML
    void createNewBill(ActionEvent event) {
        if (!validateBill()) {
            return;
        }
        if (Objects.equals(createBillButton.getText(), "Create Bill")) {
            createBill();
        } else {
            updateBill();
        }
    }


    @FXML
    void payBill(ActionEvent event) {
        if (!validateBill()) {
            return;
        }
        // pay bill if alert is ok
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Pay Bill");
        alert.setHeaderText("Are you sure this bill is purchased ?");
        alert.setContentText("This action cannot be undone");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                payBill();
            }
        });

    }


    public Bill setBillData() {
        Bill bill = new Bill();
        BillDB billDB = new BillDB();
        bill.setBill_id(billId.getText());
        bill.setEmployee_id(employeeId.getText().split("-")[0]);
        bill.setTable_id(tableId.getText());
        bill.setTotal(Double.valueOf(totalBill.getText()));
        bill.setCustomerName(customerName.getText());
        bill.setCustomerPhone(customerPhone.getText());
        bill.setStatus("pending");
        bill.setDate(dateNow.getText());
        ArrayList<Dish> dishList = new ArrayList<>(addedDishTable.getItems());
        bill.setDishList(dishList);
        return bill;
    }

    public void createBill() {
        Bill bill = setBillData();
        BillDB billDB = new BillDB();
        if (billDB.addNewBill(bill)) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Create new bill");
            alert.setHeaderText("Create new bill successfully");
            alert.setContentText("Create new bill successfully");
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    Stage stage = (Stage) createBillButton.getScene().getWindow();
                    stage.close();
                }
            });
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Create new bill");
            alert.setHeaderText("Create new bill failed");
            alert.setContentText("Create new bill failed");
            alert.showAndWait();
        }
    }

    public void updateBill() {
        Bill bill = setBillData();
        BillDB billDB = new BillDB();
        if (billDB.updateBill(bill)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Update bill");
            alert.setHeaderText("Update bill successfully");
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    Stage stage = (Stage) createBillButton.getScene().getWindow();
                    stage.close();
                }
            });
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Update bill");
            alert.setHeaderText("Update bill failed");
            alert.showAndWait();
        }
    }

    public void payBill() {
        Bill bill = setBillData();
        bill.setStatus("purchased");
        BillDB billDB = new BillDB();
        if (billDB.updateBill(bill)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Update bill");
            alert.setHeaderText("Purchase bill successfully");
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    //close this window
                    Stage stage = (Stage) payBillButton.getScene().getWindow();
                    stage.close();
                }
            });
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Update bill");
            alert.setHeaderText("Purchase bill failed");
            alert.showAndWait();
        }
    }

    public boolean validateBill() {
        if (customerName.getText().isEmpty() || customerPhone.getText().isEmpty() || addedDishTable.getItems().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Create new bill");
            alert.setHeaderText("Create new bill failed");
            alert.setContentText("Please fill in customer name, phone number and add at least 1 dish to bill");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    public void setData(String tableId, String employeeId, String employeeName) {
        this.tableId.setText(tableId);
        this.employeeId.setText(employeeId + "-" + employeeName);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss");
        LocalDateTime  localDate = LocalDateTime.now();
        this.dateNow.setText(dtf.format(localDate));
    }

    public void setData(Bill bill, String employeeId, String employeeName) {
        this.tableId.setText(bill.getTable_id());
        this.employeeId.setText(employeeId + "-" + employeeName);
        this.dateNow.setText(bill.getDate());
        this.customerName.setText(bill.getCustomerName());
        this.customerPhone.setText(bill.getCustomerPhone());
        this.totalBill.setText(String.valueOf(bill.getTotal()));
        createBillButton.setText("Update Bill");
        tableIdBox.setVisible(true);
        payBillButton.setDisable(false);
        billId.setText(bill.getBill_id());
        ObservableList<Dish> dishObservableList = FXCollections.observableArrayList(bill.getDishList());
        //for each dish in bill, add to addedDishTable
        for (Dish dish : dishObservableList) {
            addedDishTable.getItems().add(dish);
        }
    }

    Integer index;


    @FXML
    void getRowData(MouseEvent event) {
        index = dishTable.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        Dish selected = dishTable.getItems().get(index);
        Dish dish = new Dish();
        dish.setDish_id(selected.getDish_id());
        dish.setName(selected.getName());
        dish.setQty(0);
        dish.setTotal_price(dish.getDish_price());
        dish.setDish_price(selected.getDish_price());
        dish.setCat_id(selected.getCat_id());

        if (!addedDishList.contains(dish) && addedDishList.stream().noneMatch(dish1 -> dish1.getDish_id().equals(dish.getDish_id()))) {
            addedDishList.add(dish);
        }
        if (addedDishList.stream().anyMatch(dish1 -> dish1.getDish_id().equals(dish.getDish_id()))) {
            addDish(dish);
        }
        addedDishTable.refresh();
        setTotalBill();
    }

    private void addDish(Dish dish) {
        addedDishList.forEach(dish1 -> {
            if (dish1.getDish_id().equals(dish.getDish_id())) {
                dish1.setQty(dish1.getQty() + 1);
                dish1.setTotal_price(String.valueOf(
                        dish1.getQty() * Double.parseDouble(dish1.getDish_price())
                ));
            }
        });
    }

    public void updateAddedDishList(TableColumn.CellEditEvent<Dish, Integer> editedCell) {
        Dish dish = addedDishTable.getSelectionModel().getSelectedItem();
        if (editedCell.getNewValue() == 0) {
            addedDishList.remove(dish);
        } else {
            dish.setQty(editedCell.getNewValue());
            dish.setTotal_price(String.valueOf(
                    dish.getQty() * Double.parseDouble(dish.getDish_price())
            ));
        }
        addedDishTable.refresh();
        setTotalBill();
    }

    public void setTotalBill() {
        double total = 0;
        for (Dish dish : addedDishList) {
            total += Double.parseDouble(dish.getTotal_price());
        }
        totalBill.setText(String.valueOf(total));
    }

    public static void numericOnly(final TextField field) {
        field.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                field.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }


    private final ObservableList<Dish> dishList = FXCollections.observableArrayList();
    private ObservableList<Dish> addedDishList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        numericOnly(customerPhone);
        //set column dish data
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("dish_price"));
        dishIdColumn.setCellValueFactory(new PropertyValueFactory<>("dish_id"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        DishDB dishDB = new DishDB();
        ArrayList<Dish> dishes = dishDB.getAllDish();
        dishList.addAll(dishes);
        //dynamic search dish
        FilteredList<Dish> filteredData = new FilteredList<>(dishList, b -> true);
        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(dish -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (dish.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (dish.getCat_id().contains(lowerCaseFilter)) {
                    return true;
                } else if (dish.getDish_price().contains(lowerCaseFilter)) {
                    return true;
                } else return dish.getDish_id().toLowerCase().contains(lowerCaseFilter);
            });
        });
        SortedList<Dish> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(dishTable.comparatorProperty());
        dishTable.selectionModelProperty().get().setSelectionMode(SelectionMode.MULTIPLE);
        dishTable.setItems(sortedData);
        //added dish table
        addedDishId.setCellValueFactory(new PropertyValueFactory<>("dish_id"));
        addedDish.setCellValueFactory(new PropertyValueFactory<>("name"));
        price.setCellValueFactory(new PropertyValueFactory<>("total_price"));
        qty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        addedDishTable.setEditable(true);
        qty.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        addedDishTable.setTooltip(new Tooltip("Double click to edit quantity"));
        addedDishTable.setItems(addedDishList);
    }
}
