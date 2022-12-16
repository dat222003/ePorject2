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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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



    public boolean changePassword(Employee employee) {
        if (!checkOldPassword(employee)) {
            return false;
        }
        employeeDB employeeDB = new employeeDB();
        employee.setPassword(updatePasswordField.getText());
        if (employeeDB.changePassword(employee)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Change Password");
            alert.setContentText("Change Password Success");
            alert.showAndWait();
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Change Password");
            alert.setContentText("Change Password Fail");
            alert.showAndWait();
            return false;
        }
    }

    public boolean checkOldPassword(Employee employee) {
        DatabaseConnect databaseConnect = new DatabaseConnect();
        String oldPassword = oldPasswordField.getText();
        Employee oldEmployee = new Employee();
        //get user_account from database
        try( Connection con = databaseConnect.getConnect();) {
            PreparedStatement getFromUserAccount = con.prepareStatement("SELECT * from user_account where user_id = ?");
            getFromUserAccount.setString(1, Integer.toString(employee.getUserid()));
            ResultSet resultSet = getFromUserAccount.executeQuery();
            while (resultSet.next()) {
                oldEmployee.setUserid(resultSet.getInt("user_id"));
                oldEmployee.setPassword(resultSet.getString("password"));
            }
        } catch (SQLException e) {
            return false;
        }
        //check old pass
        if (!databaseConnect.hash(oldPassword).equals(oldEmployee.getPassword())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Wrong password");
            alert.setContentText("Please check your password");
            alert.showAndWait();
            return false;
        }
        return true;
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
