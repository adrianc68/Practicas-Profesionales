package org.gui.users.coordinator.project.removeproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.database.dao.ProjectDAO;
import org.domain.Project;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RemoveProjectController {
    private boolean status;
    private Project project;
    @FXML
    private TextField confirmationInputField;

    public RemoveProjectController(Project project) {
        this.project = project;
    }

    public void showStage() {
        FXMLLoader loader = new FXMLLoader( getClass().getResource("/org/gui/users/coordinator/project/removeproject/RemoveProjectVista.fxml") );
        loader.setController(this);
        Parent root = null;
        try{
            root = loader.load();
        } catch(IOException ioe) {
            Logger.getLogger(RemoveProjectController.class.getName()).log(Level.WARNING, null, ioe);
        }
        Stage removeStage = new Stage();
        removeStage.initModality(Modality.APPLICATION_MODAL);
        removeStage.initStyle(StageStyle.UTILITY);
        removeStage.setScene( new Scene(root) );
        removeStage.showAndWait();
    }

    public boolean getStatusRemoveOperation() {
        return status;
    }

    @FXML
    void removeButtonPressed(ActionEvent event) {
        if(confirmationInputField.getText().equals("ELIMINAR")){
            ProjectDAO projectDAO = new ProjectDAO();
            status = projectDAO.removeProjectByID(project.getId());
            closeStage(event);
        }
    }

    @FXML
    void cancelButtonPressed(ActionEvent event) {
        closeStage(event);
    }

    private void closeStage(ActionEvent event) {
        Stage stage = ( (Stage) ( (Node) event.getSource() ).getScene().getWindow() );
        stage.close();
    }

}
