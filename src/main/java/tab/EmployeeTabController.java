package tab;

import com.jfoenix.controls.JFXButton;
import employee.Employee;
import employee.employeeDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class EmployeeTabController implements Initializable {

    @FXML
    private TableColumn<?, ?> ActionColumn;

    @FXML
    private Button addNewEmployee;

    @FXML
    private AnchorPane employeePane;

    @FXML
    private TableView<Employee> employeeTable;

    @FXML
    private TableColumn<Employee, String> genderColumn;

    @FXML
    private TableColumn<Employee, Integer> idColumn;

    @FXML
    private TableColumn<Employee, String> nameColumn;

    @FXML
    private TableColumn<Employee, ?> phoneColumn;

    @FXML
    private TableColumn<Employee, ?> salaryColumn;

    @FXML
    private Button searchButton;

    @FXML
    private TextField searchField;

    @FXML
    private JFXButton reloadButton;

    @FXML
    private TableColumn<Employee, ?> userColumn;

    ObservableList<Employee> employeeList = FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        reloadButton.fire();


    }

    @FXML
    private void reloadTable(ActionEvent event){
        employeeList.clear();
        idColumn.setCellValueFactory(new PropertyValueFactory<>("userid"));
        userColumn.setCellValueFactory(new PropertyValueFactory<>("user"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
        salaryColumn.setCellValueFactory(new PropertyValueFactory<>("salary"));
        ArrayList<Employee> list = employeeDB.getAllEmployee();
        employeeList.addAll(list);
        employeeTable.setItems(employeeList);
    }


}
