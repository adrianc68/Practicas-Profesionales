package org.gui.users.professor.practitioner;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PractitionerListController {

    public void showStage() {
        FXMLLoader loader = new FXMLLoader( getClass().getResource("/org/gui/users/professor/practitioner/PractitionerListVIsta.fxml") );
        PractitionerListController practitionerListController = new PractitionerListController();
        loader.setController(practitionerListController);
        Parent root = null;
        try{
            root = loader.load();
        } catch(IOException ioe) {
            Logger.getLogger(PractitionerListController.class.getName()).log(Level.WARNING, null, ioe);
        }
        Stage practitionerListStage = new Stage();
        practitionerListStage.setScene( new Scene(root) );
        practitionerListStage.show();
    }

}
