package org.gui.users.professor.activity.evaluteactivity;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.gui.users.professor.activity.createactivity.AddActivityController;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EvaluateActivityController {

    public void showStage() {
        FXMLLoader loader = new FXMLLoader( getClass().getResource("/org/gui/users/professor/activity/evaluateactivity/EvaluateActivityVista.fxml") );
        loader.setController(this);
        Parent root = null;
        try{
            root = loader.load();
        } catch(IOException ioe) {
            Logger.getLogger(EvaluateActivityController.class.getName()).log(Level.WARNING, null, ioe);
        }
        Stage addActivityStage = new Stage();
        addActivityStage.setScene( new Scene(root) );
        addActivityStage.show();
    }

    @FXML
    void evaluateButtonPressed(ActionEvent event) {

    }

    @FXML
    void viewActivityButtonPressed(ActionEvent event) {

    }

}
