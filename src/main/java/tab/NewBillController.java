package tab;

import category.Category;
import category.CategoryDB;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeView;
import dish.Dish;
import dish.DishDB;
import employee.Employee;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

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
    void createNewBill(ActionEvent event) {

    }
    public void setData(String tableId, String employeeId, String employeeName) {
        this.tableId.setText(tableId);
        this.employeeId.setText(employeeId + "-" + employeeName);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu-MM-dd");
        LocalDate localDate = LocalDate.now();
        this.dateNow.setText(dtf.format(localDate));
    }


    private final ObservableList<Dish> dishList = FXCollections.observableArrayList();
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
    }
}
