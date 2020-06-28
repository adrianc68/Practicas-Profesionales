package org.gui.auth.users.practitioner.activity.adddelivery;

import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.database.dao.DeliveryDAO;
import org.domain.Activity;
import org.domain.Delivery;
import org.domain.Practitioner;
import org.gui.ValidatorController;
import org.gui.auth.resources.alerts.OperationAlert;
import org.util.CSSProperties;
import org.util.Validator;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddDeliveryController extends ValidatorController implements Initializable {
    private boolean addOperationStatus;
    private Activity activity;
    private Practitioner practitioner;
    @FXML private AnchorPane rootStage;
    @FXML private MaterialDesignIconView checkIconPath;
    @FXML private TextField pathTextField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.setStyleClass(rootStage, getClass().getResource("../../../../resources/" + CSSProperties.readTheme().getTheme() ).toExternalForm() );
        initValidatorToTextFields();
    }

    public void showStage() {
        loadFXMLFile( getClass().getResource("/org/gui/auth/users/practitioner/activity/adddelivery/AddDeliveryVista.fxml"), this);
        stage.showAndWait();
    }

    public AddDeliveryController(Activity activity, Practitioner practitioner) {
        this.activity = activity;
        this.practitioner = practitioner;
    }

    @FXML
    protected void addDeliveryButtonPressed(ActionEvent event) {
        if( verifyInputData() ) {
            addDelivery();
            stage.close();
            if(addOperationStatus) {
                showSuccessfullAlert();
            }
        }
    }

    @FXML
    protected void selectButtonPressed(ActionEvent event) {

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

    private void addDelivery() {
        Delivery delivery = new Delivery();
        delivery.setPractitioner(practitioner);
        delivery.setActivity(activity);
        delivery.setDocumentPath( pathTextField.getText() );
        delivery.setObservation( null);
        delivery.setScore(0);
        try {
            delivery.setId( new DeliveryDAO().addDeliveryToActivity( delivery ) );
        } catch (SQLException e) {
            OperationAlert.showSuccessfullAlert( "¡Error!", e.getMessage() );
            Logger.getLogger( AddDeliveryController.class.getName() ).log(Level.WARNING, null, e);
        }
        addOperationStatus = ( delivery.getId() != 0);
    }

    private void showSuccessfullAlert() {
        String title = "¡Se ha entregado la actividad correctamente!";
        String contentText = "¡Se ha entregado la actividad correctamente! puedes volver a subirla si no se ha excedido la fecha límite.";
        OperationAlert.showSuccessfullAlert(title, contentText);
    }

    private void initValidatorToTextFields() {
        interruptorMap = new LinkedHashMap<>();
        interruptorMap.put(pathTextField, false);
        Map<TextInputControl, Object[]> validator = new HashMap<>();
        Object[] nameConstraints = {Validator.PATH_PATTERN, Validator.PATH_LENGTH, checkIconPath};
        validator.put(pathTextField, nameConstraints);
        initValidatorToTextInputControl(validator);
    }

}
