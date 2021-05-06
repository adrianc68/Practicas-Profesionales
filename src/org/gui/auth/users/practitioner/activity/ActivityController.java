package org.gui.auth.users.practitioner.activity;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import org.database.dao.ActivityDAO;
import org.database.dao.DeliveryDAO;
import org.database.dao.ProfessorDAO;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.util.Auth;
import org.domain.Activity;
import org.domain.Practitioner;
import org.gui.Controller;
import org.gui.auth.resources.alerts.OperationAlert;
import org.gui.auth.resources.card.ActivityPractitionerCard;
import org.gui.auth.users.practitioner.activity.adddelivery.AddDeliveryController;

public class ActivityController extends Controller implements Initializable {
    private Practitioner practitioner;
    @FXML private AnchorPane rootStage;
    @FXML private FlowPane activitiesPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.setStyleClass(rootStage);
        if(practitioner != null) {
            getAssignedProfessorByActualPractitioner();
            if( practitioner.getProfessor() != null) {
                setActivitiesFromDatabaseToScrollPane();
            }
        }
    }

    public void showStage() {
        loadFXMLFile( getClass().getResource("/org/gui/auth/users/practitioner/activity/ActivityVista.fxml"), this);
        stage.showAndWait();
    }

    public ActivityController(Practitioner practitioner) {
        this.practitioner = practitioner;
    }

    @FXML
    protected void closeButtonPressed(ActionEvent event) {
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


    private void getAssignedProfessorByActualPractitioner() {
        practitioner = ( (Practitioner) Auth.getInstance().getCurrentUser() );
        try {
            practitioner.setProfessor( new ProfessorDAO().getAssignedProfessorByPractitionerID( practitioner.getId() ) );
        } catch (SQLException sqlException) {
            OperationAlert.showLostConnectionAlert();
            Logger.getLogger( ActivityController.class.getName() ).log(Level.WARNING, null, sqlException);
        }
    }

    private void addActivityToScrollPane(Activity activity) {
        ActivityPractitionerCard card = new ActivityPractitionerCard(activity);
        card.setOnMouseReleased( (MouseEvent event) -> {
            AddDeliveryController addDeliveryController = new AddDeliveryController(activity, practitioner);
            addDeliveryController.showStage();
        });
        activitiesPane.getChildren().add(card);
    }

    private void setActivitiesFromDatabaseToScrollPane() {
        List<Activity> activityList;
        try {
            activityList = new ActivityDAO().getAllActivitiesByProfessorID( practitioner.getProfessor().getId() );
            if( activityList != null ) {
                for (Activity activity : activityList) {
                    activity.setDeliveries( new DeliveryDAO().getAllDeliveriesByPractitionerID( practitioner.getId() ) );
                    addActivityToScrollPane(activity);
                }
            }
        } catch (SQLException sqlException) {
            OperationAlert.showLostConnectionAlert();
            Logger.getLogger( ActivityController.class.getName() ).log(Level.WARNING, null, sqlException);
        }
    }

}
