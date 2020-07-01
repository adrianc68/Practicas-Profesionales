package org.gui.auth.users.coordinator.project.removeproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import org.database.dao.ProjectDAO;
import org.domain.Project;
import org.gui.Controller;
import org.gui.auth.resources.alerts.OperationAlert;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RemoveProjectController extends Controller implements Initializable {
    private boolean removeOperationStatus;
    private Project project;
    @FXML private TextField confirmationTextField;
    @FXML private Label systemLabel;
    @FXML private Label projectLabel;
    @FXML private Button closeButton;
    @FXML private AnchorPane rootStage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setStyleClass(rootStage);
        if(project != null) {
            projectLabel.setText( project.getName() );
        }
    }

    public void showStage() {
        loadFXMLFile(getClass().getResource("/org/gui/auth/users/coordinator/project/removeproject/RemoveProjectVista.fxml"), this);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public boolean getRemoveOperationStatus() {
        return removeOperationStatus;
    }

    @FXML
    protected void cancelButtonPressed(ActionEvent event) {
        stage.close();
    }

    @FXML
    protected void stageDragged(MouseEvent event) {
        super.stageDragged(event);
    }

    @FXML
    protected void stagePressed(MouseEvent event) {
        super.stagePressed(event);
    }

    @FXML
    protected void removeButtonPressed(ActionEvent event) {
        if( confirmationTextField.getText().equals("ELIMINAR") ){
            removeProject();
            stage.close();
            if(removeOperationStatus) {
                showSucessfullAlert();
            }
        } else {
            systemLabel.setText("¡Verifica tus datos!");
        }
    }

    private void showSucessfullAlert() {
        String title = "Eliminado correctamente.";
        String contentText = "¡Se ha eliminado correctamente el proyecto";
        OperationAlert.showSuccessfullAlert(title, contentText);
    }

    private void removeProject() {
        try {
            removeOperationStatus = new ProjectDAO().removeProjectByID( project.getId() );
        } catch (SQLException sqlException) {
            OperationAlert.showLostConnectionAlert();
            Logger.getLogger( RemoveProjectController.class.getName() ).log(Level.WARNING, null, sqlException);
        }
    }

}
