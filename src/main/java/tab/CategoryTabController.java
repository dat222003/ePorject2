package tab;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.Category;
import model.CategoryDB;

import java.net.URL;
import java.util.ResourceBundle;

public class CategoryTabController implements Initializable {
    @FXML
    private AnchorPane categoryPane;

    @FXML
    private TableView<Category> categoryTable;

    @FXML
    private TableColumn<Category, String> descriptions;

    @FXML
    private TableColumn<Category, String> id;

    @FXML
    private TableColumn<Category, String> name;

    @FXML
    private JFXButton reloadButton;

    @FXML
    private TextField searchField;

    @FXML
    void addNewCategory(ActionEvent event) {

    }

    @FXML
    void deleteDish(ActionEvent event) {

    }

    @FXML
    void editDish(ActionEvent event) {

    }

    @FXML
    void reloadTable(ActionEvent event) {
        categoryList.clear();
        CategoryDB categoryDB = new CategoryDB();
        categoryList.addAll(categoryDB.getAllCategory());

        categoryTable.refresh();


    }

    ObservableList<Category> categoryList = FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //set table data
        id.setCellValueFactory(new PropertyValueFactory<>("cat_id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        descriptions.setCellValueFactory(new PropertyValueFactory<>("description"));

    }
}
