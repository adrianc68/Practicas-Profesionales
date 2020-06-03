package org.gui.auth.users.coordinator.practitioner.assignproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.database.dao.PractitionerDAO;
import org.domain.Practitioner;
import org.domain.Project;
import org.gui.auth.resources.alerts.OperationAlert;
import org.util.CSSProperties;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AssignProjectController implements Initializable {
    private double mousePositionOnX;
    private double mousePositionOnY;
    private boolean assignProjectStatus;
    private Practitioner practitioner;
    private Project project;
    @FXML private Label practitionerNameLabel;
    @FXML private Label practitionerPhoneNumberLabel;
    @FXML private Label projectNameLabel;
    @FXML private Label projectScheduleLabel;
    @FXML private Label projectEmailResponsableLabel;
    @FXML private Button closeButton;
    @FXML private AnchorPane rootStage;
    @FXML private Label systemLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setStyleClass();
        assignProjectStatus = false;
        if(practitioner != null && project != null) {
            setInformationToCards();
        }
    }

    public void showStage() {
        FXMLLoader loader = new FXMLLoader( getClass().getResource("/org/gui/auth/users/coordinator/practitioner/assignproject/AssignProjectVista.fxml") );
        loader.setController(this);
        Parent root = null;
        try{
            root = loader.load();
        } catch(IOException ioe) {
            Logger.getLogger( AssignProjectController.class.getName()).log(Level.WARNING, null, ioe);
        }
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.TRANSPARENT);
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.showAndWait();
    }

    public void setInformationToCards() {
        setPractitionerToCard();
        setProjectToCard();
    }

    public void setPractitioner(Practitioner practitioner) {
        this.practitioner = practitioner;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public boolean getAssignationOperationStatus() {
        return assignProjectStatus;
    }

    @FXML
    void assignProjectButtonPressed(ActionEvent event) {
        if( practitioner != null && project != null ) {
            assignProjectStatus = new PractitionerDAO().assignProjectToPractitioner( practitioner.getId(), project.getId() );
        }
        if(assignProjectStatus) {
            closeButton.fire();
            if(assignProjectStatus) {
                String title = "Proyecto asignado correctamente.";
                String contentText = "¡Se asigno el proyecto al practicante correctamente!";
                OperationAlert.showSuccessfullAlert(title, contentText);
            } else {
                String title = "No se pudo asignar el proyecto.";
                String contentText = "¡No se pudo asignar el proyecto al practicante!";
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

    private void setPractitionerToCard() {
        practitionerNameLabel.setText( practitioner.getName() );
        practitionerPhoneNumberLabel.setText( practitioner.getPhoneNumber() );
    }

    private void setProjectToCard() {
        projectNameLabel.setText( project.getName() );
        projectScheduleLabel.setText( project.getSchedule() );
        projectEmailResponsableLabel.setText( project.getEmailResponsable() );
    }

    private void setStyleClass() {
        rootStage.getStylesheets().clear();
        rootStage.getStylesheets().add(getClass().getResource("../../../../resources/" + CSSProperties.readTheme().getTheme() ).toExternalForm() );
    }

}
