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
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class EditDishController implements Initializable {


    @FXML
    private DialogPane dialogPane;
    @FXML
    private ChoiceBox<Category> category;

    @FXML
    private Label editLabel;

    @FXML
    private ImageView image;

    @FXML
    private TextField name;

    @FXML
    private TextField price;

    @FXML
    private Label imageName;

    private File file;

    private Dish dish;
    @FXML
    void changeImage(ActionEvent event) {
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

    ObservableList<Category> categoryList = FXCollections.observableArrayList();
    public void setData(Dish dish) {
        editLabel.setText("Edit " + dish.getName());
        name.setText(dish.getName());
        price.setText(dish.getDish_price());
        this.image.setImage(dish.getImage().getImage());
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
            this.category.setItems(categoryList);
            if (Objects.equals(newCategory.getCat_id(), dish.getCat_id())) {
                this.category.setValue(newCategory);
            }
        });
        this.dish = dish;
        imageName.setText(dish.getImg_name());
    }
    public Dish getDishData() {
        this.dish.setName(name.getText());
        this.dish.setDish_price(price.getText());
        this.dish.setCat_id(category.getValue().getCat_id());
        return dish;
    }

    public void updateDish() {
        DishDB dishDB = new DishDB();
        Dish dish = getDishData();
        if (file != null) {
            Path path = Paths.get("dish_images/" + file.getName());
            dish.setImg_name(file.getName());
            try {
                Files.copy(file.toPath(), path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (dishDB.updateDish(dish)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("updated successfully");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("update failed");
            alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //set price to number and 2 decimal
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
            }
        });
    }
}
