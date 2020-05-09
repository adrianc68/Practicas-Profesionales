package org.gui.users.coordinator.project;

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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import org.database.dao.ProjectDAO;
import org.domain.Project;
import org.gui.resources.card.ProjectCard;
import org.gui.users.coordinator.project.registerproject.RegisterProjectController;
import org.gui.users.coordinator.project.removeproject.RemoveProjectController;
import org.gui.users.coordinator.project.updateproject.UpdateProjectController;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProjectController implements Initializable {
    private ProjectCard selectedProjectCard;

    @FXML
    private Label projectNameLabel;

    @FXML
    private Label projectDescriptionLabel;

    @FXML
    private Label projectPurposeLabel;

    @FXML
    private Label projectScheduleLabel;

    @FXML
    private Label projectResponsableNameLabel;

    @FXML
    private Label projectResponsableChargeLabel;

    @FXML
    private Label projectEmailResponsableLabel;

    @FXML
    private Label projectCompanyNameLabel;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private FlowPane projectsPane;

    @FXML
    private ListView<String> responsabilitiesListView;

    @FXML
    private ListView<String> mediateObjetivesListView;

    @FXML
    private ListView<String> activitiesListView;

    @FXML
    private ListView<String> resourcesListView;

    @FXML
    private ListView<String> immeadiateObjetivesListView;

    @FXML
    private ListView<String> methodologiesListView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setProjectsFromDatabaseToScrollPane();
    }

    public void showStage() {
        FXMLLoader loader = new FXMLLoader( getClass().getResource("/org/gui/users/coordinator/project/ProjectVista.fxml") );
        loader.setController(this);
        Parent root = null;
        try{
            root = loader.load();
        } catch(IOException ioe) {
            Logger.getLogger( ProjectController.class.getName() ).log(Level.WARNING, null, ioe);
        }
        Stage projectStage = new Stage();
        projectStage.setScene( new Scene(root) );
        projectStage.show();
    }

    @FXML
    void addButtonPressed(ActionEvent event) {
        rootPane.setDisable(true);
        RegisterProjectController registerProjectController = new RegisterProjectController();
        registerProjectController.showStage();
        if( registerProjectController.getAddOperationStatus() ) {
            addProjectInACardToScrollPane( registerProjectController.getNewProject() );
        }
        rootPane.setDisable(false);
    }

    @FXML
    void removeButtonPressed(ActionEvent event) {
        if(selectedProjectCard != null){
            rootPane.setDisable(true);
            RemoveProjectController removeProjectController = new RemoveProjectController( selectedProjectCard.getProject() );
            removeProjectController.showStage();
            if( removeProjectController.getRemoveOperationStatus() ) {
                System.out.println(selectedProjectCard);
                projectsPane.getChildren().remove(selectedProjectCard);
                selectedProjectCard = null;
            }
            rootPane.setDisable(false);
        }
    }

    @FXML
    void updateButtonPressed(ActionEvent event) {
        if(selectedProjectCard != null) {
            rootPane.setDisable(true);
            UpdateProjectController updateProjectController = new UpdateProjectController( selectedProjectCard.getProject() );
            updateProjectController.showStage();
            if( updateProjectController.getUpdateOperationStatus() ) {
                selectedProjectCard.setProject( updateProjectController.getSelectedProject() );
                selectedProjectCard.repaint();
                setProjectInformationToLabels( selectedProjectCard.getProject() );
            }
            rootPane.setDisable(false);
        }
    }

    @FXML
    void cancelButtonPressed(ActionEvent event) {
        Stage stage = ( (Stage) ( (Node) event.getSource() ).getScene().getWindow() );
        stage.close();
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
            setProjectInformationToLabels( selectedProjectCard.getProject() );
        });
        projectsPane.getChildren().add(card);
    }

    private void setProjectInformationToLabels(Project project) {
        projectNameLabel.setText( project.getName() );
        projectDescriptionLabel.setText( project.getGeneralDescription() );
        projectPurposeLabel.setText( project.getGeneralPurpose() );
        projectScheduleLabel.setText( project.getSchedule() );
        projectResponsableNameLabel.setText( project.getNameResponsable() );
        projectResponsableChargeLabel.setText( project.getChargeResponsable() );
        projectEmailResponsableLabel.setText( project.getEmailResponsable() );
        projectCompanyNameLabel.setText( project.getCompany().getName() );
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
