package org.gui.auth.users.professor;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import org.domain.Professor;
import org.gui.Controller;
import org.gui.auth.users.professor.activity.ActivityController;
import org.gui.auth.users.professor.practitioner.PractitionerController;
import org.gui.auth.util.changepassword.ChangePasswordController;
import org.gui.auth.util.theme.SelectThemeController;
import org.util.Auth;

public class ProfessorController extends Controller implements Initializable {
    @FXML private Label nameLabel;
    @FXML private Label periodLabel;
    @FXML private Label dateLabel;
    @FXML private Label courseLabel;
    @FXML private AnchorPane rootStage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setStyleClass();
        setPractitionerInformationToLabels();
    }

    private void setPractitionerInformationToLabels() {
        Professor practitioner = (Professor) Auth.getInstance().getCurrentUser();
        if(practitioner != null) {
            nameLabel.setText( practitioner.getName() );
            dateLabel.setText(LocalDate.now().toString() );
            courseLabel.setText( practitioner.getCourse().getName() );
            periodLabel.setText( practitioner.getCourse().getPeriod() );
        }
    }


    public void showStage() {
        loadFXMLFile( getClass().getResource("/org/gui/auth/users/professor/ProfessorVista.fxml"), this);
        stage.showAndWait();
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
    protected void activityButtonPressed(ActionEvent event) {
        ActivityController activityController = new ActivityController();
        activityController.showStage();
    }

    @FXML
    protected void practitionerButtonPressed(ActionEvent event) {
        PractitionerController practitionerController = new PractitionerController();
        practitionerController.showStage();
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
    protected void changePasswordButtonPressed(ActionEvent event) {
        ChangePasswordController changePasswordController = new ChangePasswordController();
        changePasswordController.showStage();
    }

    private void setStyleClass() {
        super.setStyleClass(rootStage);
    }

}
