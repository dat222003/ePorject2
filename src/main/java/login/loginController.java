package login;

import com.jfoenix.controls.JFXRadioButton;
import home.homeApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class loginController {
        @FXML
        private Button loginButton;
        @FXML
        private JFXRadioButton rememberButton;
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
                createUserSession("user");
//                checkUser(e);
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
                ResultSet resultSet = admin_query.executeQuery();
                if (resultSet.next() && resultSet.getString("user") != null) {
                    loadHome(event); //admin rights
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

        public void createUserSession(String userName) {
            LocalDateTime dateTime = LocalDateTime.now();
            String session = userName + dateTime;
//            String sha256hex = Hashing.sha256()
//                    .hashString(session, StandardCharsets.UTF_8)
//                    .toString();
            System.out.println(session);
            Path session_path = Paths.get("src/main/resources/session.txt");
            try (
                    BufferedReader session_reader = Files.newBufferedReader(session_path, StandardCharsets.UTF_8)
            ) {
                String line = session_reader.readLine();
                if (line != null) {
                    System.out.println(line);
                }


            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

}
