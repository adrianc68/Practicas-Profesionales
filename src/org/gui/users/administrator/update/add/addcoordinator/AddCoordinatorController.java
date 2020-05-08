package org.gui.users.administrator.update.add.addcoordinator;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.database.dao.CoordinatorDAO;
import org.domain.Coordinator;
import org.domain.Course;
import org.util.Security;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddCoordinatorController implements Initializable {
    private boolean dataInputValid;
    private boolean addOperationStatus;
    private Coordinator newCoordinator;
    private Course course;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField phoneNumberTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField staffNumberTextField;

    @FXML
    private TextField cubicleTextField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setListenerToTextFields();
    }

    public AddCoordinatorController(Course course) {
        this.course = course;
    }

    public void showStage() {
        FXMLLoader loader = new FXMLLoader( getClass().getResource("/org/gui/users/administrator/update/add/addcoordinator/AddCoordinatorVista.fxml") );
        loader.setController(this);
        Parent root = null;
        try{
            root = loader.load();
        } catch(IOException ioe) {
            Logger.getLogger(AddCoordinatorController.class.getName()).log(Level.WARNING, null, ioe);
        }
        Stage addCoordinator = new Stage();
        addCoordinator.initModality(Modality.APPLICATION_MODAL);
        addCoordinator.initStyle(StageStyle.UTILITY);
        addCoordinator.setScene( new Scene(root) );
        addCoordinator.showAndWait();
    }

    public boolean getAddOperationStatus() {
        return addOperationStatus;
    }

    public Coordinator getNewCoordinator() {
        return newCoordinator;
    }

    @FXML
    void cancelButtonPressed(ActionEvent event) {
        addOperationStatus = false;
        closeStage(event);
    }

    @FXML
    void saveButtonPressed(ActionEvent event) {
        if(dataInputValid) {
            newCoordinator = new Coordinator();
            newCoordinator.setCourse(course);
            newCoordinator.setName( nameTextField.getText() );
            newCoordinator.setPhoneNumber( phoneNumberTextField.getText() );
            newCoordinator.setEmail( emailTextField.getText() );
            newCoordinator.setCubicle( Integer.valueOf( cubicleTextField.getText() ) );
            newCoordinator.setStaffNumber( staffNumberTextField.getText() );
            CoordinatorDAO coordinatorDAO = new CoordinatorDAO();
            newCoordinator.setId( coordinatorDAO.addCoordinator(newCoordinator) );
            addOperationStatus = true;
            closeStage(event);
        }
    }

    private void setListenerToTextFields() {
        Map<Object[], TextField> textfields = new LinkedHashMap<>();
        Object[] nameConstraints = {Security.NAME_PATTERN, Security.NAME_LENGTH};
        textfields.put(nameConstraints, nameTextField);
        Object[] phoneNumberConstraints = {Security.PHONE_NUMBER_PATTERN, Security.PHONE_NUMBER_LENGTH};
        textfields.put(phoneNumberConstraints, phoneNumberTextField);
        Object[] emailConstraints = {Security.EMAIL_PATTERN, Security.EMAIL_LENGTH};
        textfields.put(emailConstraints, emailTextField);
        Object[] staffNumberConstraints = {Security.STAFF_NUMBER_PATTERN, Security.STAFF_NUMBER_LENGTH};
        textfields.put(staffNumberConstraints, staffNumberTextField);
        Object[] cubicleConstraints = {Security.NUMBER_PATTERN, Security.NUMBER_LENGTH};
        textfields.put(cubicleConstraints, cubicleTextField);
        for (Map.Entry<Object[], TextField> entry : textfields.entrySet() ) {
            entry.getValue().textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    if( !Security.doesStringMatchPattern( newValue, ( (String) entry.getKey()[0] ) ) || Security.isStringLargerThanLimitOrEmpty( newValue, ( (Integer) entry.getKey()[1]) ) ){
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
        Stage stage = ( (Stage) ( (Node) event.getSource() ).getScene().getWindow() );
        stage.close();
    }

}
