package home;


import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import login.DatabaseConnect;
import login.loginApplication;

import java.io.IOException;
import java.net.URL;
import java.util.EventListener;
import java.util.Optional;
import java.util.ResourceBundle;

public class homeController implements Initializable {

    @FXML
    private Label userNameLabel;
    @FXML
    private Button logoutButton;
    @FXML
    private BorderPane homePane;

    @FXML
    private JFXButton employeeButton;

    @FXML
    private JFXButton dashboardButton;
    @FXML
    private JFXButton tableButton;

    Pane pane;

    @FXML
    private void loadLogin(ActionEvent event) throws IOException {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setHeaderText("Confirm you want to logout");
        confirm.setContentText("You are about to logout!");
        Optional<ButtonType> result = confirm.showAndWait();
        if (result.get() == ButtonType.OK) {
            loginApplication loginApplication = new loginApplication();
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.close();
            DatabaseConnect.deleteUserSession();
            loginApplication.start(new Stage());
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Message");
            alert.setHeaderText("You logged out");
            alert.showAndWait();
        }

    }

    @FXML
    // load employee.fxml into homePane
    private void loadEmployee(ActionEvent event) {
        try {
            pane = FXMLLoader.load(getClass().getResource("/employeeTab.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        homePane.setCenter(pane);

    }

    @FXML
    // load dashboard.fxml into homePane
    private void loadDashBoard(ActionEvent event) {
        try {
            pane = FXMLLoader.load(getClass().getResource("/dashBoardTab.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        homePane.setCenter(pane);

    }

    @FXML
    // load table.fxml into homePane
    private void loadTable(ActionEvent event) {
        try {
            pane = FXMLLoader.load(getClass().getResource("/tableTab.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        homePane.setCenter(pane);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dashboardButton.fire();

    }
}
