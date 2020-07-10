package org.gui.auth.users.professor.activity.createactivity;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import org.database.dao.ActivityDAO;
import org.domain.Activity;
import org.domain.Professor;
import org.gui.Controller;
import org.gui.auth.resources.alerts.OperationAlert;
import org.util.Auth;
import org.util.Validator;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddActivityController extends Controller implements Initializable {
    private boolean dataInputValid;
    private boolean addOperationStatus;
    private Activity newActivity;
    @FXML private TextField tittleTextField;
    @FXML private TextArea descriptionTextArea;
    @FXML private DatePicker deadlineDatePicker = new DatePicker();
    @FXML private AnchorPane rootStage;
    @FXML private Label systemLabel;
    @FXML private Spinner<Integer> hourSpinner;
    @FXML private Spinner<Integer> minuteSpinner;
    @FXML private Spinner<Integer> secondSpinner;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //setStyleClass(rootStage, getClass().getResource("../../../resources/" + CSSProperties.readTheme().getTheme() ).toExternalForm() );
        setListenerToTextFields();
        setListenerToTextArea();
        deadlineDatePicker.setEditable(false);
        hourSpinner.setValueFactory( new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23,12,1) );
        minuteSpinner.setValueFactory( new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59,0,1) );
        secondSpinner.setValueFactory( new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59,0,1) );
    }

    public void showStage() {
        loadFXMLFile( getClass().getResource("/org/gui/auth/users/professor/activity/createactivity/AddActivityVista.fxml"), this );
        stage.show();
    }

    @FXML
    void addButtonPressed(ActionEvent event) {
        if( deadlineDatePicker.getValue() != null ){
            if ( compareDates( deadlineDatePicker.getValue()+"-"+hourSpinner.getValue()+"-"+minuteSpinner.getValue()+"-"+secondSpinner.getValue() ) ) {
                if (dataInputValid) {
                    addOperationStatus = false;
                    newActivity = new Activity();
                    newActivity.setName(tittleTextField.getText());
                    newActivity.setDescription( descriptionTextArea.getText() );
                    newActivity.setDeadline( deadlineDatePicker.getValue()+"-"+hourSpinner.getValue()+"-"+minuteSpinner.getValue()+"-"+secondSpinner.getValue() );
                    newActivity.setProfessor( (Professor) Auth.getInstance().getCurrentUser() );
                    ActivityDAO activityDAO = new ActivityDAO();
                    try {
                        newActivity.setId(activityDAO.addActivity(newActivity));
                    } catch (SQLException sqlException) {
                        OperationAlert.showLostConnectionAlert();
                        Logger.getLogger( AddActivityController.class.getName() ).log(Level.WARNING, null, sqlException);                    }
                    closeStage(event);
                }else{
                    OperationAlert.showUnsuccessfullAlert("Información no valida", "En los campos mascados de color rojo no se ingreso informacion valida");
                }
            }else{
                OperationAlert.showUnsuccessfullAlert("Fecha invalida", "La fecha seleccionada no es valida");
            }
        }else{
            OperationAlert.showUnsuccessfullAlert("fecha no seleccionada", "No has seleccionado una fecha para la actividad");
        }
    }

    @FXML
    void cancelButtonPressed(ActionEvent event) {
        addOperationStatus = false;
        closeStage(event);
    }

    private void setListenerToTextFields() {
        Map<Object[], TextField> textfields = new LinkedHashMap<>();
        Object[] tittleConstraints = {Validator.NAME_PATTERN, Validator.NAME_LENGTH};
        textfields.put(tittleConstraints, tittleTextField);
        for (Map.Entry<Object[], TextField> entry : textfields.entrySet() ) {
            entry.getValue().textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    if( !Validator.doesStringMatchPattern( newValue, ( (String) entry.getKey()[0] ) ) || Validator.isStringLargerThanLimitOrEmpty( newValue, ( (Integer) entry.getKey()[1]) ) ){
                        dataInputValid = false;
                        entry.getValue().setStyle("-fx-background-color:red;");
                    } else {
                        dataInputValid = true;
                        entry.getValue().setStyle("-fx-background-color:green;");
                    }
                }
            });
        }
    }
    private void setListenerToTextArea(){
        Map<Object[], TextArea> textarea = new LinkedHashMap<>();
        Object[] descriptionConstraints = {Validator.LARGE_TEXT_PATTERN, Validator.LARGE_TEXT_LENGTH};
        textarea.put(descriptionConstraints, descriptionTextArea);
        for (Map.Entry<Object[], TextArea> entry : textarea.entrySet() ) {
            entry.getValue().textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    if( !Validator.doesStringMatchPattern( newValue, ( (String) entry.getKey()[0] ) ) || Validator.isStringLargerThanLimitOrEmpty( newValue, ( (Integer) entry.getKey()[1]) ) ){
                        dataInputValid = false;
                        entry.getValue().setStyle("-fx-background-color:red;");
                    } else {
                        dataInputValid = true;
                        entry.getValue().setStyle("-fx-background-color:green;");
                    }
                }
            });
        }
    }

    private void closeStage(ActionEvent event) {
        stage.close();
    }

    private boolean compareDates(String date) {
        String actualDate[] = String.valueOf( LocalDate.now() ).split("-");
        String actualHour[] = String.valueOf( LocalTime.now() ).split(":");
        String splitDate[] = date.split("-");
        System.out.println(splitDate[5]);
        if ( Integer.parseInt(actualDate[0]) > Integer.parseInt(splitDate[0]) ) {
            return false;
        } else {
            if ( Integer.parseInt(actualDate[1]) > Integer.parseInt(splitDate[1]) ) {
                return false;
            } else {
                if ( Integer.parseInt(actualDate[2]) > Integer.parseInt(splitDate[2]) ) {
                    return false;
                } else {
                    if ( Integer.parseInt(actualDate[2]) == Integer.parseInt(splitDate[2]) ) {
                        if ( Integer.parseInt(actualHour[0]) > Integer.parseInt(splitDate[3]) ) {
                            return false;
                        } else {
                            if ( Integer.parseInt(actualHour[0]) == Integer.parseInt(splitDate[3]) ) {
                                if ( Integer.parseInt(actualHour[1]) > Integer.parseInt(splitDate[4]) ) {
                                    return false;
                                } else {
                                    if ( Integer.parseInt(actualHour[1]) == Integer.parseInt(splitDate[4]) ) {
                                        if ( Integer.parseInt(actualHour[2].substring(0, 2)) > Integer.parseInt(splitDate[5]) ) {
                                            return false;
                                        } else {
                                            if ( Integer.parseInt(actualHour[2].substring(0, 2)) == Integer.parseInt(splitDate[5]) ) {
                                                return false;
                                            } else {
                                                return true;
                                            }
                                        }
                                    } else {
                                        return true;
                                    }
                                }
                            } else {
                                return true;
                            }
                        }
                    } else {
                        return true;
                    }
                }
            }
        }
    }
}


