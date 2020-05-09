package org.gui.users.professor.activity;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.gui.users.professor.activity.createactivity.AddActivityController;
import org.gui.users.professor.activity.evaluteactivity.EvaluateActivityController;


public class ActivityController {

    @FXML
    private AnchorPane rootPane;

    public void showStage() {
        FXMLLoader loader = new FXMLLoader( getClass().getResource("/org/gui/users/professor/activity/ActivityVista.fxml") );
        ActivityController activityController = new ActivityController();
        loader.setController(activityController);
        Parent root = null;
        try{
            root = loader.load();
        } catch(IOException ioe) {
            Logger.getLogger(ActivityController.class.getName()).log(Level.WARNING, null, ioe);
        }
        Stage activityStage = new Stage();
        activityStage.setScene( new Scene(root) );
        activityStage.show();
    }

    @FXML
    void EvaluateActivityButtonPressed(ActionEvent event) {
        EvaluateActivityController evaluateActivityController = new EvaluateActivityController();
        evaluateActivityController.showStage();
    }

    @FXML
    void addActivityButtonPressed(ActionEvent event) {
        AddActivityController addActivityController = new AddActivityController();
        addActivityController.showStage();
    }

}
