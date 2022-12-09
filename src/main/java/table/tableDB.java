package table;

import login.DatabaseConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class tableDB {
    private final DatabaseConnect databaseConnect = new DatabaseConnect();


    public boolean addTable() {
        try(
                Connection con = databaseConnect.getConnect();
                ) {
            PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO `table` (status) VALUES (?)");
            preparedStatement.setString(1, "available");
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean updateTable(Table table) {
        try(
                Connection con = databaseConnect.getConnect();
        ) {
            PreparedStatement preparedStatement = con.prepareStatement("UPDATE `table` SET status = ? WHERE table_id = ?");
            preparedStatement.setString(1, table.getTableStatus());
            preparedStatement.setString(2, table.getTable_id());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean deleteTable(String table_id) {
        try(
                Connection con = databaseConnect.getConnect();
        ) {
            PreparedStatement preparedStatement = con.prepareStatement("DELETE FROM `table` WHERE table_id = ?");
            preparedStatement.setString(1, table_id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    public ArrayList<Table> getAllTable() {
        ArrayList<Table> tableList = new ArrayList<>();
        try(
                Connection con = databaseConnect.getConnect();
        ) {
            PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM `table`");
            ResultSet resultset = preparedStatement.executeQuery();
            while (resultset.next()) {
                Table table = new Table();
                table.setTable_id(resultset.getString("table_id"));
                table.setTableStatus(resultset.getString("status"));
                tableList.add(table);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return tableList;
    }

    public Table getOneTable(String table_id) {
        Table table = new Table();
        try(
                Connection con = databaseConnect.getConnect();
        ) {
            PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM `table` WHERE table_id = ?");
            preparedStatement.setString(1, table_id);
            ResultSet resultset = preparedStatement.executeQuery();
            if (resultset.next()) {
                table.setTable_id(resultset.getString("table_id"));
                table.setTableStatus(resultset.getString("status"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        databaseConnect.closeConnection();
        return table;
    }


    public ArrayList<Table> getTableWithStatus(String status) {
        ArrayList<Table> tableList = new ArrayList<>();
        try(
                Connection con = databaseConnect.getConnect();
        ) {
            PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM `table` WHERE status = ?");
            preparedStatement.setString(1, status);
            ResultSet resultset = preparedStatement.executeQuery();
            while (resultset.next()) {
                Table table = new Table();
                table.setTable_id(resultset.getString("table_id"));
                table.setTableStatus(resultset.getString("status"));
                tableList.add(table);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return tableList;
    }


}
