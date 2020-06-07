package org.gui.auth.users.administrator.update.add.addcoordinator;

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
import org.domain.Coordinator;
import org.domain.Course;
import org.domain.Person;
import org.gui.ValidatorController;
import org.gui.auth.resources.alerts.OperationAlert;
import org.util.Auth;
import org.util.CSSProperties;
import org.util.Cryptography;
import org.util.Validator;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class AddCoordinatorController extends ValidatorController implements Initializable {
    private boolean addOperationStatus;
    private Coordinator newCoordinator;
    private Course course;
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
        Person user = Auth.getInstance().getCurrentUser();
        if(user != null) {
            course = user.getCourse();
        }
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
    void cancelButtonPressed(ActionEvent event) {
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
            createCoordinatorObject();
            closeButton.fire();
            if(newCoordinator.getId() != 0) {
                addOperationStatus = true;
                generateRandomPasswordToUser( emailTextField.getText() );
            } else {
                String title = "¡Error en el registro del coordinador!";
                String contentText = "¡No se pudo realizar el registro del coordinador!";
                OperationAlert.showUnsuccessfullAlert(title, contentText);
            }
        } else {
            systemLabel.setText("¡Verifica tus datos!");
        }
    }

    private void createCoordinatorObject() {
        newCoordinator = new Coordinator();
        newCoordinator.setCourse(course);
        newCoordinator.setName( nameTextField.getText() );
        newCoordinator.setPhoneNumber( phoneNumberTextField.getText() );
        newCoordinator.setEmail( emailTextField.getText() );
        newCoordinator.setCubicle( Integer.valueOf( cubicleTextField.getText() ) );
        newCoordinator.setStaffNumber( staffNumberTextField.getText() );
        newCoordinator.setId( new CoordinatorDAO().addCoordinator(newCoordinator) );
    }

    private void generateRandomPasswordToUser(String email) {
        String randomPassword = Cryptography.generateRandomPassword();
        if ( new AccessAccountDAO().changePasswordByIdUser( Cryptography.cryptSHA2(randomPassword), newCoordinator.getId() ) ) {
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
