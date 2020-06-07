package org.gui.auth.users.administrator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import org.domain.Person;
import org.gui.Controller;
import org.gui.auth.util.changepassword.ChangePasswordController;
import org.gui.auth.users.administrator.update.ManagementController;
import org.gui.auth.util.theme.SelectThemeController;
import org.util.Auth;
import org.util.CSSProperties;

public class AdministratorController extends Controller implements Initializable {
    @FXML private Label nameLabel;
    @FXML private Label periodLabel;
    @FXML private Label dateLabel;
    @FXML private Label courseLabel;
    @FXML private AnchorPane rootStage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setStyleClass();
        Person user = Auth.getInstance().getCurrentUser();
        if( user != null) {
            nameLabel.setText( user.getName() );
            periodLabel.setText( user.getCourse().getPeriod() );
            dateLabel.setText( LocalDate.now().toString() );
            courseLabel.setText( user.getCourse().getName() );
        }
    }

    public void showStage() {
        super.loadFXMLFile(getClass().getResource("/org/gui/auth/users/administrator/AdministradorVista.fxml"), this);
        stage.showAndWait();
    }

    @FXML
    protected void managementButtonPressed(ActionEvent event) {
        ManagementController managementController = new ManagementController();
        managementController.showStage();
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

    private void setStyleClass() {
        rootStage.getStylesheets().clear();
        rootStage.getStylesheets().add( getClass().getResource("../../resources/" + CSSProperties.readTheme().getTheme() ).toExternalForm() );
    }

}
