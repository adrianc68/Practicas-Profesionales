package org.gui.users.coordinator.project.updateproject;

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
import org.gui.users.coordinator.project.editionproject.screens.ScreenController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UpdateProjectController implements Initializable {
    private boolean updateOperationstatus;
    private Project selectedProject;
    private AnchorPane firstScreen;
    private AnchorPane secondScreen;
    private AnchorPane thirdScreen;
    private ScreenController screenController;

    @FXML
    private AnchorPane registrationPane;

    @FXML
    private Button addButton;

    public UpdateProjectController(Project selectedProject) {
        this.selectedProject = selectedProject;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        screenController = new ScreenController(selectedProject);
        firstScreen = loadFXMLScreen("/org/gui/users/coordinator/project/editionproject/screens/firstscreen/FirstScreenVista.fxml");
        secondScreen = loadFXMLScreen("/org/gui/users/coordinator/project/editionproject/screens/secondscreen/SecondScreenVista.fxml");
        thirdScreen = loadFXMLScreen("/org/gui/users/coordinator/project/editionproject/screens/thirdscreen/ThirdScreenVista.fxml");
        setScreenAndButtonText(firstScreen, "Siguiente");
    }

    public Project getSelectedProject() {
        return selectedProject;
    }

    public void showStage() {
        FXMLLoader loader = new FXMLLoader( getClass().getResource("/org/gui/users/coordinator/project/updateproject/UpdateProjectVista.fxml") );
        loader.setController(this);
        Parent root = null;
        try{
            root = loader.load();
        } catch(IOException ioe) {
            Logger.getLogger( UpdateProjectController.class.getName() ).log(Level.WARNING, null, ioe);
        }
        Stage udpateProject = new Stage();
        udpateProject.setScene( new Scene(root) );
        udpateProject.showAndWait();
    }

    public boolean getUpdateOperationStatus() {
        return updateOperationstatus;
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
        updateOperationstatus = false;
        closeStage(event);
    }

    @FXML
    void nextButtonPressed(ActionEvent event) {
        if(registrationPane.getChildren().get(0) == thirdScreen) {
            if( screenController.isInputDataValid() ) {
                updateProjectInDatabase();
                closeStage(event);
            }
        } else if(registrationPane.getChildren().get(0) == secondScreen) {
            setScreenAndButtonText(thirdScreen, "Actualizar");
        } else {
            setScreenAndButtonText(secondScreen, "Siguiente");
        }
    }

    private AnchorPane loadFXMLScreen(String sourcePath) {
        AnchorPane pane = null;
        FXMLLoader loader = new FXMLLoader( getClass().getResource(sourcePath) );
        loader.setController(screenController);
        try {
            pane = loader.load();
        } catch (IOException e) {
            Logger.getLogger(UpdateProjectController.class.getName()).log(Level.WARNING, null, e);
        }
        return pane;
    }

    private void setScreenAndButtonText(AnchorPane anchorPane, String buttonText) {
        addButton.setText(buttonText);
        registrationPane.getChildren().clear();
        registrationPane.getChildren().add(anchorPane);
    }

    private void updateProjectInDatabase() {
        int idProject = selectedProject.getId();
        selectedProject = screenController.getNewProject();
        selectedProject.setId(idProject);
        ProjectDAO projectDAO = new ProjectDAO();
        updateOperationstatus = projectDAO.updateProjectInformation(selectedProject);
    }

    private void closeStage(ActionEvent event) {
        Stage stage = ( (Stage) ( (Node) event.getSource() ).getScene().getWindow() );
        stage.close();
    }

}
