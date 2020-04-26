package org.gui.users.coordinator.project.registerproject.company;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import org.database.dao.CompanyDAO;
import org.domain.Company;
import org.gui.users.coordinator.card.CompanyCard;
import org.gui.users.coordinator.project.registerproject.company.addcompany.AddCompanyController;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CompanyController implements Initializable {
    @FXML
    private AnchorPane rootPane;

    @FXML
    private FlowPane companiesPane;

    private CompanyCard selectedCompanyCard;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setCompanyToScrollPaneFromDatabase();
    }

    public void showStage() {
        FXMLLoader loader = new FXMLLoader( getClass().getResource("/org/gui/users/coordinator/project/registerproject/company/CompanyVista.fxml") );
        loader.setController(this);
        Parent root = null;
        try{
            root = loader.load();
        } catch(IOException ioe) {
            Logger.getLogger(CompanyController.class.getName()).log(Level.WARNING, null, ioe);
        }
        Stage companyStage = new Stage();
        companyStage.setScene( new Scene(root) );
        companyStage.showAndWait();
    }

    public CompanyCard getSelectedCompanyCard() {
        return selectedCompanyCard;
    }

    @FXML
    void addCompanyButtonPressed(ActionEvent event) {
        rootPane.setDisable(true);
        AddCompanyController addCompanyController = new AddCompanyController();
        addCompanyController.showStage();
        if( addCompanyController.getStatus() ) {
            addCompanyInACardToScrollPane( addCompanyController.getNewCompany() );
        }
        rootPane.setDisable(false);
    }

    @FXML
    void closeButtonPressed(ActionEvent event) {
        closeStage(event);
    }

    private void setCompanyToScrollPaneFromDatabase() {
        List<Company> companies = new CompanyDAO().getAllCompaniesFromLastCourse();
        if(companies != null) {
            for ( Company company : companies ) {
                addCompanyInACardToScrollPane(company);
            }
        }
    }

    private void addCompanyInACardToScrollPane(Company company) {
        CompanyCard card = new CompanyCard(company);
        card.setOnMouseReleased( (MouseEvent mouseEvent) -> {
            selectedCompanyCard = card;
            Stage stage = ( (Stage) ( (Node) mouseEvent.getSource() ).getScene().getWindow() );
            stage.close();
        });
        companiesPane.getChildren().add(card);
    }

    private void closeStage(ActionEvent event) {
        Stage stage = ( (Stage) ( (Node) event.getSource() ).getScene().getWindow() );
        stage.close();
    }

}
