package org.gui.auth.users.coordinator.project.editionproject.company.addcompany;

import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.database.dao.OrganizationDAO;
import org.domain.Organization;
import org.domain.Coordinator;
import org.domain.Course;
import org.domain.Sector;
import org.gui.ValidatorController;
import org.util.Auth;
import org.util.CSSProperties;
import org.util.Validator;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;
import static org.gui.auth.resources.alerts.OperationAlert.showSuccessfullAlert;
import static org.gui.auth.resources.alerts.OperationAlert.showUnsuccessfullAlert;

public class AddCompanyController extends ValidatorController implements Initializable {
    private boolean addOperationStatus;
    private Organization newOrganization;
    private Coordinator coordinator;
    private Course course;
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
        setStyleClass(rootStage, getClass().getResource("../../../../../../resources/" + CSSProperties.readTheme().getTheme() ).toExternalForm()  );
        addOperationStatus = false;
        initValidatorToTextFields();
        initChoiceBoxAndSetValidator();
        coordinator = ( (Coordinator) Auth.getInstance().getCurrentUser() );
        if(coordinator != null) {
            course = coordinator.getCourse();
        }
    }

    public void showStage() {
        loadFXMLFile(getClass().getResource("/org/gui/auth/users/coordinator/project/editionproject/company/addcompany/AddCompanyVista.fxml"), this);
        stage.showAndWait();
    }

    public boolean getAddOperationStatus() {
        return addOperationStatus;
    }

    public Organization getNewOrganization() {
        return newOrganization;
    }

    @FXML
    protected void cancelButtonPressed(ActionEvent event) {
        stage.close();
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
    protected void saveButtonPressed(ActionEvent event) {
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

    private void initChoiceBoxAndSetValidator() {
        companySectorChoiceBox.getItems().add( Sector.PRIMARY.getSector() );
        companySectorChoiceBox.getItems().add( Sector.SECONDARY.getSector() );
        companySectorChoiceBox.getItems().add( Sector.TERTIARY.getSector() );
        companySectorChoiceBox.getSelectionModel().selectedIndexProperty().addListener( (observable, oldValue, newValue) -> {
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
        Map<TextInputControl, Object[]> validatorMap = new HashMap<>();
        Object[] nameConstraints = {Validator.NAME_PATTERN, Validator.NAME_LENGTH, checkIconName};
        validatorMap.put(companyNameTextField, nameConstraints);
        Object[] addressConstraints = {Validator.ADDRESS_PATTERN, Validator.ADDRESS_LENGTH, checkIconAddress};
        validatorMap.put(companyAddressTextField, addressConstraints);
        Object[] emailConstraints = {Validator.EMAIL_PATTERN, Validator.EMAIL_LENGTH, checkIconEmail};
        validatorMap.put(companyEmailTextField, emailConstraints);
        Object[] phoneNumberConstraints = {Validator.PHONE_NUMBER_PATTERN, Validator.PHONE_NUMBER_LENGTH, checkIconPhoneNumber};
        validatorMap.put(companyPhoneNumberTextField, phoneNumberConstraints);
        Object[] cityConstraints = {Validator.CITY_PATTERN, Validator.CITY_LENGTH, checkIconCity};
        validatorMap.put(companyCityTextField, cityConstraints);
        Object[] stateConstraints = {Validator.STATE_PATTERN, Validator.STATE_LENGTH, checkIconState};
        validatorMap.put(companyStateTextField, stateConstraints);
        Object[] directUsersContraints = {Validator.NUMBER_PATTERN, Validator.NUMBER_LENGTH, checkIconDirectUsers};
        validatorMap.put(companyDirectUsersTextField, directUsersContraints);
        Object[] indirectUsersConstraints = {Validator.NUMBER_PATTERN, Validator.NUMBER_LENGTH, checkIconIndirectUsers};
        validatorMap.put(companyIndirectUsersTextField, indirectUsersConstraints);
        initValidatorToTextInputControl(validatorMap);
    }

}
