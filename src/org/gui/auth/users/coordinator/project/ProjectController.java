package org.gui.auth.users.coordinator.project;

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
import org.domain.Project;
import org.gui.Controller;
import org.gui.auth.resources.card.ProjectCard;
import org.gui.auth.users.coordinator.project.registerproject.RegisterProjectController;
import org.gui.auth.users.coordinator.project.removeproject.RemoveProjectController;
import org.gui.auth.users.coordinator.project.updateproject.UpdateProjectController;
import org.util.CSSProperties;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ProjectController extends Controller implements Initializable {
    private ProjectCard selectedProjectCard;
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
        setStyleClass(rootStage, getClass().getResource("../../../resources/" + CSSProperties.readTheme().getTheme() ).toExternalForm());
        setProjectsFromDatabaseToScrollPane();
    }

    public void showStage() {
        loadFXMLFile(getClass().getResource("/org/gui/auth/users/coordinator/project/ProjectVista.fxml"), this);
        stage.show();
    }

    @FXML
    protected void addButtonPressed(ActionEvent event) {
        rootStage.setDisable(true);
        RegisterProjectController registerProjectController = new RegisterProjectController();
        registerProjectController.showStage();
        if( registerProjectController.getAddOperationStatus() ) {
            addProjectInACardToScrollPane( registerProjectController.getNewProject() );
        }
        rootStage.setDisable(false);
    }

    @FXML
    protected void removeButtonPressed(ActionEvent event) {
        if(selectedProjectCard != null){
            rootStage.setDisable(true);
            RemoveProjectController removeProjectController = new RemoveProjectController();
            removeProjectController.setProject( selectedProjectCard.getProject() );
            removeProjectController.showStage();
            if( removeProjectController.getRemoveOperationStatus() ) {
                projectsPane.getChildren().remove(selectedProjectCard);
                selectedProjectCard = null;
                clearLabels();
            }
            rootStage.setDisable(false);
        }
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
    protected void updateButtonPressed(ActionEvent event) {
        if(selectedProjectCard != null) {
            rootStage.setDisable(true);
            UpdateProjectController updateProjectController = new UpdateProjectController();
            updateProjectController.setSelectedProject( selectedProjectCard.getProject() );
            updateProjectController.showStage();
            if( updateProjectController.getUpdateOperationStatus() ) {
                selectedProjectCard.setProject( updateProjectController.getSelectedProject() );
                selectedProjectCard.repaint();
                setProjectInformationToLabelsBySelectedProject( selectedProjectCard.getProject() );
            }
            rootStage.setDisable(false);
        }
    }

    private void setProjectsFromDatabaseToScrollPane() {
        List<Project> projects = new ProjectDAO().getAllProjectsFromLastCourse();
        if(projects != null) {
            for ( Project project : projects ) {
                addProjectInACardToScrollPane(project);
            }
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
