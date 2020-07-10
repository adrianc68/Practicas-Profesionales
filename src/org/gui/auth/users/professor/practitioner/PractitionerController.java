package org.gui.auth.users.professor.practitioner;

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
import javafx.stage.Stage;
import org.database.dao.PractitionerDAO;
import org.database.dao.ProfessorDAO;
import org.database.dao.ProjectDAO;
import org.domain.Practitioner;
import org.domain.Project;
import org.gui.auth.resources.alerts.OperationAlert;
import org.gui.auth.resources.card.PractitionerCard;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PractitionerController implements Initializable {
    private PractitionerCard practitionerCardSelected;
    @FXML private AnchorPane rootStage;
    @FXML private FlowPane practitionersPane;
    @FXML private Label practitionerSelectedLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setPractitionerToScrollPaneFromDatabase();

    }

    public void showStage() {
        FXMLLoader loader = new FXMLLoader( getClass().getResource("/org/gui/auth/users/professor/practitioner/PractitionerVista.fxml") );
        loader.setController(this);
        Parent root = null;
        try{
            root = loader.load();
        } catch(IOException ioe) {
            Logger.getLogger( PractitionerController.class.getName() ).log(Level.WARNING, null, ioe);
        }
        Stage stage = new Stage();
        stage.setScene( new Scene(root) );
        stage.showAndWait();
    }


    @FXML
    protected void closeButtonPressed(ActionEvent event) {
        closeStage(event);
    }

    private void clearSelected() {
        practitionerCardSelected = null;
        practitionerSelectedLabel.setText("");
    }

    private void setPractitionerToScrollPaneFromDatabase() {
        try {
            List<Practitioner> practitioners = new PractitionerDAO().getAllPractitionersFromLastCourse();
            if (practitioners != null) {
                for (Practitioner practitioner : practitioners) {
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
            //assignProjectButtonPressed(projectToAssign, card);
        });
        practitionersPane.getChildren().add(card);
    }

    private void closeStage(ActionEvent event) {
        Stage stage = ( (Stage) ( (Node) event.getSource() ).getScene().getWindow() );
        stage.close();
    }

}
