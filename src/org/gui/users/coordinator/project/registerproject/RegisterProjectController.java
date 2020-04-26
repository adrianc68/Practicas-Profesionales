package org.gui.users.coordinator.project.registerproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.database.dao.ProjectDAO;
import org.domain.Project;
import org.gui.users.coordinator.project.registerproject.screens.ScreenRegisterController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RegisterProjectController implements Initializable {
    private boolean status;
    private Project newProject;
    private AnchorPane firstScreen;
    private AnchorPane secondScreen;
    private AnchorPane thirdScreen;
    private ScreenRegisterController screenRegisterController;

    @FXML
    private AnchorPane registrationPane;

    @FXML
    private Button addButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        screenRegisterController = new ScreenRegisterController();
        firstScreen = loadFXMLScreen("/org/gui/users/coordinator/project/registerproject/screens/firstscreen/projectRegisterFirstScene.fxml");
        secondScreen = loadFXMLScreen("/org/gui/users/coordinator/project/registerproject/screens/secondscreen/RegisterSecondVista.fxml");
        thirdScreen = loadFXMLScreen("/org/gui/users/coordinator/project/registerproject/screens/thirdscreen/ThirdScreenVista.fxml");
        setScreenAndButtonText(firstScreen, "Siguiente");
    }

    public Project getNewProject() {
        return newProject;
    }

    public void showStage() {
        FXMLLoader loader = new FXMLLoader( getClass().getResource("/org/gui/users/coordinator/project/registerproject/RegisterProjectVista.fxml") );
        loader.setController(this);
        Parent root = null;
        try{
            root = loader.load();
        } catch(IOException ioe) {
            Logger.getLogger( RegisterProjectController.class.getName() ).log(Level.WARNING, null, ioe);
        }
        Stage registerProject = new Stage();
        registerProject.setScene( new Scene(root) );
        registerProject.showAndWait();
    }

    public boolean getStatusAddOperation() {
        return status;
    }

    @FXML
    void loadFirstScreenPressed(MouseEvent event) {
        setScreenAndButtonText(firstScreen, "Siguiente");
    }

    @FXML
    void loadSecondScreenPressed(MouseEvent event) {
        setScreenAndButtonText(secondScreen, "Siguiente");
    }

    @FXML
    void loadThirdScreenPressed(MouseEvent event) {
        setScreenAndButtonText(thirdScreen, "Registrar");
    }

    @FXML
    void cancelButtonPressed(ActionEvent event) {
        status = false;
        closeStage(event);
    }

    @FXML
    void nextButtonPressed(ActionEvent event) {
        if(registrationPane.getChildren().get(0) == thirdScreen) {
            addProjectToDatabase();
            closeStage(event);
        } else if(registrationPane.getChildren().get(0) == secondScreen) {
                setScreenAndButtonText(thirdScreen, "Registrar");
            } else {
                setScreenAndButtonText(secondScreen, "Siguiente");
            }
    }

    private void addProjectToDatabase() {
        newProject = screenRegisterController.getNewProject();
        int idProject = 0;
        idProject = new ProjectDAO().addProject(newProject);
        newProject.setId(idProject);
        status = (idProject == 0) ? false : true ;
    }

    private void setScreenAndButtonText(AnchorPane anchorPane, String buttonText) {
        addButton.setText(buttonText);
        registrationPane.getChildren().clear();
        registrationPane.getChildren().add(anchorPane);
    }

    private AnchorPane loadFXMLScreen(String sourcePath) {
        AnchorPane pane = null;
        FXMLLoader loader = new FXMLLoader( getClass().getResource(sourcePath) );
        loader.setController(screenRegisterController);
        try {
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pane;
    }

    private void closeStage(ActionEvent event) {
        Stage stage = ( (Stage) ( (Node) event.getSource() ).getScene().getWindow() );
        stage.close();
    }

}
