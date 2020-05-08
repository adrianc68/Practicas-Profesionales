package org.gui.users.coordinator.practitioner.addpractitioner;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.database.dao.CourseDAO;
import org.database.dao.PractitionerDAO;
import org.domain.Practitioner;
import org.util.Security;
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
            newPractitioner = new Practitioner();
            newPractitioner.setName( practitionerNameTextField.getText() );
            newPractitioner.setPhoneNumber( practitionerPhoneNumberTextField.getText() );
            newPractitioner.setEmail( practitionerEmailTextField.getText() );
            newPractitioner.setEnrollment( practitionerEnrollmentTextField.getText() );
            newPractitioner.setCourse( new CourseDAO().getLastCourse() );
            addPractitionerToDatabase();
            closeStage(event);
        }
    }

    private void addPractitionerToDatabase() {
        PractitionerDAO practitionerDAO = new PractitionerDAO();
        int idPractitioner = 0;
        idPractitioner = practitionerDAO.addPractitioner(newPractitioner);
        newPractitioner.setId(idPractitioner);
        // T-O Ternary Operator!
        addOperationStatus = (idPractitioner == 0) ? false : true ;
    }

    private void setListenerToTextFields() {
        Map<Object[], TextField> textfields = new LinkedHashMap<>();
        Object[] nameConstraints = {Security.NAME_PATTERN, Security.NAME_LENGTH};
        textfields.put(nameConstraints, practitionerNameTextField);
        Object[] phoneNumberConstraints = {Security.PHONE_NUMBER_PATTERN, Security.PHONE_NUMBER_LENGTH};
        textfields.put(phoneNumberConstraints, practitionerPhoneNumberTextField);
        Object[] emailConstraints = {Security.EMAIL_PATTERN, Security.EMAIL_LENGTH};
        textfields.put(emailConstraints, practitionerEmailTextField);
        Object[] enrollmentConstraints = {Security.ENROLLMENT_PATTERN, Security.ENROLLMENT_LENGTH};
        textfields.put(enrollmentConstraints, practitionerEnrollmentTextField);
        for (Map.Entry<Object[], TextField> entry : textfields.entrySet() ) {
            entry.getValue().textProperty().addListener( (observable, oldValue, newValue) -> {
                if( !Security.doesStringMatchPattern( newValue, ( (String) entry.getKey()[0] ) ) || Security.isStringLargerThanLimitOrEmpty( newValue, ( (Integer) entry.getKey()[1]) ) ){
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
