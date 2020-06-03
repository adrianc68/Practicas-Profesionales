package org.gui.auth.users.coordinator.practitioner.addpractitioner;

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
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.database.dao.AccessAccountDAO;
import org.database.dao.CourseDAO;
import org.database.dao.PractitionerDAO;
import org.domain.Practitioner;
import org.gui.auth.resources.alerts.OperationAlert;
import org.util.CSSProperties;
import org.util.Cryptography;
import org.util.Validator;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddPractitionerController implements Initializable {
    private double mousePositionOnX;
    private double mousePositionOnY;
    private boolean addOperationStatus;
    private Practitioner newPractitioner;
    private Map<TextField, Boolean> interruptorMap = new HashMap<>();
    @FXML private TextField practitionerNameTextField;
    @FXML private TextField practitionerPhoneNumberTextField;
    @FXML private TextField practitionerEmailTextField;
    @FXML private TextField practitionerEnrollmentTextField;
    @FXML private Button closeButton;
    @FXML private AnchorPane rootStage;
    @FXML private Label systemLabel;
    @FXML private MaterialDesignIconView checkIconName;
    @FXML private MaterialDesignIconView checkIconEmail;
    @FXML private MaterialDesignIconView checkIconPhoneNumber;
    @FXML private MaterialDesignIconView checkIconEnrollment;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setStyleClass();
        addOperationStatus = false;
        initValidatorToTextFields();
    }

    public void showStage() {
            FXMLLoader loader = new FXMLLoader( getClass().getResource("/org/gui/auth/users/coordinator/practitioner/addpractitioner/AddPractitionerVista.fxml") );
            loader.setController(this);
            Parent root = null;
            try{
                root = loader.load();
            } catch(IOException ioe) {
                Logger.getLogger( AddPractitionerController.class.getName() ).log(Level.WARNING, null, ioe);
            }
            Stage assignProjectStage = new Stage();
            assignProjectStage.initModality(Modality.APPLICATION_MODAL);
            assignProjectStage.initStyle(StageStyle.TRANSPARENT);
            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT);
            assignProjectStage.setScene(scene);
            assignProjectStage.showAndWait();
    }

    public Practitioner getNewPractitioner() {
        return newPractitioner;
    }

    public boolean getAddOperationStatus() {
        return addOperationStatus;
    }

    @FXML
    void cancelButtonPressed(ActionEvent event) {
        Stage stage = ( (Stage) ( (Node) event.getSource() ).getScene().getWindow() );
        stage.close();
    }

    @FXML
    void saveButtonPressed(ActionEvent event) {
        if( verifyInputData() ) {
            registerPractitioner();
            closeButton.fire();
            if(newPractitioner.getId() != 0) {
                addOperationStatus = true;
                generateRandomPasswordToUser( practitionerEmailTextField.getText() );
            } else {
                String title = "¡Error en el registro del practicante!";
                String contentText = "¡No se pudo realizar el registro del practicante!";
                OperationAlert.showUnsuccessfullAlert(title, contentText);
            }
        } else {
            systemLabel.setText("¡Verifica tus datos!");
        }
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

    private void registerPractitioner() {
        newPractitioner = new Practitioner();
        newPractitioner.setName( practitionerNameTextField.getText() );
        newPractitioner.setPhoneNumber( practitionerPhoneNumberTextField.getText() );
        newPractitioner.setEmail( practitionerEmailTextField.getText() );
        newPractitioner.setEnrollment( practitionerEnrollmentTextField.getText() );
        newPractitioner.setCourse( new CourseDAO().getLastCourse() );
        PractitionerDAO practitionerDAO = new PractitionerDAO();
        newPractitioner.setId( practitionerDAO.addPractitioner(newPractitioner) );
    }

    private void generateRandomPasswordToUser(String email) {
        String randomPassword = Cryptography.generateRandomPassword();
        if ( new AccessAccountDAO().changePasswordByIdUser( Cryptography.cryptSHA2(randomPassword), newPractitioner.getId() ) ) {
            copyToClipboardSystem(randomPassword, email);
            String title = "Cuenta de acceso generada";
            String contentText = "¡Se ha copiado la cuenta de acceso al portapapeles de tu sistema!";
            OperationAlert.showSuccessfullAlert(title, contentText);
        } else {
            String title = "¡Error al obtener la cuenta de acceso!";
            String contentText = "¡No se pudo copiar la cuenta de acceso al portapapeles de tu sistema! \n¡Usar el modulo para recuperar contraseña!";
            OperationAlert.showUnsuccessfullAlert(title, contentText);
        }
    }

    private void copyToClipboardSystem(String email, String password) {
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putString(email + ":" + password);
        clipboard.setContent(content);
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
        interruptorMap.put(practitionerNameTextField, false);
        interruptorMap.put(practitionerEmailTextField, false);
        interruptorMap.put(practitionerPhoneNumberTextField, false);
        interruptorMap.put(practitionerEnrollmentTextField, false);
        Map<TextField, Object[]> validator = new HashMap<>();
        Object[] nameConstraints = {Validator.NAME_PATTERN, Validator.NAME_LENGTH, checkIconName};
        Object[] phoneNumberConstraints = {Validator.PHONE_NUMBER_PATTERN, Validator.PHONE_NUMBER_LENGTH, checkIconPhoneNumber};
        Object[] emailConstraints = {Validator.EMAIL_PATTERN, Validator.EMAIL_LENGTH, checkIconEmail};
        Object[] enrollmentConstraints = {Validator.ENROLLMENT_PATTERN, Validator.ENROLLMENT_LENGTH, checkIconEnrollment};
        validator.put(practitionerNameTextField, nameConstraints);
        validator.put(practitionerEmailTextField, emailConstraints);
        validator.put(practitionerPhoneNumberTextField, phoneNumberConstraints);
        validator.put(practitionerEnrollmentTextField, enrollmentConstraints);
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
                    ( (MaterialDesignIconView) entry.getValue()[THIRD_CONSTRAINT_ICON]).getStyleClass().clear();
                    ( (MaterialDesignIconView) entry.getValue()[THIRD_CONSTRAINT_ICON]).getStyleClass().add("correctlyTextField");
                }
            });
        }
    }

    private void setStyleClass() {
        rootStage.getStylesheets().clear();
        rootStage.getStylesheets().add(getClass().getResource("../../../../resources/" + CSSProperties.readTheme().getTheme() ).toExternalForm() );
    }

}
