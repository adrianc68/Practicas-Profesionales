package org.gui.login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.domain.Coordinator;
import org.domain.Person;
import org.domain.Practitioner;
import org.domain.Professor;
import org.gui.users.administrator.AdministratorController;
import org.gui.users.coordinator.CoordinatorController;
import org.util.Auth;
import org.util.Cryptography;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginController {
    private Person user;

    @FXML
    private TextField emailTextField;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private Label systemLabel;

    public void showStage() {
        FXMLLoader loader = new FXMLLoader( getClass().getResource("/org/gui/login/LoginVista.fxml") );
        loader.setController(this);
        Parent root = null;
        try{
            root = loader.load();
        } catch(IOException ioe) {
            Logger.getLogger( LoginController.class.getName() ).log(Level.SEVERE, null, ioe);
        }
        loginStage = new Stage();
        loginStage.setScene( new Scene(root) );
        loginStage.show();
    }

    private Stage loginStage;

    public Person getUser() {
        return user;
    }

    @FXML
    void cancelButtonPressed(ActionEvent event) {
        closeStage(event);
    }

    @FXML
    void loginButtonPressed(ActionEvent event) {
        Auth auth = Auth.getInstance();
        auth.setEmail( emailTextField.getText() );
        auth.setPassword( Cryptography.cryptSHA2( passwordTextField.getText() ) );
        auth.login();
        user = auth.getUser();
        if(user != null) {
            loginStage.hide();
            openAWindowBySpecifiedPerson();
            user = null;
            loginStage.show();
            passwordTextField.setText("");
            emailTextField.setText("");
        } else {
            systemLabel.setText("Los datos son incorrectos.");
        }
    }

    private void openAWindowBySpecifiedPerson() {
        if(user != null) {
            System.out.println(user);
            if(user instanceof Professor) {
                System.out.println("Agregar ventana profesor");
            } else if(user instanceof Coordinator) {
                CoordinatorController coordinatorController = new CoordinatorController();
                coordinatorController.showStage();
            } else if(user instanceof Practitioner) {
                System.out.println("Agregar ventana practicante");
            } else {
                AdministratorController administratorController = new AdministratorController();
                administratorController.showStage();
            }
        }
    }

    private void closeStage(ActionEvent event) {
        Stage stage = ( (Stage) ( (Node) event.getSource() ).getScene().getWindow() );
        stage.close();
    }

}
