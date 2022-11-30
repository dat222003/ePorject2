package model;

import login.DatabaseConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDB {
    private final DatabaseConnect databaseConnect = new DatabaseConnect();

    public String addNewCustomer(Customer customer){
        try (
                Connection con = databaseConnect.getConnect();
                )
        {
            PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO `client_info` " +
                    "(`name`, `phone`, `email`) VALUES (?,?,?)");
            preparedStatement.setString(1,customer.getName());
            preparedStatement.setString(2,customer.getPhone());
            preparedStatement.setString(3,customer.getEmail());
            preparedStatement.executeUpdate();
            //return last insert id
            PreparedStatement preparedStatement1 = con.prepareStatement("SELECT LAST_INSERT_ID()");
            ResultSet resultSet = preparedStatement1.executeQuery();
            if (resultSet.next()){
                return resultSet.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }
    public boolean updateCustomer(Customer customer){
        try (
                Connection con = databaseConnect.getConnect();
        )
        {
            PreparedStatement preparedStatement = con.prepareStatement("UPDATE `client_info` SET `name`=?,`phone`=?,`email`=? WHERE `client_id`=?");
            preparedStatement.setString(1,customer.getName());
            preparedStatement.setString(2,customer.getPhone());
            preparedStatement.setString(3,customer.getEmail());
            preparedStatement.setString(4,customer.getClient_id());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public ArrayList<Customer> getAllCustomer(){
        ArrayList<Customer> customerList = new ArrayList<>();
        try (
                Connection con = databaseConnect.getConnect();
        )
        {
            PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM `client_info`");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Customer customer = new Customer();
                customer.setClient_id(resultSet.getString("client_id"));
                customer.setName(resultSet.getString("name"));
                customer.setPhone(resultSet.getString("phone"));
                customer.setEmail(resultSet.getString("email"));
                customerList.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return customerList;
    }

    public Customer getOneCustomer(String id) {
        Customer customer = new Customer();
        try (
                Connection con = databaseConnect.getConnect();
        )
        {
            PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM `client_info` WHERE `client_id`=?");
            preparedStatement.setString(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                customer.setClient_id(resultSet.getString("client_id"));
                customer.setName(resultSet.getString("name"));
                customer.setPhone(resultSet.getString("phone"));
                customer.setEmail(resultSet.getString("email"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return customer;
    }




}
