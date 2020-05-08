package org.gui.users.coordinator.practitioner.assignprofessor;

import javafx.event.ActionEvent;
import javafx.event.Event;
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
import javafx.stage.StageStyle;
import org.database.dao.PractitionerDAO;
import org.database.dao.ProfessorDAO;
import org.domain.Practitioner;
import org.domain.Professor;
import org.gui.resources.card.ProfessorCard;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AssignProfessorController implements Initializable {
    private boolean assignOperationStatus;
    private ProfessorCard professorCardSelected;
    private Practitioner practitioner;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private FlowPane professorsPane;

    @FXML
    private Label professorSelectedLabel;

    public AssignProfessorController(Practitioner practitioner) {
        this.practitioner = practitioner;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setProfessorsToScrollPaneFromDatabase();
    }

    public void showStage() {
        FXMLLoader loader = new FXMLLoader( getClass().getResource("/org/gui/users/coordinator/practitioner/assignprofessor/AssignProfessorVista.fxml") );
        loader.setController(this);
        Parent root = null;
        try{
            root = loader.load();
        } catch(IOException ioe) {
            Logger.getLogger(AssignProfessorController.class.getName()).log(Level.WARNING, null, ioe);
        }
        Stage professorStage = new Stage();
        professorStage.initModality(Modality.APPLICATION_MODAL);
        professorStage.initStyle(StageStyle.UTILITY);
        professorStage.setScene( new Scene(root) );
        professorStage.showAndWait();
    }

    public boolean getAssignOperationStatus() {
        return assignOperationStatus;
    }

    public ProfessorCard getProfessorCardSelected() {
        return professorCardSelected;
    }

    @FXML
    void cancelButtonPresed(ActionEvent event) {
        closeStage(event);
    }

    private void assignProfessorToDatabase() {
            PractitionerDAO practitionerDAO = new PractitionerDAO();
            assignOperationStatus = practitionerDAO.assignProfessorToPractitioner( practitioner.getId(), professorCardSelected.getProfessor().getId() );
    }

    private void addProfessorInACardToScrollPane(Professor professor) {
        ProfessorCard card = new ProfessorCard(professor);
        card.setOnMouseReleased( (MouseEvent mouseEvent) -> {
            professorCardSelected = card;
            assignProfessorToDatabase();
            closeStage(mouseEvent);
        });
        professorsPane.getChildren().add(card);
    }

    private void setProfessorsToScrollPaneFromDatabase() {
        List<Professor> professors = new ProfessorDAO().getAllProfessorsFromLastCourse();
        if(professors != null) {
            for ( Professor professor : professors ) {
                addProfessorInACardToScrollPane(professor);
            }
        }
    }

    private void closeStage(Event event) {
        Stage stage = ( (Stage) ( (Node) event.getSource() ).getScene().getWindow() );
        stage.close();
    }

}
