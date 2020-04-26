package org.gui.users.coordinator.project.registerproject.company.addcompany;

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
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddCompanyController implements Initializable {
    private boolean status;
    private Company newCompany;
    private Coordinator coordinator;
    private Course course;

    @FXML
    private TextField companyNameInput;

    @FXML
    private TextField companyAddressInput;

    @FXML
    private TextField companyCityInput;

    @FXML
    private TextField companyStateInput;

    @FXML
    private TextField companyEmailInput;

    @FXML
    private TextField companyPhoneNumberInput;

    @FXML
    private TextField companyDirectUsersInput;

    @FXML
    private TextField companyIndirectUsersInput;

    @FXML
    private ChoiceBox<String> companySectorChoiceBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        companySectorChoiceBox.getItems().add( Sector.PRIMARY.getSector() );
        companySectorChoiceBox.getItems().add( Sector.SECONDARY.getSector() );
        companySectorChoiceBox.getItems().add( Sector.TERTIARY.getSector() );
        coordinator = new Coordinator();
        coordinator.setId(1);
        course = new Course();
        course.setId(1);
    }

    public void showStage() {
        FXMLLoader loader = new FXMLLoader( getClass().getResource("/org/gui/users/coordinator/project/registerproject/company/addcompany/AddCompanyVista.fxml") );
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

    public boolean getStatus() {
        return status;
    }

    public Company getNewCompany() {
        return newCompany;
    }

    @FXML
    void cancelButtonPressed(ActionEvent event) {
        status = false;
        closeStage(event);
    }

    @FXML
    void saveButtonPressed(ActionEvent event) {
        newCompany = new Company();
        newCompany.setName( companyNameInput.getText() );
        newCompany.setAddress( companyAddressInput.getText() );
        newCompany.setPhoneNumber( companyPhoneNumberInput.getText() );
        newCompany.setEmail( companyEmailInput.getText() );
        newCompany.setSector( getSectorFromField(companySectorChoiceBox.getSelectionModel().getSelectedItem()) );
        newCompany.setCity( companyCityInput.getText() );
        newCompany.setState( companyStateInput.getText() );
        newCompany.setDirectUsers( Integer.valueOf(companyDirectUsersInput.getText()) );
        newCompany.setIndirectUsers( Integer.valueOf(companyIndirectUsersInput.getText()) );
        newCompany.setCoordinator(coordinator);
        newCompany.setCourse(course);
        addCompanyToDatabase();
        closeStage(event);
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
        status = (idCompany == 0) ? false : true ;
    }

    private void closeStage(Event event) {
        Stage stage = ( (Stage) ( (Node) event.getSource() ).getScene().getWindow() );
        stage.close();
    }





}
