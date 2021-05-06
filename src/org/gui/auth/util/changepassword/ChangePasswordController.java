package org.gui.auth.util.changepassword;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import org.gui.Controller;
import org.gui.auth.resources.alerts.OperationAlert;
import org.util.Auth;
import org.util.Cryptography;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChangePasswordController extends Controller implements Initializable {
    private String email;
    @FXML private PasswordField passwordTextField;
    @FXML private PasswordField confirmationPasswordTextField;
    @FXML private Label systemLabel;
    @FXML private Button closeButton;
    @FXML private AnchorPane rootStage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setStyleClass(rootStage);
    }

    public void showStage() {
        loadFXMLFile(getClass().getResource("/org/gui/auth/util/changepassword/ChangePasswordVista.fxml"), this);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @FXML
    protected void cancelButtonPressed() {
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
    protected void changePasswordButtonPressed(ActionEvent event) {
        if(!isFieldEmpty() && isPasswordsEquals() ) {
            changePassword();
        }
    }

    private void showSuccessfullAlert() {
        String title = "Cambio de contraseña exitoso";
        String contextText = "¡Se cambio la contraseña con exito! Se ha copiado la cuenta de acceso al portapapeles de tu sistema";
        OperationAlert.showSuccessfullAlert(title, contextText);
    }

    private boolean isFieldEmpty() {
        boolean isFieldEmpty = true;
        String password = passwordTextField.getText();
        String passwordConfirmation = confirmationPasswordTextField.getText();
        if( !password.equals("") && !passwordConfirmation.equals("") ) {
            isFieldEmpty = false;
        } else {
            systemLabel.setText("¡Los campos están vacios!");
        }
        return isFieldEmpty;
    }

    private boolean isPasswordsEquals() {
        boolean isPasswordFieldEqual = false;
        String password = passwordTextField.getText();
        String passwordConfirmation = confirmationPasswordTextField.getText();
        if( password.equals(passwordConfirmation) ) {
            isPasswordFieldEqual = true;
        } else {
            systemLabel.setText("¡Las contraseñas no coinciden!");
        }
        return isPasswordFieldEqual;
    }

    private void changePassword() {
        String newPassword = passwordTextField.getText();
        String newPasswordConfirmation = confirmationPasswordTextField.getText();
        if( newPassword.equals(newPasswordConfirmation) ) {
            if( Auth.getInstance().getCurrentUser() == null ) {
                changePasswordByUnloggedUser(newPassword, email);
            } else {
                changePasswordByLoggedUser(newPassword);
            }
        } else {
            systemLabel.setText("¡Las contraseñas no coinciden!");
        }
    }

    private void changePasswordByLoggedUser(String newPassword) {
        boolean isPasswordChanged = false;
        String passwordEncrypted = Cryptography.cryptSHA2( newPassword );
        try {
            isPasswordChanged = Auth.getInstance().resetPassword(passwordEncrypted);
        } catch (SQLException sqlException) {
            OperationAlert.showLostConnectionAlert();
            Logger.getLogger( ChangePasswordController.class.getName() ).log(Level.WARNING, null, sqlException);
        }
        if (isPasswordChanged) {
            stage.close();
            showSuccessfullAlert();
            copyToClipboardSystem(Auth.getInstance().getCurrentUser().getEmail(), passwordTextField.getText() );
        }
    }

    private void changePasswordByUnloggedUser(String newPassword, String email) {
        boolean isPasswordChanged = false;
        String passwordEncrypted = Cryptography.cryptSHA2(newPassword);
        try {
            isPasswordChanged = Auth.getInstance().resetPasswordByUnloggedUser(email, passwordEncrypted);
        } catch (SQLException sqlException) {
            OperationAlert.showLostConnectionAlert();
            Logger.getLogger( ChangePasswordController.class.getName() ).log(Level.WARNING, null, sqlException);
        }
        if (isPasswordChanged) {
            stage.close();
            showSuccessfullAlert();
            copyToClipboardSystem( email, newPassword );
        }
    }

    private void copyToClipboardSystem(String email, String password) {
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putString(email + ":" + password);
        clipboard.setContent(content);
    }

}
