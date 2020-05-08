package org.gui.users.coordinator.practitioner.removepractitioner;

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
import org.database.dao.PractitionerDAO;
import org.domain.Practitioner;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RemovePractitionerController {
    private boolean removeOperationStatus;
    private Practitioner practitionerToBeRemoved;
    @FXML
    private TextField confirmationTextField;

    public RemovePractitionerController(Practitioner practitionerToBeRemoved) {
        this.practitionerToBeRemoved = practitionerToBeRemoved;
    }

    public void showStage() {
        FXMLLoader loader = new FXMLLoader( getClass().getResource("/org/gui/users/coordinator/practitioner/removepractitioner/RemovePractitionerVista.fxml") );
        loader.setController(this);
        Parent root = null;
        try{
            root = loader.load();
        } catch(IOException ioe) {
            Logger.getLogger( RemovePractitionerController.class.getName() ).log(Level.WARNING, null, ioe);
        }
        Stage removeStage = new Stage();
        removeStage.initModality(Modality.APPLICATION_MODAL);
        removeStage.initStyle(StageStyle.UTILITY);
        removeStage.setScene( new Scene(root) );
        removeStage.showAndWait();
    }

    public boolean getRemoveOperationStatus() {
        return removeOperationStatus;
    }

    @FXML
    void removeButtonPressed(ActionEvent event) {
        if(confirmationTextField.getText().equals("ELIMINAR")){
            PractitionerDAO practitionerDAO = new PractitionerDAO();
            removeOperationStatus = practitionerDAO.removePractitioner( practitionerToBeRemoved.getId() );
            closeStage(event);
        }
    }

    @FXML
    void cancelButtonPressed(ActionEvent event) {
        closeStage(event);
    }

    private void closeStage(ActionEvent event) {
        Stage stage = ( (Stage) ( (Node) event.getSource() ).getScene().getWindow() );
        stage.close();
    }

}
