package org.gui.auth.users.coordinator.project.editionproject.screens;

import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.layout.AnchorPane;
import org.domain.Organization;
import org.domain.Project;
import org.gui.auth.users.coordinator.project.editionproject.screens.secondscreen.textinput.TextInputController;
import org.gui.auth.users.coordinator.project.editionproject.company.CompanyController;
import org.util.Validator;

public class ScreenController implements Initializable {
    private Project project;
    private String textListViewSelected;
    private Organization selectedOrganization;
    private ObservableList<String> mediateObjetivesObservableList;
    private ObservableList<String> resourcesObservableList;
    private ObservableList<String> activitiesObservableList;
    private ObservableList<String> immediateObjetivesObservableList;
    private ObservableList<String> methodologiesObservableList;
    private ObservableList<String> responsabilitiesObservableList;
    private Map<Control, Boolean> interruptorMap = new HashMap<>();
    @FXML private Button selectCompanyButton;
    @FXML private Label companyNameLabel;
    @FXML private Label companyAddressLabel;
    @FXML private Label companyCityLabel;
    @FXML private Label companyStateLabel;
    @FXML private Label companyEmailLabel;
    @FXML private Label companyPhoneNumberLabel;
    @FXML private Label companyDirectUsersLabel;
    @FXML private Label companyIndirectUsersLabel;
    @FXML private Label companySectorLabel;
    @FXML private TextField projectResponsableNameTextField;
    @FXML private TextField projectResponsableChargeTextField;
    @FXML private TextField projectResponsableEmailTextField;
    @FXML private TextField projectNameTextField;
    @FXML private TextField projectScheduleTextField;
    @FXML private TextField projectDurationTextField;
    @FXML private TextArea projectDescriptionTextArea;
    @FXML private TextArea projectPurposeTextArea;
    @FXML private ListView<String> mediateObjetivesListView;
    @FXML private ListView<String> resourcesListView;
    @FXML private ListView<String> activitiesListView;
    @FXML private ListView<String> immediateObjetivesListView;
    @FXML private ListView<String> methodologiesListView;
    @FXML private ListView<String> responsabilitiesListView;
    @FXML private AnchorPane rootStage;
    @FXML private MaterialDesignIconView checkIconName;
    @FXML private MaterialDesignIconView checkIconGeneralDescription;
    @FXML private MaterialDesignIconView checkIconGeneralPurpose;
    @FXML private MaterialDesignIconView checkIconSchedule;
    @FXML private MaterialDesignIconView checkIconDuration;
    @FXML private MaterialDesignIconView checkIconNameResponsable;
    @FXML private MaterialDesignIconView checkIconChargeResponsable;
    @FXML private MaterialDesignIconView checkIconEmail;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mediateObjetivesObservableList = FXCollections.observableArrayList();
        resourcesObservableList = FXCollections.observableArrayList();
        activitiesObservableList = FXCollections.observableArrayList();
        immediateObjetivesObservableList = FXCollections.observableArrayList();
        methodologiesObservableList = FXCollections.observableArrayList();
        responsabilitiesObservableList = FXCollections.observableArrayList();
        initValidatorToTextFields();
    }

    public ScreenController(){ }

    public ScreenController(Project project) {
        this.project = project;
    }

    public boolean verifyInputData() {
        boolean dataInputValid = true;
        for( Map.Entry<Control, Boolean> entry : interruptorMap.entrySet() ) {
            if( !entry.getValue() ){
                dataInputValid = false;
                break;
            }
        }
        return dataInputValid;
    }

    public Project getNewProject() {
        project = new Project();
        project.setName( projectNameTextField.getText() );
        project.setGeneralDescription( projectDescriptionTextArea.getText() );
        project.setGeneralPurpose( projectPurposeTextArea.getText() );
        project.setSchedule( projectScheduleTextField.getText() );
        project.setDuration( Float.valueOf(projectDurationTextField.getText()) );
        project.setNameResponsable( projectResponsableNameTextField.getText() );
        project.setChargeResponsable( projectResponsableChargeTextField.getText() );
        project.setEmailResponsable( projectResponsableEmailTextField.getText() );
        project.setOrganization(selectedOrganization);
        project.setActivities( castObservableListToList(activitiesObservableList) );
        project.setMediateObjectives( castObservableListToList(mediateObjetivesObservableList) );
        project.setImmediateObjetives( castObservableListToList(immediateObjetivesObservableList) );
        project.setResources( castObservableListToList(resourcesObservableList) );
        project.setMethodologies( castObservableListToList(methodologiesObservableList) );
        project.setResponsibilities( castObservableListToList(responsabilitiesObservableList) );
        return project;
    }

    public void setProjectInformationToFields() {
        projectDescriptionTextArea.setText( project.getGeneralDescription() );
        projectDurationTextField.setText( String.valueOf( project.getDuration() ) );
        projectNameTextField.setText( project.getName() );
        projectPurposeTextArea.setText( project.getGeneralPurpose() );
        projectResponsableChargeTextField.setText( project.getChargeResponsable() );
        projectResponsableEmailTextField.setText( project.getEmailResponsable() );
        projectResponsableNameTextField.setText( project.getNameResponsable() );
        projectScheduleTextField.setText( project.getSchedule() );
        selectedOrganization = project.getOrganization();
        activitiesObservableList.addAll( project.getImmediateObjetives() );
        activitiesListView.setItems(activitiesObservableList);
        immediateObjetivesObservableList.addAll( project.getImmediateObjetives() );
        immediateObjetivesListView.setItems(immediateObjetivesObservableList);
        resourcesObservableList.addAll( project.getResources() );
        resourcesListView.setItems(resourcesObservableList);
        methodologiesObservableList.addAll( project.getMethodologies() );
        methodologiesListView.setItems(methodologiesObservableList);
        responsabilitiesObservableList.addAll( project.getResponsibilities() );
        responsabilitiesListView.setItems(responsabilitiesObservableList);
        mediateObjetivesObservableList.addAll( project.getMediateObjectives() );
        mediateObjetivesListView.setItems(mediateObjetivesObservableList);
        companyAddressLabel.setText( project.getOrganization().getAddress() );
        companyCityLabel.setText( project.getOrganization().getCity() );
        companyDirectUsersLabel.setText( String.valueOf( project.getOrganization().getDirectUsers() ) );
        companyEmailLabel.setText( project.getOrganization().getEmail() );
        companyIndirectUsersLabel.setText( String.valueOf( project.getOrganization().getIndirectUsers() ) );
        companyNameLabel.setText( project.getOrganization().getName() );
        companyPhoneNumberLabel.setText( project.getOrganization().getPhoneNumber() );
        companySectorLabel.setText( project.getOrganization().getSector().getSector() );
        companyStateLabel.setText( project.getOrganization().getState() );
    }


    @FXML
    void activityAddButtonPressed(ActionEvent event) {
        addTextInputToListViewFromObservableList(activitiesObservableList, activitiesListView);
    }

    @FXML
    void activityRemoveButtonPressed(ActionEvent event) {
        textListViewSelected = activitiesListView.getSelectionModel().getSelectedItem();
        removeTextInputFromListView(activitiesListView, textListViewSelected);
    }

    @FXML
    void immediateAddButtonPressed(ActionEvent event) {
        addTextInputToListViewFromObservableList(immediateObjetivesObservableList, immediateObjetivesListView);
    }

    @FXML
    void immediateRemoveButtonPressed(ActionEvent event) {
        textListViewSelected = immediateObjetivesListView.getSelectionModel().getSelectedItem();
        removeTextInputFromListView(immediateObjetivesListView, textListViewSelected);
    }

    @FXML
    void mediateAddButtonPressed(ActionEvent event) {
        addTextInputToListViewFromObservableList(mediateObjetivesObservableList, mediateObjetivesListView);
    }

    @FXML
    void mediateRemoveButtonPressed(ActionEvent event) {
        textListViewSelected = mediateObjetivesListView.getSelectionModel().getSelectedItem();
        removeTextInputFromListView(mediateObjetivesListView, textListViewSelected);
    }

    @FXML
    void methodologyAddButtonPressed(ActionEvent event) {
        addTextInputToListViewFromObservableList(methodologiesObservableList, methodologiesListView);
    }

    @FXML
    void methodologyRemoveButtonPressed(ActionEvent event) {
        textListViewSelected = methodologiesListView.getSelectionModel().getSelectedItem();
        removeTextInputFromListView(methodologiesListView, textListViewSelected);
    }

    @FXML
    void resourcesAddButtonPressed(ActionEvent event) {
        addTextInputToListViewFromObservableList(resourcesObservableList, resourcesListView);
    }

    @FXML
    void resourceRemoveButtonPressed(ActionEvent event) {
        textListViewSelected = resourcesListView.getSelectionModel().getSelectedItem();
        removeTextInputFromListView(resourcesListView, textListViewSelected);
    }

    @FXML
    void responsabilityAddButtonPressed(ActionEvent event) {
        addTextInputToListViewFromObservableList(responsabilitiesObservableList, responsabilitiesListView);
    }

    @FXML
    void responsabilityRemoveButtonPressed(ActionEvent event) {
        textListViewSelected = responsabilitiesListView.getSelectionModel().getSelectedItem();
        removeTextInputFromListView(responsabilitiesListView, textListViewSelected);
    }

    @FXML
    void selectCompanyButtonPressed(ActionEvent event) {
        selectCompanyButton.setDisable(true);
        CompanyController companyController = new CompanyController();
        companyController.showStage();
        if( companyController.getSelectedOrganizationCard() != null ){
            selectedOrganization = companyController.getSelectedOrganizationCard().getOrganization();
            insertCompayInformationToLabels(selectedOrganization);
        }
        selectCompanyButton.setDisable(false);
    }

    private void addTextInputToListViewFromObservableList(ObservableList<String> observableList, ListView<String> listView) {
        TextInputController textInputController = new TextInputController();
        textInputController.showStage();
        if(textInputController.getInputData() != null) {
            observableList.add(textInputController.getInputData());
            listView.setItems(observableList);
        }
    }

    private void removeTextInputFromListView(ListView<String> listView, String stringToRemove) {
        listView.getItems().remove(stringToRemove);
    }

    private List<String> castObservableListToList(ObservableList<String> observableList) {
        List<String> list = new ArrayList<>();
        for ( String input : observableList ) {
            list.add(input);
        }
        return list;
    }

    private void insertCompayInformationToLabels(Organization organization) {
        companyNameLabel.setText( selectedOrganization.getName() );
        companyAddressLabel.setText( selectedOrganization.getAddress() );
        companyCityLabel.setText( selectedOrganization.getCity() );
        companyStateLabel.setText( selectedOrganization.getState() );
        companyEmailLabel.setText( selectedOrganization.getEmail() );
        companyPhoneNumberLabel.setText( selectedOrganization.getPhoneNumber() );
        companyDirectUsersLabel.setText( String.valueOf( selectedOrganization.getDirectUsers() ) );
        companyIndirectUsersLabel.setText( String.valueOf( selectedOrganization.getIndirectUsers() ) );
        companySectorLabel.setText( selectedOrganization.getSector().getSector() );
    }

    private void initValidatorToTextFields() {
        interruptorMap = new LinkedHashMap<>();
        interruptorMap.put(projectNameTextField, false);
        interruptorMap.put(projectDescriptionTextArea, false);
        interruptorMap.put(projectPurposeTextArea, false);
        interruptorMap.put(projectScheduleTextField, false);
        interruptorMap.put(projectDurationTextField, false);
        interruptorMap.put(projectResponsableNameTextField, false);
        interruptorMap.put(projectResponsableChargeTextField, false);
        interruptorMap.put(projectResponsableEmailTextField, false);
        interruptorMap.put(projectDescriptionTextArea, false);
        interruptorMap.put(projectPurposeTextArea, false);
        Map<TextInputControl, Object[]> validator = new HashMap<>();
        Object[] nameConstraints = {Validator.PROJECT_NAME_PATTERN, Validator.NAME_LENGTH, checkIconName};
        validator.put(projectNameTextField, nameConstraints);
        Object[] durationConstraints = {Validator.DURATION_PATTERN, Validator.DURATION_LENGTH, checkIconDuration};
        validator.put(projectDurationTextField, durationConstraints);
        Object[] scheduleConstraints = {Validator.SCHEDULE_PATTERN, Validator.SCHEDULE_LENGTH, checkIconSchedule};
        validator.put(projectScheduleTextField, scheduleConstraints);
        Object[] nameResponsableConstraints = {Validator.NAME_PATTERN, Validator.NAME_LENGTH, checkIconNameResponsable};
        validator.put(projectResponsableNameTextField, nameResponsableConstraints);
        Object[] chargeResponsableConstraints = {Validator.CHARGE_RESPONSABLE_PATTERN, Validator.CHARGE_RESPONSABLE_LENGTH, checkIconChargeResponsable};
        validator.put(projectResponsableChargeTextField, chargeResponsableConstraints);
        Object[] emailResponsableConstraints = {Validator.EMAIL_PATTERN, Validator.EMAIL_LENGTH, checkIconEmail};
        validator.put(projectResponsableEmailTextField, emailResponsableConstraints);
        Object[] projectDurationConstraints = {Validator.DURATION_PATTERN, Validator.DURATION_LENGTH, checkIconDuration};
        validator.put(projectDurationTextField,projectDurationConstraints);
        Object[] projectDescriptionConstraints = {Validator.LARGE_TEXT_PATTERN, Validator.LARGE_TEXT_LENGTH, checkIconGeneralDescription};
        validator.put(projectDescriptionTextArea, projectDescriptionConstraints);
        Object[] projectPurposeConstraints = {Validator.LARGE_TEXT_PATTERN, Validator.LARGE_TEXT_LENGTH, checkIconGeneralPurpose};
        validator.put(projectPurposeTextArea, projectPurposeConstraints);
        final int FIRST_CONTRAINT = 0;
        final int SECOND_CONTRAINT = 1;
        final int THIRD_CONSTRAINT_ICON = 2;
        for (Map.Entry<TextInputControl, Object[]> entry : validator.entrySet() ) {
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

}
