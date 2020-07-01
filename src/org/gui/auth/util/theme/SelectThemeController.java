package org.gui.auth.util.theme;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.gui.Controller;
import org.util.CSSProperties;
import org.util.Theme;
import java.net.URL;
import java.util.ResourceBundle;

public class SelectThemeController extends Controller implements Initializable {
    private boolean configuredStatusOperation;
    @FXML private RadioButton lightRadioButton;
    @FXML private ToggleGroup toggleGroup;
    @FXML private RadioButton darkRadioButton;
    @FXML AnchorPane rootStage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configuredStatusOperation = false;
        setStyleClass();
    }

    public void showStage() {
        loadFXMLFile( getClass().getResource("/org/gui/auth/util/theme/SelectThemeVista.fxml"), this);
        stage.showAndWait();
    }

    public boolean getConfiguredStatusOperation() {
        return configuredStatusOperation;
    }

    @FXML
    protected void configureButtonPressed(ActionEvent event) {
        configuredStatusOperation = CSSProperties.writeConfiguredAppProperties();
        stage.close();
    }

    @FXML
    protected void darkThemeButtonPressed(MouseEvent event) {
        CSSProperties.writeThemeProperties(Theme.DARK_THEME);
        darkRadioButton.setSelected(true);
        setStyleClass();
    }

    @FXML
    protected void lightThemeButtonPressed(MouseEvent event) {
        CSSProperties.writeThemeProperties(Theme.LIGHT_THEME);
        lightRadioButton.setSelected(true);
        setStyleClass();
    }

    @FXML
    protected void stageDragged(MouseEvent event) {
        super.stageDragged(event);
    }

    @FXML
    protected void stagePressed(MouseEvent event) {
        super.stagePressed(event);
    }

    private void setStyleClass() {
        Theme theme = CSSProperties.readTheme();
        setStyleClass(rootStage);
        if (theme == Theme.LIGHT_THEME) {
            lightRadioButton.setSelected(true);
        } else {
            darkRadioButton.setSelected(true);
        }
    }

}
