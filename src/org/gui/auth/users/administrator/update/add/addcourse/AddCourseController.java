package org.gui.auth.users.administrator.update.add.addcourse;

import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.database.dao.CourseDAO;
import org.domain.Course;
import org.gui.auth.resources.alerts.OperationAlert;
import org.util.CSSProperties;
import org.util.Validator;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddCourseController implements Initializable {
    private double mousePositionOnX;
    private double mousePositionOnY;
    private boolean addOperationStatus;
    private Course newCourse;
    private Map<TextField, Boolean> interruptorMap = new HashMap<>();
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
        setStyleClass();
        addOperationStatus = false;
        initValidatorToTextFields();
    }

    public void showStage() {
        FXMLLoader loader = new FXMLLoader( getClass().getResource("/org/gui/auth/users/administrator/update/add/addcourse/AddCourseVista.fxml") );
        loader.setController(this);
        Parent root = null;
        try{
            root = loader.load();
        } catch(IOException ioe) {
            Logger.getLogger( AddCourseController.class.getName() ).log(Level.WARNING, null, ioe);
        }
        Stage addCourseStage = new Stage();
        addCourseStage.initModality(Modality.APPLICATION_MODAL);
        addCourseStage.initStyle(StageStyle.TRANSPARENT);
        addCourseStage.setScene( new Scene(root) );
        addCourseStage.showAndWait();
    }

    public boolean getAddOperationStatus() {
        return addOperationStatus;
    }

    public Course getNewCourse() {
        return newCourse;
    }

    @FXML
    void cancelButtonPressed(ActionEvent event) {
        Stage stage = ( (Stage) ( (Node) event.getSource() ).getScene().getWindow() );
        stage.close();
    }

    @FXML
    void saveButtonPressed(ActionEvent event) {
        if( verifyInputData() ) {
            registerCourse();
            closeButton.fire();
            if( newCourse.getId() != 0){
                showSuccessfullAlert();
            } else {
                showUnsuccessfullAlert();
            }
        } else {
            systemLabel.setText("¡Verifica tus datos!");
        }
    }

    @FXML
    void stageDragged(MouseEvent event) {
        Stage stage = (Stage) ( ( (Node) event.getSource() ).getScene().getWindow() );
        stage.setX( event.getScreenX() - mousePositionOnX );
        stage.setY( event.getScreenY() - mousePositionOnY );
    }

    @FXML
    void stagePressed(MouseEvent event) {
        mousePositionOnX = event.getSceneX();
        mousePositionOnY = event.getSceneY();
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

    private boolean verifyInputData() {
        boolean dataInputValid = true;
        for( Map.Entry<TextField, Boolean> entry : interruptorMap.entrySet() ) {
            if( !entry.getValue() ){
                dataInputValid = false;
                break;
            }
        }
        return dataInputValid;
    }

    private void initValidatorToTextFields() {
        interruptorMap = new LinkedHashMap<>();
        interruptorMap.put(nameTextField, false);
        interruptorMap.put(periodTextField,false);
        interruptorMap.put(nrcTextField, false);
        Map<TextField, Object[]> validator = new HashMap<>();
        Object[] nameConstraints = {Validator.NAME_PATTERN, Validator.NAME_LENGTH, checkIconName};
        validator.put(nameTextField, nameConstraints);
        Object[] nrcConstraints = {Validator.NRC_PATTERN, Validator.NRC_LENGTH, checkIconNRC};
        validator.put(nrcTextField, nrcConstraints);
        Object[] periodConstraints = {Validator.PERIOD_PATTERN, Validator.PERIOD_LENGTH, checkIconPeriod};
        validator.put(periodTextField, periodConstraints);
        final int FIRST_CONTRAINT = 0;
        final int SECOND_CONTRAINT = 1;
        final int THIRD_CONSTRAINT_ICON = 2;
        for (Map.Entry<TextField, Object[]> entry : validator.entrySet() ) {
            entry.getKey().textProperty().addListener( (observable, oldValue, newValue) -> {
                if( !Validator.doesStringMatchPattern( newValue, ( (String) entry.getValue()[FIRST_CONTRAINT] ) ) || Validator.isStringLargerThanLimitOrEmpty( newValue, ( (Integer) entry.getValue()[SECOND_CONTRAINT]) ) ){
                    interruptorMap.put(entry.getKey(), false );
                    ( (MaterialDesignIconView) entry.getValue()[THIRD_CONSTRAINT_ICON]).getStyleClass().clear();
                    ( (MaterialDesignIconView) entry.getValue()[THIRD_CONSTRAINT_ICON]).getStyleClass().add("wrongTextField");
                } else {
                    interruptorMap.put(entry.getKey(), true );
                    ((MaterialDesignIconView) entry.getValue()[THIRD_CONSTRAINT_ICON]).getStyleClass().clear();
                    ( (MaterialDesignIconView) entry.getValue()[THIRD_CONSTRAINT_ICON]).getStyleClass().add("correctlyTextField");
                }
            });
        }
    }

    private void setStyleClass() {
        rootStage.getStylesheets().clear();
        rootStage.getStylesheets().add(getClass().getResource("../../../../../resources/" + CSSProperties.readTheme().getTheme() ).toExternalForm() );
    }

}
