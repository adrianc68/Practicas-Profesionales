package org.gui.auth.users.coordinator.project.updateproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.database.dao.ProjectDAO;
import org.domain.Project;
import org.gui.Controller;
import org.gui.auth.resources.alerts.OperationAlert;
import org.gui.auth.users.coordinator.project.editionproject.screens.ScreenController;
import org.util.CSSProperties;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.gui.auth.resources.alerts.OperationAlert.showSuccessfullAlert;

public class UpdateProjectController extends Controller implements Initializable {
    private boolean updateOperationstatus;
    private Project selectedProject;
    private AnchorPane firstScreen;
    private AnchorPane secondScreen;
    private AnchorPane thirdScreen;
    private ScreenController screenController;
    @FXML private AnchorPane projectsPane;
    @FXML private Button nextButton;
    @FXML private Button closeButton;
    @FXML private AnchorPane rootStage;
    @FXML private ToggleGroup loader;
    @FXML private RadioButton firstLoaderRadioButton;
    @FXML private RadioButton secondLoaderRadioButton;
    @FXML private RadioButton thirdLoaderRadioButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setStyleClass(rootStage,  getClass().getResource("../../../../resources/" + CSSProperties.readTheme().getTheme() ).toExternalForm() );
        if(selectedProject != null) {
            screenController = new ScreenController(selectedProject);
        } else {
            screenController = new ScreenController();
        }
        initScreens();
        firstLoaderRadioButton.fire();
        updateOperationstatus = false;
    }

    public void showStage() {
        loadFXMLFile(getClass().getResource("/org/gui/auth/users/coordinator/project/updateproject/UpdateProjectVista.fxml"), this);
        stage.showAndWait();
    }

    public void setSelectedProject(Project selectedProject) {
        this.selectedProject = selectedProject;
    }

    public Project getSelectedProject() {
        return selectedProject;
    }

    public boolean getUpdateOperationStatus() {
        return updateOperationstatus;
    }

    @FXML
    protected void loadFirstScreenPressed(ActionEvent event) {
        setScreenAndButtonText(firstScreen, "Siguiente");
    }

    @FXML
    protected void loadSecondScreenPressed(ActionEvent event) {
        setScreenAndButtonText(secondScreen, "Siguiente");
    }

    @FXML
    protected void loadThirdScreenPressed(ActionEvent event) {
        setScreenAndButtonText(thirdScreen, "Actualizar");
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

    // BAD SCREEN MANAGER <--- FIX THIS
    @FXML
    protected void nextButtonPressed(ActionEvent event) {
        if(projectsPane.getChildren().get(0) == thirdScreen) {
            if( screenController.verifyInputData() ) {
                updateProjectInDatabase();
                stage.close();
            }
        } else if(projectsPane.getChildren().get(0) == secondScreen) {
            thirdLoaderRadioButton.fire();
        } else {
            secondLoaderRadioButton.fire();
        }
    }

    private void setScreenAndButtonText(AnchorPane anchorPane, String buttonText) {
        nextButton.setText(buttonText);
        projectsPane.getChildren().clear();
        projectsPane.getChildren().add(anchorPane);
    }

    private void updateProjectInDatabase() {
        int idProject = selectedProject.getId();
        selectedProject = screenController.getNewProject();
        selectedProject.setId(idProject);
        try {
            updateOperationstatus = new ProjectDAO().updateProjectInformation(selectedProject);
        } catch (SQLException sqlException) {
            OperationAlert.showLostConnectionAlert();
            Logger.getLogger( UpdateProjectController.class.getName() ).log(Level.WARNING, null, sqlException);
        }
        if(updateOperationstatus) {
            String title = "Proyecto actualizado";
            String contentText = "¡Se actualizó correctamente el proyecto!";
            showSuccessfullAlert(title, contentText);
        }
    }

    private void initScreens() {
        firstScreen = loadSpecifiedScreen("/org/gui/auth/users/coordinator/project/editionproject/screens/firstscreen/FirstScreenVista.fxml");
        secondScreen = loadSpecifiedScreen("/org/gui/auth/users/coordinator/project/editionproject/screens/secondscreen/SecondScreenVista.fxml");
        thirdScreen = loadSpecifiedScreen("/org/gui/auth/users/coordinator/project/editionproject/screens/thirdscreen/ThirdScreenVista.fxml");
        if(selectedProject != null) {
            screenController.setProjectInformationToFields();
        }
    }

    private AnchorPane loadSpecifiedScreen(String sourcePath) {
        AnchorPane pane = null;
        FXMLLoader loader = new FXMLLoader( getClass().getResource(sourcePath) );
        loader.setController(screenController);
        try {
            pane = loader.load();
        } catch (IOException e) {
            Logger.getLogger( UpdateProjectController.class.getName() ).log(Level.WARNING, null, e);
        }
        pane.getStylesheets().clear();
        pane.getStylesheets().add( getClass().getResource("../../../../resources/" + CSSProperties.readTheme().getTheme() ).toExternalForm() );
        return pane;
    }

}
