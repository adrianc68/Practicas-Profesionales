package org.gui.auth.users.coordinator.project.registerproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.database.dao.ProjectDAO;
import org.domain.Project;
import org.gui.Controller;
import org.gui.auth.resources.alerts.OperationAlert;
import org.gui.auth.users.administrator.update.add.addcoordinator.AddCoordinatorController;
import org.util.CSSProperties;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.gui.auth.users.coordinator.project.editionproject.screens.ScreenController;

public class RegisterProjectController extends Controller implements Initializable {
    private boolean addOperationStatus;
    private Project newProject;
    private AnchorPane firstScreen;
    private AnchorPane secondScreen;
    private AnchorPane thirdScreen;
    private ScreenController screenController;
    @FXML private AnchorPane registrationPane;
    @FXML private Button addButton;
    @FXML private Button closeButton;
    @FXML private AnchorPane rootStage;
    @FXML private RadioButton firstLoaderRadioButton;
    @FXML private RadioButton secondLoaderRadioButton;
    @FXML private RadioButton thirdLoaderRadioButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setStyleClass(rootStage, getClass().getResource("../../../../resources/" + CSSProperties.readTheme().getTheme() ).toExternalForm());
        initScreens();
        firstLoaderRadioButton.fire();
        addOperationStatus = false;
    }

    public void showStage() {
        loadFXMLFile(getClass().getResource("/org/gui/auth/users/coordinator/project/registerproject/RegisterProjectVista.fxml"), this);
        stage.showAndWait();
    }

    public Project getNewProject() {
        return newProject;
    }

    public boolean getAddOperationStatus() {
        return addOperationStatus;
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
        setScreenAndButtonText(thirdScreen, "Registrar");
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

    // BAD SCREEN MANAGER ??? <--- FIX THIS
    @FXML
    protected void nextButtonPressed(ActionEvent event) {
        if(registrationPane.getChildren().get(0) == thirdScreen) {
            if(screenController.verifyInputData() ) {
                addProjectToDatabase();
                closeButton.fire();
            }
        } else if(registrationPane.getChildren().get(0) == secondScreen) {
            thirdLoaderRadioButton.fire();
        } else {
            secondLoaderRadioButton.fire();
        }
    }

    private void addProjectToDatabase() {
        newProject = screenController.getNewProject();
        try {
            newProject.setId( new ProjectDAO().addProject(newProject) );
        } catch (SQLException sqlException) {
            OperationAlert.showLostConnectionAlert();
            Logger.getLogger( RegisterProjectController.class.getName() ).log(Level.WARNING, null, sqlException);
        }
        addOperationStatus = (newProject.getId() != 0);
        if(addOperationStatus) {
            String title = "Se agregó el proyecto.";
            String contentText = "¡Se ha agregado al sistema el nuevo proyecto.";
            OperationAlert.showSuccessfullAlert(title, contentText);
        }
    }

    private void setScreenAndButtonText(AnchorPane anchorPane, String buttonText) {
        addButton.setText(buttonText);
        registrationPane.getChildren().clear();
        registrationPane.getChildren().add(anchorPane);
    }

    private void initScreens() {
        screenController = new ScreenController();
        firstScreen = loadSpecifiedScreen("/org/gui/auth/users/coordinator/project/editionproject/screens/firstscreen/FirstScreenVista.fxml");
        secondScreen = loadSpecifiedScreen("/org/gui/auth/users/coordinator/project/editionproject/screens/secondscreen/SecondScreenVista.fxml");
        thirdScreen = loadSpecifiedScreen("/org/gui/auth/users/coordinator/project/editionproject/screens/thirdscreen/ThirdScreenVista.fxml");
    }

    private AnchorPane loadSpecifiedScreen(String sourcePath) {
        AnchorPane pane = null;
        FXMLLoader loader = new FXMLLoader( getClass().getResource(sourcePath) );
        loader.setController(screenController);
        try {
            pane = loader.load();
        } catch (IOException e) {
            Logger.getLogger( RegisterProjectController.class.getName() ).log(Level.WARNING, null, e);
        }
        pane.getStylesheets().clear();
        pane.getStylesheets().add( getClass().getResource("../../../../resources/" + CSSProperties.readTheme().getTheme() ).toExternalForm() );
        return pane;
    }

}
