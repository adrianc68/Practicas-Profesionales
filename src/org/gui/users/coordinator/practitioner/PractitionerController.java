package org.gui.users.coordinator.practitioner;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.database.dao.PractitionerDAO;
import org.database.dao.ProfessorDAO;
import org.database.dao.ProjectDAO;
import org.domain.Practitioner;
import org.domain.Project;
import org.gui.users.coordinator.practitioner.addpractitioner.AddPractitionerController;
import org.gui.users.coordinator.practitioner.assignprofessor.AssignProfessorController;
import org.gui.users.coordinator.practitioner.assignproject.AssignProjectController;
import org.gui.resources.card.PractitionerCard;
import org.gui.users.coordinator.practitioner.assignproject.assignother.AssignOtherProject;
import org.gui.users.coordinator.practitioner.removepractitioner.RemovePractitionerController;

public class PractitionerController implements Initializable {
    private PractitionerCard practitionerCardSelected;
    @FXML
    private AnchorPane rootPane;

    @FXML
    private FlowPane practitionersPane;

    @FXML
    private Label practitionerSelectedLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setPractitionerToScrollPaneFromDatabase();
    }

    public void showStage() {
        FXMLLoader loader = new FXMLLoader( getClass().getResource("/org/gui/users/coordinator/practitioner/PractitionerVista.fxml") );
        PractitionerController practitionerController = new PractitionerController();
        loader.setController(practitionerController);
        Parent root = null;
        try{
            root = loader.load();
        } catch(IOException ioe) {
            Logger.getLogger(PractitionerController.class.getName()).log(Level.WARNING, null, ioe);
        }
        Stage practitionerStage = new Stage();
        practitionerStage.initModality(Modality.APPLICATION_MODAL);
        practitionerStage.setScene( new Scene(root) );
        practitionerStage.show();
    }

    @FXML
    void removePractitionerButtonPressed(ActionEvent event) {
        if(practitionerCardSelected != null) {
            rootPane.setDisable(true);
            RemovePractitionerController removePractitionerController = new RemovePractitionerController( practitionerCardSelected.getPractitioner() );
            removePractitionerController.showStage();
            if( removePractitionerController.getRemoveOperationStatus() ) {
                practitionersPane.getChildren().remove(practitionerCardSelected);
                practitionerCardSelected = null;
                practitionerSelectedLabel.setText("Eliminado");
            }
            rootPane.setDisable(false);
        }
    }

    @FXML
    void assignProfessorButtonPressed(ActionEvent event) {
        if( practitionerCardSelected != null) {
            rootPane.setDisable(true);
            AssignProfessorController assignProfessorController = new AssignProfessorController(practitionerCardSelected.getPractitioner());
            assignProfessorController.showStage();
            if( assignProfessorController.getAssignOperationStatus() ) {
                practitionerCardSelected.getPractitioner().setProfessor(assignProfessorController.getProfessorCardSelected().getProfessor());
                practitionerCardSelected.repaint();
            }
            rootPane.setDisable(false);
        }
    }

    @FXML
    void addPractitionerButtonPressed(ActionEvent event) {
        rootPane.setDisable(true);
        AddPractitionerController addPractitionerController = new AddPractitionerController();
        addPractitionerController.showStage();
        if(addPractitionerController.getAddOperationStatus() ) {
            Practitioner newPractitioner = addPractitionerController.getNewPractitioner();
            int idPractitioner = newPractitioner.getId();
            newPractitioner.setProject( new ProjectDAO().getAssignedProjectByPractitionerID(idPractitioner) );
            newPractitioner.setProfessor( new ProfessorDAO().getAssignedProfessorByPractitionerID(idPractitioner) );
            newPractitioner.setSelectedProjects( new ProjectDAO().getSelectedProjectsByPractitionerID(idPractitioner) );
            addPractitionerInACardToScrollPane(newPractitioner);
        }
        rootPane.setDisable(false);
    }

    @FXML
    void closeButtonPressed(ActionEvent event) {
        Stage stage = ( (Stage) ( (Node) event.getSource() ).getScene().getWindow() );
        stage.close();
    }

    private void setPractitionerToScrollPaneFromDatabase() {
        List<Practitioner> practitioners = new PractitionerDAO().getAllPractitionersFromLastCourse();
        if(practitioners != null) {
            for ( Practitioner practitioner : practitioners ) {
                int idPractitioner = practitioner.getId();
                practitioner.setProject( new ProjectDAO().getAssignedProjectByPractitionerID(idPractitioner) );
                practitioner.setProfessor( new ProfessorDAO().getAssignedProfessorByPractitionerID(idPractitioner) );
                practitioner.setSelectedProjects( new ProjectDAO().getSelectedProjectsByPractitionerID(idPractitioner) );
                addPractitionerInACardToScrollPane(practitioner);
            }
        }
    }

    private void addPractitionerInACardToScrollPane(Practitioner practitioner) {
        PractitionerCard card = new PractitionerCard(practitioner);
        card.setOnMouseReleased((MouseEvent mouseEvent) -> {
            practitionerCardSelected = card;
            practitionerSelectedLabel.setText(practitionerCardSelected.getPractitioner().getName());
        });

        card.getButton().setOnMouseReleased((MouseEvent mouseEvent) -> {
            Project projectToAssign = card.getListView().getSelectionModel().getSelectedItem();
            rootPane.setDisable(true);
            if (projectToAssign == null) {
                AssignOtherProject assignOtherProject = new AssignOtherProject();
                assignOtherProject.showStage();
                projectToAssign = assignOtherProject.getSelectedProjectCard().getProject();
            }
            AssignProjectController assignProjectController = new AssignProjectController(projectToAssign, card.getPractitioner());
            assignProjectController.showStage();
            if (assignProjectController.getAssignationOperationStatus()) {
                card.getPractitioner().setProject(projectToAssign);
                card.repaint();
                card.addAssignButton();
            }
            rootPane.setDisable(false);

        });
        practitionersPane.getChildren().add(card);
    }

}
