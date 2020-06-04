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
import org.util.CSSProperties;
import java.net.URL;
import java.util.ResourceBundle;

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
        setStyleClass(rootStage, getClass().getResource("../../../../resources/" + CSSProperties.readTheme().getTheme() ).toExternalForm());
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
    protected void removeButtonPressed(ActionEvent event) {
        if(confirmationTextField.getText().equals("ELIMINAR")){
            removeObjectFromDatabase();
            stage.close();
            if(statusRemoveOperation) {
                showSucessfullAlert();
            } else {
                showUnsuccessfullAlert();
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

}
