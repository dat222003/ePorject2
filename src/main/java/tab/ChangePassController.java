package tab;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.VBox;
import login.DatabaseConnect;
import model.Employee;
import model.employeeDB;

import java.net.URL;
import java.util.ResourceBundle;

public class ChangePassController implements Initializable {


    @FXML
    private PasswordField oldPasswordField;

    @FXML
    private DialogPane dialogPane;
    @FXML
    private PasswordField retypeUpdatePasswordField;

    @FXML
    private PasswordField updatePasswordField;



    public void changePassword(Employee employee) {
        employeeDB employeeDB = new employeeDB();
        DatabaseConnect databaseConnect = new DatabaseConnect();
        String oldPassword = oldPasswordField.getText();
        Employee oldemployee = employeeDB.getOneEmployee(Integer.toString(employee.getUserid()));
        if (!databaseConnect.hash(oldPassword).equals(oldemployee.getPassword())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Wrong password");
            alert.setContentText("Please check your password");
            alert.showAndWait();
            return;
        }
        employee.setPassword(updatePasswordField.getText());
        if (employeeDB.changePassword(employee)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Change Password");
            alert.setContentText("Change Password Success");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Change Password");
            alert.setContentText("Change Password Fail");
            alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dialogPane.lookupButton(ButtonType.OK).addEventFilter(javafx.event.ActionEvent.ACTION, event -> {
            if (updatePasswordField.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Empty field");
                alert.setContentText("New password can not be empty");
                alert.showAndWait();
                event.consume();
            } else if (retypeUpdatePasswordField.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Empty field");
                alert.setContentText("Retype password can not be empty");
                alert.showAndWait();
                event.consume();
            } else if (oldPasswordField.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Empty field");
                alert.setContentText("Old password can not be empty");
                alert.showAndWait();
                event.consume();
            } else if (!retypeUpdatePasswordField.getText().equals(updatePasswordField.getText())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Retype password");
                alert.setContentText("New password and retype password not match");
                alert.showAndWait();
                event.consume();
            }
        });
    }
}
