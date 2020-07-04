package org.gui.auth.users.practitioner.project.myproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.database.dao.ProjectDAO;
import org.domain.Practitioner;
import org.domain.Project;
import org.gui.Controller;
import org.gui.auth.resources.alerts.OperationAlert;
import org.util.Auth;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MyProjectController extends Controller implements Initializable {
    Practitioner practitioner;
    private Project selectedProject;
    private Project assignedProject;
    private List<Project> projectSelected = new ArrayList<>();
    private ObservableList<Project> projectObservableList;
    private int selectedProjects = 0;
    private int projectPosition = 0;
    @FXML private Label projectNameLabel;
    @FXML private Label projectDescriptionLabel;
    @FXML private Label projectPurposeLabel;
    @FXML private Label projectScheduleLabel;
    @FXML private Label projectResponsableNameLabel;
    @FXML private Label projectResponsableChargeLabel;
    @FXML private Label projectEmailResponsableLabel;
    @FXML private Label projectDurationLabel;
    @FXML private Label companyNameLabel;
    @FXML private Label companyAddressLabel;
    @FXML private Label companyCityLabel;
    @FXML private Label companyStateLabel;
    @FXML private Label companyEmailLabel;
    @FXML private Label companyPhoneNumberLabel;
    @FXML private Label companyDirectUsersLabel;
    @FXML private Label companyIndirectUsersLabel;
    @FXML private Label companySectorLabel;
    @FXML private ListView<String> responsabilitiesListView;
    @FXML private ListView<String> mediateObjetivesListView;
    @FXML private ListView<String> activitiesListView;
    @FXML private ListView<String> resourcesListView;
    @FXML private ListView<String> immeadiateObjetivesListView;
    @FXML private ListView<String> methodologiesListView;
    @FXML private TableView<Project> projecTableView;
    @FXML private TableColumn<Project, String> projectNameColumn;
    @FXML private AnchorPane rootStage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        practitioner = ( (Practitioner) Auth.getInstance().getCurrentUser() );
        //setStyleClass(rootStage, getClass().getResource("../../../resources/" + CSSProperties.readTheme().getTheme() ).toExternalForm());
        getAssignedProjectFromDatabase();
    }

    public void showStage() {
        loadFXMLFile( getClass().getResource("/org/gui/auth/users/practitioner/project/myproject/MyProjectVista.fxml"), this );
        stage.show();
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

    private void getAssignedProjectFromDatabase() {
        ProjectDAO projectDAO = new ProjectDAO();
        try {
            assignedProject = projectDAO.getAssignedProjectByPractitionerID( practitioner.getId() );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if( assignedProject != null){
            setProjectInformationToLabelsBySelectedProject( assignedProject );
        }else{
            OperationAlert.showUnsuccessfullAlert("Proyecto no asignado aun", "El coordinador aun no le ha asignado un Proyecto, cuando se le sea asignado la informacion aparecera en la siguiente ventana ventana");
        }
    }

    private void clearLabels() {
        projectNameLabel.setText("");
        projectDescriptionLabel.setText("");
        projectPurposeLabel.setText("");
        projectScheduleLabel.setText("");
        projectResponsableNameLabel.setText("");
        projectResponsableChargeLabel.setText("");
        projectEmailResponsableLabel.setText("");
        companyAddressLabel.setText("");
        companyCityLabel.setText("");
        companyDirectUsersLabel.setText("");
        companyEmailLabel.setText("");
        companyIndirectUsersLabel.setText("");
        companyNameLabel.setText("");
        companyPhoneNumberLabel.setText("");
        companySectorLabel.setText("");
        companyStateLabel.setText("");
        activitiesListView.setItems(null);
        immeadiateObjetivesListView.setItems(null);
        mediateObjetivesListView.setItems(null);
        methodologiesListView.setItems(null);
        resourcesListView.setItems(null);
        responsabilitiesListView.setItems(null);
    }

    private void setProjectInformationToLabelsBySelectedProject(Project project) {
        projectNameLabel.setText( project.getName() );
        projectDescriptionLabel.setText( project.getGeneralDescription() );
        projectPurposeLabel.setText( project.getGeneralPurpose() );
        projectScheduleLabel.setText( project.getSchedule() );
        projectDurationLabel.setText( String.valueOf( project.getDuration() ) );
        projectResponsableNameLabel.setText( project.getNameResponsable() );
        projectResponsableChargeLabel.setText( project.getChargeResponsable() );
        projectEmailResponsableLabel.setText( project.getEmailResponsable() );
        companyAddressLabel.setText( project.getOrganization().getAddress() );
        companyCityLabel.setText( project.getOrganization().getCity() );
        companyDirectUsersLabel.setText( String.valueOf( project.getOrganization().getDirectUsers() ) );
        companyEmailLabel.setText( project.getOrganization().getEmail() );
        companyIndirectUsersLabel.setText( String.valueOf( project.getOrganization().getIndirectUsers() ) );
        companyNameLabel.setText( project.getOrganization().getName() );
        companyPhoneNumberLabel.setText( project.getOrganization().getPhoneNumber() );
        companySectorLabel.setText( project.getOrganization().getSector().getSector() );
        companyStateLabel.setText( project.getOrganization().getState() );
        ObservableList<String> activitiesObservableList = FXCollections.observableArrayList();
        activitiesObservableList.addAll( project.getActivities() );
        activitiesListView.setItems(activitiesObservableList);
        ObservableList<String> immediateObjetivessObservableList = FXCollections.observableArrayList();
        immediateObjetivessObservableList.addAll( project.getImmediateObjetives() );
        immeadiateObjetivesListView.setItems(immediateObjetivessObservableList);
        ObservableList<String> mediateObjetivesObservableList = FXCollections.observableArrayList();
        mediateObjetivesObservableList.addAll( project.getMediateObjectives() );
        mediateObjetivesListView.setItems(mediateObjetivesObservableList);
        ObservableList<String> methodologiesObservableList = FXCollections.observableArrayList();
        methodologiesObservableList.addAll( project.getMethodologies() );
        methodologiesListView.setItems(methodologiesObservableList);
        ObservableList<String> resourcesObservableList = FXCollections.observableArrayList();
        resourcesObservableList.addAll( project.getResources() );
        resourcesListView.setItems(resourcesObservableList);
        ObservableList<String> responsabilitiesObservableList = FXCollections.observableArrayList();
        responsabilitiesObservableList.addAll( project.getResponsibilities() );
        responsabilitiesListView.setItems(responsabilitiesObservableList);
    }

}
