package org.gui.auth.users.administrator.update.change;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.database.dao.PersonDAO;
import org.domain.ActivityState;
import org.domain.Person;
import org.gui.Controller;
import org.gui.auth.resources.alerts.OperationAlert;
import org.util.CSSProperties;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ChangeActivityStateController extends Controller implements Initializable {
    private Person user;
    private boolean changeStatusOperation;
    @FXML private Button closeButton;
    @FXML private AnchorPane rootStage;
    @FXML private Label stateLabel;
    @FXML private Label userLabel;
    @FXML private Label systemLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setStyleClass(rootStage, getClass().getResource("../../../../resources/" + CSSProperties.readTheme().getTheme() ).toExternalForm() );
        changeStatusOperation = false;
        if( user != null) {
            userLabel.setText( user.getName() );
            // T-O Ternary Operator here!!
            stateLabel.setText( ( user.getActivityState() == ActivityState.ACTIVE ) ? "Inactivo" : "Activo");
        }
    }

    public void showStage() {
        loadFXMLFile(getClass().getResource("/org/gui/auth/users/administrator/update/change/ChangeActivityStateVista.fxml") , this);
        stage.showAndWait();
    }

    public void setUser(Person user) {
        this.user = user;
    }

    public boolean getChangeStatusOperation() {
        return changeStatusOperation;
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
    protected void cancelButtonPressed(ActionEvent event) {
        stage.close();
    }

    @FXML
    protected void changeActivityStateButtonPressed(ActionEvent event) throws SQLException {
        if( user != null ) {
            stage.close();
            if ( new PersonDAO().changeActivityStateByID( user.getId() ) ) {
                String title = "Cambio de estado de actividad realizado.";
                String contentText = "¡Se ha cambiado el estado de actividad del usuario!";
                OperationAlert.showSuccessfullAlert(title, contentText);
                changeStatusOperation = true;
            } else {
                String title = "No se pudo cambiar el estado de actividad";
                String contentText = "¡No se pudo realizar el cambio de estado del usuario!";
                OperationAlert.showUnsuccessfullAlert(title, contentText);
            }
        } else {
            systemLabel.setText("¡Verifica tus datos!");
        }
    }

}
