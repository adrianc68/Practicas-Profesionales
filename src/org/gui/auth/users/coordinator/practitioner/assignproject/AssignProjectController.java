package org.gui.auth.users.coordinator.practitioner.assignproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import org.database.dao.ProjectDAO;
import org.domain.Practitioner;
import org.domain.Project;
import org.gui.Controller;
import org.gui.auth.resources.alerts.OperationAlert;
import org.util.CSSProperties;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AssignProjectController extends Controller implements Initializable {
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
        setStyleClass(rootStage, getClass().getResource( "../../../../resources/" + CSSProperties.readTheme().getTheme() ).toExternalForm() );
        assignProjectStatus = false;
        if(practitioner != null && project != null) {
            setInformationToCards();
        }
    }

    public void showStage() {
        loadFXMLFile(getClass().getResource("/org/gui/auth/users/coordinator/practitioner/assignproject/AssignProjectVista.fxml"), this);
        stage.initModality(Modality.APPLICATION_MODAL);
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
            assignProject();
        } else {
            systemLabel.setText("¡Verifica tus datos!");
        }
    }

    private void assignProject() {
        try {
            assignProjectStatus = new ProjectDAO().assignProjectToPractitioner( practitioner.getId(), project.getId() );
        } catch (SQLException sqlException) {
            OperationAlert.showLostConnectionAlert();
            Logger.getLogger( AssignProjectController.class.getName() ).log(Level.WARNING, null, sqlException);
        }
        if(assignProjectStatus) {
            stage.close();
            String title = "Proyecto asignado correctamente.";
            String contentText = "¡Se asigno el proyecto al practicante correctamente!";
            OperationAlert.showSuccessfullAlert(title, contentText);
        }
    }

    @FXML
    void cancelButtonPressed(ActionEvent event) {
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

    private void setPractitionerToCard() {
        practitionerNameLabel.setText( practitioner.getName() );
        practitionerPhoneNumberLabel.setText( practitioner.getPhoneNumber() );
    }

    private void setProjectToCard() {
        projectNameLabel.setText( project.getName() );
        projectScheduleLabel.setText( project.getSchedule() );
        projectEmailResponsableLabel.setText( project.getEmailResponsable() );
    }

}
