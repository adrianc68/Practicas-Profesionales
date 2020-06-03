package org.gui.auth.users.coordinator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.domain.Coordinator;
import org.gui.auth.users.coordinator.practitioner.PractitionerController;
import org.gui.auth.users.coordinator.project.ProjectController;
import org.gui.auth.util.changepassword.ChangePasswordController;
import org.gui.auth.util.theme.SelectThemeController;
import org.util.Auth;
import org.util.CSSProperties;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CoordinatorController implements Initializable {
    private double mousePositionOnX;
    private double mousePositionOnY;
    @FXML private Label coordinatorNameLabel;
    @FXML private Label periodLabel;
    @FXML private Label dateLabel;
    @FXML private Label courseLabel;
    @FXML private AnchorPane rootStage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setStyleClass();
        setInformationToFields();
    }

    private void setInformationToFields() {
        Coordinator user = (Coordinator) Auth.getInstance().getUser();
        if(user != null) {
            coordinatorNameLabel.setText( user.getName() );
            periodLabel.setText( user.getCourse().getPeriod() );
            dateLabel.setText( String.valueOf( LocalDate.now() ) );
            courseLabel.setText( user.getCourse().getName() );
        }
    }

    public void showStage() {
        FXMLLoader loader = new FXMLLoader( getClass().getResource("/org/gui/auth/users/coordinator/CoordinatorVista.fxml") );
        loader.setController(this);
        Parent root = null;
        try{
            root = loader.load();
        } catch(IOException ioe) {
            Logger.getLogger( CoordinatorController.class.getName() ).log(Level.WARNING, null, ioe);
        }
        Stage stage = new Stage();
        stage.initStyle(StageStyle.TRANSPARENT);
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.showAndWait();
    }

    @FXML
    void practitionerButtonPressed(ActionEvent event) {
        PractitionerController practitionerController = new PractitionerController();
        practitionerController.showStage();
    }

    @FXML
    void projectButtonPressed(ActionEvent event) {
        ProjectController projectController = new ProjectController();
        projectController.showStage();
    }

    @FXML
    void changePasswordButtonPressed(ActionEvent event) {
        ChangePasswordController changePasswordController = new ChangePasswordController();
        changePasswordController.showStage();
    }

    @FXML
    void aspectButtonPressed(ActionEvent event) {
        SelectThemeController selectThemeController = new SelectThemeController();
        selectThemeController.showStage();
        if( selectThemeController.getConfiguredStatusOperation() ) {
            setStyleClass();
        }
    }

    @FXML
    void logOutButtonPressed(ActionEvent event) {
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

    private void setStyleClass() {
        rootStage.getStylesheets().clear();
        rootStage.getStylesheets().add( getClass().getResource("../../resources/" + CSSProperties.readTheme().getTheme() ).toExternalForm() );
    }

}
