package org.gui.users.administrator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.gui.users.administrator.update.ManagementController;

import java.io.IOException;

public class AdministratorController {
    @FXML
    void managementButtonPressed(ActionEvent event) {
        ManagementController managementController = new ManagementController();
        managementController.showStage();
    }

    public AdministratorController() {
    }

    public void showStage() {
        FXMLLoader loader = new FXMLLoader( getClass().getResource("/org/gui/users/administrator/AdministradorVista.fxml") );
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


}
