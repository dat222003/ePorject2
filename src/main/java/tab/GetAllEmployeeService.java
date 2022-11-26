package tab;

import employee.Employee;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class GetAllEmployeeService extends Service<ObservableList<Employee>> {
    @Override
    protected Task<ObservableList<Employee>> createTask() {
        return new GetEmployeeTask();
    }
}
