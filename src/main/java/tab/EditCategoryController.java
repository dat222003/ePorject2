package tab;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Category;
import model.CategoryDB;

import java.net.URL;
import java.util.ResourceBundle;

public class EditCategoryController implements Initializable {

    @FXML
    private DialogPane dialogPane;
    @FXML
    private TextField descriptions;

    @FXML
    private Label label;

    @FXML
    private TextField name;

    private Category category;
    public void setData(Category category){
        label.setText("Edit: " + category.getName());
        name.setText(category.getName());
        descriptions.setText(category.getDescription());
        this.category = category;
    }

    public void getData(){
        category.setName(name.getText());
        category.setDescription(descriptions.getText());
    }

    public void updateCategory(){
        getData();
        CategoryDB categoryDB = new CategoryDB();
        categoryDB.updateCategory(category);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //validate();
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
