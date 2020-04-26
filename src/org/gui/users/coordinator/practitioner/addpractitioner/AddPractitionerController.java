package org.gui.users.coordinator.practitioner.addpractitioner;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddPractitionerController {
    private boolean status;
    private Practitioner newPractitioner;

    @FXML
    private TextField practitionerNameInput;

    @FXML
    private TextField practitionerPhoneNumberInput;

    @FXML
    private TextField practitionerEmailInput;

    @FXML
    private TextField practitionerEnrollmentInput;

    public Practitioner getNewPractitioner() {
        return newPractitioner;
    }

    public boolean getStatus() {
        return status;
    }

    public void showStage() {
            FXMLLoader loader = new FXMLLoader( getClass().getResource("/org/gui/users/coordinator/practitioner/addpractitioner/AddPractitionerVista.fxml") );
            loader.setController(this);
            Parent root = null;
            try{
                root = loader.load();
            } catch(IOException ioe) {
                Logger.getLogger(AddPractitionerController.class.getName()).log(Level.WARNING, null, ioe);
            }
            Stage assignProjectStage = new Stage();
            assignProjectStage.initModality(Modality.APPLICATION_MODAL);
            assignProjectStage.initStyle(StageStyle.UTILITY);
            assignProjectStage.setScene( new Scene(root) );
            assignProjectStage.showAndWait();
    }

    @FXML
    void cancelButtonPressed(ActionEvent event) {
        status = false;
        closeStage(event);
    }

    @FXML
    void saveButtonPressed(ActionEvent event) {
        newPractitioner = new Practitioner();
        newPractitioner.setName( practitionerNameInput.getText() );
        newPractitioner.setPhoneNumber( practitionerPhoneNumberInput.getText() );
        newPractitioner.setEmail( practitionerEmailInput.getText() );
        newPractitioner.setEnrollment( practitionerEnrollmentInput.getText() );
        newPractitioner.setCourse(new CourseDAO().getLastCourse());
        addPractitionerToDatabase();
        closeStage(event);
    }

    private void addPractitionerToDatabase() {
        PractitionerDAO practitionerDAO = new PractitionerDAO();
        int idPractitioner = 0;
        idPractitioner = practitionerDAO.addPractitioner(newPractitioner);
        newPractitioner.setId(idPractitioner);
        status = (idPractitioner == 0) ? false : true ;
    }

    private void closeStage(ActionEvent event) {
        Stage stage = ( (Stage) ( (Node) event.getSource() ).getScene().getWindow() );
        stage.close();
    }



}
