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
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

            String query = "Select * from user where user_name = '" + usernameField.getText() + "' and password = '" + passwordField.getText() + "'";

            try {
                Statement statement = con.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                if (resultSet.next()) {
                    this.messageField.setText("");
                    loadHome(event);
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Message");
                    alert.setHeaderText("You logged in");
                    alert.setContentText("user: " + usernameField.getText());
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
