package org.gui.auth.util.theme;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.gui.auth.LoginController;
import org.util.CSSProperties;
import org.util.Theme;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SelectThemeController implements Initializable {
    private double mousePositionOnX;
    private double mousePositionOnY;
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
        FXMLLoader loader = new FXMLLoader( getClass().getResource("/org/gui/auth/util/theme/SelectThemeVista.fxml") );
        loader.setController(this);
        Parent root = null;
        try{
            root = loader.load();
        } catch(IOException ioe) {
            Logger.getLogger( LoginController.class.getName() ).log(Level.SEVERE, null, ioe);
        }
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.showAndWait();
    }

    public boolean getConfiguredStatusOperation() {
        return configuredStatusOperation;
    }

    @FXML
    void configureButtonPressed(ActionEvent event) {
        configuredStatusOperation = CSSProperties.writeConfiguredAppProperties();
        Stage stage = ((Stage) ((Node) event.getSource()).getScene().getWindow());
        stage.close();
    }

    @FXML
    void darkThemeButtonPressed(MouseEvent event) {
        CSSProperties.writeThemeProperties(Theme.DARK_THEME);
        darkRadioButton.setSelected(true);
        setStyleClass();
    }

    @FXML
    void lightThemeButtonPressed(MouseEvent event) {
        CSSProperties.writeThemeProperties(Theme.LIGHT_THEME);
        lightRadioButton.setSelected(true);
        setStyleClass();
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

    private void setStyleClass() {
        Theme theme = CSSProperties.readTheme();
        rootStage.getStylesheets().clear();
        rootStage.getStylesheets().add(getClass().getResource("../../resources/" + theme.getTheme() ).toExternalForm() );
        if( theme.name().equals("LIGHT_THEME") ) {
            lightRadioButton.setSelected(true);
        } else {
            darkRadioButton.setSelected(true);
        }

    }

}
