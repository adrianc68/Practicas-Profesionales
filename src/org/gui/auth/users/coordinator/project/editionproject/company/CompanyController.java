package org.gui.auth.users.coordinator.project.editionproject.company;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Modality;
import org.database.dao.OrganizationDAO;
import org.domain.Organization;
import org.gui.Controller;
import org.gui.auth.resources.alerts.OperationAlert;
import org.gui.auth.resources.card.OrganizationCard;
import org.gui.auth.users.coordinator.project.editionproject.company.addcompany.AddCompanyController;
import org.util.CSSProperties;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CompanyController extends Controller implements Initializable {
    private OrganizationCard selectedOrganizationCard;
    @FXML private AnchorPane rootStage;
    @FXML private FlowPane companiesPane;
    @FXML private Button closeButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setStyleClass(rootStage, getClass().getResource("../../../../../resources/" + CSSProperties.readTheme().getTheme() ).toExternalForm() );
        setCompanyToScrollPaneFromDatabase();
    }

    public void showStage() {
        loadFXMLFile(getClass().getResource("/org/gui/auth/users/coordinator/project/editionproject/company/CompanyVista.fxml"), this);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    public OrganizationCard getSelectedOrganizationCard() {
        return selectedOrganizationCard;
    }

    @FXML
    protected void addCompanyButtonPressed(ActionEvent event) {
        rootStage.setDisable(true);
        AddCompanyController addCompanyController = new AddCompanyController();
        addCompanyController.showStage();
        if( addCompanyController.getAddOperationStatus() ) {
            addCompanyInACardToScrollPane( addCompanyController.getNewOrganization() );
        }
        rootStage.setDisable(false);
    }

    @FXML
    protected void closeButtonPressed(ActionEvent event) {
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

    private void setCompanyToScrollPaneFromDatabase() {
        List<Organization> companies = null;
        try {
            companies = new OrganizationDAO().getAllCompaniesFromLastCourse();
        } catch (SQLException sqlException) {
            OperationAlert.showLostConnectionAlert();
            Logger.getLogger( CompanyController.class.getName() ).log(Level.WARNING, null, sqlException);
        }
        if(companies != null) {
            for ( Organization organization : companies ) {
                addCompanyInACardToScrollPane(organization);
            }
        }
    }

    private void addCompanyInACardToScrollPane(Organization organization) {
        OrganizationCard card = new OrganizationCard(organization);
        card.setOnMouseReleased( (MouseEvent mouseEvent) -> {
            selectedOrganizationCard = card;
            closeButton.fire();
        });
        companiesPane.getChildren().add(card);
    }

}
