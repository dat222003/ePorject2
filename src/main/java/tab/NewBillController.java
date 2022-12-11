package tab;

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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.util.converter.IntegerStringConverter;
import model.Bill;
import model.BillDB;
import model.Dish;
import model.DishDB;

import java.io.File;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class NewBillController implements Initializable {

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
    private TableColumn<Dish, ImageView> imageColumn;

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
    private Label toolTip;

    @FXML
    private  DialogPane dialogPane;




    public Bill setBillData() {
        Bill bill = new Bill();
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

    public boolean createBill() {
        Bill bill = setBillData();
        BillDB billDB = new BillDB();
        if (billDB.addNewBill(bill)) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Create new bill");
            alert.setHeaderText("Create new bill successfully");
            alert.setContentText("Create new bill successfully");
            alert.showAndWait();
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Create new bill");
            alert.setContentText("Create new bill failed");
            alert.showAndWait();
            return false;
        }

    }

    public void updateBill() {
        Bill bill = setBillData();
        BillDB billDB = new BillDB();
        if (billDB.updateBill(bill)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Update bill");
            alert.setHeaderText("Update bill successfully");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Update bill");
            alert.setHeaderText("Update bill failed");
            alert.showAndWait();
        }
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
        tableIdBox.setVisible(true);
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

    @FXML
    void removeDish(ActionEvent event) {
        Dish dish = addedDishTable.getSelectionModel().getSelectedItem();
        if (dish != null) {
            addedDishList.remove(dish);
            addedDishTable.refresh();
            setTotalBill();
        }
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
    private final ObservableList<Dish> addedDishList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        toolTip.setTooltip(new Tooltip("Right click to modify dish"));
        numericOnly(customerPhone);
        //set column dish data
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("dish_price"));
        dishIdColumn.setCellValueFactory(new PropertyValueFactory<>("dish_id"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        imageColumn.setCellValueFactory(new PropertyValueFactory<>("image"));
        DishDB dishDB = new DishDB();
        ArrayList<Dish> dishes = dishDB.getAllDish();
        dishes.forEach(dish -> {
            File file = new File("dish_images/" + dish.getImg_name());
            Image image = new Image(file.toURI().toString());
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(150);
            imageView.setFitWidth(150);
            dish.setImage(imageView);
        });
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
        //validate
        dialogPane.lookupButton(ButtonType.OK).addEventFilter(ActionEvent.ACTION, event -> {
            if (customerName.getText().isEmpty()) {
                event.consume();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Customer name is empty");
                alert.showAndWait();
            } else if (customerPhone.getText().isEmpty()) {
                event.consume();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Customer phone is empty");
                alert.showAndWait();
            } else if (addedDishList.isEmpty()) {
                event.consume();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("No dish added");
                alert.showAndWait();
            }
        });

    }

}
