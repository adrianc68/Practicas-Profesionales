package org.gui.auth.users.coordinator.practitioner.assignprofessor;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.database.dao.PractitionerDAO;
import org.database.dao.ProfessorDAO;
import org.domain.Practitioner;
import org.domain.Professor;
import org.gui.auth.resources.alerts.OperationAlert;
import org.gui.auth.resources.card.ProfessorCard;
import org.util.CSSProperties;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AssignProfessorController implements Initializable {
    private double mousePositionOnX;
    private double mousePositionOnY;
    private boolean assignOperationStatus;
    private ProfessorCard professorCardSelected;
    private Practitioner practitioner;
    @FXML private AnchorPane rootStage;
    @FXML private FlowPane professorsPane;
    @FXML private Label professorSelectedLabel;
    @FXML private Button closeButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setStyleClass();
        assignOperationStatus = false;
        setProfessorsToScrollPaneFromDatabase();
    }

    public void showStage() {
        FXMLLoader loader = new FXMLLoader( getClass().getResource("/org/gui/auth/users/coordinator/practitioner/assignprofessor/AssignProfessorVista.fxml") );
        loader.setController(this);
        Parent root = null;
        try{
            root = loader.load();
        } catch(IOException ioe) {
            Logger.getLogger( AssignProfessorController.class.getName() ).log(Level.WARNING, null, ioe);
        }
        Stage professorStage = new Stage();
        professorStage.initModality(Modality.APPLICATION_MODAL);
        professorStage.initStyle(StageStyle.TRANSPARENT);
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        professorStage.setScene(scene);
        professorStage.showAndWait();
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
            closeButton.fire();
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

    private void setStyleClass() {
        rootStage.getStylesheets().clear();
        rootStage.getStylesheets().add(getClass().getResource("../../../../resources/" + CSSProperties.readTheme().getTheme() ).toExternalForm() );
    }

}
