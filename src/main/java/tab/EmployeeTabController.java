package tab;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXMLLoader;
import login.DatabaseConnect;
import model.Employee;
import model.employeeDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class EmployeeTabController implements Initializable {

    @FXML
    private TextField EmailTextField;

    @FXML
    private Button eraseInfoButton;

    @FXML
    private TextField nameTextField;

    @FXML
    private JFXButton reloadButton;
    @FXML
    private TextField userTextField;
    @FXML
    private TextField salaryTextField;
    @FXML
    private TextField phoneTextField;

    @FXML
    private JFXButton deleteButton;

    @FXML
    private AnchorPane employeePane;

    @FXML
    private TableView<Employee> employeeTable;

    @FXML
    private TableColumn<Employee, Integer> genderColumn;

    @FXML
    private TableColumn<Employee, Integer> idColumn;

    @FXML
    private TableColumn<Employee, String> nameColumn;

    @FXML
    private TableColumn<Employee, String> phoneColumn;

    @FXML
    private TableColumn<Employee, Double> salaryColumn;

    @FXML
    private TableColumn<Employee, String> idCardColumn;

    @FXML
    private ToggleGroup genderGroup;
    @FXML
    private TextField searchField;
    @FXML
    private TextField idCardTextField;
    @FXML
    private TableColumn<Employee, String> emailColumn;
    @FXML
    private TableColumn<Employee, String> userColumn;
    @FXML
    private Button showChangePassword;
    @FXML
    private Button updateButton;

    // set a text-field to only number input
    public static void numericOnly(final TextField field) {
        field.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                field.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }
    Integer index;

    @FXML
    //put data to text-field if row is selected
    void getRowData(MouseEvent event) {
        index = employeeTable.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        showChangePassword.setDisable(false);
        updateButton.setDisable(false);
        deleteButton.setDisable(false);
        nameTextField.setText(nameColumn.getCellData(index));
        userTextField.setText(userColumn.getCellData(index));
        phoneTextField.setText(phoneColumn.getCellData(index));
        salaryTextField.setText(String.valueOf(salaryColumn.getCellData(index)));
        EmailTextField.setText(emailColumn.getCellData(index));
        idCardTextField.setText(idCardColumn.getCellData(index));
        if (genderColumn.getCellData(index) == 0) {
            genderGroup.selectToggle(genderGroup.getToggles().get(0));
        } else {
            genderGroup.selectToggle(genderGroup.getToggles().get(1));
        }
    }

    @FXML
    //clear emp info in text-field
    void EraseInfo(ActionEvent event) {
        nameTextField.setText("");
        userTextField.setText("");
        phoneTextField.setText("");
        salaryTextField.setText("");
        EmailTextField.setText("");
        genderGroup.selectToggle(null);
        idCardTextField.setText("");
    }
    @FXML
    private void reloadTable(ActionEvent event) {
        showChangePassword.setDisable(true);
        updateButton.setDisable(true);
        deleteButton.setDisable(true);
        employeeList.clear();
        idColumn.setCellValueFactory(new PropertyValueFactory<>("userid"));
        userColumn.setCellValueFactory(new PropertyValueFactory<>("user"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
        salaryColumn.setCellValueFactory(new PropertyValueFactory<>("salary"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        idCardColumn.setCellValueFactory(new PropertyValueFactory<>("idcard"));
        searchField.setText("");
        employeeDB employeeDB = new employeeDB();
        employeeList.addAll(employeeDB.getAllEmployee());
    }

    //validate emp info
    public boolean validateInfo() {
        if (nameTextField.getText().isEmpty() ||
                userTextField.getText().isEmpty() ||
                phoneTextField.getText().isEmpty() ||
                salaryTextField.getText().isEmpty() ||
                EmailTextField.getText().isEmpty() ||
                idCardTextField.getText().isEmpty() ||
                genderGroup.getSelectedToggle() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Please fill all the fields");
            alert.showAndWait();
            return false;
        }
        // validate email regex
        if (!EmailTextField.getText().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Please enter a valid email ( sample@sample.sample");
            alert.showAndWait();
            return false;
        }
        if (phoneTextField.getText().length() != 10) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Please enter a valid phone number (10 digits)");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    //set employee info
    public Employee setEmployee() {
        Employee employee = new Employee();
        if (idColumn.getCellData(index) != null) {
            employee.setUserid(idColumn.getCellData(index));
        }
        employee.setUser(userTextField.getText());
        employee.setName(nameTextField.getText());
        employee.setPhone(phoneTextField.getText());
        employee.setSalary(Double.parseDouble(salaryTextField.getText()));
        employee.setEmail(EmailTextField.getText());
        employee.setIdcard(idCardTextField.getText());
        RadioButton selectedRadioButton = (RadioButton) genderGroup.getSelectedToggle();
        if (selectedRadioButton.getText().equals("Male")) {
            employee.setGender(0);
        } else {
            employee.setGender(1);
        }
        return employee;
    }

    @FXML
    private void UpdateEmployee(ActionEvent event) {
        if (!validateInfo()) {
            return;
        }
        Employee employee = setEmployee();
        if (employee.getUserid() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Please select a row to update");
            alert.showAndWait();
            return;
        }
        employeeDB employeeDB = new employeeDB();
        if (employeeDB.updateEmployee(employee)) {
            reloadTable(event);
            eraseInfoButton.fire();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Updated Employee");
            alert.setHeaderText(null);
            alert.setContentText("Employee Updated Successfully");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Employee Update Failed");
            alert.showAndWait();
        }

    }

    @FXML
    private void deleteEmployee(ActionEvent event) {
        if (!validateInfo()) {
            return;
        }
        Employee employee = setEmployee();
        if (employee.getUserid() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Please select a row to update");
            alert.showAndWait();
            return;
        }
        employeeDB employeeDB = new employeeDB();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Employee");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete this employee?");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                if (employeeDB.deleteEmployee(Integer.toString(employee.getUserid()))) {
                    reloadTable(event);
                    eraseInfoButton.fire();
                    Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                    alert1.setTitle("Deleted Employee");
                    alert1.setHeaderText(null);
                    alert1.setContentText("Employee Deleted Successfully");
                    alert1.showAndWait();
                } else {
                    Alert alert2 = new Alert(Alert.AlertType.ERROR);
                    alert2.setTitle("Error");
                    alert2.setHeaderText(null);
                    alert2.setContentText("Employee Delete Failed");
                    alert2.showAndWait();
                }
            }
        });
    }

    @FXML
    private void addEmployee(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/newEmployee.fxml"));
        DialogPane dialogPane = null;
        try {
            dialogPane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        NewEmployeeController newEmployeeController = loader.getController();
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setDialogPane(dialogPane);
        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            if (newEmployeeController.addEmployee()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Added Employee");
                alert.setHeaderText(null);
                alert.setContentText("Employee Added Successfully");
                alert.showAndWait();
                reloadButton.fire();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Employee Add Failed");
                alert.showAndWait();
            }
        }
    }

    @FXML
    private void showPasswordBox() {
        if (index <= -1) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Please select a row to update");
            alert.showAndWait();
            return;
        }
        if (nameTextField.getText().isEmpty() ||
                userTextField.getText().isEmpty() ||
                phoneTextField.getText().isEmpty() ||
                salaryTextField.getText().isEmpty() ||
                EmailTextField.getText().isEmpty() ||
                idCardTextField.getText().isEmpty() ||
                genderGroup.getSelectedToggle() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Please select an employee to change password");
            alert.showAndWait();
            return;
        }
        Employee employee = setEmployee();
        if (employee.getUserid() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Please select a row to update");
            alert.showAndWait();
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/changePassword.fxml"));
            DialogPane dialogPane = null;
            try {
                dialogPane = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            ChangePassController changePasswordController = loader.getController();
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);
            Optional<ButtonType> result = dialog.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                changePasswordController.changePassword(employee);
                reloadButton.fire();
            }

        }

    }

    ObservableList<Employee> employeeList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        numericOnly(salaryTextField);
        // set phone number format 10 digits
        phoneTextField.setTextFormatter(new TextFormatter<>(change ->
                change.getControlNewText().length() <= 10 ? change : null));
        numericOnly(phoneTextField);
        showChangePassword.setDisable(true);

        //get data from database
        reloadButton.fire();
        //dynamic Search table
        FilteredList<Employee> filteredData = new FilteredList<>(employeeList, b -> true);
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(employee -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (employee.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (Integer.toString(employee.getUserid()).toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (employee.getPhone().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (Double.toString(employee.getSalary()).toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }else if (employee.getUser().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (employee.getEmail().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else return employee.getIdcard().toLowerCase().contains(lowerCaseFilter);
            });
        });
        SortedList<Employee> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(employeeTable.comparatorProperty());
        employeeTable.setItems(sortedData);
    }


}