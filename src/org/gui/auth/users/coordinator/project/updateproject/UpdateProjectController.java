package org.gui.auth.users.coordinator.project.updateproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.database.dao.ProjectDAO;
import org.domain.Project;
import org.gui.auth.users.coordinator.project.editionproject.screens.ScreenController;
import org.util.CSSProperties;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.gui.auth.resources.alerts.OperationAlert.showSuccessfullAlert;
import static org.gui.auth.resources.alerts.OperationAlert.showUnsuccessfullAlert;

public class UpdateProjectController implements Initializable {
    private double mousePositionOnX;
    private double mousePositionOnY;
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
        if(selectedProject != null) {
            screenController = new ScreenController(selectedProject);
        } else {
            screenController = new ScreenController();
        }
        setStyleClass();
        initScreens();
        firstLoaderRadioButton.fire();
        updateOperationstatus = false;
    }

    public void showStage() {
        FXMLLoader loader = new FXMLLoader( getClass().getResource("/org/gui/auth/users/coordinator/project/updateproject/UpdateProjectVista.fxml") );
        loader.setController(this);
        Parent root = null;
        try{
            root = loader.load();
        } catch(IOException ioe) {
            Logger.getLogger( UpdateProjectController.class.getName() ).log(Level.WARNING, null, ioe);
        }
        Stage stage = new Stage();
        stage.initStyle(StageStyle.TRANSPARENT);
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
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

    // BAD SCREEN MANAGER ??? <--- FIX THIS
    @FXML
    void nextButtonPressed(ActionEvent event) {
        if(projectsPane.getChildren().get(0) == thirdScreen) {
            if( screenController.verifyInputData() ) {
                updateProjectInDatabase();
                closeButton.fire();
            }
        } else if(projectsPane.getChildren().get(0) == secondScreen) {
            thirdLoaderRadioButton.fire();
        } else {
            secondLoaderRadioButton.fire();
        }
    }

    @FXML
    void loadFirstScreenPressed(ActionEvent event) {
        setScreenAndButtonText(firstScreen, "Siguiente");
    }

    @FXML
    void loadSecondScreenPressed(ActionEvent event) {
        setScreenAndButtonText(secondScreen, "Siguiente");
    }

    @FXML
    void loadThirdScreenPressed(ActionEvent event) {
        setScreenAndButtonText(thirdScreen, "Actualizar");
    }

    @FXML
    void cancelButtonPressed(ActionEvent event) {
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


    private void updateProjectInDatabase() {
        int idProject = selectedProject.getId();
        selectedProject = screenController.getNewProject();
        selectedProject.setId(idProject);
        ProjectDAO projectDAO = new ProjectDAO();
        updateOperationstatus = projectDAO.updateProjectInformation(selectedProject);
        if(updateOperationstatus) {
            String title = "Proyecto actualizado";
            String contentText = "¡Se actualizó correctamente el proyecto!";
            showSuccessfullAlert(title, contentText);
        } else {
            String title = "No se pudo actualizar el proyecto";
            String contentText = "¡No se pudo eliminar el proyecto!";
            showUnsuccessfullAlert(title, contentText);
        }
    }

    private void setScreenAndButtonText(AnchorPane anchorPane, String buttonText) {
        nextButton.setText(buttonText);
        projectsPane.getChildren().clear();
        projectsPane.getChildren().add(anchorPane);
    }

    private void initScreens() {
        firstScreen = loadSpecifiedScreen("/org/gui/auth/users/coordinator/project/editionproject/screens/firstscreen/FirstScreenVista.fxml");
        setStyleClassToScreens(firstScreen);
        secondScreen = loadSpecifiedScreen("/org/gui/auth/users/coordinator/project/editionproject/screens/secondscreen/SecondScreenVista.fxml");
        setStyleClassToScreens(secondScreen);
        thirdScreen = loadSpecifiedScreen("/org/gui/auth/users/coordinator/project/editionproject/screens/thirdscreen/ThirdScreenVista.fxml");
        setStyleClassToScreens(thirdScreen);
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
        return pane;
    }

    private void setStyleClassToScreens(AnchorPane screen) {
        screen.getStylesheets().clear();
        screen.getStylesheets().add( getClass().getResource("../../../../resources/" + CSSProperties.readTheme().getTheme() ).toExternalForm() );
    }

    private void setStyleClass() {
        rootStage.getStylesheets().clear();
        rootStage.getStylesheets().add( getClass().getResource("../../../../resources/" + CSSProperties.readTheme().getTheme() ).toExternalForm() );
    }

}
