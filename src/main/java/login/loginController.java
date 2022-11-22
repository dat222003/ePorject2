package login;

import home.homeApp;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.*;
import java.util.Scanner;

public class loginController {
        @FXML
        private Button loginButton;
        @FXML
        private Button closeButton;

        @FXML
        private Label messageField;

        @FXML
        private TextField usernameField;

        @FXML
        private TextField passwordField;

        @FXML
        private BorderPane loginPane;

        public void loginButtonOnAction(ActionEvent e) {

            if (usernameField.getText().isBlank()) {
                messageField.setText("username missing");
            } else if (passwordField.getText().isBlank()) {
                messageField.setText("password missing");
            } else {
                checkUser(e);
            }
        }

        public void checkUser(ActionEvent event) {
            DatabaseConnect databaseConnect = new DatabaseConnect();
            Connection con = DatabaseConnect.getConnect();
            try {
                PreparedStatement admin_query = con.prepareStatement("select * from user_account" +
                        "    right join admin a on user_account.user_id = a.user_id" +
                        "    and user_account.user = ? and user_account.password = ?");
                admin_query.setString(1, usernameField.getText());
                admin_query.setString(2, passwordField.getText());
//                System.out.println(admin_query);
                ResultSet resultSet = admin_query.executeQuery();
                if (resultSet.next() && resultSet.getString("user") != null) {
                    loadHome(event);
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Message");
                    alert.setHeaderText("You logged in as admin");
                    alert.setContentText("admin: " + usernameField.getText());
                    alert.showAndWait();
                    return;
                } else {
                    PreparedStatement emp_query = con.prepareStatement("select * from user_account" +
                            "    right join employee a on user_account.user_id = a.user_id" +
                            "    and user_account.user =  ? and user_account.password = ?");
                    emp_query.setString(1, usernameField.getText());
                    emp_query.setString(2, passwordField.getText());
//                    System.out.println(emp_query);
                    resultSet = emp_query.executeQuery();
                }
                if (resultSet.next() && resultSet.getString("user") != null) {
                    loadHome(event);
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Message");
                    alert.setHeaderText("You logged in as employee");
                    alert.setContentText("emp: " + usernameField.getText());
                    alert.showAndWait();
                } else {
                    messageField.setText("Invalid Credentials!");
                }
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }

        }

        public void closeButtonOnAction(ActionEvent event) {
            Stage s = (Stage) closeButton.getScene().getWindow();
            s.close();
        }

        @FXML
        private void loadHome(ActionEvent event) throws IOException {
            homeApp homeApp = new homeApp();
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.close();
            homeApp.start(new Stage());

        }

}
