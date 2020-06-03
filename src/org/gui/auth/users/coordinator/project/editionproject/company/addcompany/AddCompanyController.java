package org.gui.auth.users.coordinator.project.editionproject.company.addcompany;

import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.database.dao.OrganizationDAO;
import org.domain.Organization;
import org.domain.Coordinator;
import org.domain.Course;
import org.domain.Sector;
import org.util.Auth;
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
import static org.gui.auth.resources.alerts.OperationAlert.showSuccessfullAlert;
import static org.gui.auth.resources.alerts.OperationAlert.showUnsuccessfullAlert;

public class AddCompanyController implements Initializable {
    private double mousePositionOnX;
    private double mousePositionOnY;
    private boolean addOperationStatus;
    private Organization newOrganization;
    private Coordinator coordinator;
    private Course course;
    private Map<Control, Boolean> interruptorMap = new HashMap<>();
    @FXML private TextField companyNameTextField;
    @FXML private TextField companyAddressTextField;
    @FXML private TextField companyCityTextField;
    @FXML private TextField companyStateTextField;
    @FXML private TextField companyEmailTextField;
    @FXML private TextField companyPhoneNumberTextField;
    @FXML private TextField companyDirectUsersTextField;
    @FXML private TextField companyIndirectUsersTextField;
    @FXML private ChoiceBox<String> companySectorChoiceBox;
    @FXML private Button closeButton;
    @FXML private AnchorPane rootStage;
    @FXML private MaterialDesignIconView checkIconName;
    @FXML private MaterialDesignIconView checkIconAddress;
    @FXML private MaterialDesignIconView checkIconEmail;
    @FXML private MaterialDesignIconView checkIconPhoneNumber;
    @FXML private MaterialDesignIconView checkIconCity;
    @FXML private MaterialDesignIconView checkIconState;
    @FXML private MaterialDesignIconView checkIconDirectUsers;
    @FXML private MaterialDesignIconView checkIconIndirectUsers;
    @FXML private MaterialDesignIconView checkIconSector;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setStyleClass();
        addOperationStatus = false;
        initValidatorToTextFields();
        initChoiceBoxAndSetValidator();
        coordinator = ( (Coordinator) Auth.getInstance().getUser() );
        if(coordinator != null) {
            course = coordinator.getCourse();
        }
    }

    public void showStage() {
        FXMLLoader loader = new FXMLLoader( getClass().getResource("/org/gui/auth/users/coordinator/project/editionproject/company/addcompany/AddCompanyVista.fxml") );
        loader.setController(this);
        Parent root = null;
        try{
            root = loader.load();
        } catch(IOException ioe) {
            Logger.getLogger( AddCompanyController.class.getName() ).log(Level.WARNING, null, ioe);
        }
        Stage stage = new Stage();
        stage.initStyle(StageStyle.TRANSPARENT);
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.showAndWait();
    }

    public boolean getAddOperationStatus() {
        return addOperationStatus;
    }

    public Organization getNewOrganization() {
        return newOrganization;
    }

    @FXML
    void cancelButtonPressed(ActionEvent event) {
        Stage stage = ( (Stage) ( (Node) event.getSource() ).getScene().getWindow() );
        stage.close();
    }

    @FXML
    void saveButtonPressed(ActionEvent event) {
        if( verifyInputData() ) {
            newOrganization = new Organization();
            newOrganization.setName( companyNameTextField.getText() );
            newOrganization.setAddress( companyAddressTextField.getText() );
            newOrganization.setPhoneNumber( companyPhoneNumberTextField.getText() );
            newOrganization.setEmail( companyEmailTextField.getText() );
            newOrganization.setSector( getSectorFromField(companySectorChoiceBox.getSelectionModel().getSelectedItem() ) );
            newOrganization.setCity( companyCityTextField.getText() );
            newOrganization.setState( companyStateTextField.getText() );
            newOrganization.setDirectUsers( Integer.valueOf( companyDirectUsersTextField.getText() ) );
            newOrganization.setIndirectUsers( Integer.valueOf( companyIndirectUsersTextField.getText() ) );
            newOrganization.setCoordinator(coordinator);
            newOrganization.setCourse(course);
            addOrganizationToDatabase();
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

    private void addOrganizationToDatabase() {
        OrganizationDAO organizationDAO = new OrganizationDAO();
        int idCompany;
        idCompany = organizationDAO.addOrganization(newOrganization);
        newOrganization.setId( idCompany );
        if(idCompany != 0) {
            addOperationStatus = true;
            String title = "Se agregó la organizacion";
            String contentText = "¡Se ha agregado la organizacion con exito";
            showSuccessfullAlert(title, contentText);
        } else {
            String title = "No se pudo agregar la organizacion";
            String contentText = "¡No se pudo agregar la organizacion!";
            showUnsuccessfullAlert(title, contentText);
        }
    }

    private Sector getSectorFromField(String value) {
        Sector sectorRequired = null;
        for ( Sector sector : Sector.values() ) {
            if( sector.getSector().equals(value) ) {
                sectorRequired = sector;
                break;
            }
        }
        return sectorRequired;
    }

    private boolean verifyInputData() {
        boolean dataInputValid = true;
        for( Map.Entry<Control, Boolean> entry : interruptorMap.entrySet() ) {
            if( !entry.getValue() ){
                dataInputValid = false;
                break;
            }
        }
        return dataInputValid;
    }

    private void initChoiceBoxAndSetValidator() {
        companySectorChoiceBox.getItems().add( Sector.PRIMARY.getSector() );
        companySectorChoiceBox.getItems().add( Sector.SECONDARY.getSector() );
        companySectorChoiceBox.getItems().add( Sector.TERTIARY.getSector() );
        companySectorChoiceBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if( newValue != null ) {
                interruptorMap.replace(companySectorChoiceBox, true);
                checkIconSector.getStyleClass().add("correctlyTextField");
            } else {
                interruptorMap.replace(companySectorChoiceBox, false);
                checkIconSector.getStyleClass().add("wrongTextField");
            }
        });
    }

    private void initValidatorToTextFields() {
        interruptorMap = new LinkedHashMap<>();
        interruptorMap.put(companyNameTextField, false);
        interruptorMap.put(companyAddressTextField, false);
        interruptorMap.put(companyEmailTextField, false);
        interruptorMap.put(companyPhoneNumberTextField, false);
        interruptorMap.put(companyCityTextField, false);
        interruptorMap.put(companyStateTextField, false);
        interruptorMap.put(companyDirectUsersTextField, false);
        interruptorMap.put(companyIndirectUsersTextField, false);
        interruptorMap.put(companySectorChoiceBox, false);
        Map<TextField, Object[]> validator = new HashMap<>();
        Object[] nameConstraints = {Validator.NAME_PATTERN, Validator.NAME_LENGTH, checkIconName};
        validator.put(companyNameTextField, nameConstraints);
        Object[] addressConstraints = {Validator.ADDRESS_PATTERN, Validator.ADDRESS_LENGTH, checkIconAddress};
        validator.put(companyAddressTextField, addressConstraints);
        Object[] emailConstraints = {Validator.EMAIL_PATTERN, Validator.EMAIL_LENGTH, checkIconEmail};
        validator.put(companyEmailTextField, emailConstraints);
        Object[] phoneNumberConstraints = {Validator.PHONE_NUMBER_PATTERN, Validator.PHONE_NUMBER_LENGTH, checkIconPhoneNumber};
        validator.put(companyPhoneNumberTextField, phoneNumberConstraints);
        Object[] cityConstraints = {Validator.CITY_PATTERN, Validator.CITY_LENGTH, checkIconCity};
        validator.put(companyCityTextField, cityConstraints);
        Object[] stateConstraints = {Validator.STATE_PATTERN, Validator.STATE_LENGTH, checkIconState};
        validator.put(companyStateTextField, stateConstraints);
        Object[] directUsersContraints = {Validator.NUMBER_PATTERN, Validator.NUMBER_LENGTH, checkIconDirectUsers};
        validator.put(companyDirectUsersTextField, directUsersContraints);
        Object[] indirectUsersConstraints = {Validator.NUMBER_PATTERN, Validator.NUMBER_LENGTH, checkIconIndirectUsers};
        validator.put(companyIndirectUsersTextField, indirectUsersConstraints);
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
        rootStage.getStylesheets().add(getClass().getResource("../../../../../../resources/" + CSSProperties.readTheme().getTheme() ).toExternalForm() );
    }

}
