package org.gui.auth.users.practitioner.project.selectproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import org.database.dao.ProjectDAO;
import org.domain.Practitioner;
import org.domain.Project;
import org.gui.Controller;
import org.gui.auth.resources.alerts.OperationAlert;
import org.gui.auth.resources.card.ProjectCard;
import org.gui.auth.util.theme.SelectThemeController;
import org.util.CSSProperties;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SelectProjectController extends Controller implements Initializable {
    private Practitioner practitioner;
    private ProjectCard selectedProjectCard;
    private boolean selectOperationStatus;
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
    @FXML private FlowPane projectsPane;
    @FXML private ListView<String> responsabilitiesListView;
    @FXML private ListView<String> mediateObjetivesListView;
    @FXML private ListView<String> activitiesListView;
    @FXML private ListView<String> resourcesListView;
    @FXML private ListView<String> immeadiateObjetivesListView;
    @FXML private ListView<String> methodologiesListView;
    @FXML private AnchorPane rootStage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setStyleClass(rootStage, getClass().getResource("../../../../resources/" + CSSProperties.readTheme().getTheme() ).toExternalForm() );
        setProjectsFromDatabaseToScrollPane();
    }

    public void showStage() {
        loadFXMLFile(getClass().getResource("/org/gui/auth/users/practitioner/project/selectproject/SelectProjectVista.fxml"), this);
        stage.show();
    }

    public SelectProjectController(Practitioner practitioner) {
        this.practitioner = practitioner;
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
    void selectProjectButtonPressed(ActionEvent event) {
        if( selectedProjectCard != null && practitioner != null) {
           selectProject();
           if(selectOperationStatus) {
               stage.close();
               String title = "Se seleccionó el proyecto correctamente.";
               String contentText = "¡Has seleccionado el proyecto " + projectNameLabel.getText() + "!";
               OperationAlert.showSuccessfullAlert(title, contentText);
           }
        }
    }

    private void selectProject() {
        try {
            selectOperationStatus = new ProjectDAO().addSelectedProjectByPractitionerID( practitioner.getId(), selectedProjectCard.getProject().getId() );
        } catch (SQLException sqlException) {
            OperationAlert.showLostConnectionAlert();
            Logger.getLogger( SelectThemeController.class.getName() ).log(Level.WARNING, null, sqlException);
        }
    }

    private void addProjectInACardToScrollPane(Project project) {
        ProjectCard card = new ProjectCard(project);
        card.setOnMouseReleased( (MouseEvent mouseEvent) -> {
            selectedProjectCard = card;
            setProjectInformationToLabelsBySelectedProject( selectedProjectCard.getProject() );
        });
        projectsPane.getChildren().add(card);
    }

    private void setProjectsFromDatabaseToScrollPane() {
        List<Project> projects = null;
        try {
            projects = new ProjectDAO().getAllProjectsFromLastCourse();
        } catch (SQLException sqlException) {
            OperationAlert.showLostConnectionAlert();
            Logger.getLogger( SelectThemeController.class.getName() ).log(Level.WARNING, null, sqlException);
        }
        if(projects != null) {
            for ( Project project : projects ) {
                addProjectInACardToScrollPane(project);
            }
        }
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
