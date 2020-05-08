package org.gui.users.professor;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.gui.users.professor.activity.ActivityController;
import org.gui.users.professor.practitioner.PractitionerListController;

public class ProfessorController {

    @FXML
    void activityButtonPressed(ActionEvent event) {
        ActivityController activityController = new ActivityController();
        activityController.showStage();
    }

    @FXML
    void studentButtonPressed(ActionEvent event) {
        PractitionerListController practitionerListController = new PractitionerListController();
        practitionerListController.showStage();
    }

}
