package login;

import home.homeApp;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.security.auth.login.LoginContext;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class loginApplication extends Application {

    public static URL url = loginApplication.class.getResource("/login-view.fxml");

    double x,y = 0;
    @Override
    public void start(Stage stage) throws IOException {
        if (UserSession.checkSession()) {
            try {
                loginController loginController = new loginController();
                if (Objects.equals(UserSession.getSession().split(",")[2], "admin")) {
                    loginController.loadHome();
                } else {
                    loginController.loadEmpHome();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return;
        }
        FXMLLoader fxmlLoader = new FXMLLoader(url);
        Scene scene = new Scene(fxmlLoader.load(), 775, 500);
        stage.initStyle(StageStyle.UNDECORATED);



        scene.setOnMousePressed(event -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });

        scene.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
        });

        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();


    }

    public static void main(String[] args) {
        launch();
    }
}
