package org.gui.users.coordinator.project.registerproject.screens.secondscreen.textinput;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TextInputController {

    @FXML
    private TextField textInputField;

   private String input;

    public String getInput() {
        return input;
    }

    public void showStage() {
        FXMLLoader loader = new FXMLLoader( getClass().getResource("/org/gui/users/coordinator/project/registerproject/screens/secondscreen/textinput/TextInputVista.fxml") );
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
        if(!textInputField.getText().equals("")){
            input = textInputField.getText();
            closeStage(event);
        }
        textInputField.setPromptText("Debes ingresar datos");
    }

    @FXML
    void cancelButtonPressed(ActionEvent event) {
        closeStage(event);
    }

    private void closeStage(ActionEvent event) {
        Stage stage = ( (Stage) ( (Node) event.getSource() ).getScene().getWindow() );
        stage.close();
    }

}
