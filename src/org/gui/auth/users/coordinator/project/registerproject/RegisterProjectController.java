package org.gui.auth.users.coordinator.project.registerproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.database.dao.ProjectDAO;
import org.domain.Project;
import org.util.CSSProperties;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.gui.auth.resources.alerts.OperationAlert.showSuccessfullAlert;
import static org.gui.auth.resources.alerts.OperationAlert.showUnsuccessfullAlert;
import org.gui.auth.users.coordinator.project.editionproject.screens.ScreenController;

public class RegisterProjectController implements Initializable {
    private double mousePositionOnX;
    private double mousePositionOnY;
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
        setStyleClass();
        initScreens();
        firstLoaderRadioButton.fire();
        addOperationStatus = false;
    }

    public void showStage() {
        FXMLLoader loader = new FXMLLoader( getClass().getResource("/org/gui/auth/users/coordinator/project/registerproject/RegisterProjectVista.fxml") );
        loader.setController(this);
        Parent root = null;
        try{
            root = loader.load();
        } catch(IOException ioe) {
            Logger.getLogger( RegisterProjectController.class.getName() ).log(Level.WARNING, null, ioe);
        }
        Stage stage = new Stage();
        stage.initStyle(StageStyle.TRANSPARENT);
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.showAndWait();
    }

    public Project getNewProject() {
        return newProject;
    }

    public boolean getAddOperationStatus() {
        return addOperationStatus;
    }

    // BAD SCREEN MANAGER ??? <--- FIX THIS
    @FXML
    void nextButtonPressed(ActionEvent event) {
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
        setScreenAndButtonText(thirdScreen, "Registrar");
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

    private void addProjectToDatabase() {
        newProject = screenController.getNewProject();
        int idProject = 0;
        idProject = new ProjectDAO().addProject(newProject);
        newProject.setId(idProject);
        if(idProject != 0) {
            addOperationStatus = true;
            String title = "Se agregó el proyecto.";
            String contentText = "¡Se ha agregado al sistema el nuevo proyecto.";
            showSuccessfullAlert(title, contentText);
        } else {
            String title = "No se pudo agregar el proyecto";
            String contentText = "¡No se pudo agregar el nuevo proyecto al sistema!";
            showUnsuccessfullAlert(title, contentText);
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
        setStyleClassToScreens(firstScreen);
        secondScreen = loadSpecifiedScreen("/org/gui/auth/users/coordinator/project/editionproject/screens/secondscreen/SecondScreenVista.fxml");
        setStyleClassToScreens(secondScreen);
        thirdScreen = loadSpecifiedScreen("/org/gui/auth/users/coordinator/project/editionproject/screens/thirdscreen/ThirdScreenVista.fxml");
        setStyleClassToScreens(thirdScreen);
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
