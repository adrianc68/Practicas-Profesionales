package org.gui.auth.users.coordinator.project.removeproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.database.dao.ProjectDAO;
import org.domain.Project;
import org.gui.auth.resources.alerts.OperationAlert;
import org.util.CSSProperties;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


public class RemoveProjectController implements Initializable {
    private double mousePositionOnX;
    private double mousePositionOnY;
    private boolean removeOperationStatus;
    private Project project;
    @FXML private TextField confirmationTextField;
    @FXML private Label systemLabel;
    @FXML private Label projectLabel;
    @FXML private Button closeButton;
    @FXML private AnchorPane rootStage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setStyleClass();
        if(project != null) {
            projectLabel.setText( project.getName() );
        }
    }

    public void showStage() {
        FXMLLoader loader = new FXMLLoader( getClass().getResource("/org/gui/auth/users/coordinator/project/removeproject/RemoveProjectVista.fxml") );
        loader.setController(this);
        Parent root = null;
        try{
            root = loader.load();
        } catch(IOException ioe) {
            Logger.getLogger( RemoveProjectController.class.getName() ).log(Level.WARNING, null, ioe);
        }
        Stage removeStage = new Stage();
        removeStage.initModality(Modality.APPLICATION_MODAL);
        removeStage.initStyle(StageStyle.TRANSPARENT);
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        removeStage.setScene(scene);
        removeStage.showAndWait();
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public boolean getRemoveOperationStatus() {
        return removeOperationStatus;
    }

    @FXML
    void removeButtonPressed(ActionEvent event) {
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

    @FXML
    void cancelButtonPressed(ActionEvent event) {
        Stage stage = ( (Stage) ( (Node) event.getSource() ).getScene().getWindow() );
        stage.close();
    }

    @FXML
    void stageDragged(MouseEvent event) {
        Stage stage = (Stage) ( ( (Node) event.getSource() ).getScene().getWindow() );
        stage.setX( event.getScreenX() - mousePositionOnX );
        stage.setY( event.getScreenY() - mousePositionOnY );
    }

    @FXML
    void stagePressed(MouseEvent event) {
        mousePositionOnX = event.getSceneX();
        mousePositionOnY = event.getSceneY();
    }

    private void setStyleClass() {
        rootStage.getStylesheets().clear();
        rootStage.getStylesheets().add(getClass().getResource("../../../../resources/" + CSSProperties.readTheme().getTheme() ).toExternalForm() );
    }

}
