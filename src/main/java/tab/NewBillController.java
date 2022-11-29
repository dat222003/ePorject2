package tab;

import category.Category;
import category.CategoryDB;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeView;
import dish.Dish;
import dish.DishDB;
import employee.Employee;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
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

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
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
    private TableColumn<Dish, Button> remove;


    @FXML
    void createNewBill(ActionEvent event) {

    }
    public void setData(String tableId, String employeeId, String employeeName) {
        this.tableId.setText(tableId);
        this.employeeId.setText(employeeId + "-" + employeeName);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu-MM-dd");
        LocalDate localDate = LocalDate.now();
        this.dateNow.setText(dtf.format(localDate));
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
        dish.setQty(editedCell.getNewValue());
        dish.setTotal_price(String.valueOf(dish.getQty() * Double.parseDouble(dish.getDish_price())));
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


    private final ObservableList<Dish> dishList = FXCollections.observableArrayList();
    private ObservableList<Dish> addedDishList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //set column
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("dish_price"));
        dishIdColumn.setCellValueFactory(new PropertyValueFactory<>("dish_id"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("cat_id"));
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
