package org.gui.users.practitioner.project;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.gui.users.practitioner.project.myproject.MyProjectController;
import org.gui.users.practitioner.project.selectproject.SelectProjectController;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProjectController {

    public void showStage() {
        FXMLLoader loader = new FXMLLoader( getClass().getResource("/org/gui/users/practitioner/project/ProjectVista.fxml") );
        ProjectController projectController = new ProjectController();
        loader.setController(projectController);
        Parent root = null;
        try{
            root = loader.load();
        } catch(IOException ioe) {
            Logger.getLogger(ProjectController.class.getName()).log(Level.WARNING, null, ioe);
        }
        Stage projectStage = new Stage();
        projectStage.setScene( new Scene(root) );
        projectStage.show();
    }

    @FXML
    void myProjectButtonPressed(ActionEvent event) {
        MyProjectController myProjectController = new MyProjectController();
        myProjectController.showStage();
    }

    @FXML
    void selectProjectButtonPressed(ActionEvent event) {
        SelectProjectController selectProjectController = new SelectProjectController();
        selectProjectController.showStage();
    }

}