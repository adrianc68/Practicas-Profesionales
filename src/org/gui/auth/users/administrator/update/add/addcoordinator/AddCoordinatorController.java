package org.gui.auth.users.administrator.update.add.addcoordinator;

import com.mysql.cj.exceptions.CJCommunicationsException;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
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
import org.database.dao.CoordinatorDAO;
import org.database.dao.DeliveryDAO;
import org.domain.Coordinator;
import org.domain.Course;
import org.domain.Person;
import org.gui.ValidatorController;
import org.gui.auth.resources.alerts.OperationAlert;
import org.util.Auth;
import org.util.CSSProperties;
import org.util.Cryptography;
import org.util.Validator;

import java.net.ConnectException;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddCoordinatorController extends ValidatorController implements Initializable {
    private boolean addOperationStatus;
    private Coordinator newCoordinator;
    @FXML private Button closeButton;
    @FXML private TextField nameTextField;
    @FXML private TextField phoneNumberTextField;
    @FXML private TextField emailTextField;
    @FXML private TextField staffNumberTextField;
    @FXML private TextField cubicleTextField;
    @FXML private Label systemLabel;
    @FXML private AnchorPane rootStage;
    @FXML private MaterialDesignIconView checkIconName;
    @FXML private MaterialDesignIconView checkIconEmail;
    @FXML private MaterialDesignIconView checkIconPhoneNumber;
    @FXML private MaterialDesignIconView checkIconStaffNumber;
    @FXML private MaterialDesignIconView checkIconCubicle;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setStyleClass(rootStage, getClass().getResource("../../../../../resources/" + CSSProperties.readTheme().getTheme() ).toExternalForm() );
        addOperationStatus = false;
        initValidatorToTextFields();
    }

    public void showStage() {
        loadFXMLFile( getClass().getResource("/org/gui/auth/users/administrator/update/add/addcoordinator/AddCoordinatorVista.fxml"), this);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
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
    void saveButtonPressed(ActionEvent event) {
        if( verifyInputData() ) {
            stage.close();
            addCoordinator();
            if(addOperationStatus) {
                generateRandomPassword();
            }
        } else {
            systemLabel.setText("¡Verifica tus datos!");
        }
    }


    private void addCoordinator() {
        Person user = Auth.getInstance().getCurrentUser();
        // T-O Ternary Operator here!
        Course course = ( user != null) ? user.getCourse() : null ;
        newCoordinator = new Coordinator();
        newCoordinator.setCourse( course );
        newCoordinator.setName( nameTextField.getText() );
        newCoordinator.setPhoneNumber( phoneNumberTextField.getText() );
        newCoordinator.setEmail( emailTextField.getText() );
        newCoordinator.setCubicle( Integer.parseInt( cubicleTextField.getText() ) );
        newCoordinator.setStaffNumber( staffNumberTextField.getText() );
//        try {
            newCoordinator.setId( new CoordinatorDAO().addCoordinator(newCoordinator) );
//        } catch (SQLException sqlException) {
//            String title = "¡Error al conectar con la base de datos!";
//            String contentText = "¡No se pudo realizar una conexión a la base de datos! ¡Intentalo más tarde!";
//            OperationAlert.showUnsuccessfullAlert(title, contentText);
//            Logger.getLogger( AddCoordinatorController.class.getName() ).log(Level.WARNING, null, sqlException);
//        }
        addOperationStatus = (newCoordinator.getId() != 0);
    }

    private void generateRandomPassword() {
        String randomPasswordEncrypted = Cryptography.cryptSHA2( Cryptography.generateRandomPassword() );
        String email = emailTextField.getText();
        boolean isGeneratedRandomPassword = false;
//        try {
            isGeneratedRandomPassword = new AccessAccountDAO().changePasswordByIdUser( randomPasswordEncrypted, newCoordinator.getId() );
//        } catch (SQLException sqlException) {
//            String title = "¡Se generó un problema al generar una contraseña aleatoria a tu cuenta de acceso!";
//            String contentText = "¡Hubo un problema al intentar generar la contraseña!\nUsa el modulo de recuperar contraseña para recuperarla.";
//            OperationAlert.showUnsuccessfullAlert(title, contentText);
//            Logger.getLogger( AddCoordinatorController.class.getName() ).log(Level.WARNING, null, sqlException);
//        } finally {
            if(isGeneratedRandomPassword) {
                showSuccessfullAlertFromPasswordGenerated(email, randomPasswordEncrypted);
            }
    }

    private void showSuccessfullAlertFromPasswordGenerated(String email, String password) {
        copyToClipboardSystem(password, email);
        String title = "Cuenta de acceso generada";
        String contentText = "¡Se ha generado una contraseña a la cuenta de acceso y se ha copiado a tu sistema!";
        OperationAlert.showSuccessfullAlert(title, contentText);
    }

    private void copyToClipboardSystem(String password, String email) {
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putString(email + ":" + password);
        clipboard.setContent(content);
    }

    private void initValidatorToTextFields() {
        interruptorMap = new LinkedHashMap<>();
        interruptorMap.put(nameTextField, false);
        interruptorMap.put(emailTextField, false);
        interruptorMap.put(phoneNumberTextField, false);
        interruptorMap.put(staffNumberTextField, false);
        interruptorMap.put(cubicleTextField,false);
        Map<TextInputControl, Object[]> validator = new HashMap<>();
        Object[] nameConstraints = {Validator.NAME_PATTERN, Validator.NAME_LENGTH, checkIconName};
        validator.put(nameTextField, nameConstraints);
        Object[] emailConstraints = {Validator.EMAIL_PATTERN, Validator.EMAIL_LENGTH, checkIconEmail};
        validator.put(emailTextField, emailConstraints);
        Object[] phoneNumberConstraints = {Validator.PHONE_NUMBER_PATTERN, Validator.PHONE_NUMBER_LENGTH, checkIconPhoneNumber};
        validator.put(phoneNumberTextField, phoneNumberConstraints);
        Object[] staffNumberConstraints = {Validator.STAFF_NUMBER_PATTERN, Validator.STAFF_NUMBER_LENGTH, checkIconStaffNumber};
        validator.put(staffNumberTextField, staffNumberConstraints);
        Object[] cubicleConstraints = {Validator.NUMBER_PATTERN, Validator.NUMBER_LENGTH, checkIconCubicle};
        validator.put(cubicleTextField, cubicleConstraints);
        super.initValidatorToTextInputControl(validator);
    }

}
