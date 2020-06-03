package org.gui.auth.users.practitioner.activity;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import org.database.dao.ActivityDAO;
import org.database.dao.DeliveryDAO;
import org.database.dao.ProfessorDAO;
import org.domain.Activity;
import org.domain.Practitioner;
import org.gui.auth.resources.card.ActivityCard;
import org.util.Auth;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ActivityController implements Initializable {
    private Practitioner practitioner;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private FlowPane activitiesPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        practitioner = ( (Practitioner) Auth.getInstance().getUser() );
        practitioner.setProfessor( new ProfessorDAO().getAssignedProfessorByPractitionerID( practitioner.getId() ) );
        if(practitioner.getProfessor() != null ) {
            setActivitiesFromDatabaseToScrollPane();
        }
    }

    public void showStage() {
        FXMLLoader loader = new FXMLLoader( getClass().getResource("/org/gui/auth/users/practitioner/activity/ActivityVista.fxml") );
        loader.setController(this);
        Parent root = null;
        try{
            root = loader.load();
        } catch(IOException ioe) {
            Logger.getLogger( ActivityController.class.getName() ).log(Level.WARNING, null, ioe);
        }
        Stage coordinator = new Stage();
        coordinator.setScene( new Scene(root) );
        coordinator.showAndWait();
    }

    @FXML
    void closeButtonPressed(ActionEvent event) {
        Stage stage = ( (Stage) ( (Node) event.getSource() ).getScene().getWindow() );
        stage.close();
    }

    private void setActivitiesFromDatabaseToScrollPane() {
        List<Activity> activityList = new ActivityDAO().getAllActivitiesByProfessorID( practitioner.getProfessor().getId() );
        if( activityList != null ) {
            for (Activity activity : activityList) {
                activity.setDeliveries( new DeliveryDAO().getAllDeliveriesByPractitionerID(practitioner.getId() ) );
                addActivityToScrollPane(activity);
            }
        }
    }

    private void addActivityToScrollPane(Activity activity) {
        ActivityCard activityCard = new ActivityCard(activity);
        // Agregar un listener que abra una ventana
        // para ver detalladamente la actividad
        // y desde ahi se pueda entregar la actividad
        activitiesPane.getChildren().add(activityCard);
    }

}
