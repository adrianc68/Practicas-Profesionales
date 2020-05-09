package org.gui.users.professor.activity.createactivity;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class AddActivityController {

    public void showStage() {
        FXMLLoader loader = new FXMLLoader( getClass().getResource("/org/gui/users/professor/activity/createactivity/AddActivityVista.fxml") );
        loader.setController(this);
        Parent root = null;
        try{
            root = loader.load();
        } catch(IOException ioe) {
            Logger.getLogger(AddActivityController.class.getName()).log(Level.WARNING, null, ioe);
        }
        Stage addActivityStage = new Stage();
        addActivityStage.setScene( new Scene(root) );
        addActivityStage.show();
    }

    @FXML
    void addButtonPressed(ActionEvent event) {

    }

    @FXML
    void cancel(ActionEvent event) {

    }

}


