package home;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class EmpHome extends Application {

    public static URL url = homeApp.class.getResource("/emp-home-view.fxml");

    private double x,y;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(url);
        Scene scene = new Scene(fxmlLoader.load(), 1200, 720);
//        stage.initStyle(StageStyle.UNDECORATED);
        stage.setMinWidth(1200);
        stage.setMinHeight(720);
        stage.setResizable(true);
        scene.setOnMousePressed(event -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });

        scene.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
        });
        stage.setTitle("Home");
        stage.setScene(scene);
        stage.show();
    }

}
