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
import org.database.dao.AccessAccountDAO;
import org.gui.Controller;
import org.gui.auth.resources.alerts.OperationAlert;
import org.util.Auth;
import org.util.CSSProperties;
import org.util.Cryptography;
import java.net.URL;
import java.util.ResourceBundle;

public class ChangePasswordController extends Controller implements Initializable {
    private String email;
    @FXML private PasswordField passwordTextField;
    @FXML private PasswordField confirmationPasswordTextField;
    @FXML private Label systemLabel;
    @FXML private Button closeButton;
    @FXML private AnchorPane rootStage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setStyleClass(rootStage, getClass().getResource("../../resources/" + CSSProperties.readTheme().getTheme() ).toExternalForm() );
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
    protected void changePasswordButtonPressed(ActionEvent event) {
        if( !passwordTextField.getText().equals("") && !confirmationPasswordTextField.getText().equals("") ) {
            changePassword();
        } else {
            systemLabel.setText("¡Los campos están vacios!");
        }

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

    private void changePassword() {
        String newPassword = passwordTextField.getText();
        String newPasswordConfirmation = confirmationPasswordTextField.getText();
        if( newPassword.equals(newPasswordConfirmation) ) {
            if( Auth.getInstance().getUser() == null ) {
                changePasswordByUnloggedUser(newPassword, email);
            } else {
                changePasswordByLoggedUser(newPassword);
            }
            closeButton.fire();
        } else {
            systemLabel.setText("¡Las contraseñas no coinciden!");
        }
    }

    private void changePasswordByLoggedUser(String newPassword) {
        int currentUserId = Auth.getInstance().getUser().getId();
        String passwordEncrypted = Cryptography.cryptSHA2( newPassword );
        if ( new AccessAccountDAO().changePasswordByIdUser(passwordEncrypted, currentUserId) ) {
            showSuccessfullAlert();
            copyToClipboardSystem(Auth.getInstance().getUser().getEmail(), passwordTextField.getText() );
        } else {
            showUnsuccessfulAlert();
        }
    }

    private void changePasswordByUnloggedUser(String newPassword, String email) {
        String passwordEncrypted = Cryptography.cryptSHA2(newPassword);
        if ( new AccessAccountDAO().changePasswordByEmail(passwordEncrypted, email) ) {
            showSuccessfullAlert();
            copyToClipboardSystem( Auth.getInstance().getEmail() , newPassword );
        } else {
            showUnsuccessfulAlert();
        }
    }

    private void showUnsuccessfulAlert() {
        String title = "Cambio de contraseña fallido";
        String contextText = "¡No se pudo realizar el cambio de contraseña!";
        OperationAlert.showUnsuccessfullAlert(title, contextText);
    }

    private void showSuccessfullAlert() {
        String title = "Cambio de contraseña exitoso";
        String contextText = "¡Se cambio la contraseña con exito! Se ha copiado la cuenta de acceso al portapapeles de tu sistema";
        OperationAlert.showSuccessfullAlert(title, contextText);
    }

    private void copyToClipboardSystem(String email, String password) {
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putString(email + ":" + password);
        clipboard.setContent(content);
    }

}
