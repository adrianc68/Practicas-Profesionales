package org.gui.auth.users.professor.activity.delivery;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Modality;
import org.database.dao.DeliveryDAO;
import org.database.dao.PractitionerDAO;
import org.domain.Activity;
import org.domain.Delivery;
import org.gui.Controller;
import org.gui.auth.resources.alerts.OperationAlert;
import org.gui.auth.resources.card.DeliveryCard;
import org.gui.auth.users.professor.activity.delivery.evaluate.EvaluateDeliveryController;
import org.util.CSSProperties;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DeliveryController extends Controller implements Initializable {
    private Activity activity;
    @FXML private AnchorPane rootStage;
    @FXML private FlowPane deliveriesPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.setStyleClass(rootStage, getClass().getResource("../../../../resources/" + CSSProperties.readTheme().getTheme() ).toExternalForm() );
        if ( activity != null ) {
            getDeliveriesFromDatabase();
        }
    }

    public void showStage() {
        loadFXMLFile( getClass().getResource("/org/gui/auth/users/professor/activity/delivery/DeliveryVista.fxml"), this);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    public DeliveryController(Activity activity) {
        this.activity = activity;
    }

    private void getDeliveriesFromDatabase() {
        try {
            List<Delivery> deliveryList = new DeliveryDAO().getAllDeliveriesByActivity( activity.getId() );
            if( deliveryList != null ) {
                for ( Delivery delivery: deliveryList) {
                    delivery.setPractitioner( new PractitionerDAO().getPractitionerByDelivery( delivery.getId() ) );
                    addDeliveryToScrollPane(delivery);
                }
            }
        } catch (SQLException e) {
            OperationAlert.showLostConnectionAlert();
            Logger.getLogger( DeliveryController.class.getName() ).log(Level.WARNING, null, e);
        }

    }

    private void addDeliveryToScrollPane(Delivery delivery) {
        DeliveryCard card = new DeliveryCard(delivery);
        card.setOnMouseReleased( (MouseEvent event) -> {
            EvaluateDeliveryController evaluateDeliveryController = new EvaluateDeliveryController(delivery);
            evaluateDeliveryController.showStage();
            if( evaluateDeliveryController.getUpdateOperationStatus() ) {
                deliveriesPane.getChildren().clear();
                getDeliveriesFromDatabase();
            }
        });
        deliveriesPane.getChildren().add(card);
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

}
