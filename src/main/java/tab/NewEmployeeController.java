package tab;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.Employee;
import model.employeeDB;

import java.net.URL;
import java.util.ResourceBundle;

public class NewEmployeeController implements Initializable {
    @FXML
    private TextField emailTextField;

    @FXML
    private DialogPane dialogPane;

    @FXML
    private ToggleGroup genderGroup;

    @FXML
    private TextField idCardTextField;

    @FXML
    private TextField nameTextField;

    @FXML
    private PasswordField newPasswordField;

    @FXML
    private TextField phoneTextField;

    @FXML
    private PasswordField reNewPasswordField;

    @FXML
    private TextField salaryTextField;

    @FXML
    private TextField userTextField;

    public boolean addEmployee() {
        Employee employee = new Employee();
        employee.setUser(userTextField.getText());
        employee.setName(nameTextField.getText());
        employee.setPhone(phoneTextField.getText());
        employee.setSalary(Double.parseDouble(salaryTextField.getText()));
        employee.setEmail(emailTextField.getText());
        employee.setIdcard(idCardTextField.getText());
        RadioButton selectedRadioButton = (RadioButton) genderGroup.getSelectedToggle();
        if (selectedRadioButton.getText().equals("Male")) {
            employee.setGender(0);
        } else {
            employee.setGender(1);
        }
        employee.setPassword(newPasswordField.getText());
        employeeDB employeeDB = new employeeDB();
        return employeeDB.addEmployee(employee);
    }

    public void NumericOnly(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                textField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }
    public void CharOnly(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\sa-zA-Z*")) {
                textField.setText(newValue.replaceAll("[^\\sa-zA-Z]", ""));
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        NumericOnly(salaryTextField);
        NumericOnly(phoneTextField);
        NumericOnly(idCardTextField);
        CharOnly(nameTextField);
        dialogPane.lookupButton(ButtonType.OK).addEventFilter(javafx.event.ActionEvent.ACTION, event -> {
            if (userTextField.getText().isEmpty() || userTextField.getText() == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("user name is empty");
                alert.setContentText("Please enter user name");
                alert.showAndWait();
                event.consume();
            }
            else if (nameTextField.getText().isEmpty() || nameTextField.getText() == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("name is empty");
                alert.setContentText("Please enter name");
                alert.showAndWait();
                event.consume();
            }
            else if (phoneTextField.getText().isEmpty() || phoneTextField.getText() == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("phone is empty");
                alert.setContentText("Please enter phone");
                alert.showAndWait();
                event.consume();
            }
            else if (salaryTextField.getText().isEmpty() || salaryTextField.getText() == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("salary is empty");
                alert.setContentText("Please enter salary");
                alert.showAndWait();
                event.consume();
            }
            else if (emailTextField.getText().isEmpty() || emailTextField.getText() == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("email is empty");
                alert.setContentText("Please enter email");
                alert.showAndWait();
                event.consume();
            }
            else if (idCardTextField.getText().isEmpty() || idCardTextField.getText() == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("id card is empty");
                alert.setContentText("Please enter id card");
                alert.showAndWait();
                event.consume();
            }
            else if (newPasswordField.getText().isEmpty() || newPasswordField.getText() == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("password is empty");
                alert.setContentText("Please enter password");
                alert.showAndWait();
                event.consume();
            }
            else if (reNewPasswordField.getText().isEmpty() || reNewPasswordField.getText() == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("re password is empty");
                alert.setContentText("Please enter re password");
                alert.showAndWait();
                event.consume();
            }
            else if (!newPasswordField.getText().equals(reNewPasswordField.getText())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("password is not match");
                alert.setContentText("Please enter password again");
                alert.showAndWait();
                event.consume();
            }
            else if (genderGroup.getSelectedToggle() == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("gender is empty");
                alert.setContentText("Please select your gender");
                alert.showAndWait();
                event.consume();
            }
        });
    }
}
