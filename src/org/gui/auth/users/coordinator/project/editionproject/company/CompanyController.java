package org.gui.auth.users.coordinator.project.editionproject.company;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.database.dao.OrganizationDAO;
import org.domain.Organization;
import org.gui.auth.resources.card.OrganizationCard;
import org.gui.auth.users.coordinator.project.editionproject.company.addcompany.AddCompanyController;
import org.util.CSSProperties;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CompanyController implements Initializable {
    private OrganizationCard selectedOrganizationCard;
    private double mousePositionOnX;
    private double mousePositionOnY;
    @FXML private AnchorPane rootStage;
    @FXML private FlowPane companiesPane;
    @FXML private Button closeButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setStyleClass();
        setCompanyToScrollPaneFromDatabase();
    }

    public void showStage() {
        FXMLLoader loader = new FXMLLoader( getClass().getResource("/org/gui/auth/users/coordinator/project/editionproject/company/CompanyVista.fxml") );
        loader.setController(this);
        Parent root = null;
        try{
            root = loader.load();
        } catch(IOException ioe) {
            Logger.getLogger(CompanyController.class.getName()).log(Level.WARNING, null, ioe);
        }
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.TRANSPARENT);
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.showAndWait();
    }

    public OrganizationCard getSelectedOrganizationCard() {
        return selectedOrganizationCard;
    }

    @FXML
    void addCompanyButtonPressed(ActionEvent event) {
        rootStage.setDisable(true);
        AddCompanyController addCompanyController = new AddCompanyController();
        addCompanyController.showStage();
        if( addCompanyController.getAddOperationStatus() ) {
            addCompanyInACardToScrollPane( addCompanyController.getNewOrganization() );
        }
        rootStage.setDisable(false);
    }

    @FXML
    void closeButtonPressed(ActionEvent event) {
        Stage stage = ( (Stage) ( (Node) event.getSource() ).getScene().getWindow() );
        stage.close();
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

    private void setCompanyToScrollPaneFromDatabase() {
        List<Organization> companies = new OrganizationDAO().getAllCompaniesFromLastCourse();
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

    private void setStyleClass() {
        rootStage.getStylesheets().clear();
        rootStage.getStylesheets().add(getClass().getResource("../../../../../resources/" + CSSProperties.readTheme().getTheme() ).toExternalForm() );
    }

}
