package org.gui.auth.users.administrator.update.add.addcourse;

import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import org.database.dao.CourseDAO;
import org.domain.Course;
import org.gui.ValidatorController;
import org.gui.auth.resources.alerts.OperationAlert;
import org.util.CSSProperties;
import org.util.Validator;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class AddCourseController extends ValidatorController implements Initializable {
    private boolean addOperationStatus;
    private Course newCourse;
    @FXML private TextField nameTextField;
    @FXML private TextField nrcTextField;
    @FXML private TextField periodTextField;
    @FXML private Button closeButton;
    @FXML private AnchorPane rootStage;
    @FXML private Label systemLabel;
    @FXML private MaterialDesignIconView checkIconName;
    @FXML private MaterialDesignIconView checkIconPeriod;
    @FXML private MaterialDesignIconView checkIconNRC;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setStyleClass(rootStage, getClass().getResource("../../../../../resources/" + CSSProperties.readTheme().getTheme() ).toExternalForm() );
        addOperationStatus = false;
        initValidatorToTextFields();
    }

    public void showStage() {
        loadFXMLFile(getClass().getResource("/org/gui/auth/users/administrator/update/add/addcourse/AddCourseVista.fxml"), this);
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
        stagePressed(event);
    }

    @FXML
    void saveButtonPressed(ActionEvent event) {
        if( verifyInputData() ) {
            registerCourse();
            stage.close();
            if( newCourse.getId() != 0){
                showSuccessfullAlert();
            } else {
                showUnsuccessfullAlert();
            }
        } else {
            systemLabel.setText("¡Verifica tus datos!");
        }
    }

    private void showSuccessfullAlert() {
        String title = "¡Se ha registrado un nuevo curso!";
        String contentText = "Se ha registrado correctamente el nuevo curso. Verificar en la lista.";
        OperationAlert.showSuccessfullAlert(title, contentText);
    }

    private void showUnsuccessfullAlert() {
        String title = "¡No se ha podido registrar el curso!";
        String contentText = "¡No se ha podido registrar el curso, contactar con el administrdor!";
        OperationAlert.showUnsuccessfullAlert(title, contentText);
    }

    private void registerCourse() {
        newCourse = new Course();
        newCourse.setName( nameTextField.getText() );
        newCourse.setNRC( nrcTextField.getText() );
        newCourse.setPeriod( periodTextField.getText() );
        CourseDAO courseDAO = new CourseDAO();
        newCourse.setId( courseDAO.addCourse(newCourse) );
    }

    private void initValidatorToTextFields() {
        interruptorMap = new LinkedHashMap<>();
        interruptorMap.put(nameTextField, false);
        interruptorMap.put(periodTextField,false);
        interruptorMap.put(nrcTextField, false);
        Map<TextInputControl, Object[]> validator = new HashMap<>();
        Object[] nameConstraints = {Validator.NAME_PATTERN, Validator.NAME_LENGTH, checkIconName};
        validator.put(nameTextField, nameConstraints);
        Object[] nrcConstraints = {Validator.NRC_PATTERN, Validator.NRC_LENGTH, checkIconNRC};
        validator.put(nrcTextField, nrcConstraints);
        Object[] periodConstraints = {Validator.PERIOD_PATTERN, Validator.PERIOD_LENGTH, checkIconPeriod};
        validator.put(periodTextField, periodConstraints);
        super.initValidatorToTextFields(validator);
    }

}
