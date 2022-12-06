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
import javafx.scene.layout.AnchorPane;
import model.Category;
import model.CategoryDB;

import java.net.URL;
import java.util.Optional;
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/newCategory.fxml"));
        DialogPane dialogPane = null;
        try {
            dialogPane = loader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        NewCategoryController newCategoryController = loader.getController();
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setDialogPane(dialogPane);
        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            newCategoryController.createCategory();
            reloadButton.fire();
        }
    }


    @FXML
    void deleteCategory(ActionEvent event) {
        Category selectedCategory = categoryTable.getSelectionModel().getSelectedItem();
        if (selectedCategory == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Category Selected");
            alert.setHeaderText("No Category Selected");
            alert.setContentText("Please select a category in the table.");
            alert.showAndWait();
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Category");
        alert.setHeaderText("Are you sure you want to delete the category " + selectedCategory.getName() + "?");
        alert.setContentText("This action will delete all dishes in this category.");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            CategoryDB categoryDB = new CategoryDB();
            categoryDB.deleteCategory(selectedCategory.getCat_id());
            categoryList.remove(selectedCategory);
        }

    }

    @FXML
    void editCategory(ActionEvent event) {
        Category category = categoryTable.getSelectionModel().getSelectedItem();
        if (category == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No category selected");
            alert.setContentText("Please select a category to edit");
            alert.showAndWait();
            return;
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/editCategory.fxml"));
        DialogPane dialogPane = null;
        try {
            dialogPane = loader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        EditCategoryController editCategoryController = loader.getController();
        editCategoryController.setData(category);
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setDialogPane(dialogPane);
        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            editCategoryController.updateCategory();
            reloadButton.fire();
        }
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

        reloadButton.fire();
        //dynamic search
        FilteredList<Category> filteredList = new FilteredList<>(categoryList, b -> true);
        searchField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            filteredList.setPredicate(category -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (category.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else return category.getDescription().toLowerCase().contains(lowerCaseFilter);
            });
        });
        SortedList<Category> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(categoryTable.comparatorProperty());
        categoryTable.setItems(sortedList);


    }
}
