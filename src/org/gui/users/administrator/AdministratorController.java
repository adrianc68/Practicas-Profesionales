package org.gui.users.administrator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.gui.users.administrator.update.ManagementController;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AdministratorController {

    public AdministratorController() {
    }

    public void showStage() {
        FXMLLoader loader = new FXMLLoader( getClass().getResource("/org/gui/users/administrator/AdministradorVista.fxml") );
        loader.setController(this);
        Parent root = null;
        try{
            root = loader.load();
        } catch(IOException ioe) {
            Logger.getLogger( AdministratorController.class.getName() ).log(Level.WARNING, null, ioe);
        }
        Stage coordinator = new Stage();
        coordinator.setScene( new Scene(root) );
        coordinator.showAndWait();
    }

    @FXML
    void managementButtonPressed(ActionEvent event) {
        ManagementController managementController = new ManagementController();
        managementController.showStage();
    }

    @FXML
    void logOutButtonPressed(ActionEvent event) {
        Stage stage = ( (Stage) ( (Node) event.getSource() ).getScene().getWindow() );
        stage.close();
    }


}
