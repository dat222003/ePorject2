package model;

import login.DatabaseConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CategoryDB {
    private final DatabaseConnect databaseConnect = new DatabaseConnect();

    public boolean addCategory(Category category) {
        try(
                Connection con = databaseConnect.getConnect();
        ) {
            PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO `dish_category` (name, description, img) VALUES (?, ?, ?)");
            preparedStatement.setString(1, category.getName());
            preparedStatement.setString(2, category.getDescription());
            preparedStatement.setString(3, category.getImg());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public boolean updateCategory(Category category) {
        try(
                Connection con = databaseConnect.getConnect();
        ) {
            PreparedStatement preparedStatement = con.prepareStatement("UPDATE `dish_category` SET name = ?, description = ?, img = ? WHERE cat_id = ?");
            preparedStatement.setString(1, category.getName());
            preparedStatement.setString(2, category.getDescription());
            preparedStatement.setString(3, category.getImg());
            preparedStatement.setString(4, category.getCat_id());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean deleteCategory(String cat_id) {
        try(
                Connection con = databaseConnect.getConnect();
        ) {
            PreparedStatement preparedStatement = con.prepareStatement("UPDATE `dish_category` SET available = 0 WHERE cat_id = ?");
            preparedStatement.setString(1, cat_id);
            PreparedStatement getAllDishInCategory = con.prepareStatement("SELECT * FROM `dish` WHERE cat_id = ?");
            getAllDishInCategory.setString(1, cat_id);
            ResultSet resultSet = getAllDishInCategory.executeQuery();
            while (resultSet.next()) {
                DishDB dishDB = new DishDB();
                dishDB.deleteDish(resultSet.getString("dish_id"));
            }
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public ArrayList<Category> getAllCategory() {
        ArrayList<Category> categories = new ArrayList<>();
        try(
                Connection con = databaseConnect.getConnect();
        ) {
            PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM `dish_category`");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Category category = new Category();
                category.setCat_id(resultSet.getString("cat_id"));
                category.setName(resultSet.getString("name"));
                category.setDescription(resultSet.getString("description"));
                if (resultSet.getInt("available") == 1) {
                    categories.add(category);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }

    public Category getOneCategory(String cat_id) {
        Category category = new Category();
        try(
                Connection con = databaseConnect.getConnect();
        ) {
            PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM `dish_category` WHERE cat_id = ?");
            preparedStatement.setString(1, cat_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                category.setCat_id(resultSet.getString("cat_id"));
                category.setName(resultSet.getString("name"));
                category.setDescription(resultSet.getString("description"));
//                category.setImg(resultSet.getString("img"));
                }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return category;
    }


}
