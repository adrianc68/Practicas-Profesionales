package org.gui.users.coordinator.project.registerproject.screens;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.scene.layout.AnchorPane;
import org.domain.Company;
import org.domain.Project;
import org.gui.users.coordinator.project.registerproject.screens.secondscreen.textinput.TextInputController;
import org.gui.users.coordinator.project.registerproject.company.CompanyController;

public class ScreenRegisterController implements Initializable {
    private String textSelected;
    private Company selectedCompany;
    private Project newProject;
    private ObservableList<String> mediateObjetivesObservableList;
    private ObservableList<String> resourcesObservableList;
    private ObservableList<String> activitiesObservableList;
    private ObservableList<String> immediateObjetivesObservableList;
    private ObservableList<String> methodologiesObservableList;
    private ObservableList<String> responsabilitiesObservableList;

    @FXML
    private Button selectCompanyButton;

    @FXML
    private Label companyNameLabel;

    @FXML
    private Label companyAddressLabel;

    @FXML
    private Label companyCityLabel;

    @FXML
    private Label companyStateLabel;

    @FXML
    private Label companyEmailLabel;

    @FXML
    private Label companyPhoneNumberLabel;

    @FXML
    private Label companyDirectUsersLabel;

    @FXML
    private Label companyIndirectUsersLabel;

    @FXML
    private Label companySectorLabel;

    @FXML
    private TextField projectNameInput;

    @FXML
    private TextField projectScheduleInput;

    @FXML
    private TextField projectResponsableNameInput;

    @FXML
    private TextField projectResponsableChargeInput;

    @FXML
    private TextField projectResponsableEmailInput;

    @FXML
    private TextField projectDurationInput;

    @FXML
    private TextArea projectDescriptionInput;

    @FXML
    private TextArea projectPurposeInput;

    @FXML
    private ListView<String> mediateObjetives;

    @FXML
    private ListView<String> resources;

    @FXML
    private ListView<String> activities;

    @FXML
    private ListView<String> immediateObjetives;

    @FXML
    private ListView<String> methodologies;

    @FXML
    private ListView<String> responsabilities;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mediateObjetivesObservableList = FXCollections.observableArrayList();
        resourcesObservableList = FXCollections.observableArrayList();
        activitiesObservableList = FXCollections.observableArrayList();
        immediateObjetivesObservableList = FXCollections.observableArrayList();
        methodologiesObservableList = FXCollections.observableArrayList();
        responsabilitiesObservableList = FXCollections.observableArrayList();
    }

    public Project getNewProject() {
        newProject = new Project();
        newProject.setName( projectNameInput.getText() );
        newProject.setGeneralDescription( projectDescriptionInput.getText() );
        newProject.setGeneralPurpose( projectPurposeInput.getText() );
        newProject.setSchedule( projectScheduleInput.getText() );
        newProject.setDuration( Integer.valueOf(projectDurationInput.getText()) );
        newProject.setNameResponsable( projectResponsableNameInput.getText() );
        newProject.setChargeResponsable( projectResponsableChargeInput.getText() );
        newProject.setEmailResponsable( projectResponsableEmailInput.getText() );
        newProject.setCompany(selectedCompany);
        newProject.setActivities( getListFromObservableList(activitiesObservableList) );
        newProject.setMediateObjectives( getListFromObservableList(mediateObjetivesObservableList) );
        newProject.setImmediateObjetives( getListFromObservableList(immediateObjetivesObservableList) );
        newProject.setResources( getListFromObservableList(resourcesObservableList) );
        newProject.setMethodologies( getListFromObservableList(methodologiesObservableList) );
        newProject.setResponsibilities( getListFromObservableList(responsabilitiesObservableList) );
        return newProject;
    }

    @FXML
    void activityAddButtonPressed(ActionEvent event) {
        addInputToListView(activitiesObservableList, activities);
    }

    @FXML
    void activityRemoveButtonPressed(ActionEvent event) {
        textSelected = activities.getSelectionModel().getSelectedItem();
        removeFromListView(activities, textSelected);
    }

    @FXML
    void immediateAddButtonPressed(ActionEvent event) {
        addInputToListView(immediateObjetivesObservableList, immediateObjetives);
    }

    @FXML
    void immediateRemoveButtonPressed(ActionEvent event) {
        textSelected = immediateObjetives.getSelectionModel().getSelectedItem();
        removeFromListView(immediateObjetives, textSelected);
    }

    @FXML
    void mediateAddButtonPressed(ActionEvent event) {
        addInputToListView(mediateObjetivesObservableList, mediateObjetives);
    }

    @FXML
    void mediateRemoveButtonPressed(ActionEvent event) {
        textSelected = mediateObjetives.getSelectionModel().getSelectedItem();
        removeFromListView(mediateObjetives, textSelected);
    }

    @FXML
    void methodologyAddButtonPressed(ActionEvent event) {
        addInputToListView(methodologiesObservableList, methodologies);
    }

    @FXML
    void methodologyRemoveButtonPressed(ActionEvent event) {
        textSelected = methodologies.getSelectionModel().getSelectedItem();
        removeFromListView(methodologies, textSelected);
    }

    @FXML
    void resourcesAddButtonPressed(ActionEvent event) {
        addInputToListView(resourcesObservableList, resources);
    }

    @FXML
    void resourceRemoveButtonPressed(ActionEvent event) {
        textSelected = resources.getSelectionModel().getSelectedItem();
        removeFromListView(resources, textSelected);
    }

    @FXML
    void responsabilityAddButtonPressed(ActionEvent event) {
        addInputToListView(responsabilitiesObservableList, responsabilities);
    }

    @FXML
    void responsabilityRemoveButtonPressed(ActionEvent event) {
        textSelected = responsabilities.getSelectionModel().getSelectedItem();
        removeFromListView(responsabilities, textSelected);
    }

    @FXML
    void selectCompanyButtonPressed(ActionEvent event) {
        selectCompanyButton.setDisable(true);
        CompanyController companyController = new CompanyController();
        companyController.showStage();
        if( companyController.getSelectedCompanyCard() != null ){
            selectedCompany = companyController.getSelectedCompanyCard().getCompany();
            companyNameLabel.setText( selectedCompany.getName() );
            companyAddressLabel.setText( selectedCompany.getAddress() );
            companyCityLabel.setText( selectedCompany.getCity() );
            companyStateLabel.setText( selectedCompany.getState() );
            companyEmailLabel.setText( selectedCompany.getEmail() );
            companyPhoneNumberLabel.setText( selectedCompany.getPhoneNumber() );
            companyDirectUsersLabel.setText( String.valueOf( selectedCompany.getDirectUsers() ) );
            companyIndirectUsersLabel.setText( String.valueOf( selectedCompany.getIndirectUsers() ) );
            companySectorLabel.setText( selectedCompany.getSector().getSector() );
        }
        selectCompanyButton.setDisable(false);
    }

    private void removeFromListView(ListView<String> listView, String remove) {
        listView.getItems().remove(remove);
    }

    private void addInputToListView(ObservableList<String> observableList, ListView<String> listView) {
        TextInputController textInputController = new TextInputController();
        textInputController.showStage();
        if(textInputController.getInput() != null) {
            observableList.add(textInputController.getInput());
            listView.setItems(observableList);
        }
    }

    private List<String> getListFromObservableList(ObservableList<String> observableList) {
        List<String> list = new ArrayList<>();
        for ( String input : observableList ) {
            list.add(input);
        }
        return list;
    }

}
