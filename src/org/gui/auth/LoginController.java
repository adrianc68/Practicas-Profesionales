package org.gui.auth;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.domain.Coordinator;
import org.domain.Person;
import org.domain.Practitioner;
import org.domain.Professor;
import org.gui.Controller;
import org.gui.auth.resources.alerts.OperationAlert;
import org.gui.auth.util.recoverpassword.RecoveryPasswordController;
import org.util.Auth;
import org.util.CSSProperties;
import org.util.Cryptography;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.gui.auth.users.administrator.AdministratorController;
import org.gui.auth.users.coordinator.CoordinatorController;
import org.gui.auth.users.practitioner.PractitionerController;
import org.util.exceptions.InactiveUserException;
import org.util.exceptions.LimitReachedException;
import org.util.exceptions.UserNotFoundException;

public class LoginController extends Controller implements Initializable {
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
        loadFXMLFile(getClass().getResource("/org/gui/auth/LoginVista.fxml"), this);
        stage.show();
    }

    @FXML
    protected void passwordRecoveryButtonPressed(MouseEvent event) {
        RecoveryPasswordController recoveryPasswordController = new RecoveryPasswordController();
        recoveryPasswordController.showStage();
    }

    @FXML
    protected void cancelButtonPressed(ActionEvent event) {
        stage.close();
    }

    @FXML
    protected void stageDragged(MouseEvent event) {
        super.stageDragged(event);
    }

    @FXML
    protected void stagePressed(MouseEvent event) {
        super.stagePressed(event);
    }

    @FXML
    protected void loginButtonPressed(ActionEvent event) {
        if ( login() ) {
            stage.hide();
            showStageBySpecifiedUser();
            clearLogIn();
            setStyleClass();
            stage.show();
        }
    }

    private boolean login() {
        boolean isLogged = false;
        String emailInput = emailTextField.getText();
        String passwordInputEncrypted = Cryptography.cryptSHA2(passwordTextField.getText() );
        try {
            isLogged = Auth.getInstance().logIn(emailInput, passwordInputEncrypted);
        } catch (UserNotFoundException | LimitReachedException | InactiveUserException e) {
            systemLabel.setText( e.getMessage() );
            Logger.getLogger( LoginController.class.getName() ).log(Level.FINE, null, e);
        } catch (SQLException e) {
            OperationAlert.showLostConnectionAlert();
            Logger.getLogger( LoginController.class.getName() ).log(Level.WARNING, null, e);
        }
        return isLogged;
    }

    private void clearLogIn() {
        Auth.getInstance().logOut();
        passwordTextField.setText("");
        emailTextField.setText("");
        systemLabel.setText("");
    }

    private void showStageBySpecifiedUser() {
        Person user = Auth.getInstance().getCurrentUser();
        if (user != null) {
            if (user instanceof Professor) {
                System.out.println("Agregar ventana profesor");
            } else if (user instanceof Coordinator) {
                CoordinatorController coordinatorController = new CoordinatorController();
                coordinatorController.showStage();
            } else if (user instanceof Practitioner) {
                PractitionerController practitionerController = new PractitionerController();
                practitionerController.showStage();
            } else {
                AdministratorController administratorController = new AdministratorController();
                administratorController.showStage();
            }
        }
    }

    private void setStyleClass() {
        setStyleClass(rootStage, getClass().getResource( "resources/" + CSSProperties.readTheme().getTheme() ).toExternalForm() );
    }

}
