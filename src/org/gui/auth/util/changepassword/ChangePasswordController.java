package org.gui.auth.util.changepassword;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.database.dao.AccessAccountDAO;
import org.domain.Person;
import org.gui.auth.resources.alerts.OperationAlert;
import org.util.Auth;
import org.util.CSSProperties;
import org.util.Cryptography;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChangePasswordController implements Initializable {
    private String email;
    private double mousePositionOnX;
    private double mousePositionOnY;
    @FXML private PasswordField passwordTextField;
    @FXML private PasswordField confirmationPasswordTextField;
    @FXML private Label systemLabel;
    @FXML private Button closeButton;
    @FXML private AnchorPane rootStage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setStyleClass();
    }

    public void showStage() {
        FXMLLoader loader = new FXMLLoader( getClass().getResource("/org/gui/auth/util/changepassword/ChangePasswordVista.fxml") );
        loader.setController(this);
        Parent root = null;
        try{
            root = loader.load();
        } catch(IOException ioe) {
            Logger.getLogger( ChangePasswordController.class.getName() ).log(Level.SEVERE, null, ioe);
        }
        Stage stage = new Stage();
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene( new Scene(root) );
        stage.showAndWait();
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @FXML
    void changePasswordButtonPressed(ActionEvent event) {
        if( !passwordTextField.getText().equals("") && !confirmationPasswordTextField.getText().equals("") ) {
            changePassword();
        } else {
            systemLabel.setText("¡Los campos están vacios!");
        }

    }

    @FXML
    void cancelButtonPressed(ActionEvent event) {
        Stage stage = ( ( Stage) ( (Node) event.getSource() ).getScene().getWindow() );
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

    private void changePassword() {
        String newPassword = passwordTextField.getText();
        String newPasswordConfirmation = confirmationPasswordTextField.getText();
        Person user = Auth.getInstance().getUser();
        if( newPassword.equals(newPasswordConfirmation) ) {
            if(user == null) {
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

    private void setStyleClass() {
        rootStage.getStylesheets().clear();
        rootStage.getStylesheets().add(getClass().getResource("../../resources/" + CSSProperties.readTheme().getTheme() ).toExternalForm() );
    }

}
