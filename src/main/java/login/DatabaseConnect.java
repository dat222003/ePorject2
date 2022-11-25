package login;

import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.stream.Collectors;

public class DatabaseConnect {
    public static Connection con;
    public static String url = "jdbc:mysql://mysqldb.c1pmrcfs8z8r.ap-southeast-1.rds.amazonaws.com/restaurant";
//    public static String url = "jdbc:mysql://localhost:3306/restaurant";

    public static Connection getConnect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, "admin", "dat2003dat2003");
//            con = DriverManager.getConnection(url, "root", "Datpro22200379");
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }

        return con;
    }

    public static boolean createSession(String key) {
        try {
            PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO restaurant.user_session (`key`) VALUES (?)");
            preparedStatement.setString(1, key);
            preparedStatement.executeUpdate();
                return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public static boolean checkUserSession() {
        try {
            String key = UserSession.getSession();
            if (key == null) {
                return false;
            }
            PreparedStatement preparedStatement = con.prepareStatement("Select * from user_session where `key` = ?");
            preparedStatement.setString(1, key);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean deleteUserSession() {
        try {
            if (checkUserSession()) {
                PreparedStatement preparedStatement = con.prepareStatement("delete from user_session where `key` = ?");
                preparedStatement.setString(1, UserSession.getSession());
                UserSession.deleteUserSession();
                preparedStatement.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String hash(String inp) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] messaged = md.digest(inp.getBytes());
            BigInteger bigInteger = new BigInteger(1, messaged);
            return bigInteger.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }


}
