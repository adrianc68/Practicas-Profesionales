package org.gui.users.coordinator.project.editionproject.company.addcompany;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.database.dao.CompanyDAO;
import org.domain.Company;
import org.domain.Coordinator;
import org.domain.Course;
import org.domain.Sector;
import org.util.Security;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddCompanyController implements Initializable {
    private boolean addOperationStatus;
    private boolean validInputData;
    private Company newCompany;
    private Coordinator coordinator;
    private Course course;

    @FXML
    private TextField companyNameTextField;

    @FXML
    private TextField companyAddressTextField;

    @FXML
    private TextField companyCityTextField;

    @FXML
    private TextField companyStateTextField;

    @FXML
    private TextField companyEmailTextField;

    @FXML
    private TextField companyPhoneNumberTextField;

    @FXML
    private TextField companyDirectUsersTextField;

    @FXML
    private TextField companyIndirectUsersTextField;

    @FXML
    private ChoiceBox<String> companySectorChoiceBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        validInputData = false;
        setListenerToTextFields();
        companySectorChoiceBox.getItems().add( Sector.PRIMARY.getSector() );
        companySectorChoiceBox.getItems().add( Sector.SECONDARY.getSector() );
        companySectorChoiceBox.getItems().add( Sector.TERTIARY.getSector() );
        // USE AUTH CLASS TO INITIALIZA A COORDINATOR AND COURSE
        System.out.println("To use correctly this feature implement Auth feature");
        coordinator = new Coordinator();
        coordinator.setId(2);
        course = new Course();
        course.setId(1);
    }

    public void showStage() {
        FXMLLoader loader = new FXMLLoader( getClass().getResource("/org/gui/users/coordinator/project/editionproject/company/addcompany/AddCompanyVista.fxml") );
        loader.setController(this);
        Parent root = null;
        try{
            root = loader.load();
        } catch(IOException ioe) {
            Logger.getLogger(AddCompanyController.class.getName()).log(Level.WARNING, null, ioe);
        }
        Stage addCompanyStage = new Stage();
        addCompanyStage.setScene( new Scene(root) );
        addCompanyStage.showAndWait();
    }

    public boolean getAddOperationStatus() {
        return addOperationStatus;
    }

    public Company getNewCompany() {
        return newCompany;
    }

    @FXML
    void cancelButtonPressed(ActionEvent event) {
        addOperationStatus = false;
        closeStage(event);
    }

    @FXML
    void saveButtonPressed(ActionEvent event) {
        if(validInputData) {
            newCompany = new Company();
            newCompany.setName( companyNameTextField.getText() );
            newCompany.setAddress( companyAddressTextField.getText() );
            newCompany.setPhoneNumber( companyPhoneNumberTextField.getText() );
            newCompany.setEmail( companyEmailTextField.getText() );
            newCompany.setSector( getSectorFromField(companySectorChoiceBox.getSelectionModel().getSelectedItem()) );
            newCompany.setCity( companyCityTextField.getText() );
            newCompany.setState( companyStateTextField.getText() );
            newCompany.setDirectUsers( Integer.valueOf(companyDirectUsersTextField.getText()) );
            newCompany.setIndirectUsers( Integer.valueOf(companyIndirectUsersTextField.getText()) );
            newCompany.setCoordinator(coordinator);
            newCompany.setCourse(course);
            addCompanyToDatabase();
            closeStage(event);
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

    private void addCompanyToDatabase() {
        CompanyDAO companyDAO = new CompanyDAO();
        int idCompany = 0;
        idCompany = companyDAO.addCompany(newCompany);
        newCompany.setId( idCompany );
        // T-O Ternary Operator!
        addOperationStatus = (idCompany == 0) ? false : true ;
    }

    private void setListenerToTextFields() {
        Map<Object[], TextField> textfieldsMap = new LinkedHashMap<>();
        Object[] nameConstraints = {Security.NAME_PATTERN, Security.NAME_LENGTH};
        textfieldsMap.put(nameConstraints, companyNameTextField);
        Object[] addressConstraints = {Security.ADDRESS_PATTERN, Security.ADDRESS_LENGTH};
        textfieldsMap.put(addressConstraints, companyAddressTextField);
        Object[] cityConstraints = {Security.CITY_PATTERN, Security.CITY_LENGTH};
        textfieldsMap.put(cityConstraints, companyCityTextField);
        Object[] stateConstraints = {Security.STATE_PATTERN, Security.STATE_LENGTH};
        textfieldsMap.put(stateConstraints, companyStateTextField);
        Object[] emailConstraints = {Security.EMAIL_PATTERN, Security.EMAIL_LENGTH};
        textfieldsMap.put(emailConstraints, companyEmailTextField);
        Object[] directUsersContraints = {Security.NUMBER_PATTERN, Security.NUMBER_LENGTH};
        textfieldsMap.put(directUsersContraints, companyDirectUsersTextField);
        Object[] phoneNumberConstraints = {Security.PHONE_NUMBER_PATTERN, Security.PHONE_NUMBER_LENGTH};
        textfieldsMap.put(phoneNumberConstraints, companyPhoneNumberTextField);
        Object[] indirectUsersConstraints = {Security.NUMBER_PATTERN, Security.NUMBER_LENGTH};
        textfieldsMap.put(indirectUsersConstraints, companyIndirectUsersTextField);
        for (Map.Entry<Object[], TextField> entry : textfieldsMap.entrySet() ) {
            entry.getValue().textProperty().addListener( ((observable, oldValue, newValue) -> {
                if( !Security.doesStringMatchPattern( newValue, ( (String) entry.getKey()[0] ) ) || Security.isStringLargerThanLimitOrEmpty( newValue, ( (Integer) entry.getKey()[1]) ) ){
                    validInputData = false;
                    entry.getValue().setStyle("-fx-background-color:red;");
                } else {
                    validInputData = true;
                    entry.getValue().setStyle("-fx-background-color:green;");
                }
            }));
        }
    }

    private void closeStage(Event event) {
        Stage stage = ( (Stage) ( (Node) event.getSource() ).getScene().getWindow() );
        stage.close();
    }

}
