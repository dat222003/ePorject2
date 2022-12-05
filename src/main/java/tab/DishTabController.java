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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Dish;
import model.DishDB;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class DishTabController implements Initializable {

    @FXML
    private TableColumn<Dish, String> category;

    @FXML
    private TableView<Dish> dishTable;

    @FXML
    private TableColumn<Dish, String> id;

    @FXML
    private TableColumn<Dish, ImageView> image;

    @FXML
    private TableColumn<Dish, String> name;

    @FXML
    private TableColumn<Dish, Double> price;

    @FXML
    private JFXButton reloadButton;

    @FXML
    private TextField searchField;

    @FXML
    void reloadTable(ActionEvent event) {
        dishList.clear();
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
        dishTable.refresh();
    }



    @FXML
    void editDish(ActionEvent event) {
        Dish dish = dishTable.getSelectionModel().getSelectedItem();
        if (dish == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No dish selected");
            alert.setContentText("Please select a dish to edit");
            alert.showAndWait();
            return;
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/editDish.fxml"));
        DialogPane dialogPane = null;
        try {
            dialogPane = loader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        EditDishController editDishController = loader.getController();
        editDishController.setData(dish);
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setDialogPane(dialogPane);
        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            editDishController.updateDish();
            reloadButton.fire();
        }


    }


    @FXML
    void deleteDish(ActionEvent event) {
//        Dish dish = dishTable.getSelectionModel().getSelectedItem();
//        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//        alert.setTitle("Confirmation");
//        alert.setHeaderText("Delete " + dish.getName());
//        alert.setContentText("Are you sure you want to delete this dish?");
//        Optional<ButtonType> result = alert.showAndWait();
//        if (result.isPresent() && result.get() == ButtonType.OK) {
//            DishDB dishDB = new DishDB();
//            dishDB.deleteDish(dish.getDish_id());
//            reloadButton.fire();
//        }
    }

    @FXML
    void addNewDish(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/newDish.fxml"));
        DialogPane dialogPane = null;
        try {
            dialogPane = loader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        NewDishController addDishController = loader.getController();
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setDialogPane(dialogPane);
        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            addDishController.addDish();
            reloadButton.fire();
        }
    }




    ObservableList<Dish> dishList = FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        id.setCellValueFactory(new PropertyValueFactory<>("dish_id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        category.setCellValueFactory(new PropertyValueFactory<>("category"));
        price.setCellValueFactory(new PropertyValueFactory<>("dish_price"));
        image.setCellValueFactory(new PropertyValueFactory<>("image"));

        reloadButton.fire();

        FilteredList<Dish> filteredList = new FilteredList<>(dishList, b -> true);
        searchField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            filteredList.setPredicate(dish -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (dish.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (dish.getCategory().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else return dish.getDish_price().toLowerCase().contains(lowerCaseFilter);
            });
        });
        SortedList<Dish> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(dishTable.comparatorProperty());
        dishTable.setItems(sortedList);

    }

}
