package org.gui.users.administrator.update.add.addcourse;

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
import org.database.dao.CourseDAO;
import org.domain.Course;
import org.util.Security;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddCourseController implements Initializable {
    private boolean addOperationStatus;
    private Course newCourse;
    private boolean dataInputValid;


    @FXML
    private TextField nameTextField;

    @FXML
    private TextField nrcTextField;

    @FXML
    private TextField periodTextField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setListenerToTextFields();
    }

    public void showStage() {
        FXMLLoader loader = new FXMLLoader( getClass().getResource("/org/gui/users/administrator/update/add/addcourse/AddCourseVista.fxml") );
        loader.setController(this);
        Parent root = null;
        try{
            root = loader.load();
        } catch(IOException ioe) {
            Logger.getLogger(AddCourseController.class.getName()).log(Level.WARNING, null, ioe);
        }
        Stage addCourseStage = new Stage();
        addCourseStage.initModality(Modality.APPLICATION_MODAL);
        addCourseStage.initStyle(StageStyle.UTILITY);
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
        addOperationStatus = false;
        closeStage(event);
    }

    @FXML
    void saveButtonPressed(ActionEvent event) {
        if(dataInputValid) {
            newCourse = new Course();
            newCourse.setName( nameTextField.getText() );
            newCourse.setNRC( nrcTextField.getText() );
            newCourse.setPeriod( periodTextField.getText() );
            CourseDAO courseDAO = new CourseDAO();
            newCourse.setId( courseDAO.addCourse(newCourse) );
            addOperationStatus = true;
            closeStage(event);
        }
    }

    private void setListenerToTextFields() {
        Map<Object[], TextField> textfields = new LinkedHashMap<>();
        Object[] nameConstraints = {Security.NAME_PATTERN, Security.NAME_LENGTH};
        textfields.put(nameConstraints, nameTextField);
        Object[] nrcConstraints = {Security.NRC_PATTERN, Security.NRC_LENGTH};
        textfields.put(nrcConstraints, nrcTextField);
        Object[] periodConstraints = {Security.PERIOD_PATTERN, Security.PERIOD_LENGTH};
        textfields.put(periodConstraints, periodTextField);
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
