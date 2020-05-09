package org.gui.users.coordinator.project.editionproject.screens;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.domain.Company;
import org.domain.Project;
import org.gui.users.coordinator.project.editionproject.screens.secondscreen.textinput.TextInputController;
import org.gui.users.coordinator.project.editionproject.company.CompanyController;
import org.util.Validator;

public class ScreenController implements Initializable {
    private boolean inputDataValid;
    private String textListViewSelected;
    private Company selectedCompany;
    private Project project;
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
    private TextField projectNameTextField;

    @FXML
    private TextField projectScheduleTextField;

    @FXML
    private TextField projectResponsableNameTextField;

    @FXML
    private TextField projectResponsableChargeTextField;

    @FXML
    private TextField projectResponsableEmailTextField;

    @FXML
    private TextField projectDurationTextField;

    @FXML
    private TextArea projectDescriptionTextArea;

    @FXML
    private TextArea projectPurposeTextArea;

    @FXML
    private ListView<String> mediateObjetivesListView;

    @FXML
    private ListView<String> resourcesListView;

    @FXML
    private ListView<String> activitiesListView;

    @FXML
    private ListView<String> immediateObjetivesListView;

    @FXML
    private ListView<String> methodologiesListView;

    @FXML
    private ListView<String> responsabilitiesListView;

    public ScreenController() {
    }

    public ScreenController(Project project) {
        this.project = project;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        inputDataValid = false;
        setListenerToTextFields();
        setListenerToTextArea();
        mediateObjetivesObservableList = FXCollections.observableArrayList();
        resourcesObservableList = FXCollections.observableArrayList();
        activitiesObservableList = FXCollections.observableArrayList();
        immediateObjetivesObservableList = FXCollections.observableArrayList();
        methodologiesObservableList = FXCollections.observableArrayList();
        responsabilitiesObservableList = FXCollections.observableArrayList();
        if(project != null) {
            setProjectInformationToFields();
        }
    }

    public boolean isInputDataValid() {
        return inputDataValid;
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
        project.setCompany(selectedCompany);
        project.setActivities( castObservableListToList(activitiesObservableList) );
        project.setMediateObjectives( castObservableListToList(mediateObjetivesObservableList) );
        project.setImmediateObjetives( castObservableListToList(immediateObjetivesObservableList) );
        project.setResources( castObservableListToList(resourcesObservableList) );
        project.setMethodologies( castObservableListToList(methodologiesObservableList) );
        project.setResponsibilities( castObservableListToList(responsabilitiesObservableList) );
        return project;
    }

    @FXML
    void activityAddButtonPressed(ActionEvent event) {
        addTextInputFromObservableListToListView(activitiesObservableList, activitiesListView);
    }

    @FXML
    void activityRemoveButtonPressed(ActionEvent event) {
        textListViewSelected = activitiesListView.getSelectionModel().getSelectedItem();
        removeTextInputFromListView(activitiesListView, textListViewSelected);
    }

    @FXML
    void immediateAddButtonPressed(ActionEvent event) {
        addTextInputFromObservableListToListView(immediateObjetivesObservableList, immediateObjetivesListView);
    }

    @FXML
    void immediateRemoveButtonPressed(ActionEvent event) {
        textListViewSelected = immediateObjetivesListView.getSelectionModel().getSelectedItem();
        removeTextInputFromListView(immediateObjetivesListView, textListViewSelected);
    }

    @FXML
    void mediateAddButtonPressed(ActionEvent event) {
        addTextInputFromObservableListToListView(mediateObjetivesObservableList, mediateObjetivesListView);
    }

    @FXML
    void mediateRemoveButtonPressed(ActionEvent event) {
        textListViewSelected = mediateObjetivesListView.getSelectionModel().getSelectedItem();
        removeTextInputFromListView(mediateObjetivesListView, textListViewSelected);
    }

    @FXML
    void methodologyAddButtonPressed(ActionEvent event) {
        addTextInputFromObservableListToListView(methodologiesObservableList, methodologiesListView);
    }

    @FXML
    void methodologyRemoveButtonPressed(ActionEvent event) {
        textListViewSelected = methodologiesListView.getSelectionModel().getSelectedItem();
        removeTextInputFromListView(methodologiesListView, textListViewSelected);
    }

    @FXML
    void resourcesAddButtonPressed(ActionEvent event) {
        addTextInputFromObservableListToListView(resourcesObservableList, resourcesListView);
    }

    @FXML
    void resourceRemoveButtonPressed(ActionEvent event) {
        textListViewSelected = resourcesListView.getSelectionModel().getSelectedItem();
        removeTextInputFromListView(resourcesListView, textListViewSelected);
    }

    @FXML
    void responsabilityAddButtonPressed(ActionEvent event) {
        addTextInputFromObservableListToListView(responsabilitiesObservableList, responsabilitiesListView);
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
        if( companyController.getSelectedCompanyCard() != null ){
            selectedCompany = companyController.getSelectedCompanyCard().getCompany();
            insertCompayInformationToLabels(selectedCompany);
        }
        selectCompanyButton.setDisable(false);
    }

    private void insertCompayInformationToLabels(Company company) {
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

    private void removeTextInputFromListView(ListView<String> listView, String stringToRemove) {
        listView.getItems().remove(stringToRemove);
    }

    private void addTextInputFromObservableListToListView(ObservableList<String> observableList, ListView<String> listView) {
        TextInputController textInputController = new TextInputController();
        textInputController.showStage();
        if(textInputController.getInputData() != null) {
            observableList.add(textInputController.getInputData());
            listView.setItems(observableList);
        }
    }

    private List<String> castObservableListToList(ObservableList<String> observableList) {
        List<String> list = new ArrayList<>();
        for ( String input : observableList ) {
            list.add(input);
        }
        return list;
    }

    private void setListenerToTextFields() {
        Map<Object[], TextField> textfields = new LinkedHashMap<>();
        Object[] nameConstraints = {Validator.PROJECT_NAME_PATTERN, Validator.NAME_LENGTH};
        textfields.put(nameConstraints, projectNameTextField);
        Object[] scheduleConstraints = {Validator.SCHEDULE_PATTERN, Validator.SCHEDULE_LENGTH};
        textfields.put(scheduleConstraints, projectScheduleTextField);
        Object[] nameResponsableConstraints = {Validator.NAME_PATTERN, Validator.NAME_LENGTH};
        textfields.put(nameResponsableConstraints, projectResponsableNameTextField);
        Object[] chargeResponsableConstraints = {Validator.CHARGE_RESPONSABLE_PATTERN, Validator.CHARGE_RESPONSABLE_LENGTH};
        textfields.put(chargeResponsableConstraints, projectResponsableChargeTextField);
        Object[] emailResponsableConstraints = {Validator.EMAIL_PATTERN, Validator.EMAIL_LENGTH};
        textfields.put(emailResponsableConstraints, projectResponsableEmailTextField);
        Object[] projectDurationConstraints = {Validator.DURATION_PATTERN, Validator.DURATION_LENGTH};
        textfields.put(projectDurationConstraints, projectDurationTextField);
        for (Map.Entry<Object[], TextField> entry : textfields.entrySet() ) {
            entry.getValue().textProperty().addListener( ((observable, oldValue, newValue) -> {
                if( !Validator.doesStringMatchPattern( newValue, ( (String) entry.getKey()[0] ) ) || Validator.isStringLargerThanLimitOrEmpty( newValue, ( (Integer) entry.getKey()[1]) ) ){
                    inputDataValid = false;
                    entry.getValue().setStyle("-fx-background-color:red;");
                } else {
                    inputDataValid = true;
                    entry.getValue().setStyle("-fx-background-color:green;");
                }
            }));
        }
    }

    private void setListenerToTextArea() {
        Map<Object[], TextArea> textAreas = new LinkedHashMap<>();
        Object[] projectDescriptionConstraints = {Validator.LARGE_TEXT_PATTERN, Validator.LARGE_TEXT_LENGTH};
        textAreas.put(projectDescriptionConstraints, projectDescriptionTextArea);
        Object[] projectPurposeConstraints = {Validator.LARGE_TEXT_PATTERN, Validator.LARGE_TEXT_LENGTH};
        textAreas.put(projectPurposeConstraints, projectPurposeTextArea);
        for (Map.Entry<Object[], TextArea> entry : textAreas.entrySet() ) {
            entry.getValue().textProperty().addListener( (observable, oldValue, newValue) -> {
                if( !Validator.doesStringMatchPattern( newValue, ( (String) entry.getKey()[0] ) ) || Validator.isStringLargerThanLimitOrEmpty( newValue, ( (Integer) entry.getKey()[1]) ) ){
                    inputDataValid = false;
                    entry.getValue().setStyle("-fx-background-color:red;");
                } else {
                    inputDataValid = true;
                    entry.getValue().setStyle("-fx-background-color:green;");
                }
            });
        }
    }

    private void setProjectInformationToFields() {
        projectDescriptionTextArea.setText( project.getGeneralDescription() );
        projectDurationTextField.setText( String.valueOf( project.getDuration() ) );
        projectNameTextField.setText( project.getName() );
        projectPurposeTextArea.setText( project.getGeneralPurpose() );
        projectResponsableChargeTextField.setText( project.getChargeResponsable() );
        projectResponsableEmailTextField.setText( project.getEmailResponsable() );
        projectResponsableNameTextField.setText( project.getNameResponsable() );
        projectScheduleTextField.setText( project.getSchedule() );
        selectedCompany = project.getCompany();
        // Verify <----------------------
//        mediateObjetives
//        immediateObjetivesObservableList;
//        resourcesObservableList;
//        methodologiesObservableList;
//        responsabilitiesObservableList;
//        System.out.println(project.getCompany());
//        companyAddressLabel.setText( project.getCompany().getAddress() );
//        companyCityLabel.setText( project.getCompany().getCity() );
//        companyDirectUsersLabel.setText( String.valueOf( project.getCompany().getDirectUsers() ) );
//        companyEmailLabel.setText( project.getCompany().getEmail() );
//        companyIndirectUsersLabel.setText( String.valueOf( project.getCompany().getIndirectUsers() ) );
//        companyNameLabel.setText( project.getCompany().getName() );
//        companyPhoneNumberLabel.setText( project.getCompany().getPhoneNumber() );
//        companySectorLabel.setText( project.getCompany().getSector().getSector() );
//        companyStateLabel.setText( project.getCompany().getState() );
    }

}
