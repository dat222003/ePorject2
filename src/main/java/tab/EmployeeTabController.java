package tab;

import com.jfoenix.controls.JFXButton;
import employee.Employee;
import employee.employeeDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class EmployeeTabController implements Initializable {

    @FXML
    private TextField EmailTextField;

    @FXML
    private Button addNewButton;

    @FXML
    private JFXButton addOrUpdateButton;

    @FXML
    private Button eraseInfoButton;

    @FXML
    private TextField nameTextField;

    @FXML
    private PasswordField newPasswordField;

    @FXML
    private PasswordField oldPasswordField;

    @FXML
    private PasswordField reNewPasswordField;

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
    private Button searchButton;

    @FXML
    private ToggleGroup genderGroup;
    @FXML
    private TextField searchField;
    @FXML
    private TableColumn<Employee, String> emailColumn;
    @FXML
    private TableColumn<Employee, String> userColumn;



    Integer index;

    @FXML
     void getRowData(MouseEvent event) {
        index = employeeTable.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        nameTextField.setText(nameColumn.getCellData(index));
        userTextField.setText(userColumn.getCellData(index));
        phoneTextField.setText(phoneColumn.getCellData(index));
        salaryTextField.setText(String.valueOf(salaryColumn.getCellData(index)));
        EmailTextField.setText(emailColumn.getCellData(index));
        if (genderColumn.getCellData(index) == 0) {
            genderGroup.selectToggle(genderGroup.getToggles().get(0));
        } else {
            genderGroup.selectToggle(genderGroup.getToggles().get(1));
        }
    }


    @FXML
    void EraseInfo(ActionEvent event) {
        nameTextField.setText("");
        userTextField.setText("");
        phoneTextField.setText("");
        salaryTextField.setText("");
        EmailTextField.setText("");
        genderGroup.selectToggle(null);
        oldPasswordField.setText("");
        newPasswordField.setText("");
        reNewPasswordField.setText("");
    }

    ObservableList<Employee> employeeList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        reloadButton.fire();
    }

    @FXML
    private void reloadTable(ActionEvent event) {
        employeeList.clear();
        idColumn.setCellValueFactory(new PropertyValueFactory<>("userid"));
        userColumn.setCellValueFactory(new PropertyValueFactory<>("user"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
        salaryColumn.setCellValueFactory(new PropertyValueFactory<>("salary"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        ArrayList<Employee> list = employeeDB.getAllEmployee();
        employeeList.addAll(list);
        employeeTable.setItems(employeeList);
    }


}
