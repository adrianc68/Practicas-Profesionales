package org.gui.users.administrator.update.remove;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.database.dao.CoordinatorDAO;
import org.database.dao.CourseDAO;
import org.database.dao.ProfessorDAO;
import org.domain.Coordinator;
import org.domain.Course;
import org.domain.Professor;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RemoveObjectController implements Initializable {
    private boolean statusRemoveOperation;
    private Object selected;

    @FXML
    private Label personLabel;

    @FXML
    private TextField confirmationInputField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        personLabel.setText( selected.toString() );
    }

    public RemoveObjectController(Object selected) {
        this.selected = selected;
    }

    public void showStage() {
        FXMLLoader loader = new FXMLLoader( getClass().getResource("/org/gui/users/administrator/update/remove/RemoveObjectVista.fxml") );
        loader.setController(this);
        Parent root = null;
        try{
            root = loader.load();
        } catch(IOException ioe) {
            Logger.getLogger( RemoveObjectController.class.getName() ).log(Level.WARNING, null, ioe);
        }
        Stage removeStage = new Stage();
        removeStage.initModality(Modality.APPLICATION_MODAL);
        removeStage.initStyle(StageStyle.UTILITY);
        removeStage.setScene( new Scene(root) );
        removeStage.showAndWait();
    }

    public boolean getStatusRemoveOperation() {
        return statusRemoveOperation;
    }

    @FXML
    void removeButtonPressed(ActionEvent event) {
        if(confirmationInputField.getText().equals("ELIMINAR")){
            removeObjectFromDatabase();
            closeStage(event);
        }
    }

    private void removeObjectFromDatabase() {
        if(selected instanceof Coordinator) {
            CoordinatorDAO coordinatorDAO = new CoordinatorDAO();
            statusRemoveOperation = coordinatorDAO.removeCoordinatorByID( ( (Coordinator) selected ).getId() );
        } else if(selected instanceof Professor) {
            ProfessorDAO professorDAO = new ProfessorDAO();
            statusRemoveOperation = professorDAO.removeProfessorByID( ( (Professor) selected ).getId() );
        } else {
            CourseDAO courseDAO = new CourseDAO();
            statusRemoveOperation = courseDAO.removeCourseByID( ( (Course) selected).getId() );
        }
    }

    @FXML
    void cancelButtonPressed(ActionEvent event) {
        statusRemoveOperation = false;
        closeStage(event);
    }

    private void closeStage(ActionEvent event) {
        Stage stage = ( (Stage) ( (Node) event.getSource() ).getScene().getWindow() );
        stage.close();
    }

}
