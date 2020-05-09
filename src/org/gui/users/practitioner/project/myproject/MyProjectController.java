package org.gui.users.practitioner.project.myproject;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MyProjectController {

    public void showStage() {
        FXMLLoader loader = new FXMLLoader( getClass().getResource("/org/gui/users/practitioner/project/myproject/MyProjectVista.fxml") );
        MyProjectController myProjectController = new MyProjectController();
        loader.setController(myProjectController);
        Parent root = null;
        try{
            root = loader.load();
        } catch(IOException ioe) {
            Logger.getLogger(MyProjectController.class.getName()).log(Level.WARNING, null, ioe);
        }
        Stage myProjectStage = new Stage();
        myProjectStage.setScene( new Scene(root) );
        myProjectStage.show();
    }
}
