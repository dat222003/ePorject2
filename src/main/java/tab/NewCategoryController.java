package tab;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import model.Category;
import model.CategoryDB;

import java.net.URL;
import java.util.ResourceBundle;

public class NewCategoryController implements Initializable {

    @FXML
    private DialogPane dialogPane;
    @FXML
    private TextField descriptions;

    @FXML
    private TextField name;

    private Category category;
    public void setData() {
        category = new Category();
        category.setName(name.getText());
        category.setDescription(descriptions.getText());
    }

    public void createCategory(){
        setData();
        CategoryDB categoryDB = new CategoryDB();
        if (categoryDB.addCategory(category)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("Create category successfully");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Create category failed");
            alert.showAndWait();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dialogPane.lookupButton(javafx.scene.control.ButtonType.OK).addEventFilter(ActionEvent.ACTION, event -> {
            if (name.getText().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Please enter name");
                alert.showAndWait();
                event.consume();
            } else if (descriptions.getText().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Please enter description");
                alert.showAndWait();
                event.consume();
            }
        });
    }
}
