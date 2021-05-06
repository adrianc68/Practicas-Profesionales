package org.gui.auth.users.practitioner;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.database.dao.ProjectDAO;
import org.domain.Practitioner;
import org.gui.Controller;
import org.gui.auth.resources.alerts.OperationAlert;
import org.gui.auth.users.practitioner.activity.ActivityController;
import org.gui.auth.users.practitioner.project.myproject.MyProjectController;
import org.gui.auth.users.practitioner.project.selectproject.SelectProjectController;
import org.gui.auth.util.changepassword.ChangePasswordController;
import org.gui.auth.util.theme.SelectThemeController;
import org.util.Auth;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PractitionerController extends Controller implements Initializable{
    private Practitioner practitioner;
    @FXML private Label nameLabel;
    @FXML private Label enrollmentLabel;
    @FXML private Label periodLabel;
    @FXML private Label courseLabel;
    @FXML private AnchorPane rootStage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setStyleClass();
        setInformationToLabel();
    }

    public void showStage() {
        loadFXMLFile( getClass().getResource("/org/gui/auth/users/practitioner/PractitionerVista.fxml"), this );
        stage.showAndWait();
    }

    @FXML
    protected void activitiesButtonPressed(ActionEvent event) {
        ActivityController activityController = new ActivityController(practitioner);
        activityController.showStage();
    }

    @FXML
    protected void logOutButtonPressed(ActionEvent event) {
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
    protected void changePasswordButtonPressed(ActionEvent event) {
        ChangePasswordController changePasswordController = new ChangePasswordController();
        changePasswordController.showStage();
    }

    @FXML
    protected void aspectButtonPressed(ActionEvent event) {
        SelectThemeController selectThemeController = new SelectThemeController();
        selectThemeController.showStage();
        if( selectThemeController.getConfiguredStatusOperation() ) {
            setStyleClass();
        }
    }

    @FXML
    protected void projectButtonPressed(ActionEvent event) {
        getAssignedProjectFromDatabase();
        if( practitioner.getProject() == null ) {
            SelectProjectController selectProjectController = new SelectProjectController(practitioner);
            selectProjectController.showStage();
        } else {
            MyProjectController myProjectController = new MyProjectController( practitioner.getProject() );
            myProjectController.showStage();
        }
    }


    private void getAssignedProjectFromDatabase() {
        try {
            practitioner.setProject( new ProjectDAO().getAssignedProjectByPractitionerID( practitioner.getId() ) );
        } catch (SQLException e) {
            OperationAlert.showLostConnectionAlert();
            Logger.getLogger( PractitionerController.class.getName() ).log(Level.WARNING, null, e);
        }
    }

    private void setInformationToLabel() {
        practitioner = ( (Practitioner) Auth.getInstance().getCurrentUser() );
        if(practitioner != null ) {
            nameLabel.setText( practitioner.getName() );
            enrollmentLabel.setText( practitioner.getEnrollment() );
            periodLabel.setText( practitioner.getCourse().getPeriod() );
            courseLabel.setText( practitioner.getCourse().getName() );
        }
    }

    private void setStyleClass() {
        super.setStyleClass(rootStage);
    }

}

