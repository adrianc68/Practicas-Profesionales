package org.gui.auth.users.practitioner;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.database.dao.ProjectDAO;
import org.domain.Practitioner;
import org.gui.auth.resources.alerts.OperationAlert;
import org.gui.auth.users.administrator.update.remove.RemoveObjectController;
import org.gui.auth.users.practitioner.activity.ActivityController;
import org.gui.auth.users.practitioner.project.myproject.MyProjectController;
import org.gui.auth.users.practitioner.project.selectproject.SelectProjectController;
import org.gui.auth.util.changepassword.ChangePasswordController;
import org.util.Auth;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PractitionerController implements Initializable{
    private Practitioner practitioner;

    @FXML
    private Label nameLabel;

    @FXML
    private Label enrollmentLabel;

    @FXML
    private Label periodLabel;

    @FXML
    private Label courseLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        practitioner = ( (Practitioner) Auth.getInstance().getCurrentUser() );
        try {
            practitioner.setProject( new ProjectDAO().getAssignedProjectByPractitionerID( practitioner.getId() ));
        } catch (SQLException sqlException) {
            OperationAlert.showLostConnectionAlert();
            Logger.getLogger( PractitionerController.class.getName() ).log(Level.WARNING, null, sqlException);
        }
        nameLabel.setText( practitioner.getName() );
        enrollmentLabel.setText( practitioner.getEnrollment() );
        periodLabel.setText( practitioner.getCourse().getPeriod() );
        courseLabel.setText( practitioner.getCourse().getName() );
    }

    public void showStage() {
        FXMLLoader loader = new FXMLLoader( getClass().getResource("/org/gui/auth/users/practitioner/PractitionerVista.fxml") );
        loader.setController(this);
        Parent root = null;
        try{
            root = loader.load();
        } catch(IOException ioe) {
            Logger.getLogger( PractitionerController.class.getName() ).log(Level.WARNING, null, ioe);
        }
        Stage coordinator = new Stage();
        coordinator.setScene( new Scene(root) );
        coordinator.showAndWait();
    }

    @FXML
    void activitiesButtonPressed(ActionEvent event) {
        ActivityController activityController = new ActivityController();
        activityController.showStage();
    }

    @FXML
    void logOutButtonPressed(ActionEvent event) {
        closeStage(event);
    }

    @FXML
    void myProjectButtonPressed(ActionEvent event) {
        if( practitioner.getProject() == null ) {
            SelectProjectController selectProjectController = new SelectProjectController();
            selectProjectController.showStage();
        } else {
            MyProjectController myProjectController = new MyProjectController();
            myProjectController.showStage();
        }
    }

    @FXML
    void changePasswordButtonPressed(ActionEvent event) {
        ChangePasswordController changePasswordController = new ChangePasswordController();
        changePasswordController.showStage();
    }

    private void closeStage(ActionEvent event) {
        Stage stage = ( (Stage) ( (Node) event.getSource() ).getScene().getWindow() );
        stage.close();
    }

}

