package tab;

import employee.Employee;
import employee.employeeDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

import java.util.ArrayList;

public class GetEmployeeTask extends Task<ObservableList<Employee>> {
    @Override
    protected ObservableList<Employee> call() {

        employeeDB employeeDB = new employeeDB();
        ArrayList<Employee> data = employeeDB.getAllEmployee();
        ObservableList<Employee> empList = FXCollections.observableArrayList();
        for (int i = 0; i < data.size(); i++) {
            empList.add(data.get(i));
            updateValue(empList);
            updateProgress(i,data.size()-1);
        }
        return empList;
    }
}

