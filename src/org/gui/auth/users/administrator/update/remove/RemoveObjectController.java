package org.gui.auth.users.administrator.update.remove;

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
import org.database.dao.CoordinatorDAO;
import org.database.dao.CourseDAO;
import org.database.dao.ProfessorDAO;
import org.domain.Coordinator;
import org.domain.Course;
import org.domain.Person;
import org.domain.Professor;
import org.gui.auth.resources.alerts.OperationAlert;
import org.util.CSSProperties;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RemoveObjectController implements Initializable {
    private double mousePositionOnX;
    private double mousePositionOnY;
    private boolean statusRemoveOperation;
    private Object object;
    @FXML private Label userLabel;
    @FXML private Label systemLabel;
    @FXML private TextField confirmationTextField;
    @FXML private Button closeButton;
    @FXML private AnchorPane rootStage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setStyleClass();
        statusRemoveOperation = false;
        if(object != null) {
            // T-O Ternary Operator Here!
            userLabel.setText( (object instanceof Course) ? ( ( (Course) object).getName() ) : ( ( (Person) object).getName() ) );
        }
    }

    public void showStage() {
        FXMLLoader loader = new FXMLLoader( getClass().getResource("/org/gui/auth/users/administrator/update/remove/RemoveObjectVista.fxml") );
        loader.setController(this);
        Parent root = null;
        try{
            root = loader.load();
        } catch(IOException ioe) {
            Logger.getLogger( RemoveObjectController.class.getName() ).log(Level.WARNING, null, ioe);
        }
        Stage removeStage = new Stage();
        removeStage.initModality(Modality.APPLICATION_MODAL);
        removeStage.initStyle(StageStyle.TRANSPARENT);
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        removeStage.setScene( scene );
        removeStage.showAndWait();
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public boolean getStatusRemoveOperation() {
        return statusRemoveOperation;
    }

    @FXML
    void removeButtonPressed(ActionEvent event) {
        if(confirmationTextField.getText().equals("ELIMINAR")){
            removeObjectFromDatabase();
            closeButton.fire();
            if(statusRemoveOperation) {
                showSucessfullAlert();
            } else {
                showUnsuccessfullAlert();
            }
        } else {
            systemLabel.setText("¡Verifica los datos!");
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

    private void showSucessfullAlert() {
        String title = "Eliminado correctamente.";
        String contentText = "¡Se ha eliminado correctamente el seleccionado.";
        OperationAlert.showSuccessfullAlert(title, contentText);
    }

    private void showUnsuccessfullAlert() {
        String title = "No se ha podido eliminar";
        String contentText = "¡No se pudo eliminar el seleccionado!";
        OperationAlert.showUnsuccessfullAlert(title, contentText);
    }

    //BAD USER MANAGER >>>>>> FIX THIS!!
    private void removeObjectFromDatabase() {
        if(object instanceof Coordinator) {
            CoordinatorDAO coordinatorDAO = new CoordinatorDAO();
            statusRemoveOperation = coordinatorDAO.removeCoordinatorByID( ( (Coordinator) object).getId() );
        } else if(object instanceof Professor) {
            ProfessorDAO professorDAO = new ProfessorDAO();
            statusRemoveOperation = professorDAO.removeProfessorByID( ( (Professor) object).getId() );
        } else if(object instanceof Course){
            CourseDAO courseDAO = new CourseDAO();
            statusRemoveOperation = courseDAO.removeCourseByID( ( (Course) object).getId() );
        } else {
            systemLabel.setText("¡No se puede eliminar!");
        }
    }

    private void setStyleClass() {
        rootStage.getStylesheets().clear();
        rootStage.getStylesheets().add(getClass().getResource("../../../../resources/" + CSSProperties.readTheme().getTheme() ).toExternalForm() );
    }

}
