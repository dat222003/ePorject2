package employee;


import login.DatabaseConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class employeeDB {
    private static final Connection con = DatabaseConnect.getConnect();
    public static boolean addEmployee(Employee employee) {
        try {
            PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO user_account " +
                    "(user, password, name, phone, email, id_card, gender) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, employee.getUser());
            preparedStatement.setString(2, employee.getPassword());
            preparedStatement.setString(3, employee.getName());
            preparedStatement.setString(4, employee.getPhone());
            preparedStatement.setString(5, employee.getEmail());
            preparedStatement.setString(6, employee.getIdcard());
            preparedStatement.setInt(7, employee.getGender());
            preparedStatement.executeUpdate();
            preparedStatement = con.prepareStatement("SELECT LAST_INSERT_ID()");
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                preparedStatement = con.prepareStatement("INSERT INTO employee (user_id, salary) VALUES (?, ?)");
                preparedStatement.setInt(1, resultSet.getInt(1));
                preparedStatement.setDouble(2, employee.getSalary());
                preparedStatement.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public static ArrayList<Employee> getAllEmployee() {
        ArrayList<Employee> empList = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = con.prepareStatement("call getAllEmployee()");
            ResultSet resultset = preparedStatement.executeQuery();
            while (resultset.next()) {
                empList.add(setEmployeeProp(resultset));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return empList;
    }

    public static Employee getOneEmployee(String emp_id) {
        Employee employee = new Employee();
        try {
            PreparedStatement preparedStatement = con.prepareStatement("select * from employee " +
                            "join user_account ua on ua.user_id = employee.user_id " +
                    "where emp_id = ?");
            preparedStatement.setString(1, emp_id);
            ResultSet resultset = preparedStatement.executeQuery();
            while (resultset.next()) {
                setEmployeeProp(resultset);
            }
        } catch (SQLException e) {
            return null;
        }
        return employee;
    }

    public static boolean deleteEmployee(String user_id) {
        try {
            PreparedStatement preparedStatement;
            preparedStatement = con.prepareStatement("delete from user_account where user_id = ?");
            preparedStatement.setString(1, user_id);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    private static Employee setEmployeeProp(ResultSet resultset) throws SQLException {
        Employee employee = new Employee();
        employee.setUserid(Integer.parseInt(resultset.getString("employee.user_id")));
        employee.setEmp_id(Integer.parseInt(resultset.getString("emp_id")));
        employee.setSalary(resultset.getDouble("salary"));
        employee.setEmail(resultset.getString("email"));
        employee.setUser(resultset.getString("user"));
        employee.setGender(resultset.getInt("gender"));
        employee.setIdcard(resultset.getString("id_card"));
        employee.setPassword(resultset.getString("password"));
        employee.setName(resultset.getString("name"));
        employee.setPhone(resultset.getString("phone"));
        return employee;
    }

    public static void main(String[] args) {
        addEmployee(new Employee("user",
                DatabaseConnect.hash("pass"),
                "name", "phone", "email", 1000d, "idcard", 1, 1));
    }

}


