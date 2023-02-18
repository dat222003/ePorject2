package model;

import login.DatabaseConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TotalBillDB {
    private final DatabaseConnect databaseConnect = new DatabaseConnect();

    public void createTotalBill(Bill bill) {
        try (
                Connection con = databaseConnect.getConnect();
        ) {
            PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO `total_bill` (date, total_money) VALUES (?, ?)");
            preparedStatement.setString(1, bill.getDate().split(" ")[0]);
            preparedStatement.setString(2, Double.toString(bill.getTotal()));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public TotalBill getTotalByDate(Bill bill) {
        try (
                Connection con = databaseConnect.getConnect();
        ) {
            PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM `total_bill` WHERE date = ?");
            preparedStatement.setString(1, bill.getDate().split(" ")[0]);

            ResultSet resultSet = preparedStatement.executeQuery();
            TotalBill totalBill = null;
            while (resultSet.next()) {
                totalBill = new TotalBill();
                totalBill.setDate(resultSet.getString("date"));
                totalBill.setTotal_money(resultSet.getDouble("total_money"));
            }
            return totalBill;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateTotalBill(Bill bill) {
        try (
                Connection con = databaseConnect.getConnect();
        ) {
            TotalBill totalBill = getTotalByDate(bill);
            System.out.println(totalBill);
            if (totalBill != null) {
                PreparedStatement preparedStatement = con.prepareStatement("UPDATE `total_bill` SET total_money = ? WHERE date = ?");
                preparedStatement.setString(1, Double.toString(totalBill.getTotal_money() + bill.getTotal()));
                preparedStatement.setString(2, bill.getDate().split(" ")[0]);
                preparedStatement.executeUpdate();
            } else {
                createTotalBill(bill);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<TotalBill> getAllTotalBill() {
        try (
                Connection con = databaseConnect.getConnect();
        ) {
            PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM `total_bill`");
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<TotalBill> totalBills = new ArrayList<>();
            while (resultSet.next()) {
                TotalBill totalBill = new TotalBill();
                totalBill.setDate(resultSet.getString("date").split(" ")[0]);
                totalBill.setTotal_money(resultSet.getDouble("total_money"));
                totalBills.add(totalBill);
            }
            return totalBills;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }



}
