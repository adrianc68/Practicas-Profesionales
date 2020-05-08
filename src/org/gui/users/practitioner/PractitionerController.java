package org.gui.users.practitioner;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.gui.users.practitioner.project.ProjectController;

public class PractitionerController{

    @FXML
    void projectButtonPresed(ActionEvent event) {
        ProjectController projectController = new ProjectController();
        projectController.showStage();
    }

    @FXML
    void uploadButtonPressed(ActionEvent event) {

    }

}

