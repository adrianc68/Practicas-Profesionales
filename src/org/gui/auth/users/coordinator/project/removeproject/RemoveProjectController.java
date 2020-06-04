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
import org.util.CSSProperties;
import java.net.URL;
import java.util.ResourceBundle;

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
        setStyleClass(rootStage, getClass().getResource("../../../../resources/" + CSSProperties.readTheme().getTheme() ).toExternalForm() );
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
            ProjectDAO projectDAO = new ProjectDAO();
            removeOperationStatus = projectDAO.removeProjectByID( project.getId() );
            closeButton.fire();
            if(removeOperationStatus) {
                String title = "Eliminado correctamente.";
                String contentText = "¡Se ha eliminado correctamente el proyecto";
                OperationAlert.showSuccessfullAlert(title, contentText);
            } else {
                String title = "No se pudo eliminar el proyecto";
                String contentText = "¡No se pudo eliminar el proyecto!";
                OperationAlert.showUnsuccessfullAlert(title, contentText);
            }
        } else {
            systemLabel.setText("¡Verifica tus datos!");
        }
    }

}
