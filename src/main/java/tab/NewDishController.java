package tab;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import model.Category;
import model.CategoryDB;
import model.Dish;
import model.DishDB;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class NewDishController implements Initializable {

    @FXML
    private ChoiceBox<Category> category;

    @FXML
    private Label createLabel;

    @FXML
    private ImageView image;

    @FXML
    private Label imageName;

    @FXML
    private TextField name;

    @FXML
    private TextField price;

    @FXML
    private DialogPane dialogPane;
    private File file;

    ObservableList<Category> categoryList = FXCollections.observableArrayList();
    @FXML
    void chooseImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();

        //Set extension filter
        FileChooser.ExtensionFilter extFilterJPG =
                new FileChooser.ExtensionFilter("JPG files (*.JPG)", "*.JPG");
        FileChooser.ExtensionFilter extFilterjpg =
                new FileChooser.ExtensionFilter("jpg files (*.jpg)", "*.jpg");
        FileChooser.ExtensionFilter extFilterPNG =
                new FileChooser.ExtensionFilter("PNG files (*.PNG)", "*.PNG");
        FileChooser.ExtensionFilter extFilterpng =
                new FileChooser.ExtensionFilter("png files (*.png)", "*.png");
        fileChooser.getExtensionFilters()
                .addAll(extFilterpng, extFilterjpg, extFilterPNG, extFilterJPG);
        //Show open file dialog
        file = fileChooser.showOpenDialog(null);
        //read image file
        Image image = new Image(Objects.requireNonNull(file.toURI().toString()));
        this.image.setImage(image);
        imageName.setText(file.getName());
    }


    public void addDish() {
            Dish dish = new Dish();
            dish.setName(name.getText());
            dish.setDish_price(price.getText());
            dish.setCat_id(category.getValue().getCat_id());
            dish.setImg_name(file.getName());
            DishDB dishDB = new DishDB();
            dishDB.addDish(dish);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Dish added successfully");
            alert.showAndWait();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //set category data
        CategoryDB categoryDB = new CategoryDB();
        ArrayList<Category> categories = categoryDB.getAllCategory();
        categories.forEach(oldCategory -> {
            Category newCategory = new Category(){
                @Override
                public String toString() {
                    return oldCategory.getName();
                }
            };
            newCategory.setCat_id(oldCategory.getCat_id());
            categoryList.add(newCategory);
        });
        this.category.setItems(categoryList);
        this.category.getSelectionModel().selectFirst();

        //set price to number only
        price.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue.matches("\\d{0,7}([.]\\d{0,2})?")) {
                price.setText(oldValue);
            }
        });
        //validate dish
        dialogPane.lookupButton(ButtonType.OK).addEventFilter(ActionEvent.ACTION, event -> {
            if (name.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Name is empty");
                alert.setContentText("Please enter name");
                alert.showAndWait();
                event.consume();
            } else if (price.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Price is empty");
                alert.setContentText("Please enter price");
                alert.showAndWait();
                event.consume();
            } else if (category.getValue() == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Category is empty");
                alert.setContentText("Please choose a category");
                alert.showAndWait();
                event.consume();
            } else if (image == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Image is empty");
                alert.setContentText("Please choose an image");
                alert.showAndWait();
                event.consume();
            } else if (file == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Image is empty");
                alert.setContentText("Please choose an image");
                alert.showAndWait();
                event.consume();
            }
        });
    }
}
