package home;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;
import login.loginController;

import java.io.IOException;

public class homeController {
    @FXML
    public Button logoutButton;

    @FXML
    public AnchorPane logoutPane;

    @FXML
    private void loadLogin() throws IOException {
        FXMLLoader loader = new FXMLLoader(login.loginApplication.url);
        Parent root = loader.load();

        Scene scene = logoutButton.getScene();
        root.translateXProperty().set(scene.getWidth());
        BorderPane parentContainer = (BorderPane) logoutButton.getScene().getRoot();
        parentContainer.getChildren().add(root);



        loginController loginController = loader.getController();

        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(root.translateXProperty(), 0, Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.seconds(0.5), kv);
        timeline.getKeyFrames().add(kf);
        timeline.setOnFinished(t -> {
            parentContainer.getChildren().remove(logoutPane);
            loginController.loggedOut();
        });
        timeline.play();
    }
}
