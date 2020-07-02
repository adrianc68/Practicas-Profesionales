package org.gui.auth.users.practitioner;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.domain.Practitioner;
import org.gui.Controller;
import org.gui.auth.users.practitioner.activity.AddActivityController;
import org.gui.auth.users.practitioner.project.selectproject.SelectProjectController;
import org.gui.auth.util.changepassword.ChangePasswordController;
import org.gui.auth.util.theme.SelectThemeController;
import org.util.Auth;
import org.util.CSSProperties;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class PractitionerController extends Controller implements Initializable{
    @FXML private Label practitionerNameLabel;
    @FXML private Label periodLabel;
    @FXML private Label dateLabel;
    @FXML private Label courseLabel;
    @FXML private Label systemLabel;
    @FXML private AnchorPane rootStage;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setStyleClass();
        setInformationToFields();
    }

    public void showStage() {
        loadFXMLFile(getClass().getResource("/org/gui/auth/users/practitioner/PractitionerVista.fxml"), this);
        stage.showAndWait();
    }

    @FXML
    protected void myProjectButtonPressed(ActionEvent event) {
        if(((Practitioner) Auth.getInstance().getCurrentUser()).getSelectedProjects() == null) {
            SelectProjectController selectProjectController = new SelectProjectController();
            selectProjectController.showStage();
        }else{
            systemLabel.setText("Ya has seleccionado tus proyectos espere a que se le asigne uno");
        }
    }

    @FXML
    protected void activityButtonPressed(ActionEvent event) {
        AddActivityController addActivityController = new AddActivityController();
        addActivityController.showStage();
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
        Practitioner user = (Practitioner) Auth.getInstance().getCurrentUser();
        if(user != null) {
            practitionerNameLabel.setText( user.getName() );
            periodLabel.setText( user.getCourse().getPeriod() );
            dateLabel.setText( String.valueOf( LocalDate.now() ) );
            courseLabel.setText( user.getCourse().getName() );
        }
    }

    private void setStyleClass() {
        super.setStyleClass(rootStage, getClass().getResource("../../resources/" + CSSProperties.readTheme().getTheme() ).toExternalForm() );
    }

}
