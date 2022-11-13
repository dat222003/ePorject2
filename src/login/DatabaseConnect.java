package login;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnect {
    public static Connection con;
    public static String url = "jdbc:mysql://mysqldb.c1pmrcfs8z8r.ap-southeast-1.rds.amazonaws.com/user";


    public static Connection getConnect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, "admin", "12345678");
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }

        return con;
    }





}
