package org.gui.auth.users.administrator.update.add;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import org.gui.Controller;
import org.util.CSSProperties;
import java.net.URL;
import java.util.ResourceBundle;
import org.gui.auth.users.administrator.update.add.addcoordinator.AddCoordinatorController;
import org.gui.auth.users.administrator.update.add.addcourse.AddCourseController;
import org.gui.auth.users.administrator.update.add.addprofessor.AddProfessorController;

public class AddController extends Controller implements Initializable {
    private boolean addOperationStatus;
    @FXML private AnchorPane rootStage;
    @FXML private Button closeButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setStyleClass(rootStage, getClass().getResource("../../../../resources/" + CSSProperties.readTheme().getTheme() ).toExternalForm() );
        addOperationStatus = false;
    }

    public void showStage() {
        loadFXMLFile(getClass().getResource("/org/gui/auth/users/administrator/update/add/AddControllerVista.fxml"), this );
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    public boolean getAddOperationStatus() {
        return addOperationStatus;
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

    @FXML
    protected void addCoordinatorButtonPressed(ActionEvent event) {
        AddCoordinatorController addCoordinatorController = new AddCoordinatorController();
        addCoordinatorController.showStage();
        addOperationStatus = addCoordinatorController.getAddOperationStatus();
        if( addOperationStatus ) {
            stage.close();
        }
    }

    @FXML
    protected void addProfessorButtonPressed(ActionEvent event) {
        AddProfessorController addProfessorController = new AddProfessorController();
        addProfessorController.showStage();
        addOperationStatus = addProfessorController.getAddOperationStatus();
        if( addOperationStatus ) {
            stage.close();
        }
    }

    @FXML
    protected void addCourseButtonPressed(ActionEvent event) {
        AddCourseController addCourseController = new AddCourseController();
        addCourseController.showStage();
        addOperationStatus = addCourseController.getAddOperationStatus();
        if( addOperationStatus ) {
            stage.close();
        }
    }

}
