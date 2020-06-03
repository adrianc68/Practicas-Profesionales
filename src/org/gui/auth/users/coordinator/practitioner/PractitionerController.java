package org.gui.auth.users.coordinator.practitioner;

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
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.stage.StageStyle;
import org.database.dao.PractitionerDAO;
import org.database.dao.ProfessorDAO;
import org.database.dao.ProjectDAO;
import org.domain.Practitioner;
import org.domain.Project;
import org.gui.auth.users.coordinator.practitioner.addpractitioner.AddPractitionerController;
import org.gui.auth.users.coordinator.practitioner.assignprofessor.AssignProfessorController;
import org.gui.auth.users.coordinator.practitioner.assignproject.AssignProjectController;
import org.gui.auth.resources.card.PractitionerCard;
import org.gui.auth.users.coordinator.practitioner.assignproject.assignother.AssignOtherProject;
import org.gui.auth.users.coordinator.practitioner.removepractitioner.RemovePractitionerController;
import org.util.CSSProperties;

public class PractitionerController implements Initializable {
    private double mousePositionOnX;
    private double mousePositionOnY;
    private PractitionerCard practitionerCardSelected;
    @FXML private AnchorPane rootStage;
    @FXML private FlowPane practitionersPane;
    @FXML private Label practitionerSelectedLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setStyleClass();
        setPractitionerToScrollPaneFromDatabase();
    }

    public void showStage() {
        FXMLLoader loader = new FXMLLoader( getClass().getResource("/org/gui/auth/users/coordinator/practitioner/PractitionerVista.fxml") );
        PractitionerController practitionerController = new PractitionerController();
        loader.setController(practitionerController);
        Parent root = null;
        try{
            root = loader.load();
        } catch(IOException ioe) {
            Logger.getLogger(PractitionerController.class.getName()).log(Level.WARNING, null, ioe);
        }
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.TRANSPARENT);
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void removePractitionerButtonPressed(ActionEvent event) {
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
    void assignProfessorButtonPressed(ActionEvent event) {
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
    void addPractitionerButtonPressed(ActionEvent event) {
        rootStage.setDisable(true);
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
        rootStage.setDisable(false);
    }

    @FXML
    void closeButtonPressed(ActionEvent event) {
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
            practitionerSelectedLabel.setText( practitionerCardSelected.getPractitioner().getName() );
        });
        card.getButton().setOnMouseReleased((MouseEvent mouseEvent) -> {
            Project projectToAssign = card.getListView().getSelectionModel().getSelectedItem();
            assignProjectButtonPressed(projectToAssign, card);
        });
        practitionersPane.getChildren().add(card);
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

    private Project assignOtherProject() {
        AssignOtherProject assignOtherProject = new AssignOtherProject();
        assignOtherProject.showStage();
        return assignOtherProject.getSelectedProject();
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

    private void clearSelected() {
        practitionerCardSelected = null;
        practitionerSelectedLabel.setText("");
    }

    private void setStyleClass() {
        rootStage.getStylesheets().clear();
        rootStage.getStylesheets().add(getClass().getResource("../../../resources/" + CSSProperties.readTheme().getTheme() ).toExternalForm() );
    }

}
