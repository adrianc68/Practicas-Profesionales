package org.gui.auth.users.coordinator.practitioner.removepractitioner;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import org.database.dao.PractitionerDAO;
import org.domain.Practitioner;
import org.gui.Controller;
import org.gui.auth.resources.alerts.OperationAlert;
import org.util.CSSProperties;
import java.net.URL;
import java.util.ResourceBundle;

public class RemovePractitionerController extends Controller implements Initializable {
    private boolean removeOperationStatus;
    private Practitioner practitionerToBeRemoved;
    @FXML private TextField confirmationTextField;
    @FXML private Button closeButton;
    @FXML private AnchorPane rootStage;
    @FXML private Label systemLabel;
    @FXML private Label userLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setStyleClass(rootStage, getClass().getResource("../../../../resources/" + CSSProperties.readTheme().getTheme() ).toExternalForm());
        if( practitionerToBeRemoved != null) {
            userLabel.setText( practitionerToBeRemoved.getName() );
        }
    }

    public void showStage() {
        loadFXMLFile(getClass().getResource("/org/gui/auth/users/coordinator/practitioner/removepractitioner/RemovePractitionerVista.fxml") , this);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    public void setPractitionerToBeRemoved(Practitioner practitionerToBeRemoved) {
        this.practitionerToBeRemoved = practitionerToBeRemoved;
    }

    public boolean getRemoveOperationStatus() {
        return removeOperationStatus;
    }

    @FXML
    void removeButtonPressed(ActionEvent event) {
        if(confirmationTextField.getText().equals("ELIMINAR")){
            PractitionerDAO practitionerDAO = new PractitionerDAO();
            removeOperationStatus = practitionerDAO.removePractitioner( practitionerToBeRemoved.getId() );
            closeButton.fire();
            if(removeOperationStatus) {
                String title = "Practicante eliminado";
                String contentText = "¡Se elimino el practicante correctamente!";
                OperationAlert.showSuccessfullAlert(title, contentText);
            } else {
                String title = "¡No se pudo eliminar al practicante!";
                String contentText = "¡No se pudo eliminar al practicante! ¡Intentar más tarde!";
                OperationAlert.showUnsuccessfullAlert(title, contentText);
            }
        } else {
            systemLabel.setText("¡Verifica tus datos!");
        }
    }

    @FXML
    void cancelButtonPressed(ActionEvent event) {
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

}
