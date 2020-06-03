package org.gui.auth.users.coordinator.practitioner.removepractitioner;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.database.dao.PractitionerDAO;
import org.domain.Practitioner;
import org.gui.auth.resources.alerts.OperationAlert;
import org.util.CSSProperties;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RemovePractitionerController implements Initializable {
    private double mousePositionOnX;
    private double mousePositionOnY;
    private boolean removeOperationStatus;
    private Practitioner practitionerToBeRemoved;
    @FXML private TextField confirmationTextField;
    @FXML private Button closeButton;
    @FXML private AnchorPane rootStage;
    @FXML private Label systemLabel;
    @FXML private Label userLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setStyleClass();
        if( practitionerToBeRemoved != null) {
            userLabel.setText( practitionerToBeRemoved.getName() );
        }
    }

    public void showStage() {
        FXMLLoader loader = new FXMLLoader( getClass().getResource("/org/gui/auth/users/coordinator/practitioner/removepractitioner/RemovePractitionerVista.fxml") );
        loader.setController(this);
        Parent root = null;
        try{
            root = loader.load();
        } catch(IOException ioe) {
            Logger.getLogger( RemovePractitionerController.class.getName() ).log(Level.WARNING, null, ioe);
        }
        Stage removeStage = new Stage();
        removeStage.initModality(Modality.APPLICATION_MODAL);
        removeStage.initStyle(StageStyle.TRANSPARENT);
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        removeStage.setScene(scene);
        removeStage.showAndWait();
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

    private void setStyleClass() {
        rootStage.getStylesheets().clear();
        rootStage.getStylesheets().add(getClass().getResource("../../../../resources/" + CSSProperties.readTheme().getTheme() ).toExternalForm() );
    }

}
