package org.gui.auth.users.practitioner.activity;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import org.database.dao.ActivityDAO;
import org.database.dao.DeliveryDAO;
import org.database.dao.ProfessorDAO;
import org.domain.Activity;
import org.domain.Delivery;
import org.domain.Practitioner;
import org.gui.Controller;
import org.gui.auth.resources.alerts.OperationAlert;
import org.gui.auth.users.professor.activity.ActivityController;
import org.util.Auth;
import org.util.ftp.FTPConnection;
import java.io.File;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddActivityController extends Controller implements  Initializable{
    private Practitioner practitioner;
    private Activity selectedActivity;
    private ObservableList<Activity> activityObservableList;
    private File selectedFile;
    private Delivery delivery;

    @FXML private TableView<Activity> activityTableView;
    @FXML private TableColumn<Activity, String> activityNameColumn;
    @FXML private Label deadlineLabel;
    @FXML private TextArea descriptionTextArea;
    @FXML private TextField scoreTextField;
    @FXML private TextArea observationTextArea;
    @FXML private Label documentLabel;
    @FXML private Button selectButton;
    @FXML private Button deliverButton;
    @FXML private AnchorPane rootStage;

    public void initialize(URL location, ResourceBundle resources) {
        practitioner = ( (Practitioner) Auth.getInstance().getCurrentUser() );
        try {
            practitioner.setProfessor( new ProfessorDAO().getAssignedProfessorByPractitionerID( practitioner.getId() ) );
        } catch (SQLException sqlException) {
            OperationAlert.showLostConnectionAlert();
            Logger.getLogger( ActivityController.class.getName() ).log(Level.WARNING, null, sqlException);
        }
        if(practitioner.getProfessor() != null ) {
            setInformationFromDatabaseToTableActivity();
            setTableComponents();
        }
        selectButton.setDisable(true);
        deliverButton.setDisable(true);
    }

    public void showStage() {
        loadFXMLFile(getClass().getResource("/org/gui/auth/users/practitioner/activity/AddActivityVista.fxml"), this);
        stage.show();
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

    private void setInformationFromDatabaseToTableActivity() {
        ActivityDAO activityDAO = new ActivityDAO();
        activityObservableList = FXCollections.observableArrayList();
        try {
            activityObservableList.addAll(activityDAO.getAllActivitiesByProfessorID( practitioner.getProfessor().getId() ));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        activityTableView.setItems(activityObservableList);
    }


    private void setListenerToActivityTable() {
        activityTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedActivity = newValue;
            setDataToComponents( (Activity) selectedActivity );
        });
    }

    private void setDataToComponents(Activity activity) {
        clearComponents();
        descriptionTextArea.setEditable(false);
        scoreTextField.setEditable(false);
        observationTextArea.setEditable(false);
        descriptionTextArea.setText( activity.getDescription() );
        deadlineLabel.setText( activity.getDeadline() );
        selectButton.setDisable(true);
        deliverButton.setDisable(true);
        try {
            delivery = new DeliveryDAO().getDeliveryByActivityIdAndPractitionerId(activity.getId(), practitioner.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if( compareDates( activity.getDeadline() ) ) {
            if ( delivery.getId() != 0 ) {
                if (delivery.getObservation() != null) {
                    scoreTextField.setText( String.valueOf( delivery.getScore() ) );
                    observationTextArea.setText( delivery.getObservation() );
                }else{
                    selectButton.setDisable(false);
                    deliverButton.setDisable(false);
                }
                documentLabel.setText( "ya has subido el siguiente documento "+getFileName( delivery.getDocumentPath() ) );
            }else{
                selectButton.setDisable(false);
                deliverButton.setDisable(false);
            }
        }else{
            if ( delivery.getId() != 0 ) {
                if ( delivery.getObservation() != null ) {
                    scoreTextField.setText(String.valueOf( delivery.getScore() ));
                    observationTextArea.setText( delivery.getObservation() );
                }
            }
            documentLabel.setText("La actividad ya ha cerrado");
        }
    }

    private void setTableComponents() {
        activityNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        setListenerToActivityTable();
    }

    @FXML
    void deliverButtonPressed(ActionEvent event) {
        if( compareDates( ((Activity) selectedActivity).getDeadline() ) ) {
            if (selectedFile != null) {
                FTPConnection ftpConnection = new FTPConnection();
                if( delivery.getId() != 0 ){
                    ftpConnection.deleteFile(delivery.getDocumentPath());
                    if (ftpConnection.uploadFile(selectedFile, ((Activity) selectedActivity).getName() + ":" + practitioner.getName() + ":" + ((Activity) selectedActivity).getId() + ":" + selectedFile.getName())) {
                        try {
                            new DeliveryDAO().updateDeliveryFilePath(delivery.getId(), ((Activity) selectedActivity).getName() + ":" + practitioner.getName() + ":" + ((Activity) selectedActivity).getId() + ":" + selectedFile.getName());
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        documentLabel.setText("Se actualizado el documento en el sistema");
                        selectButton.setDisable(true);
                        deliverButton.setDisable(true);
                    } else {
                        documentLabel.setText("No se ha podido cargar el documento");
                    }
                }else{
                    if (ftpConnection.uploadFile(selectedFile, ((Activity) selectedActivity).getName() + ":" + practitioner.getName() + ":" + ((Activity) selectedActivity).getId() + ":" + selectedFile.getName())) {
                        Delivery newDelivery = new Delivery();
                        newDelivery.setPractitioner(practitioner);
                        newDelivery.setDocumentPath(((Activity) selectedActivity).getName() + ":" + practitioner.getName() + ":" + ((Activity) selectedActivity).getId() + ":" + selectedFile.getName());
                        try {
                            new DeliveryDAO().addDeliveryToActivity(newDelivery, ((Activity) selectedActivity).getId());
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        documentLabel.setText("El docuemnto se ha cargado en el sistema");
                        selectButton.setDisable(true);
                        deliverButton.setDisable(true);
                    } else {
                        documentLabel.setText("No se ha podido cargar el documento");
                    }
                }
            } else {
                documentLabel.setText("No se ha seleccionado documento, favor de seleccionar uno");
            }
        }else{
            documentLabel.setText("La actividad ya ha cerrado");
        }
    }

    @FXML
    void selectDocumentButtonPressed(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        selectedFile = fileChooser.showOpenDialog(null);
        if(selectedFile != null)
            documentLabel.setText("El documento seleccionado es: "+selectedFile.getName());
    }

    private boolean compareDates(String date) {
        String actualDate[] = String.valueOf(LocalDate.now()).split("-");
        String actualHour[] = String.valueOf(LocalTime.now()).split(":");
        String splitDate[] = date.split(" ");
        String activityDate[] = splitDate[0].split("-");
        String activityHour[] = splitDate[1].split(":");
        if (Integer.parseInt(actualDate[0]) > Integer.parseInt(activityDate[0])) {
            return false;
        } else {
            if (Integer.parseInt(actualDate[1]) > Integer.parseInt(activityDate[1])) {
                return false;
            } else {
                if (Integer.parseInt(actualDate[2]) > Integer.parseInt(activityDate[2])) {
                    return false;
                } else {
                    if (Integer.parseInt(actualDate[2]) == Integer.parseInt(activityDate[2])) {
                        if (Integer.parseInt(actualHour[0]) > Integer.parseInt(activityHour[0])) {
                            return false;
                        } else {
                            if (Integer.parseInt(actualHour[1]) > Integer.parseInt(activityHour[1])) {
                                return false;
                            } else {
                                if (Integer.parseInt(actualHour[2].substring(0, 2)) > Integer.parseInt(activityHour[2])) {
                                    return false;
                                } else {
                                    return true;
                                }
                            }
                        }
                    }else{
                        return true;
                    }
                }
            }
        }
    }

    private void clearComponents(){
        deadlineLabel.setText(" ");
        descriptionTextArea.setText(" ");
        observationTextArea.setText(" ");
        scoreTextField.setText(" ");
        documentLabel.setText(" ");
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