package org.gui.auth.users.professor.activity.add;

import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.database.dao.ActivityDAO;
import org.domain.Activity;
import org.domain.Professor;
import org.gui.ValidatorController;
import org.gui.auth.resources.alerts.OperationAlert;
import org.util.Auth;
import org.util.Validator;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddActivityController extends ValidatorController implements Initializable {
    private boolean addOperationStatus;
    private Activity newActivity;
    @FXML private AnchorPane rootStage;
    @FXML private Label systemLabel;
    @FXML private TextField titleTextField;
    @FXML private TextArea descriptionTextArea;
    @FXML private DatePicker deadlineDatePicker;
    @FXML private Button closeButton;
    @FXML private Spinner<Integer> hourSpinner;
    @FXML private Spinner<Integer> minuteSpinner;
    @FXML private Spinner<Integer> secondSpinner;
    @FXML private MaterialDesignIconView checkIconTitle;
    @FXML private MaterialDesignIconView checkIconDescription;
    @FXML private MaterialDesignIconView checkIconDateTime;
    @FXML private MaterialDesignIconView checkIconSchedule;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setStyleClass(rootStage);
        initSpinners();
        initValidatorToTextInput();
        initIconSchedule();
    }

    public void showStage() {
        loadFXMLFile( getClass().getResource("/org/gui/auth/users/professor/activity/add/AddActivityVista.fxml"), this);
        stage.showAndWait();
    }

    public boolean getAddOperationStatus() {
        return addOperationStatus;
    }

    public Activity getNewActivity() {
        return newActivity;
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
    protected void addButtonPressed(ActionEvent event) {
        if( verifyInputData() ) {
            addActivity();
            if(addOperationStatus) {
                stage.close();
                showSuccessfullAlert();
            }
        } else {
            systemLabel.setText("¡Verifica tus datos!");
        }
    }

    private void showSuccessfullAlert() {
        String title = "¡Se ha agregado la actividad correctamente!";
        String contentText = "¡Se ha agregado la actividad correctamente! Se mostrará en la lista de actividades";
        OperationAlert.showSuccessfullAlert(title, contentText);
    }

    private void initIconSchedule() {
        checkIconSchedule.getStyleClass().add("correctlyTextField");
    }

    private void initSpinners() {
        hourSpinner.setValueFactory( new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23,12,1) );
        minuteSpinner.setValueFactory( new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59,0,1) );
        secondSpinner.setValueFactory( new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59,0,1) );
    }

    private String getDatetimeFromFields() {
        String dateInput = deadlineDatePicker.getValue().toString();
        String timeInput = hourSpinner.getValue().toString() + ":"+ minuteSpinner.getValue().toString() +":" + secondSpinner.getValue().toString();
        String dateTime = dateInput + " " + timeInput;
        return dateTime;
    }

    private void addActivity() {
        newActivity = new Activity();
        newActivity.setName( titleTextField.getText() );
        newActivity.setDescription( descriptionTextArea.getText() );
        newActivity.setDeadline( getDatetimeFromFields() );
        newActivity.setProfessor( (Professor) Auth.getInstance().getCurrentUser() );
        try {
            newActivity.setId( new ActivityDAO().addActivity(newActivity) );
        } catch (SQLException ex) {
            OperationAlert.showLostConnectionAlert();
            Logger.getLogger( AddActivityController.class.getName() ).log(Level.WARNING, null, ex);
        }
        addOperationStatus = (newActivity.getId() != 0);
    }

    private void initValidatorToTextInput() {
        interruptorMap = new LinkedHashMap<>();
        interruptorMap.put(titleTextField, false);
        interruptorMap.put(descriptionTextArea, false);
        interruptorMap.put(deadlineDatePicker.getEditor(), false);
        interruptorMap.put(hourSpinner.getEditor(), true);
        interruptorMap.put(minuteSpinner.getEditor(), true);
        interruptorMap.put(secondSpinner.getEditor(), true);
        Map<TextInputControl, Object[]> validator = new HashMap<>();
        Object[] nameConstraints = {Validator.NAME_AND_NUMBERS_PATTERN, Validator.NAME_LENGTH, checkIconTitle};
        Object[] descriptionConstraints = {Validator.LARGE_TEXT_PATTERN, Validator.LARGE_TEXT_LENGTH, checkIconDescription};
        Object[] dateConstraints = {Validator.DATE_PATTERN, Validator.TIME_LENGTH, checkIconDateTime};
        Object[] timeConstraints = {Validator.TIME_PATTERN, Validator.TIME_LENGTH, checkIconSchedule};
        validator.put(titleTextField, nameConstraints);
        validator.put(descriptionTextArea, descriptionConstraints);
        validator.put(deadlineDatePicker.getEditor(), dateConstraints);
        validator.put(hourSpinner.getEditor(), timeConstraints);
        validator.put(minuteSpinner.getEditor(), timeConstraints);
        validator.put(secondSpinner.getEditor(), timeConstraints);
        initValidatorToTextInputControl(validator);
    }

}


