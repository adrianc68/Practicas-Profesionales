package org.gui.auth.users.professor.activity.evaluateactivity;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import org.database.dao.ActivityDAO;
import org.database.dao.DeliveryDAO;
import org.database.dao.PractitionerDAO;
import org.domain.Activity;
import org.domain.Delivery;
import org.domain.Practitioner;
import org.domain.Professor;
import org.gui.Controller;
import org.gui.auth.resources.alerts.OperationAlert;
import org.util.Auth;
import org.util.Validator;
import org.util.ftp.FTPConnection;
import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EvaluateActivityController extends Controller implements  Initializable{
    private Professor professor;
    private boolean dataInputValid;
    private Activity selectedActivity;
    private Practitioner selectedPractitioner;
    private Delivery delivery;
    private ObservableList<Activity> activityObservableList;
    private ObservableList<Practitioner> practitionerObservableList;
    @FXML private TableView<Activity> activityTableView;
    @FXML private TableView<Practitioner> practitionerTableView;
    @FXML private TableColumn<Activity, String> activityNameColumn;
    @FXML private TableColumn<Practitioner, String> practitionerNameColumn;
    @FXML private Button documentButton;
    @FXML private Spinner<Integer> scoreSpinner;
    @FXML private TextArea observationTextArea;
    @FXML private Button evaluateButton;
    @FXML private AnchorPane rootStage;
    @FXML private Label systemLabel;

    public void initialize(URL location, ResourceBundle resources) {
        professor  = ( (Professor) Auth.getInstance().getCurrentUser() );
        scoreSpinner.setValueFactory( new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10,0,1) );
        scoreSpinner.setDisable(true);
        observationTextArea.setDisable(true);
        evaluateButton.setDisable(true);
        documentButton.setDisable(true);
        setTableComponents();
        setInformationFromDatabaseToTableActivity();
        setDataToPractitionerTableFromDatabase();
        practitionerTableView.setDisable(true);
        setListenerToTextArea();
        setListenerToTextArea();
    }

    public void showStage() {
        loadFXMLFile(getClass().getResource("/org/gui/auth/users/professor/activity/evaluateactivity/EvaluateActivityVista.fxml"), this);
        stage.show();
    }

    @FXML
    void evaluateButtonPressed(ActionEvent event) {
        if(dataInputValid){
            DeliveryDAO deliveryDAO = new DeliveryDAO();
            try {
                deliveryDAO.evaluateDeliveryOfActivity( delivery.getId(), ( scoreSpinner.getValue() ), observationTextArea.getText() );
            } catch (SQLException sqlException) {
                OperationAlert.showLostConnectionAlert();
                Logger.getLogger( EvaluateActivityController.class.getName() ).log(Level.WARNING, null, sqlException);
            }
            evaluateButton.setDisable(true);
        }else{
            OperationAlert.showUnsuccessfullAlert("Información no valida", "La información ingresada no es valida");
        }
    }

    @FXML
    void viewActivityButtonPressed(ActionEvent event) {
        FTPConnection ftpConnection = new FTPConnection();
        System.out.println( delivery.getDocumentPath() );
        File file;
        file = ftpConnection.downloadFile( delivery.getDocumentPath() );
        if(file == null){
            OperationAlert.showSuccessfullAlert("Archivo descargado", "Se ha descargado la entrega del estudiante");
        }
        scoreSpinner.setDisable(false);
        observationTextArea.setDisable(false);
        evaluateButton.setDisable(false);
    }

    private void setInformationFromDatabaseToTableActivity() {
        ActivityDAO activityDAO = new ActivityDAO();
        activityObservableList = FXCollections.observableArrayList();
        try {
            activityObservableList.addAll( activityDAO.getAllActivitiesByProfessorID( professor.getId() ) );
        } catch (SQLException sqlException) {
            OperationAlert.showLostConnectionAlert();
            Logger.getLogger( EvaluateActivityController.class.getName() ).log(Level.WARNING, null, sqlException);
        }
        activityTableView.setItems(activityObservableList);
    }

    private void setDataToPractitionerTableFromDatabase() {
        PractitionerDAO practitionerDAO = new PractitionerDAO();
        practitionerObservableList = FXCollections.observableArrayList();
        try {
            practitionerObservableList.addAll( practitionerDAO.getAllPractitionerByProfessorId( professor.getId() ) );
        } catch (SQLException sqlException) {
            OperationAlert.showLostConnectionAlert();
            Logger.getLogger( EvaluateActivityController.class.getName() ).log(Level.WARNING, null, sqlException);
        }
        practitionerTableView.setItems(practitionerObservableList);
        setListenerToPractitionerTable();
    }

    private void setListenerToActivityTable() {
        activityTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedActivity = newValue;

        });
        practitionerTableView.setDisable(false);
    }

    public void setListenerToPractitionerTable() {
        practitionerTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedPractitioner = newValue;
            getDeliveryFromDatabase();
        });
    }

    private void setTableComponents() {
        activityNameColumn.setCellValueFactory( new PropertyValueFactory<>("name") );
        practitionerNameColumn.setCellValueFactory( new PropertyValueFactory<>("name") );
        setListenerToActivityTable();
    }

    public void getDeliveryFromDatabase() {
        DeliveryDAO deliveryDAO = new DeliveryDAO();
        try {
            delivery = deliveryDAO.getDeliveryByActivityIdAndPractitionerId( ( (Activity) selectedActivity ).getId(), ((Practitioner) selectedPractitioner).getId() );
        } catch (SQLException sqlException) {
            OperationAlert.showLostConnectionAlert();
            Logger.getLogger( EvaluateActivityController.class.getName() ).log(Level.WARNING, null, sqlException);
        }
        checkDeliveryNotNull();
    }

    public void checkDeliveryNotNull(){
        if( delivery.getId() !=  0 ){
            documentButton.setDisable(false);
            systemLabel.setText("");
        }else{
            documentButton.setDisable(true);
            systemLabel.setText("No hay entrega por parte de este estudiante");
        }
    }

    private void setListenerToTextArea(){
        Map<Object[], TextArea> textarea = new LinkedHashMap<>();
        Object[] descriptionConstraints = {Validator.LARGE_TEXT_PATTERN, Validator.LARGE_TEXT_LENGTH};
        textarea.put(descriptionConstraints, observationTextArea);
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

    private String getFileName(String path) {
        int subIndex = 0;
        char delimiter = ':';
        for( int i = path.length() - 1 ; i > 0 ; i-- ) {
            if( path.charAt(i) == delimiter) {
                subIndex = i+1;
                break;
            }
        }
        String fileName = path.substring(subIndex);
        return fileName;
    }
}