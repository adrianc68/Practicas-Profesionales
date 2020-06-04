package org.gui.auth.users.coordinator.practitioner.assignprofessor;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Modality;
import org.database.dao.PractitionerDAO;
import org.database.dao.ProfessorDAO;
import org.domain.Practitioner;
import org.domain.Professor;
import org.gui.Controller;
import org.gui.auth.resources.alerts.OperationAlert;
import org.gui.auth.resources.card.ProfessorCard;
import org.util.CSSProperties;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AssignProfessorController extends Controller implements Initializable {
    private boolean assignOperationStatus;
    private ProfessorCard professorCardSelected;
    private Practitioner practitioner;
    @FXML private AnchorPane rootStage;
    @FXML private FlowPane professorsPane;
    @FXML private Label professorSelectedLabel;
    @FXML private Button closeButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setStyleClass(rootStage, getClass().getResource("../../../../resources/" + CSSProperties.readTheme().getTheme() ).toExternalForm());
        assignOperationStatus = false;
        setProfessorsToScrollPaneFromDatabase();
    }

    public void showStage() {
        loadFXMLFile(getClass().getResource("/org/gui/auth/users/coordinator/practitioner/assignprofessor/AssignProfessorVista.fxml"), this);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    public void setPractitioner(Practitioner practitioner) {
        this.practitioner = practitioner;
    }

    public boolean getAssignOperationStatus() {
        return assignOperationStatus;
    }

    public ProfessorCard getProfessorCardSelected() {
        return professorCardSelected;
    }

    @FXML
    void closeButtonPressed(ActionEvent event) {
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

    private void assignProfessorToDatabase() {
        PractitionerDAO practitionerDAO = new PractitionerDAO();
        assignOperationStatus = practitionerDAO.assignProfessorToPractitioner( practitioner.getId(), professorCardSelected.getProfessor().getId() );
        if(assignOperationStatus) {
            String title = "Profesor asignado";
            String contentText = "¡Se ha asignado correctamente el profesor seleccionado al practicante!";
            OperationAlert.showSuccessfullAlert(title, contentText);
        } else {
            String title = "¡No se pudo realizar la asignación";
            String contentText = "¡No se pudo realizar la asignación del profesor al practicante!";
            OperationAlert.showUnsuccessfullAlert(title, contentText);
        }
    }

    private void addProfessorInACardToScrollPane(Professor professor) {
        ProfessorCard card = new ProfessorCard(professor);
        card.setOnMouseReleased( (MouseEvent mouseEvent) -> {
            professorCardSelected = card;
            stage.close();
            assignProfessorToDatabase();
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

}
