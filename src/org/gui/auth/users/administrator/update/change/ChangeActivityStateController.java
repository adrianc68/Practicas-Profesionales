package org.gui.auth.users.administrator.update.change;

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
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.database.dao.PersonDAO;
import org.domain.Person;
import org.gui.auth.resources.alerts.OperationAlert;
import org.gui.auth.util.changepassword.ChangePasswordController;
import org.util.CSSProperties;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChangeActivityStateController implements Initializable {
    private Person user;
    private boolean changeStatusOperation;
    private double mousePositionOnX;
    private double mousePositionOnY;
    @FXML private Button closeButton;
    @FXML private AnchorPane rootStage;
    @FXML private Label stateLabel;
    @FXML private Label userLabel;
    @FXML private Label systemLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setStyleClass();
        changeStatusOperation = false;
        if( user != null) {
            userLabel.setText( user.getName() );
            // T-O Ternary Operator here!!
            stateLabel.setText( ( user.getActivityState().equals("Activo") ) ? "Inactivo" : "Activo");
        }
    }

    public void showStage() {
        FXMLLoader loader = new FXMLLoader( getClass().getResource("/org/gui/auth/users/administrator/update/change/ChangeActivityStateVista.fxml") );
        loader.setController(this);
        Parent root = null;
        try{
            root = loader.load();
        } catch(IOException ioe) {
            Logger.getLogger( ChangePasswordController.class.getName() ).log(Level.SEVERE, null, ioe);
        }
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.showAndWait();
    }

    public void setUser(Person user) {
        this.user = user;
    }

    public boolean getChangeStatusOperation() {
        return changeStatusOperation;
    }

    @FXML
    void changeActivityStateButtonPressed(ActionEvent event) {
        if( user != null ) {
            closeButton.fire();
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

    @FXML
    void cancelButtonPressed(ActionEvent event) {
        Stage stage = ( (Stage) ((Node) event.getSource() ).getScene().getWindow() );
        stage.close();
    }

    private void setStyleClass() {
        rootStage.getStylesheets().clear();
        rootStage.getStylesheets().add(getClass().getResource("../../../../resources/" + CSSProperties.readTheme().getTheme() ).toExternalForm() );
    }

}
