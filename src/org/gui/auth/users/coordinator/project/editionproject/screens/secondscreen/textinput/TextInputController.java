package org.gui.auth.users.coordinator.project.editionproject.screens.secondscreen.textinput;

import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
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

public class TextInputController implements Initializable {
    private double mousePositionOnX;
    private double mousePositionOnY;
    private String inputData;
    private Map<TextField, Boolean> interruptorMap = new HashMap<>();
    @FXML private TextField inputTextField;
    @FXML private Button closeButton;
    @FXML private AnchorPane rootStage;
    @FXML private MaterialDesignIconView checkIconInput;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setStyleClass();
        initValidatorToTextFields();
    }

    public String getInputData() {
        return inputData;
    }

    public void showStage() {
        FXMLLoader loader = new FXMLLoader( getClass().getResource("/org/gui/auth/users/coordinator/project/editionproject/screens/secondscreen/textinput/TextInputVista.fxml") );
        loader.setController(this);
        Parent root = null;
        try{
            root = loader.load();
        } catch(IOException ioe) {
            Logger.getLogger(TextInputController.class.getName() ).log(Level.WARNING, null, ioe);
        }
        Stage textInput = new Stage();
        textInput.initModality(Modality.APPLICATION_MODAL);
        textInput.initStyle(StageStyle.TRANSPARENT);
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        textInput.setScene(scene);
        textInput.showAndWait();
    }

    @FXML
    void addButtonPressed(ActionEvent event) {
        if( verifyInputData() ){
            inputData = inputTextField.getText();
            closeButton.fire();
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

    @FXML
    void cancelButtonPressed(ActionEvent event) {
        Stage stage = ( (Stage) ( (Node) event.getSource() ).getScene().getWindow() );
        stage.close();
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
        interruptorMap.put(inputTextField, false);
        Map<TextField, Object[]> validator = new HashMap<>();
        Object[] elementConstraints = {Validator.PROJECT_MULTIVALUED_ATTRIBUTE_PATTERN, Validator.PROJECT_MULTIVALUED_ATTRIBUTES_LENGTH, checkIconInput};
        validator.put(inputTextField, elementConstraints);
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
        rootStage.getStylesheets().add(getClass().getResource("../../../../../../../resources/" + CSSProperties.readTheme().getTheme() ).toExternalForm() );
    }

}
