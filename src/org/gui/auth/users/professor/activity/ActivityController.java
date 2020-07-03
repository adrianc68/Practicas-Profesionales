package org.gui.auth.users.professor.activity;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import java.net.URL;
import java.util.ResourceBundle;
import org.gui.Controller;
import org.gui.auth.users.professor.activity.createactivity.AddActivityController;
import org.gui.auth.users.professor.activity.evaluateactivity.EvaluateActivityController;
import org.util.CSSProperties;

public class ActivityController extends Controller implements Initializable {
    @FXML
    private AnchorPane rootStage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setStyleClass(rootStage, getClass().getResource("../../../resources/" + CSSProperties.readTheme().getTheme() ).toExternalForm() );
    }

    public void showStage() {
        loadFXMLFile(getClass().getResource("/org/gui/auth/users/professor/activity/ActivityVista.fxml"), this);
        stage.show();
    }

    @FXML
    void EvaluateActivityButtonPressed(ActionEvent event) {
        EvaluateActivityController evaluateActivityController = new EvaluateActivityController();
        stage.close();
        evaluateActivityController.showStage();
    }

    @FXML
    void addActivityButtonPressed(ActionEvent event) {
        AddActivityController addActivityController = new AddActivityController();
        stage.close();
        addActivityController.showStage();
    }

    @FXML
    protected void stageDragged(MouseEvent event) {
        super.stageDragged(event);
    }

    @FXML
    protected void stagePressed(MouseEvent event) {
        super.stagePressed(event);
    }

}
