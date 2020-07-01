package org.gui.auth.users.professor.practitioner;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
import org.database.dao.ProjectDAO;
import org.domain.Person;
import org.domain.Practitioner;
import org.gui.Controller;
import org.gui.auth.resources.alerts.OperationAlert;
import org.gui.auth.resources.card.PractitionerProfessorCard;
import org.util.Auth;

public class PractitionerController extends Controller implements Initializable {
    private PractitionerProfessorCard practitionerCardSelected;
    @FXML private AnchorPane rootStage;
    @FXML private FlowPane practitionersPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setStyleClass(rootStage);
        setPractitionerToScrollPaneFromDatabase();
    }

    public void showStage() {
        loadFXMLFile(getClass().getResource("/org/gui/auth/users/professor/practitioner/PractitionerVista.fxml"), this);
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

    private void addPractitionerInACardToScrollPane(Practitioner practitioner) {
        PractitionerProfessorCard card = new PractitionerProfessorCard(practitioner);
        card.setOnMouseReleased((MouseEvent mouseEvent) -> {
            practitionerCardSelected = card;
        });
        practitionersPane.getChildren().add(card);
    }

    private void setPractitionerToScrollPaneFromDatabase() {
        Person professor;
        if( ( professor = Auth.getInstance().getCurrentUser() ) != null) {
            try {
                List<Practitioner> practitionersList = new PractitionerDAO().getAllPractitionersByProfessor( professor.getId() );
                if (practitionersList != null) {
                    for (Practitioner practitioner : practitionersList) {
                        int idPractitioner = practitioner.getId();
                        practitioner.setProject(new ProjectDAO().getAssignedProjectByPractitionerID(idPractitioner));
                        practitioner.setSelectedProjects(new ProjectDAO().getSelectedProjectsByPractitionerID(idPractitioner));
                        addPractitionerInACardToScrollPane(practitioner);
                    }
                }
            } catch (SQLException sqlException) {
                OperationAlert.showLostConnectionAlert();
                Logger.getLogger(PractitionerController.class.getName()).log(Level.WARNING, null, sqlException);
            }
        }
    }

}
