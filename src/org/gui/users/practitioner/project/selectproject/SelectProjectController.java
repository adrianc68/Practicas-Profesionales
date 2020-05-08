package org.gui.users.practitioner.project.selectproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SelectProjectController {

    @FXML
    private Label projectNameLabel;

    @FXML
    private Label projectDescriptionLabel;

    @FXML
    private Label projectPurposeLabel;

    @FXML
    private Label projectScheduleLabel;

    @FXML
    private Label projectResponsableNameLabel;

    @FXML
    private Label projectResponsableChargeLabel;

    @FXML
    private Label projectEmailResponsableLabel;

    @FXML
    private Label projectCompanyNameLabel;

    @FXML
    private ListView<?> responsabilitiesListView;

    @FXML
    private ListView<?> activitiesListView;

    @FXML
    private ListView<?> mediateObjetivesListView;

    @FXML
    private ListView<?> resourcesListView;

    @FXML
    private ListView<?> immeadiateObjetivesListView;

    @FXML
    private ListView<?> methodologiesListView;

    public void showStage() {
        FXMLLoader loader = new FXMLLoader( getClass().getResource("/org/gui/users/practitioner/project/selectproject/SelectProjectVIsta.fxml") );
        SelectProjectController selectProjectController = new SelectProjectController();
        loader.setController(selectProjectController);
        Parent root = null;
        try{
            root = loader.load();
        } catch(IOException ioe) {
            Logger.getLogger(SelectProjectController.class.getName()).log(Level.WARNING, null, ioe);
        }
        Stage selectProjectStage = new Stage();
        selectProjectStage.setScene( new Scene(root) );
        selectProjectStage.show();
    }

    @FXML
    void selectProjectButtonPressed(ActionEvent event) {

    }

}