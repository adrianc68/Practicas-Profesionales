package org.gui.auth.users.professor.activity;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import org.database.dao.ActivityDAO;
import org.domain.Activity;
import org.gui.Controller;
import org.gui.auth.resources.alerts.OperationAlert;
import org.gui.auth.resources.card.ActivityProfessorCard;
import org.gui.auth.users.professor.activity.add.AddActivityController;
import org.gui.auth.users.professor.activity.delivery.DeliveryController;
import org.util.Auth;
import org.util.CSSProperties;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ActivityController extends Controller implements Initializable {
    @FXML private AnchorPane rootStage;
    @FXML private FlowPane activitiesPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.setStyleClass(rootStage, getClass().getResource("../../../resources/" + CSSProperties.readTheme().getTheme() ).toExternalForm() );
        if(Auth.getInstance().getCurrentUser() != null) {
            setActivitiesFromDatabase();
        }
    }

    public void showStage() {
        loadFXMLFile( getClass().getResource("/org/gui/auth/users/professor/activity/ActivityVista.fxml"), this);
        stage.showAndWait();
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
    protected void closeButtonPressed(ActionEvent event) {
        stage.close();
    }

    @FXML
    protected void addActivityButtonPressed(ActionEvent event) {
        AddActivityController addActivityController = new AddActivityController();
        addActivityController.showStage();
        if( addActivityController.getAddOperationStatus() ) {
            addActivityInACardToScrollPane( addActivityController.getNewActivity() );
        }
    }

    @FXML
    protected void removeActivityButtonPressed(ActionEvent event) {
        throw new UnsupportedOperationException("Not in use case yet");
    }

    private void addActivityInACardToScrollPane(Activity activity) {
        ActivityProfessorCard card = new ActivityProfessorCard(activity);
        card.setOnMouseReleased( (MouseEvent event) -> {
            DeliveryController deliveryController = new DeliveryController(activity);
            deliveryController.showStage();
        });
        activitiesPane.getChildren().add(card);
    }

    private void setActivitiesFromDatabase() {
        try {
            List<Activity> activityList = new ActivityDAO().getAllActivitiesByProfessorID( Auth.getInstance().getCurrentUser().getId() );
            if (activityList != null) {
                for (Activity activity: activityList) {
                    addActivityInACardToScrollPane(activity);
                }
            }
        } catch (SQLException e) {
            OperationAlert.showLostConnectionAlert();
            Logger.getLogger( ActivityController.class.getName() ).log(Level.WARNING, null, e);
        }
    }

}
