package org.gui.auth.users.administrator.update.add.addprofessor;

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
import org.database.dao.ProfessorDAO;
import org.domain.Course;
import org.domain.Person;
import org.domain.Professor;
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

public class AddProfessorController extends ValidatorController implements Initializable {
    private boolean addOperationStatus;
    private Professor newProfessor;
    private Course course;
    @FXML private TextField nameTextField;
    @FXML private TextField phoneNumberTextField;
    @FXML private TextField emailTextField;
    @FXML private TextField staffNumberTextField;
    @FXML private TextField cubicleTextField;
    @FXML private Button closeButton;
    @FXML private Label systemLabel;
    @FXML private AnchorPane rootStage;
    @FXML private MaterialDesignIconView checkIconName;
    @FXML private MaterialDesignIconView checkIconEmail;
    @FXML private MaterialDesignIconView checkIconPhoneNumber;
    @FXML private MaterialDesignIconView checkIconStaffNumber;
    @FXML private MaterialDesignIconView checkIconCubicle;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setStyleClass(rootStage, getClass().getResource("../../../../../resources/" + CSSProperties.readTheme().getTheme() ).toExternalForm());
        addOperationStatus = false;
        Person user = Auth.getInstance().getCurrentUser();
        if(user != null) {
            course = user.getCourse();
        }
        initValidatorToTextFields();
    }

    public void showStage() {
        loadFXMLFile(getClass().getResource("/org/gui/auth/users/administrator/update/add/addprofessor/AddProfesorVista.fxml"), this);
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
    protected void saveButtonPressed(ActionEvent event) {
        if( verifyInputData() ) {
            registerProfessor();
            closeButton.fire();
            if(newProfessor.getId() != 0) {
                addOperationStatus = true;
                generateRandomPasswordToUser( emailTextField.getText() );
            } else {
                String title = "¡Error en el registro del profesor!";
                String contentText = "¡No se pudo realizar el registro del coordinador!";
                OperationAlert.showUnsuccessfullAlert(title, contentText);
            }
        } else {
            systemLabel.setText("¡Verifica tus datos!");
        }
    }

    private void generateRandomPasswordToUser(String email) {
        String randomPassword = Cryptography.generateRandomPassword();
        if ( new AccessAccountDAO().changePasswordByIdUser( Cryptography.cryptSHA2(randomPassword), newProfessor.getId() ) ) {
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

    private void registerProfessor() {
        newProfessor = new Professor();
        newProfessor.setCourse(course);
        newProfessor.setName( nameTextField.getText() );
        newProfessor.setPhoneNumber( phoneNumberTextField.getText() );
        newProfessor.setEmail( emailTextField.getText() );
        newProfessor.setCubicle( Integer.valueOf( cubicleTextField.getText() ) );
        newProfessor.setStaffNumber( staffNumberTextField.getText() );
        ProfessorDAO professorDAO = new ProfessorDAO();
        newProfessor.setId( professorDAO.addProfessor(newProfessor) );
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
