package org.gui.auth.users.administrator.update.add;

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
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.util.CSSProperties;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import org.gui.auth.users.administrator.update.add.addcoordinator.AddCoordinatorController;
import org.gui.auth.users.administrator.update.add.addcourse.AddCourseController;
import org.gui.auth.users.administrator.update.add.addprofessor.AddProfessorController;

public class AddController implements Initializable {
    private double mousePositionOnX;
    private double mousePositionOnY;
    private boolean addOperationStatus;
    @FXML private AnchorPane rootStage;
    @FXML private Button closeButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setStyleClass();
        addOperationStatus = false;
    }

    public void showStage() {
        FXMLLoader loader = new FXMLLoader( getClass().getResource("/org/gui/auth/users/administrator/update/add/AddControllerVista.fxml") );
        loader.setController(this);
        Parent root = null;
        try{
            root = loader.load();
        } catch(IOException ioe) {
            ioe.getMessage();
        }
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.TRANSPARENT);
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.showAndWait();
    }

    public boolean getAddOperationStatus() {
        return addOperationStatus;
    }

    @FXML
    void addCoordinatorButtonPressed(ActionEvent event) {
        AddCoordinatorController addCoordinatorController = new AddCoordinatorController();
        addCoordinatorController.showStage();
        addOperationStatus = addCoordinatorController.getAddOperationStatus();
        if( addOperationStatus ) {
            closeButton.fire();
        }
    }

    @FXML
    void addProfessorButtonPressed(ActionEvent event) {
        AddProfessorController addProfessorController = new AddProfessorController();
        addProfessorController.showStage();
        addOperationStatus = addProfessorController.getAddOperationStatus();
        if( addOperationStatus ) {
            closeButton.fire();
        }
    }

    @FXML
    void addCourseButtonPressed(ActionEvent event) {
        AddCourseController addCourseController = new AddCourseController();
        addCourseController.showStage();
        addOperationStatus = addCourseController.getAddOperationStatus();
        if( addOperationStatus ) {
            closeButton.fire();
        }
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

    private void setStyleClass() {
        rootStage.getStylesheets().clear();
        rootStage.getStylesheets().add(getClass().getResource("../../../../resources/" + CSSProperties.readTheme().getTheme() ).toExternalForm() );
    }

}
