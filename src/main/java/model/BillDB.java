package model;

import login.DatabaseConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

public class BillDB {

    private final DatabaseConnect databaseConnect = new DatabaseConnect();

    public ArrayList<Bill> getAllBill() {
        ArrayList<Bill> billList = new ArrayList<>();

        try (
                Connection con = databaseConnect.getConnect();
        ) {
            PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM `bill` join bill_detail bd on bill.bill_id = bd.bill_id " +
                    "join client_info ci on ci.client_id = bill.client_id");
            ResultSet resultSet = preparedStatement.executeQuery();
            PreparedStatement getDishByBillId = con.prepareStatement("select bd.dish_id, dish.name,dishAmount, bd.dishTotalPrice from `bill` join bill_detail bd on bill.bill_id = bd.bill_id " +
                    "join `dish` on bd.dish_id = dish.dish_id where bill.bill_id = ?");

            while (resultSet.next()) {
                Bill bill = new Bill();
                bill.setBill_id(resultSet.getString("bill.bill_id"));
                bill.setTable_id(resultSet.getString("table_id"));
                bill.setEmployee_id(resultSet.getString("employee_id"));
                bill.setTotal(resultSet.getDouble("total_bill"));
                bill.setStatus(resultSet.getString("status"));
                bill.setCustomerName(resultSet.getString("name"));
                bill.setClient_id(resultSet.getString("bill.client_id"));
                bill.setCustomerPhone(resultSet.getString("phone"));
                bill.setDate(resultSet.getString("date"));
                getDishByBillId.setString(1, bill.getBill_id());
                ResultSet dishResult = getDishByBillId.executeQuery();
                ArrayList<Dish> dishList = new ArrayList<>();
                while (dishResult.next()) {
                    Dish dish = new Dish();
                    dish.setDish_id(dishResult.getString("dish_id"));
                    dish.setName(dishResult.getString("name"));
                    dish.setQty(Integer.valueOf(dishResult.getString("dishAmount")));
                    dish.setDish_price(String.valueOf(dishResult.getDouble("bd.dishTotalPrice")/dish.getQty()));
                    dish.setTotal_price(String.valueOf(dishResult.getDouble("bd.dishTotalPrice")));
                    if (!dishList.contains(dish)) {
                        dishList.add(dish);
                    }
                }
                if (bill.getDishList() != dishList) {
                    bill.setDishList(dishList);
                }
                if (billList.stream().noneMatch(b -> b.getBill_id().equals(bill.getBill_id()))) {
                    billList.add(bill);
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return billList;
    }

    public Bill getOneBill(String id) {
        Bill bill = new Bill();
        try (
                Connection con = databaseConnect.getConnect();
        ) {
            PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM `bill` join bill_detail bd on bill.bill_id = bd.bill_id " +
                    "join client_info ci on ci.client_id = bill.client_id where bill.bill_id = ?");
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            PreparedStatement getDishByBillId = con.prepareStatement("select bd.dish_id, dish.name,dishAmount from `bill` join bill_detail bd on bill.bill_id = bd.bill_id " +
                    "join `dish` on bd.dish_id = dish.dish_id where bill.bill_id = ?");
            while (resultSet.next()) {
                bill.setBill_id(resultSet.getString("bill.bill_id"));
                bill.setTable_id(resultSet.getString("table_id"));
                bill.setEmployee_id(resultSet.getString("employee_id"));
                bill.setTotal(resultSet.getDouble("total_bill"));
                bill.setStatus(resultSet.getString("status"));
                bill.setCustomerName(resultSet.getString("name"));
                bill.setClient_id(resultSet.getString("bill.client_id"));
                bill.setCustomerPhone(resultSet.getString("phone"));
                bill.setDate(resultSet.getString("date"));
                getDishByBillId.setString(1, bill.getBill_id());
                ResultSet dishResult = getDishByBillId.executeQuery();
                ArrayList<Dish> dishList = new ArrayList<>();
                while (dishResult.next()) {
                    Dish dish = new Dish();
                    dish.setDish_id(dishResult.getString("dish_id"));
                    dish.setName(dishResult.getString("name"));
                    dish.setQty(Integer.valueOf(dishResult.getString("dishAmount")));
                    if (!dishList.contains(dish)) {
                        dishList.add(dish);
                    }
                }
                if (bill.getDishList() != dishList) {
                    bill.setDishList(dishList);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return bill;
    }

    public Bill getPendingBillWithTableId(String id) {
        Bill bill = new Bill();
        try (
                Connection con = databaseConnect.getConnect();
        ) {
            PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM `bill` join bill_detail bd on bill.bill_id = bd.bill_id " +
                    "join client_info ci on ci.client_id = bill.client_id where table_id = ? and status = 'pending'");
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            PreparedStatement getDishByBillId = con.prepareStatement("select bd.dish_id, dish.name,dishAmount, bd.dishTotalPrice from `bill` join bill_detail bd on bill.bill_id = bd.bill_id " +
                    "join `dish` on bd.dish_id = dish.dish_id where bill.bill_id = ?");
            while (resultSet.next()) {
                bill.setBill_id(resultSet.getString("bill.bill_id"));
                bill.setTable_id(resultSet.getString("table_id"));
                bill.setEmployee_id(resultSet.getString("employee_id"));
                bill.setTotal(resultSet.getDouble("total_bill"));
                bill.setStatus(resultSet.getString("status"));
                bill.setCustomerName(resultSet.getString("name"));
                bill.setCustomerPhone(resultSet.getString("phone"));
                bill.setDate(resultSet.getString("date"));
                getDishByBillId.setString(1, bill.getBill_id());
                ResultSet dishResult = getDishByBillId.executeQuery();
                ArrayList<Dish> dishList = new ArrayList<>();
                while (dishResult.next()) {
                    Dish dish = new Dish();
                    dish.setDish_id(dishResult.getString("dish_id"));
                    dish.setName(dishResult.getString("name"));
                    dish.setQty(Integer.valueOf(dishResult.getString("dishAmount")));
                    dish.setDish_price(String.valueOf(dishResult.getDouble("dishTotalPrice")/dish.getQty()));
                    dish.setTotal_price(String.valueOf(dishResult.getDouble("bd.dishTotalPrice")));
                    if (!dishList.contains(dish)) {
                        dishList.add(dish);
                    }
                }
                if (bill.getDishList() != dishList) {
                    bill.setDishList(dishList);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        if (bill.getBill_id() == null) {
            return null;
        }
        return bill;
    }

    public boolean addNewBill(Bill bill) {
        try (
                Connection con = databaseConnect.getConnect();
        ) {
            CustomerDB customerDB = new CustomerDB();
            Customer customer = new Customer();
            customer.setName(bill.getCustomerName());
            customer.setPhone(bill.getCustomerPhone());
            PreparedStatement insertBill = con.prepareStatement("INSERT INTO `bill`" +
                    "(`table_id`, `employee_id`, `total_bill`, `status`, `client_id`, `date`) VALUES (?,?,?,?,?,?)");
            insertBill.setString(1, bill.getTable_id());
            insertBill.setString(2, bill.getEmployee_id());
            insertBill.setDouble(3, bill.getTotal());
            insertBill.setString(4, bill.getStatus());
            insertBill.setString(5, customerDB.addNewCustomer(customer));
            insertBill.setString(6, bill.getDate());
            insertBill.executeUpdate();
            //return inserted id
            PreparedStatement getInsertedId = con.prepareStatement("SELECT LAST_INSERT_ID()");
            ResultSet resultSet = getInsertedId.executeQuery();
            String billId = "";
            while (resultSet.next()) {
                billId = resultSet.getString(1);
            }
            PreparedStatement insertDishList = con.prepareStatement("INSERT INTO `bill_detail`" +
                    "(`bill_id`, `dish_id`, `dishAmount`, `dishTotalPrice`) VALUES (?,?,?,?)");
            for (Dish dish : bill.getDishList()) {
                insertDishList.setString(1, billId);
                insertDishList.setString(2, dish.getDish_id());
                insertDishList.setInt(3, dish.getQty());
                insertDishList.setDouble(4, dish.getQty() * Double.parseDouble(dish.getDish_price()));
                insertDishList.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean updateBill(Bill bill) {
        //update all properties of bill
        try (
                Connection con = databaseConnect.getConnect();
        ) {
            //get client id from old bill
            Bill oldBill = getOneBill(bill.getBill_id());
            CustomerDB customerDB = new CustomerDB();
            Customer customer = new Customer();
            //set and update customer
            customer.setClient_id(oldBill.getClient_id());
            customer.setName(bill.getCustomerName());
            customer.setPhone(bill.getCustomerPhone());
            customerDB.updateCustomer(customer);
            //update bill
            PreparedStatement updateBill = con.prepareStatement("UPDATE `bill` SET `table_id`=?,`employee_id`=?,`total_bill`=?,`status`=?,`client_id`=?,`date`=? WHERE bill_id = ?");
            updateBill.setString(1, bill.getTable_id());
            updateBill.setString(2, bill.getEmployee_id());
            updateBill.setDouble(3, bill.getTotal());
            updateBill.setString(4, bill.getStatus());
            updateBill.setString(5, oldBill.getClient_id());
            updateBill.setString(6, bill.getDate());
            updateBill.setString(7, bill.getBill_id());
            updateBill.executeUpdate();
            if (Objects.equals(bill.getStatus(), "purchased")) {
                PreparedStatement updateTable = con.prepareStatement("UPDATE `table` SET `status`=? WHERE table_id = ?");
                updateTable.setString(1, "available");
                updateTable.setString(2, bill.getTable_id());
                updateTable.executeUpdate();
            }
            //delete all dish in bill_detail
            PreparedStatement deleteDish = con.prepareStatement("DELETE FROM `bill_detail` WHERE bill_id = ?");
            deleteDish.setString(1, bill.getBill_id());
            deleteDish.executeUpdate();
            //insert new dish list
            PreparedStatement insertDishList = con.prepareStatement("INSERT INTO `bill_detail`" +
                    "(`bill_id`, `dish_id`, `dishAmount`, `dishTotalPrice`) VALUES (?,?,?,?)");
            for (Dish dish : bill.getDishList()) {
                insertDishList.setString(1, bill.getBill_id());
                insertDishList.setString(2, dish.getDish_id());
                insertDishList.setInt(3, dish.getQty());
                insertDishList.setDouble(4, Double.parseDouble(dish.getTotal_price()));
                insertDishList.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }


        return true;
    }


    public static void main(String[] args) {
        BillDB billDB = new BillDB();
        Bill bill = new Bill();
        bill.setEmployee_id("1");
        bill.setTable_id("1");
        bill.setTotal(100d);
        bill.setStatus("Pending");
        bill.setCustomerName("Nguyen Van A");
        bill.setCustomerPhone("0123456789");
        ArrayList<Dish> dishList = new ArrayList<>();
        Dish dish = new Dish();
        dish.setDish_id("1");
        dish.setQty(2);
        dishList.add(dish);
        bill.setDishList(dishList);
        System.out.println("insert: " + billDB.addNewBill(bill));
        System.out.println(billDB.getAllBill());
        System.out.println(billDB.getOneBill("1"));
    }

}
