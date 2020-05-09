import javafx.application.Application;
import javafx.stage.Stage;
import org.gui.login.LoginController;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        LoginController loginController = new LoginController();
        loginController.showStage();
    }

    public static void main(String[] args) {
        launch(args);
    }

}