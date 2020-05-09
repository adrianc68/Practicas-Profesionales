package org.gui.users.coordinator.practitioner.addpractitioner;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.database.dao.AccessAccountDAO;
import org.database.dao.CourseDAO;
import org.database.dao.PractitionerDAO;
import org.domain.Practitioner;
import org.util.Cryptography;
import org.util.Validator;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddPractitionerController implements Initializable {
    private boolean addOperationStatus;
    private boolean inputDataValid;
    private Practitioner newPractitioner;

    @FXML
    private TextField practitionerNameTextField;

    @FXML
    private TextField practitionerPhoneNumberTextField;

    @FXML
    private TextField practitionerEmailTextField;

    @FXML
    private TextField practitionerEnrollmentTextField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        inputDataValid = false;
        setListenerToTextFields();
    }

    public Practitioner getNewPractitioner() {
        return newPractitioner;
    }

    public boolean getAddOperationStatus() {
        return addOperationStatus;
    }

    public void showStage() {
            FXMLLoader loader = new FXMLLoader( getClass().getResource("/org/gui/users/coordinator/practitioner/addpractitioner/AddPractitionerVista.fxml") );
            loader.setController(this);
            Parent root = null;
            try{
                root = loader.load();
            } catch(IOException ioe) {
                Logger.getLogger( AddPractitionerController.class.getName() ).log(Level.WARNING, null, ioe);
            }
            Stage assignProjectStage = new Stage();
            assignProjectStage.initModality(Modality.APPLICATION_MODAL);
            assignProjectStage.initStyle(StageStyle.UTILITY);
            assignProjectStage.setScene( new Scene(root) );
            assignProjectStage.showAndWait();
    }

    @FXML
    void cancelButtonPressed(ActionEvent event) {
        addOperationStatus = false;
        closeStage(event);
    }

    @FXML
    void saveButtonPressed(ActionEvent event) {
        if(inputDataValid) {
            addOperationStatus = false;
            newPractitioner = new Practitioner();
            newPractitioner.setName( practitionerNameTextField.getText() );
            newPractitioner.setPhoneNumber( practitionerPhoneNumberTextField.getText() );
            newPractitioner.setEmail( practitionerEmailTextField.getText() );
            newPractitioner.setEnrollment( practitionerEnrollmentTextField.getText() );
            newPractitioner.setCourse( new CourseDAO().getLastCourse() );
            PractitionerDAO practitionerDAO = new PractitionerDAO();
            newPractitioner.setId( practitionerDAO.addPractitioner(newPractitioner) );
            closeStage(event);
            if(newPractitioner.getId() != 0) {
                addOperationStatus = true;
                showAlertAccessAccount();
            }
        }
    }

    private void showAlertAccessAccount() {
        AccessAccountDAO accessAccountDAO = new AccessAccountDAO();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Cuenta de acceso generada");
        alert.setHeaderText(null);
        String password = Cryptography.generateRandomPassword();
        accessAccountDAO.addPasswordToUser(  Cryptography.cryptSHA2(password), newPractitioner.getId() );
        copyToClipboardSystem(newPractitioner.getEmail(), password);
        alert.setContentText("Se ha copiado la cuenta de acceso al portapapeles de tu sistema");
        alert.showAndWait();
    }

    private void copyToClipboardSystem(String email, String password) {
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putString(email + ":" + password);
        clipboard.setContent(content);
    }

    private void setListenerToTextFields() {
        Map<Object[], TextField> textfields = new LinkedHashMap<>();
        Object[] nameConstraints = {Validator.NAME_PATTERN, Validator.NAME_LENGTH};
        textfields.put(nameConstraints, practitionerNameTextField);
        Object[] phoneNumberConstraints = {Validator.PHONE_NUMBER_PATTERN, Validator.PHONE_NUMBER_LENGTH};
        textfields.put(phoneNumberConstraints, practitionerPhoneNumberTextField);
        Object[] emailConstraints = {Validator.EMAIL_PATTERN, Validator.EMAIL_LENGTH};
        textfields.put(emailConstraints, practitionerEmailTextField);
        Object[] enrollmentConstraints = {Validator.ENROLLMENT_PATTERN, Validator.ENROLLMENT_LENGTH};
        textfields.put(enrollmentConstraints, practitionerEnrollmentTextField);
        for (Map.Entry<Object[], TextField> entry : textfields.entrySet() ) {
            entry.getValue().textProperty().addListener( (observable, oldValue, newValue) -> {
                if( !Validator.doesStringMatchPattern( newValue, ( (String) entry.getKey()[0] ) ) || Validator.isStringLargerThanLimitOrEmpty( newValue, ( (Integer) entry.getKey()[1]) ) ){
                    inputDataValid = false;
                    entry.getValue().setStyle("-fx-background-color:red;");
                } else {
                    inputDataValid = true;
                    entry.getValue().setStyle("-fx-background-color:green;");
                }
            });
        }
    }

    private void closeStage(ActionEvent event) {
        Stage stage = ( (Stage) ( (Node) event.getSource() ).getScene().getWindow() );
        stage.close();
    }

}
