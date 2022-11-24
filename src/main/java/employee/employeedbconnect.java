package employee;

import login.DatabaseConnect;

import java.sql.Connection;
import java.util.List;

public class employeedbconnect {
    private Connection con = DatabaseConnect.getConnect();

    private List<employee> employeeList;


}
