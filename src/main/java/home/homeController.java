package home;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import login.loginApplication;

import java.io.IOException;

public class homeController{
    @FXML
    public Button logoutButton;
    @FXML
    public AnchorPane logoutPane;

    @FXML
    private void loadLogin(ActionEvent event) throws IOException {
        loginApplication loginApplication = new loginApplication();
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.close();
        loginApplication.start(new Stage());
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Message");
        alert.setHeaderText("You logged out");
        alert.showAndWait();
    }

}
