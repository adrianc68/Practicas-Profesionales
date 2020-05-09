package org.gui.users.coordinator.project.editionproject.screens.secondscreen.textinput;

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
import org.util.Validator;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TextInputController implements Initializable {
    private String inputData;
    private boolean validInputData;

    @FXML
    private TextField inputTextField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setListenerToTextFields();
    }

    public String getInputData() {
        return inputData;
    }

    public void showStage() {
        FXMLLoader loader = new FXMLLoader( getClass().getResource("/org/gui/users/coordinator/project/editionproject/screens/secondscreen/textinput/TextInputVista.fxml") );
        loader.setController(this);
        Parent root = null;
        try{
            root = loader.load();
        } catch(IOException ioe) {
            Logger.getLogger(TextInputController.class.getName() ).log(Level.WARNING, null, ioe);
        }
        Stage textInput = new Stage();
        textInput.initModality(Modality.APPLICATION_MODAL);
        textInput.initStyle(StageStyle.UTILITY);
        textInput.setScene( new Scene(root) );
        textInput.showAndWait();
    }

    @FXML
    void addButtonPressed(ActionEvent event) {
        if(validInputData){
            inputData = inputTextField.getText();
            closeStage(event);
        }
    }

    @FXML
    void cancelButtonPressed(ActionEvent event) {
        closeStage(event);
    }

    private void setListenerToTextFields() {
        Map<Object[], TextField> textfields = new LinkedHashMap<>();
        Object[] elementConstraints = {Validator.PROJECT_MULTIVALUED_ATTRIBUTE_PATTERN, Validator.PROJECT_MULTIVALUED_ATTRIBUTES_LENGTH};
        textfields.put(elementConstraints, inputTextField);
        for (Map.Entry<Object[], TextField> entry : textfields.entrySet() ) {
            entry.getValue().textProperty().addListener( (observable, oldValue, newValue) -> {
                if( !Validator.doesStringMatchPattern( newValue, ( (String) entry.getKey()[0] ) ) || Validator.isStringLargerThanLimitOrEmpty( newValue, ( (Integer) entry.getKey()[1]) ) ){
                    validInputData = false;
                    entry.getValue().setStyle("-fx-background-color:red;");
                } else {
                    validInputData = true;
                    entry.getValue().setStyle("-fx-background-color:green;");
                }
            });
        }
    }

    private void closeStage(ActionEvent event) {
        Stage stage = ( (Stage) ( (Node) event.getSource() ).getScene().getWindow() );
        stage.close();
    }

}
