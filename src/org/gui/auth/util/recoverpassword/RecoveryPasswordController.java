package org.gui.auth.util.recoverpassword;

import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.gui.auth.LoginController;
import org.gui.auth.util.changepassword.ChangePasswordController;
import org.util.Auth;
import org.util.CSSProperties;
import org.util.Validator;
import org.util.mail.Mail;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RecoveryPasswordController implements Initializable {
    private boolean isCodeSent;
    private double mousePositionOnX;
    private double mousePositionOnY;
    private Map<TextField, Boolean> interruptorMap = new HashMap<>();
    @FXML private Button sendCodeButton;
    @FXML private Button verifyCodeButton;
    @FXML private TextField emailTextField;
    @FXML private TextField codeTextField;
    @FXML private Button closeButton;
    @FXML private Label systemLabel;
    @FXML private Label emailLabel;
    @FXML private AnchorPane rootStage;
    @FXML private MaterialDesignIconView checkIconEmail;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        verifyCodeButton.setVisible(false);
        codeTextField.setVisible(false);
        setStyleClass();
        initValidatorToTextFields();
    }

    public void showStage() {
        FXMLLoader loader = new FXMLLoader( getClass().getResource("/org/gui/auth/util/recoverpassword/RecoveryPasswordVista.fxml") );
        loader.setController(this);
        Parent root = null;
        try{
            root = loader.load();
        } catch(IOException ioe) {
            Logger.getLogger( LoginController.class.getName() ).log(Level.SEVERE, null, ioe);
        }
        Stage stage = new Stage();
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene( new Scene(root) );
        stage.show();
    }

    @FXML
    void sendCodeButtonPressed(ActionEvent event) {
        String emailInput = emailTextField.getText();
        if( verifyInputData() ) {
            Auth.getInstance().generateRecoveryCode(emailInput);
            Auth.getInstance().setEmail(emailInput);
            isCodeSent = sendRecoveryCodeToEmail(emailInput);
            hideEmailElementsAndShowCodeElemets();
        } else {
            systemLabel.setText("¡Verifica tus datos!");
        }
    }

    @FXML
    void verifyCodeButtonPressed(ActionEvent event) {
        if(isCodeSent) {
            String emailInput = emailTextField.getText();
            String code = Auth.getInstance().getRecoveryCode(emailInput);
            String codeInput = codeTextField.getText();
            closeButton.fire();
            if( code.equals(codeInput)) {
                ChangePasswordController changePasswordController = new ChangePasswordController();
                changePasswordController.setEmail(emailInput);
                changePasswordController.showStage();
            }
        }
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

    private void hideEmailElementsAndShowCodeElemets() {
        if(isCodeSent) {
            verifyCodeButton.setVisible(true);
            codeTextField.setVisible(true);

            sendCodeButton.setVisible(false);
            emailTextField.setVisible(false);

        } else {
            systemLabel.setText("¡Verifica tus datos!");
        }
    }

    private boolean verifyInputData() {
        boolean dataInputValid = true;
        for( Map.Entry<TextField, Boolean> entry : interruptorMap.entrySet() ) {
            if( !entry.getValue() ){
                dataInputValid = false;
                break;
            }
        }
        return dataInputValid;
    }

    private void initValidatorToTextFields() {
        interruptorMap = new LinkedHashMap<>();
        interruptorMap.put(emailTextField, false);
        Map<TextField, Object[]> validator = new HashMap<>();
        Object[] elementConstraints = {Validator.EMAIL_PATTERN, Validator.EMAIL_LENGTH, checkIconEmail};
        validator.put(emailTextField, elementConstraints);
        final int FIRST_CONTRAINT = 0;
        final int SECOND_CONTRAINT = 1;
        final int THIRD_CONSTRAINT_ICON = 2;
        for (Map.Entry<TextField, Object[]> entry : validator.entrySet() ) {
            entry.getKey().textProperty().addListener( (observable, oldValue, newValue) -> {
                if( !Validator.doesStringMatchPattern( newValue, ( (String) entry.getValue()[FIRST_CONTRAINT] ) ) || Validator.isStringLargerThanLimitOrEmpty( newValue, ( (Integer) entry.getValue()[SECOND_CONTRAINT]) ) ){
                    interruptorMap.put(entry.getKey(), false );
                    ( (MaterialDesignIconView) entry.getValue()[THIRD_CONSTRAINT_ICON]).getStyleClass().clear();
                    ( (MaterialDesignIconView) entry.getValue()[THIRD_CONSTRAINT_ICON]).getStyleClass().add("wrongTextField");
                } else {
                    interruptorMap.put(entry.getKey(), true );
                    ((MaterialDesignIconView) entry.getValue()[THIRD_CONSTRAINT_ICON]).getStyleClass().clear();
                    ( (MaterialDesignIconView) entry.getValue()[THIRD_CONSTRAINT_ICON]).getStyleClass().add("correctlyTextField");
                }
            });
        }
    }

    private boolean sendRecoveryCodeToEmail(String email) {
        Auth.getInstance().generateRecoveryCode(email);
        String subject = "Recuperar contraseña";
        String code = Auth.getInstance().getRecoveryCode(email);
        String contentText = "<html>\n" +
                "<body>\n" +
                "  <div style=\"border-style:solid;border-color:#000000;border-width:1px 1px 1px 1px;text-align:justify;background-color:#f8f8f8;width:80% !important;height:80% !important;padding:3% 3% 3% 3%;\">\n" +
                "      <h1 style=\"color:#8a8a8a;font-family:sans-serif;text-align:left;\"> Recuperación de contraseña </h1>\n" +
                "      <p style=\"font-family:sans-serif;\"> Recibimos una solicitud para restablecer tu contraseña del <b><u>Sistema de Prácticas Profesionales</u></b></p>\n" +
                "      <p style=\"font-family:sans-serif;\"> Ingresa el siguiente código en el campo de texto indicado en el sistema para recuperar tu contraseña. </p>\n" +
                "      <div style=\"border-style:solid;border-color:#000000;border-width:1px 1px 1px 1px;background-color:#e2e2e2;display:inline-block;padding:15px 15px 15px 15px;\">" + code + "</div>\n" +
                "      <p style=\"font-size: 15px;font-family:sans-serif;color:#ff0000\"> Si no has sido tú, <u>reportalo</u> con tu Coordinador más cercano. </p>\n" +
                "  </div>\n" +
                "</body>\n" +
                "</html>\n";
        Mail mail = new Mail();
        return mail.sendEmail(email, subject, contentText);
    }

    private void setStyleClass() {
        rootStage.getStylesheets().clear();
        rootStage.getStylesheets().add(getClass().getResource("../../resources/" + CSSProperties.readTheme().getTheme() ).toExternalForm() );
    }

}