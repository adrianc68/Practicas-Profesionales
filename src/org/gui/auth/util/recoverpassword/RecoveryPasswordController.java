package org.gui.auth.util.recoverpassword;

import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import org.gui.ValidatorController;
import org.gui.auth.resources.alerts.OperationAlert;
import org.gui.auth.util.changepassword.ChangePasswordController;
import org.util.Auth;
import org.util.Validator;
import org.util.mail.Mail;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RecoveryPasswordController extends ValidatorController implements Initializable {
    @FXML private Button sendCodeButton;
    @FXML private Button verifyCodeButton;
    @FXML private TextField emailTextField;
    @FXML private TextField codeTextField;
    @FXML private Button closeButton;
    @FXML private Label systemLabel;
    @FXML private Label emailLabel;
    @FXML private AnchorPane rootStage;
    @FXML private Label codeLabel;
    @FXML private MaterialDesignIconView checkIconEmail;
    @FXML private MaterialDesignIconView checkIconCode;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setStyleClass(rootStage);
        verifyCodeButton.setVisible(false);
        codeTextField.setVisible(false);
        initValidatorToTextFields();
    }

    public void showStage() {
        loadFXMLFile(getClass().getResource("/org/gui/auth/util/recoverpassword/RecoveryPasswordVista.fxml") , this);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
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
    protected void sendCodeButtonPressed(ActionEvent event) {
        String emailInput = emailTextField.getText();
        if( verifyInputData() ) {
            sendCode(emailInput);
        } else {
            systemLabel.setText("¡Verifica tus datos!");
        }
    }

    @FXML
    protected void verifyCodeButtonPressed(ActionEvent event) {
        if ( !codeTextField.getText().equals("") && verifyInputData() ) {
            checkRecoveryCodeAndOpenChangePasswordStage();
        } else {
            systemLabel.setText("¡Verifica tus datos!");
        }
    }

    private void hideCodeElementsAndShowRecoveryElements() {
        verifyCodeButton.setVisible(true);
        codeTextField.setVisible(true);
        codeLabel.setVisible(true);
        sendCodeButton.setVisible(false);
        emailTextField.setVisible(false);
        emailLabel.setVisible(false);
    }

    private void checkRecoveryCodeAndOpenChangePasswordStage() {
        boolean isCodeInputEqualToCode = false;
        String emailInput = emailTextField.getText();
        String codeInput = codeTextField.getText();
        try {
            isCodeInputEqualToCode = Auth.getInstance().getRecoveryCode(emailInput).equals(codeInput);
        } catch (SQLException sqlException) {
            OperationAlert.showLostConnectionAlert();
            Logger.getLogger( RecoveryPasswordController.class.getName() ).log(Level.WARNING, null, sqlException);
        }
        if( isCodeInputEqualToCode ) {
            stage.close();
            ChangePasswordController changePasswordController = new ChangePasswordController();
            changePasswordController.setEmail(emailInput);
            changePasswordController.showStage();
        }
    }

    private void sendCode(String email) {
        try {
            if( generateAndSendRecoveryCodeToEmail(email) ) {
                systemLabel.setText("¡Se envió un código a tu correo! ¡Ingrésalo en el campo de texto!");
            }
            hideCodeElementsAndShowRecoveryElements();
        } catch (SQLException sqlException) {
            OperationAlert.showLostConnectionAlert();
            Logger.getLogger( RecoveryPasswordController.class.getName() ).log(Level.WARNING, null, sqlException);
        }
    }

    private boolean generateAndSendRecoveryCodeToEmail(String email) throws SQLException {
        boolean isCodeSent;
        Auth.getInstance().generateRecoveryCode(email);
        String code = Auth.getInstance().getRecoveryCode(email);
        String subject = "Recuperar contraseña";
        String contentText = "<html>\n" +
                "<body>\n" +
                "  <div style=\"border-style:solid;border-color:#323232;border-width:1px 1px 1px 1px;text-align:justify;background-color:#f8f8f8;width:80% !important;height:80% !important;padding:3% 3% 3% 3%;\">\n" +
                "      <h1 style=\"color:#8a8a8a;font-family:sans-serif;text-align:left;\"> Recuperación de contraseña </h1>\n" +
                "      <p style=\"font-family:sans-serif;\"> Recibimos una solicitud para restablecer tu contraseña del <b>Sistema de Prácticas Profesionales</b></p>\n" +
                "      <p style=\"font-family:sans-serif;\"> Ingresa el siguiente código en el campo de texto indicado en el sistema para recuperar tu contraseña. </p>\n" +
                "      <div style=\"border-style:solid;border-color:#000000;border-width:1px 1px 1px 1px;background-color:#e2e2e2;display:inline-block;padding:15px 15px 15px 15px;\">" + code + "</div>\n" +
                "      <p style=\"font-size: 15px;font-family:sans-serif;color:#ff0000\"> Si no has sido tú, <u>reportalo</u> con tu Coordinador más cercano. </p>\n" +
                "  </div>\n" +
                "</body>\n" +
                "</html>\n";
        Mail mail = new Mail();
        isCodeSent = mail.sendEmail(email, subject, contentText);
        return isCodeSent;
    }

    private void initValidatorToTextFields() {
        interruptorMap = new LinkedHashMap<>();
        interruptorMap.put(emailTextField, false);
        interruptorMap.put(codeTextField, true);
        Map<TextInputControl, Object[]> validatorMap = new HashMap<>();
        Object[] emailContraints = {Validator.EMAIL_PATTERN, Validator.EMAIL_LENGTH, checkIconEmail};
        Object[] codeTextConstraints = {Validator.RECOVERY_CODE_PATTERN, Validator.RECOVERY_CODE_LENGTH, checkIconCode};
        validatorMap.put(codeTextField, codeTextConstraints);
        validatorMap.put(emailTextField, emailContraints);
        initValidatorToTextInputControl(validatorMap);
    }

}