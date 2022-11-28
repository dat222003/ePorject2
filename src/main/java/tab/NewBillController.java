package tab;

import category.Category;
import category.CategoryDB;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeView;
import dish.Dish;
import dish.DishDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;

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
    private JFXTreeView<String> dishDataTree;

    @FXML
    private JFXTreeView<String> pickedDishTree;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TreeItem<String> root = new TreeItem<>("Dishes");
        DishDB dishDB = new DishDB();
        CategoryDB categoryDB = new CategoryDB();
        ArrayList<Dish> dishArray = new ArrayList<>(dishDB.getAllDish());
        ArrayList<Category> categoryArray = new ArrayList<>(categoryDB.getAllCategory());
        categoryArray.forEach(category -> {
            TreeItem<String> categoryItem = new TreeItem<>(category.getName());//set category
            dishArray.forEach(dish -> {
                if (Objects.equals(dish.getCat_id(), category.getCat_id())) {
                    TreeItem<String> dishItem = new TreeItem<>(dish.getName());//set dish in category
                    categoryItem.getChildren().add(dishItem);
                }
            });
            root.getChildren().add(categoryItem);// add category to root
        });
        dishDataTree.setRoot(root);
    }
}
