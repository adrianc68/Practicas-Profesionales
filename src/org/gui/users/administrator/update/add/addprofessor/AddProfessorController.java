package org.gui.users.administrator.update.add.addprofessor;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import org.database.dao.ProfessorDAO;
import org.domain.Course;
import org.domain.Professor;
import org.util.Cryptography;
import org.util.Validator;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddProfessorController implements Initializable {
    private boolean dataInputValid;
    private boolean addOperationStatus;
    private Professor newProfessor;
    private Course course;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField phoneNumberTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField staffNumberTextField;

    @FXML
    private TextField cubicleTextField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setListenerToTextFields();
    }

    public AddProfessorController(Course course) {
        this.course = course;
    }

    public void showStage() {
        FXMLLoader loader = new FXMLLoader( getClass().getResource("/org/gui/users/administrator/update/add/addprofessor/AddProfesorVista.fxml") );
        loader.setController(this);
        Parent root = null;
        try{
            root = loader.load();
        } catch(IOException ioe) {
            Logger.getLogger(AddProfessorController.class.getName()).log(Level.WARNING, null, ioe);
        }
        Stage addProfessor = new Stage();
        addProfessor.initModality(Modality.APPLICATION_MODAL);
        addProfessor.initStyle(StageStyle.UTILITY);
        addProfessor.setScene( new Scene(root) );
        addProfessor.showAndWait();
    }

    public boolean getAddOperationStatus() {
        return addOperationStatus;
    }

    public Professor getNewProfessor() {
        return newProfessor;
    }

    @FXML
    void cancelButtonPressed(ActionEvent event) {
        addOperationStatus = false;
        closeStage(event);
    }

    @FXML
    void saveButtonPressed(ActionEvent event) {
        if(dataInputValid) {
            addOperationStatus = false;
            newProfessor = new Professor();
            newProfessor.setCourse(course);
            newProfessor.setName( nameTextField.getText() );
            newProfessor.setPhoneNumber( phoneNumberTextField.getText() );
            newProfessor.setEmail( emailTextField.getText() );
            newProfessor.setCubicle( Integer.valueOf( cubicleTextField.getText() ) );
            newProfessor.setStaffNumber( staffNumberTextField.getText() );
            ProfessorDAO professorDAO = new ProfessorDAO();
            newProfessor.setId( professorDAO.addProfessor(newProfessor) );
            closeStage(event);
            if(newProfessor.getId() != 0) {
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
        accessAccountDAO.addPasswordToUser(  Cryptography.cryptSHA2(password), newProfessor.getId() );
        copyToClipboardSystem(newProfessor.getEmail(), password);
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
        textfields.put(nameConstraints, nameTextField);
        Object[] phoneNumberConstraints = {Validator.PHONE_NUMBER_PATTERN, Validator.PHONE_NUMBER_LENGTH};
        textfields.put(phoneNumberConstraints, phoneNumberTextField);
        Object[] emailConstraints = {Validator.EMAIL_PATTERN, Validator.EMAIL_LENGTH};
        textfields.put(emailConstraints, emailTextField);
        Object[] staffNumberConstraints = {Validator.STAFF_NUMBER_PATTERN, Validator.STAFF_NUMBER_LENGTH};
        textfields.put(staffNumberConstraints, staffNumberTextField);
        Object[] cubicleConstraints = {Validator.NUMBER_PATTERN, Validator.NUMBER_LENGTH};
        textfields.put(cubicleConstraints, cubicleTextField);
        for (Map.Entry<Object[], TextField> entry : textfields.entrySet() ) {
            entry.getValue().textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    if( !Validator.doesStringMatchPattern( newValue, ( (String) entry.getKey()[0] ) ) || Validator.isStringLargerThanLimitOrEmpty( newValue, ( (Integer) entry.getKey()[1]) ) ){
                        dataInputValid = false;
                        entry.getValue().setStyle("-fx-background-color:red;");
                    } else {
                        dataInputValid = true;
                        entry.getValue().setStyle("-fx-background-color:green;");
                    }
                }
            });
        }
    }

    private void closeStage(ActionEvent event) {
        Stage stage = ( (Stage) ( (Node) event.getSource() ).getScene().getWindow() );
        stage.close();
    }
}
