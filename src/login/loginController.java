package login;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
                checkUser();
            }
        }

        public void checkUser() {
            DatabaseConnect databaseConnect = new DatabaseConnect();
            Connection con = DatabaseConnect.getConnect();

            String query = "Select * from user where user_name = '" + usernameField.getText() + "' and password = '" + passwordField.getText() + "'";

            try {
                Statement statement = con.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                if (resultSet.next()) {
                    this.messageField.setText("");
                    loadHome();
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
        private void loadHome() throws IOException {
            Parent root = FXMLLoader.load(home.homeApp.url);
            Scene scene = loginButton.getScene();
            root.translateXProperty().set(scene.getWidth());

            BorderPane parentContainer = (BorderPane) loginButton.getScene().getRoot();

            parentContainer.getChildren().add(root);

            Timeline timeline = new Timeline();
            KeyValue kv = new KeyValue(root.translateXProperty(), 0, Interpolator.EASE_IN);
            KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
            timeline.getKeyFrames().add(kf);
            timeline.setOnFinished(t -> {
                parentContainer.getChildren().remove(loginPane);
            });
            timeline.play();
        }

        public void loggedOut() {
            this.messageField.setText("You Logged Out");
        }

    }
