import javafx.application.Application;
import javafx.stage.Stage;
import org.gui.auth.LoginController;
import org.gui.auth.util.theme.SelectThemeController;
import org.util.CSSProperties;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
       if( CSSProperties.readConfiguredAppProperties() ) {
            SelectThemeController selectThemeController = new SelectThemeController();
            selectThemeController.showStage();
        }
        LoginController loginController = new LoginController();
        loginController.showStage();

    }

    public static void main(String[] args) {
        launch(args);
    }

}