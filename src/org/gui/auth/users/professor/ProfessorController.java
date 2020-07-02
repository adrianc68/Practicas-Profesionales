package org.gui.auth.users.professor;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.domain.Professor;
import org.gui.Controller;
import org.gui.auth.users.professor.activity.ActivityController;
import org.gui.auth.users.professor.practitioner.PractitionerController;
import org.gui.auth.util.changepassword.ChangePasswordController;
import org.gui.auth.util.theme.SelectThemeController;
import org.util.Auth;
import org.util.CSSProperties;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;


public class ProfessorController extends Controller implements Initializable {
    @FXML private Label professorNameLabel;
    @FXML private Label periodLabel;
    @FXML private Label dateLabel;
    @FXML private Label courseLabel;
    @FXML private AnchorPane rootStage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setStyleClass();
        setInformationToFields();
    }

    public void showStage() {
        loadFXMLFile(getClass().getResource("/org/gui/auth/users/professor/ProfessorVista.fxml"), this);
        stage.showAndWait();
    }

    @FXML
    protected void practitionerButtonPressed(ActionEvent event) {
        PractitionerController practitionerController = new PractitionerController();
        practitionerController.showStage();
    }

    @FXML
    protected void activityButtonPressed(ActionEvent event) {
        ActivityController activityController = new ActivityController();
        activityController.showStage();
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

    private void setInformationToFields() {
        Professor user = (Professor) Auth.getInstance().getCurrentUser();
        if(user != null) {
            professorNameLabel.setText( user.getName() );
            periodLabel.setText( user.getCourse().getPeriod() );
            dateLabel.setText( String.valueOf( LocalDate.now() ) );
            courseLabel.setText( user.getCourse().getName() );
        }
    }

    private void setStyleClass() {
        super.setStyleClass(rootStage, getClass().getResource("../../resources/" + CSSProperties.readTheme().getTheme() ).toExternalForm() );
    }

}
