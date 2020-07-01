package org.gui.auth.users.coordinator.practitioner;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Modality;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.database.dao.PractitionerDAO;
import org.database.dao.ProfessorDAO;
import org.database.dao.ProjectDAO;
import org.domain.Practitioner;
import org.domain.Project;
import org.gui.Controller;
import org.gui.auth.resources.alerts.OperationAlert;
import org.gui.auth.users.coordinator.practitioner.addpractitioner.AddPractitionerController;
import org.gui.auth.users.coordinator.practitioner.assignprofessor.AssignProfessorController;
import org.gui.auth.users.coordinator.practitioner.assignproject.AssignProjectController;
import org.gui.auth.resources.card.PractitionerCard;
import org.gui.auth.users.coordinator.practitioner.assignproject.assignother.AssignOtherProject;
import org.gui.auth.users.coordinator.practitioner.removepractitioner.RemovePractitionerController;

public class PractitionerController extends Controller implements Initializable {
    private PractitionerCard practitionerCardSelected;
    @FXML private AnchorPane rootStage;
    @FXML private FlowPane practitionersPane;
    @FXML private Label practitionerSelectedLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setStyleClass(rootStage);
        setPractitionerToScrollPaneFromDatabase();
    }

    public void showStage() {
        loadFXMLFile(getClass().getResource("/org/gui/auth/users/coordinator/practitioner/PractitionerVista.fxml"), this);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    @FXML
    protected void closeButtonPressed(ActionEvent event) {
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
    protected void removePractitionerButtonPressed(ActionEvent event) {
        if(practitionerCardSelected != null) {
            rootStage.setDisable(true);
            RemovePractitionerController removePractitionerController = new RemovePractitionerController();
            removePractitionerController.setPractitionerToBeRemoved( practitionerCardSelected.getPractitioner() );
            removePractitionerController.showStage();
            if( removePractitionerController.getRemoveOperationStatus() ) {
                practitionersPane.getChildren().remove(practitionerCardSelected);
                clearSelected();
            }
            rootStage.setDisable(false);
        }
    }

    @FXML
    protected void assignProfessorButtonPressed(ActionEvent event) {
        if( practitionerCardSelected != null) {
            rootStage.setDisable(true);
            AssignProfessorController assignProfessorController = new AssignProfessorController();
            assignProfessorController.setPractitioner( practitionerCardSelected.getPractitioner() );
            assignProfessorController.showStage();
            if( assignProfessorController.getAssignOperationStatus() ) {
                practitionerCardSelected.getPractitioner().setProfessor( assignProfessorController.getProfessorCardSelected().getProfessor() );
                practitionerCardSelected.repaint();
                clearSelected();
            }
            rootStage.setDisable(false);
        }
    }

    @FXML
    protected void addPractitionerButtonPressed(ActionEvent event) {
        rootStage.setDisable(true);
        AddPractitionerController addPractitionerController = new AddPractitionerController();
        addPractitionerController.showStage();
        if(addPractitionerController.getAddOperationStatus() ) {
            Practitioner newPractitioner = addPractitionerController.getNewPractitioner();
            int idPractitioner = newPractitioner.getId();
            try {
                newPractitioner.setProject( new ProjectDAO().getAssignedProjectByPractitionerID(idPractitioner) );
                newPractitioner.setProfessor( new ProfessorDAO().getAssignedProfessorByPractitionerID(idPractitioner) );
                newPractitioner.setSelectedProjects( new ProjectDAO().getSelectedProjectsByPractitionerID(idPractitioner) );
            } catch (SQLException sqlException) {
                Logger.getLogger( PractitionerController.class.getName() ).log(Level.WARNING, null, sqlException);
            }

            addPractitionerInACardToScrollPane(newPractitioner);
        }
        rootStage.setDisable(false);
    }

    private void clearSelected() {
        practitionerCardSelected = null;
        practitionerSelectedLabel.setText("");
    }

    private Project assignOtherProject() {
        AssignOtherProject assignOtherProject = new AssignOtherProject();
        assignOtherProject.showStage();
        return assignOtherProject.getSelectedProject();
    }

    private void assignProjectButtonPressed(Project projectToAssign, PractitionerCard card) {
        rootStage.setDisable(true);
        if(projectToAssign == null) {
            projectToAssign = assignOtherProject();
        }
        if(projectToAssign != null) {
            confirmationProject(projectToAssign, card);
        }
        rootStage.setDisable(false);
    }

    private void setPractitionerToScrollPaneFromDatabase() {
        try {
            List<Practitioner> practitionersList = new PractitionerDAO().getAllPractitionersFromLastCourse();
            if (practitionersList != null) {
                for (Practitioner practitioner : practitionersList) {
                    int idPractitioner = practitioner.getId();
                    practitioner.setProject( new ProjectDAO().getAssignedProjectByPractitionerID(idPractitioner) );
                    practitioner.setProfessor( new ProfessorDAO().getAssignedProfessorByPractitionerID(idPractitioner) );
                    practitioner.setSelectedProjects( new ProjectDAO().getSelectedProjectsByPractitionerID(idPractitioner) );
                    addPractitionerInACardToScrollPane(practitioner);
                }
            }
        } catch (SQLException sqlException) {
            OperationAlert.showLostConnectionAlert();
            Logger.getLogger( PractitionerController.class.getName() ).log(Level.WARNING, null, sqlException);
        }
    }

    private void addPractitionerInACardToScrollPane(Practitioner practitioner) {
        PractitionerCard card = new PractitionerCard(practitioner);
        card.setOnMouseReleased((MouseEvent mouseEvent) -> {
            practitionerCardSelected = card;
            practitionerSelectedLabel.setText( practitionerCardSelected.getPractitioner().getName() );
        });
        card.getButton().setOnMouseReleased((MouseEvent mouseEvent) -> {
            Project projectToAssign = card.getListView().getSelectionModel().getSelectedItem();
            assignProjectButtonPressed(projectToAssign, card);
        });
        practitionersPane.getChildren().add(card);
    }

    private void confirmationProject(Project projectToAssign, PractitionerCard card) {
        AssignProjectController assignProjectController = new AssignProjectController();
        assignProjectController.setProject(projectToAssign);
        assignProjectController.setPractitioner( card.getPractitioner() );
        assignProjectController.showStage();
        if ( assignProjectController.getAssignationOperationStatus() ) {
            card.getPractitioner().setProject(projectToAssign);
            card.repaint();
            card.addAssignButton();
        }
    }



}
