package org.gui.auth;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.StageStyle;
import org.domain.Coordinator;
import org.domain.Person;
import org.domain.Practitioner;
import org.domain.Professor;
import org.gui.auth.util.recoverpassword.RecoveryPasswordController;
import org.util.Auth;
import org.util.CSSProperties;
import org.util.Cryptography;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.gui.auth.users.administrator.AdministratorController;
import org.gui.auth.users.coordinator.CoordinatorController;
import org.gui.auth.users.practitioner.PractitionerController;
import org.util.NetworkAddress;

public class LoginController implements Initializable {
    private double mousePositionOnX;
    private double mousePositionOnY;
    private Stage stage;
    @FXML private TextField emailTextField;
    @FXML private PasswordField passwordTextField;
    @FXML private Label systemLabel;
    @FXML private Button closeButton;
    @FXML private AnchorPane rootStage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setStyleClass();
    }

    public void showStage() {
        FXMLLoader loader = new FXMLLoader( getClass().getResource("/org/gui/auth/LoginVista.fxml") );
        loader.setController(this);
        Parent root = null;
        try{
            root = loader.load();
        } catch(IOException ioe) {
            Logger.getLogger( LoginController.class.getName() ).log(Level.SEVERE, null, ioe);
        }
        stage = new Stage();
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void loginButtonPressed(ActionEvent event) {
        String macAddresEncrypted = Cryptography.cryptSHA2( NetworkAddress.getLocalAdress() );
        if( !Auth.getInstance().isAttempsLimitReached(macAddresEncrypted) ) {
            if( login() != null) {
                stage.hide();
                showStageBySpecifiedUser();
                clearLogIn();
                stage.show();
            } else {
                systemLabel.setText("¡Verifica tus datos!");
            }
        } else {
            systemLabel.setText("¡Alcanzaste el limite de intentos! ¡Espera 10 minutos!");
        }
    }

    @FXML
    void passwordRecoveryButtonPressed(MouseEvent event) {
        RecoveryPasswordController recoveryPasswordController = new RecoveryPasswordController();
        recoveryPasswordController.showStage();
    }

    @FXML
    void cancelButtonPressed(ActionEvent event) {
        Stage stage = ( (Stage) ( (Node) event.getSource() ).getScene().getWindow() );
        stage.close();
    }

    @FXML
    void stageDragged(MouseEvent event) {
        Stage stage = (Stage) ( ( (Node) event.getSource() ).getScene().getWindow() );
        stage.setX( event.getScreenX() - mousePositionOnX );
        stage.setY( event.getScreenY() - mousePositionOnY );
    }

    @FXML
    void stagePressed(MouseEvent event) {
        mousePositionOnX = event.getSceneX();
        mousePositionOnY = event.getSceneY();
    }

    private Person login() {
        String emailInput = emailTextField.getText();
        String passwordInputEncrypted = Cryptography.cryptSHA2( passwordTextField.getText() );
        String macAddressEncrypted = Cryptography.cryptSHA2( NetworkAddress.getLocalAdress() );
        Auth auth = Auth.getInstance();
        auth.logIn(macAddressEncrypted, emailInput, passwordInputEncrypted);
        return auth.getUser();
    }

    private void clearLogIn() {
        Auth.getInstance().logOut();
        passwordTextField.setText("");
        emailTextField.setText("");
        setStyleClass();
    }

    // BAD USER MANAGER
    // FIX HERE
    private void showStageBySpecifiedUser() {
        Person user = Auth.getInstance().getUser();
        if(user != null) {
            if(user instanceof Professor) {
                System.out.println("Agregar ventana profesor");
            } else if(user instanceof Coordinator) {
                CoordinatorController coordinatorController = new CoordinatorController();
                coordinatorController.showStage();
            } else if(user instanceof Practitioner) {
                PractitionerController practitionerController = new PractitionerController();
                practitionerController.showStage();
            } else {
                AdministratorController administratorController = new AdministratorController();
                administratorController.showStage();
            }
        }
    }

    private void setStyleClass() {
        rootStage.getStylesheets().clear();
        rootStage.getStylesheets().add(getClass().getResource("resources/" + CSSProperties.readTheme().getTheme() ).toExternalForm() );
    }

}
