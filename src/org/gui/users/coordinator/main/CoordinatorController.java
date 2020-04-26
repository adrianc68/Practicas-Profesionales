package org.gui.users.coordinator.main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.gui.users.coordinator.practitioner.PractitionerController;
import org.gui.users.coordinator.project.ProjectController;

public class CoordinatorController {

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
