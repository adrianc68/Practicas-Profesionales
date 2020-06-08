package org.gui.auth.users.coordinator.practitioner.addpractitioner;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import org.database.dao.AccessAccountDAO;
import org.database.dao.PractitionerDAO;
import org.domain.Course;
import org.domain.Person;
import org.domain.Practitioner;
import org.gui.ValidatorController;
import org.util.Auth;
import org.util.CSSProperties;
import org.util.Cryptography;
import org.util.Validator;
import org.gui.auth.resources.alerts.OperationAlert;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;


public class AddPractitionerController extends ValidatorController implements Initializable {
    private boolean addOperationStatus;
    private Practitioner newPractitioner;
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
        setStyleClass(rootStage, getClass().getResource("../../../../resources/" + CSSProperties.readTheme().getTheme() ).toExternalForm() );
        addOperationStatus = false;
        initValidatorToTextFields();
    }

    public void showStage() {
        loadFXMLFile( getClass().getResource("/org/gui/auth/users/coordinator/practitioner/addpractitioner/AddPractitionerVista.fxml"), this);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
    }

    public Practitioner getNewPractitioner() {
        return newPractitioner;
    }

    public boolean getAddOperationStatus() {
        return addOperationStatus;
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
    protected void saveButtonPressed(ActionEvent event) {
        if( verifyInputData() ) {
            stage.close();
            registerPractitioner();
            if( addOperationStatus ) {
                generateRandomPasswordToUser();
            }
        } else {
            systemLabel.setText("¡Verifica tus datos!");
        }
    }

    private void registerPractitioner() {
        Person currentUser = Auth.getInstance().getCurrentUser();
        // T-O Ternary Operator here !!!
        Course course = ( currentUser != null) ? currentUser.getCourse() : null ;
        newPractitioner = new Practitioner();
        newPractitioner.setName( practitionerNameTextField.getText() );
        newPractitioner.setPhoneNumber( practitionerPhoneNumberTextField.getText() );
        newPractitioner.setEmail( practitionerEmailTextField.getText() );
        newPractitioner.setEnrollment( practitionerEnrollmentTextField.getText() );
        newPractitioner.setCourse(course);
        try {
            newPractitioner.setId( new PractitionerDAO().addPractitioner(newPractitioner) );
        } catch (SQLException sqlException) {
            OperationAlert.showLostConnectionAlert();
            Logger.getLogger( AddPractitionerController.class.getName() ).log(Level.WARNING, null, sqlException);
        }
        addOperationStatus = (newPractitioner.getId() != 0);
    }

    private void generateRandomPasswordToUser() {
        boolean isRandomPasswordGenerated = false;
        String randomPassword = Cryptography.generateRandomPassword();
        String email = practitionerEmailTextField.getText();
        try {
            isRandomPasswordGenerated = new AccessAccountDAO().changePasswordByIdUser( Cryptography.cryptSHA2(randomPassword), newPractitioner.getId() );
        } catch (SQLException sqlException) {
            OperationAlert.showLostConnectionAlert();
            Logger.getLogger( AddPractitionerController.class.getName() ).log(Level.WARNING, null, sqlException);
        }
        if(isRandomPasswordGenerated) {
            copyToClipboardSystem(email, randomPassword);
            showSuccessfullAlertFromPasswordGenerated();
        }
    }

    private void showSuccessfullAlertFromPasswordGenerated() {
        String title = "Cuenta de acceso generada";
        String contentText = "¡Se ha generado una contraseña a la cuenta de acceso y se ha copiado a tu sistema!";
        OperationAlert.showSuccessfullAlert(title, contentText);
    }

    private void copyToClipboardSystem(String email, String password) {
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putString(email + ":" + password);
        clipboard.setContent(content);
    }

    private void initValidatorToTextFields() {
        interruptorMap = new LinkedHashMap<>();
        interruptorMap.put(practitionerNameTextField, false);
        interruptorMap.put(practitionerEmailTextField, false);
        interruptorMap.put(practitionerPhoneNumberTextField, false);
        interruptorMap.put(practitionerEnrollmentTextField, false);
        Map<TextInputControl, Object[]> validator = new HashMap<>();
        Object[] nameConstraints = {Validator.NAME_PATTERN, Validator.NAME_LENGTH, checkIconName};
        Object[] phoneNumberConstraints = {Validator.PHONE_NUMBER_PATTERN, Validator.PHONE_NUMBER_LENGTH, checkIconPhoneNumber};
        Object[] emailConstraints = {Validator.EMAIL_PATTERN, Validator.EMAIL_LENGTH, checkIconEmail};
        Object[] enrollmentConstraints = {Validator.ENROLLMENT_PATTERN, Validator.ENROLLMENT_LENGTH, checkIconEnrollment};
        validator.put(practitionerNameTextField, nameConstraints);
        validator.put(practitionerEmailTextField, emailConstraints);
        validator.put(practitionerPhoneNumberTextField, phoneNumberConstraints);
        validator.put(practitionerEnrollmentTextField, enrollmentConstraints);
        initValidatorToTextInputControl(validator);
    }

}
