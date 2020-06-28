package org.gui.auth.users.professor.activity.delivery.evaluate;

import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import org.database.dao.DeliveryDAO;
import org.domain.Delivery;
import org.gui.ValidatorController;
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

public class EvaluateDeliveryController extends ValidatorController implements Initializable {
    private boolean updateOperationStatus;
    private Delivery delivery;
    @FXML private AnchorPane rootStage;
    @FXML private MaterialDesignIconView checkIconDescription;
    @FXML private TextArea descriptionTextArea;
    @FXML private MaterialDesignIconView checkIconScore;
    @FXML private Spinner<Double> scoreSpinner;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.setStyleClass(rootStage, getClass().getResource("../../../../../resources/" + CSSProperties.readTheme().getTheme() ).toExternalForm() );
        initSpinner();
        initValidatorToTextInput();
        setInformationToTextInputs();
        setStyleClassToCheckIcon();
    }

    public void showStage() {
        loadFXMLFile( getClass().getResource("/org/gui/auth/users/professor/activity/delivery/evaluate/EvaluateDeliveryVista.fxml"), this);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    public EvaluateDeliveryController(Delivery delivery) {
        this.delivery = delivery;
    }

    public boolean getUpdateOperationStatus() {
        return updateOperationStatus;
    }

    public void initSpinner() {
        scoreSpinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0,9999,1,1));
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

    @FXML
    protected void evaluateButtonPressed(ActionEvent event) {
        if( verifyInputData() ) {
            evaluateDelivery();
            if(updateOperationStatus) {
                stage.close();
            }
        }
    }

    private void setStyleClassToCheckIcon() {
        checkIconScore.setStyleClass("correctlyTextField");
    }

    private void setInformationToTextInputs() {
        if( delivery.getObservation() != null ) {
            descriptionTextArea.setText( delivery.getObservation() );
            scoreSpinner.getEditor().setText( String.valueOf( delivery.getScore() ) );
        }
    }

    private void evaluateDelivery() {
        float score = Float.valueOf( scoreSpinner.getEditor().getText() );
        String observation = descriptionTextArea.getText();
        try {
            updateOperationStatus = new DeliveryDAO().evaluateDeliveryOfActivity(delivery.getId(), (float) score, observation);
        } catch (SQLException e) {
            Logger.getLogger( EvaluateDeliveryController.class.getName() ).log(Level.WARNING, null, e);
        }
    }

    private void initValidatorToTextInput() {
        interruptorMap = new LinkedHashMap<>();
        interruptorMap.put(descriptionTextArea, false);
        interruptorMap.put(scoreSpinner.getEditor(), false);
        Map<TextInputControl, Object[]> validator = new HashMap<>();
        Object[] scoreConstraints = {Validator.NUMBER_PATTERN, Validator.NUMBER_LENGTH, checkIconScore};
        Object[] descriptionConstraints = {Validator.LARGE_TEXT_PATTERN, Validator.LARGE_TEXT_LENGTH, checkIconDescription};
        validator.put(descriptionTextArea, descriptionConstraints);
        validator.put(scoreSpinner.getEditor(), scoreConstraints);
        initValidatorToTextInputControl(validator);
    }

}
