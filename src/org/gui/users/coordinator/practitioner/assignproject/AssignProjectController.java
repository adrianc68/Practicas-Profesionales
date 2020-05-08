package org.gui.users.coordinator.practitioner.assignproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.database.dao.PractitionerDAO;
import org.domain.Practitioner;
import org.domain.Project;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AssignProjectController implements Initializable {
    private boolean assignProjectStatus;
    private Practitioner practitioner;
    private Project project;

    @FXML
    private Label practitionerNameLabel;

    @FXML
    private Label practitionerEnrollmentLabel;

    @FXML
    private Label practitionerPhoneNumber;

    @FXML
    private ListView<Project> practitionerListView;

    @FXML
    private Label projectNameLabel;

    @FXML
    private Label projectCompanyNameLabel;

    @FXML
    private Label projectGeneralPurposeLabel;

    @FXML
    private Label projectDescriptionLabel;

    @FXML
    private Label projectScheduleLabel;

    @FXML
    private Label projectEmailResponsableLabel;

    public AssignProjectController(Project project, Practitioner practitioner) {
        this.project = project;
        this.practitioner = practitioner;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setPractitionerToCard();
        setProjectToCard();
    }

    public boolean getAssignationOperationStatus() {
        return assignProjectStatus;
    }

    public void showStage() {
        FXMLLoader loader = new FXMLLoader( getClass().getResource("/org/gui/users/coordinator/practitioner/assignproject/AssignProjectVista.fxml") );
        loader.setController(this);
        Parent root = null;
        try{
            root = loader.load();
        } catch(IOException ioe) {
            Logger.getLogger( AssignProjectController.class.getName()).log(Level.WARNING, null, ioe);
        }
        Stage assignProjectStage = new Stage();
        assignProjectStage.initModality(Modality.APPLICATION_MODAL);
        assignProjectStage.initStyle(StageStyle.UTILITY);
        assignProjectStage.setScene( new Scene(root) );
        assignProjectStage.showAndWait();
    }

    @FXML
    void assignProjectButtonPressed(ActionEvent event) {
        PractitionerDAO practitionerDAO = new PractitionerDAO();
        assignProjectStatus = practitionerDAO.assignProjectToPractitioner( practitioner.getId(), project.getId() );
        closeStage(event);
    }

    @FXML
    void cancelButtonPressed(ActionEvent event) {
        closeStage(event);
    }

    private void setPractitionerToCard() {
        practitionerNameLabel.setText( practitioner.getName() );
        practitionerEnrollmentLabel.setText( practitioner.getEnrollment() );
        practitionerPhoneNumber.setText( practitioner.getPhoneNumber() );
        ObservableList<Project> selectedProjectsObservableList = FXCollections.observableArrayList();
        selectedProjectsObservableList.setAll( practitioner.getSelectedProjects() );
        practitionerListView.setItems( selectedProjectsObservableList );
    }

    private void setProjectToCard() {
        projectNameLabel.setText( project.getName() );
        projectScheduleLabel.setText( project.getSchedule() );
        projectDescriptionLabel.setText( project.getGeneralDescription() );
        projectGeneralPurposeLabel.setText( project.getGeneralPurpose() );
        projectCompanyNameLabel.setText( project.getCompany().getName() );
        projectEmailResponsableLabel.setText( project.getEmailResponsable() );
    }

    private void closeStage(ActionEvent event) {
        Stage stage = ( (Stage) ( (Node) event.getSource() ).getScene().getWindow() );
        stage.close();
    }

}
