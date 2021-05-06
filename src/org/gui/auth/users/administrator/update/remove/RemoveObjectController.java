package org.gui.auth.users.administrator.update.remove;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import org.database.dao.CoordinatorDAO;
import org.database.dao.CourseDAO;
import org.database.dao.ProfessorDAO;
import org.domain.Coordinator;
import org.domain.Course;
import org.domain.Person;
import org.domain.Professor;
import org.gui.Controller;
import org.gui.auth.resources.alerts.OperationAlert;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RemoveObjectController extends Controller implements Initializable {
    private boolean statusRemoveOperation;
    private Object object;
    @FXML private Label userLabel;
    @FXML private Label systemLabel;
    @FXML private TextField confirmationTextField;
    @FXML private Button closeButton;
    @FXML private AnchorPane rootStage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setStyleClass(rootStage);
        statusRemoveOperation = false;
        if(object != null) {
            // T-O Ternary Operator Here!
            userLabel.setText( (object instanceof Course) ? ( ( (Course) object).getName() ) : ( ( (Person) object).getName() ) );
        }
    }

    public void showStage() {
        loadFXMLFile(getClass().getResource("/org/gui/auth/users/administrator/update/remove/RemoveObjectVista.fxml"), this);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public boolean getStatusRemoveOperation() {
        return statusRemoveOperation;
    }

    @FXML
    protected void cancelButtonPressed(ActionEvent event) {
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

    @FXML
    protected void removeButtonPressed(ActionEvent event) throws SQLException {
        if( confirmationTextField.getText().equals("ELIMINAR") ){
            removeObjectFromDatabase();
            if(statusRemoveOperation) {
                stage.close();
                showSucessfullAlert();
            }
        } else {
            systemLabel.setText("¡Verifica los datos!");
        }
    }

    private void showSucessfullAlert() {
        String title = "Eliminado correctamente.";
        String contentText = "¡Se ha eliminado correctamente el seleccionado.";
        OperationAlert.showSuccessfullAlert(title, contentText);
    }

    private void removeObjectFromDatabase() {
        if(object instanceof Coordinator) {
            removeCoordinator();
        } else if(object instanceof Professor) {
            removeProfessor();
        } else if(object instanceof Course){
            removeCourse();
        } else {
            systemLabel.setText("¡No se puede eliminar!");
        }
    }

    private void removeCoordinator() {
        CoordinatorDAO coordinatorDAO = new CoordinatorDAO();
        try {
            statusRemoveOperation = coordinatorDAO.removeCoordinatorByID( ( (Coordinator) object).getId() );
        } catch (SQLException sqlException) {
            OperationAlert.showLostConnectionAlert();
            Logger.getLogger( RemoveObjectController.class.getName() ).log(Level.WARNING, null, sqlException);
        }
    }

    private void removeProfessor() {
        ProfessorDAO professorDAO = new ProfessorDAO();
        try {
            statusRemoveOperation = professorDAO.removeProfessorByID( ( (Professor) object).getId() );
        } catch (SQLException sqlException) {
            OperationAlert.showLostConnectionAlert();
            Logger.getLogger( RemoveObjectController.class.getName() ).log(Level.WARNING, null, sqlException);
        }
    }

    private void removeCourse() {
        CourseDAO courseDAO = new CourseDAO();
        try {
            statusRemoveOperation = courseDAO.removeCourseByID( ( (Course) object).getId() );
        } catch (SQLException sqlException) {
            OperationAlert.showLostConnectionAlert();
            Logger.getLogger( RemoveObjectController.class.getName() ).log(Level.WARNING, null, sqlException);
        }
    }

}
