package org.gui.auth.users.coordinator.project.editionproject.screens.secondscreen.textinput;

import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import org.gui.ValidatorController;
import org.util.CSSProperties;
import org.util.Validator;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class TextInputController extends ValidatorController implements Initializable {
    private String inputData;
    @FXML private TextField inputTextField;
    @FXML private Button closeButton;
    @FXML private AnchorPane rootStage;
    @FXML private MaterialDesignIconView checkIconInput;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setStyleClass(rootStage, getClass().getResource("../../../../../../../resources/" + CSSProperties.readTheme().getTheme() ).toExternalForm() );
        initValidatorToTextFields();
    }

    public String getInputData() {
        return inputData;
    }

    public void showStage() {
        loadFXMLFile(getClass().getResource("/org/gui/auth/users/coordinator/project/editionproject/screens/secondscreen/textinput/TextInputVista.fxml") , this);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    @FXML
    protected void addButtonPressed(ActionEvent event) {
        if( verifyInputData() ){
            inputData = inputTextField.getText();
            closeButton.fire();
        }
    }

    @FXML
    protected void stageDragged(MouseEvent event) {
        super.stageDragged(event);
    }

    @FXML
    protected void stagePressed(MouseEvent event) {
        super.stagePressed(event);
    }

    @FXML
    protected void cancelButtonPressed(ActionEvent event) {
        stage.close();
    }

    private void initValidatorToTextFields() {
        interruptorMap = new LinkedHashMap<>();
        interruptorMap.put(inputTextField, false);
        Map<TextInputControl, Object[]> validator = new HashMap<>();
        Object[] elementConstraints = {Validator.PROJECT_MULTIVALUED_ATTRIBUTE_PATTERN, Validator.PROJECT_MULTIVALUED_ATTRIBUTES_LENGTH, checkIconInput};
        validator.put(inputTextField, elementConstraints);
        initValidatorToTextFields(validator);
    }

}
