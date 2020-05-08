package org.gui.users.coordinator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.gui.users.coordinator.practitioner.PractitionerController;
import org.gui.users.coordinator.project.ProjectController;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CoordinatorController {

    public void showStage() {
        FXMLLoader loader = new FXMLLoader( getClass().getResource("/org/gui/users/coordinator/CoordinatorVista.fxml") );
        loader.setController(this);
        Parent root = null;
        try{
            root = loader.load();
        } catch(IOException ioe) {
            ioe.getMessage();
        }
        Stage coordinator = new Stage();
        coordinator.setScene( new Scene(root) );
        coordinator.show();
    }

    @FXML
    void practitionerButtonPressed(ActionEvent event) {
        PractitionerController practitionerController = new PractitionerController();
        practitionerController.showStage();
    }

    @FXML
    void projectButtonPressed(ActionEvent event) {
        ProjectController projectController = new ProjectController();
        projectController.showStage();
    }


}
