package model;

import login.DatabaseConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DishDB {
    private final DatabaseConnect databaseConnect = new DatabaseConnect();

    public boolean addDish(Dish dish) {
        try(
                Connection con = databaseConnect.getConnect();
        ) {
            PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO `dish` (name, cat_id, price, img) VALUES (?, ?, ?, ?)");
            preparedStatement.setString(1, dish.getName());
            preparedStatement.setString(2, dish.getCat_id());
            preparedStatement.setString(3, dish.getDish_price());
            preparedStatement.setString(4, dish.getImg_name());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean updateDish(Dish dish) {
        try(
                Connection con = databaseConnect.getConnect();
        ) {
            PreparedStatement preparedStatement = con.prepareStatement("UPDATE `dish` SET name = ?, cat_id = ?, price = ?, img = ? WHERE dish_id = ?");
            preparedStatement.setString(1, dish.getName());
            preparedStatement.setString(2, dish.getCat_id());
            preparedStatement.setString(3, dish.getDish_price());
            preparedStatement.setString(4, dish.getImg_name());
            preparedStatement.setString(5, dish.getDish_id());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean deleteDish(String dish_id) {
        try(
                Connection con = databaseConnect.getConnect();
        ) {
            PreparedStatement preparedStatement = con.prepareStatement("DELETE FROM `dish` WHERE dish_id = ?");
            preparedStatement.setString(1, dish_id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public ArrayList<Dish> getAllDish() {
        ArrayList<Dish> dishList = new ArrayList<>();
        try (
                Connection con = databaseConnect.getConnect();
        ) {
            PreparedStatement preparedStatement = con.prepareStatement("select * from `dish` join `dish_category` category on dish.cat_id = category.cat_id;");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Dish dish = new Dish();
                dish.setDish_id(resultSet.getString("dish_id"));
                dish.setName(resultSet.getString("dish.name"));
                dish.setCat_id(resultSet.getString("dish.cat_id"));
                dish.setCategory(resultSet.getString("category.name"));
                dish.setDish_price(resultSet.getString("price"));
                dish.setImg_name(resultSet.getString("dish.img"));
                dishList.add(dish);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return dishList;
    }


    public Dish getOneDish(String dish_id) {
        Dish dish = new Dish();
        try (
                Connection con = databaseConnect.getConnect();
        ) {
            PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM `dish` WHERE dish_id = ?");
            preparedStatement.setString(1, dish_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                dish.setDish_id(resultSet.getString("dish_id"));
                dish.setName(resultSet.getString("name"));
                dish.setCat_id(resultSet.getString("cat_id"));
                dish.setDish_price(resultSet.getString("price"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return dish;
    }
}
