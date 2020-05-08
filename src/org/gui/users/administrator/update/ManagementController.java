package org.gui.users.administrator.update;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.database.dao.CoordinatorDAO;
import org.database.dao.CourseDAO;
import org.database.dao.ProfessorDAO;
import org.domain.Coordinator;
import org.domain.Course;
import org.domain.Professor;
import org.gui.users.administrator.update.add.addcoordinator.AddCoordinatorController;
import org.gui.users.administrator.update.add.addcourse.AddCourseController;
import org.gui.users.administrator.update.add.addprofessor.AddProfessorController;
import org.gui.users.administrator.update.remove.RemoveObjectController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ManagementController implements Initializable {
    private Object selected;
    private Course selectedCourse;
    private ObservableList<Coordinator> coordinatorsObservableList;
    private ObservableList<Professor> professorsObservableList;
    private ObservableList<Course> coursesObservableList;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private TableView<Course> coursesTableView;

    @FXML
    private TableColumn<Course, String> courseNRCTableColumn;

    @FXML
    private TableColumn<Course, String> coursePeriodTableColum;

    @FXML
    private TableColumn<Course, String> courseNameTableColumn;

    @FXML
    private TableView<Coordinator> coordinatorsTableView;

    @FXML
    private TableColumn<Coordinator, String> coordinatorNameTableColum;

    @FXML
    private TableColumn<Coordinator, String> coordinatorPhoneNumberTableColum;

    @FXML
    private TableColumn<Coordinator, String> coordinatorEmailTableColum;

    @FXML
    private TableColumn<Coordinator, String> coordinatorStaffNumberTableColum;

    @FXML
    private TableColumn<Coordinator, Integer> coordinatorCubicleTableColum;

    @FXML
    private TableView<Professor> professorsTableView;

    @FXML
    private TableColumn<Professor, String> professorNameTableColumn;

    @FXML
    private TableColumn<Professor, String> professorPhoneNumberTableColumn;

    @FXML
    private TableColumn<Professor, String> professorEmailTableColumn;

    @FXML
    private TableColumn<Professor, String> professorStaffNumberTableColumn;

    @FXML
    private TableColumn<Professor, Integer> professorCubicleTableColumn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setTableComponents();
        setInformationFromDatabaseToCourseTableView();
    }

    public void showStage() {
        FXMLLoader loader = new FXMLLoader( getClass().getResource("/org/gui/users/administrator/update/ManagementPersonalVista.fxml") );
        loader.setController(this);
        Parent root = null;
        try{
            root = loader.load();
        } catch(IOException ioe) {
            ioe.getMessage();
        }
        Stage managementStage = new Stage();
        managementStage.initModality(Modality.APPLICATION_MODAL);
        managementStage.setScene( new Scene(root) );
        managementStage.show();
    }

    @FXML
    void addCoordinatorButtonPressed(ActionEvent event) {
        if( selectedCourse != null ) {
            AddCoordinatorController addCoordinatorController = new AddCoordinatorController( ( selectedCourse ) );
            addCoordinatorController.showStage();
            if( addCoordinatorController.getAddOperationStatus() ) {
                coordinatorsObservableList.add( addCoordinatorController.getNewCoordinator() );
                coordinatorsTableView.setItems(coordinatorsObservableList);
            }
        }
    }

    @FXML
    void addProfessorButtonPressed(ActionEvent event) {
        if( selectedCourse != null ) {
            AddProfessorController addProfessorController = new AddProfessorController( ( selectedCourse ) );
            addProfessorController.showStage();
            if( addProfessorController.getAddOperationStatus() ) {
                professorsObservableList.add( addProfessorController.getNewProfessor() );
                professorsTableView.setItems(professorsObservableList);
            }
        }
    }

    @FXML
    void addCourseButtonPressed(ActionEvent event) {
        AddCourseController addCourseController = new AddCourseController();
        addCourseController.showStage();
        if( addCourseController.getAddOperationStatus() ) {
            coursesObservableList.add( addCourseController.getNewCourse() );
            coursesTableView.setItems(coursesObservableList);
        }
    }

    @FXML
    void closeButtonPressed(ActionEvent event) {
        Stage stage = ( (Stage) ( (Node) event.getSource() ).getScene().getWindow() );
        stage.close();
    }

    @FXML
    void removeButtonPressed(ActionEvent event) {
        if(selected != null) {
            RemoveObjectController removeObjectController = new RemoveObjectController(selected);
            removeObjectController.showStage();
            if( removeObjectController.getStatusRemoveOperation() ) {
                removeObjectFromTableView();
            }
        }
    }

    private void setTableComponents() {
        coordinatorCubicleTableColum.setCellValueFactory(new PropertyValueFactory<>("cubicle"));
        coordinatorEmailTableColum.setCellValueFactory(new PropertyValueFactory<>("email"));
        coordinatorNameTableColum.setCellValueFactory(new PropertyValueFactory<>("name"));
        coordinatorPhoneNumberTableColum.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        coordinatorStaffNumberTableColum.setCellValueFactory(new PropertyValueFactory<>("staffNumber"));
        courseNRCTableColumn.setCellValueFactory(new PropertyValueFactory<>("NRC"));
        courseNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        coursePeriodTableColum.setCellValueFactory(new PropertyValueFactory<>("period"));
        professorCubicleTableColumn.setCellValueFactory(new PropertyValueFactory<>("cubicle"));
        professorEmailTableColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        professorNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        professorPhoneNumberTableColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        professorStaffNumberTableColumn.setCellValueFactory(new PropertyValueFactory<>("staffNumber"));
        setListenersToTableView();
    }

    // Bug: May 6 2020
    // Select a course to remove
    private void setListenersToTableView() {
        coursesTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selected = newValue;
            selectedCourse = newValue;
            setDataToCoordinatorTableView( ( (Course) selected).getId() );
            setDataToProfessorTableView( ( (Course) selected).getId() );
        });

        professorsTableView.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue) {
                selected = professorsTableView.getSelectionModel().getSelectedItem();
            }
        });

        coordinatorsTableView.focusedProperty().addListener( (observable, oldValue, newValue) -> {
            if(!newValue) {
                selected = coordinatorsTableView.getSelectionModel().getSelectedItem();
            }
        });
    }

    private void setDataToCoordinatorTableView(int idCourse) {
        CoordinatorDAO coordinatorDAO = new CoordinatorDAO();
        coordinatorsObservableList = FXCollections.observableArrayList();
        coordinatorsObservableList.addAll( coordinatorDAO.getAllCoordinatorsByCourseID(idCourse) );
        coordinatorsTableView.setItems(coordinatorsObservableList);
    }

    private void setDataToProfessorTableView(int idCourse) {
        ProfessorDAO professorDAO = new ProfessorDAO();
        professorsObservableList = FXCollections.observableArrayList();
        professorsObservableList.addAll( professorDAO.getAllProfessorsByCourseID(idCourse) );
        professorsTableView.setItems(professorsObservableList);

    }

    private void setInformationFromDatabaseToCourseTableView() {
        CourseDAO courseDAO = new CourseDAO();
        coursesObservableList = FXCollections.observableArrayList();
        coursesObservableList.addAll(courseDAO.getAllCourses());
        coursesTableView.setItems(coursesObservableList);
    }

    private void removeObjectFromTableView() {
        if(selected instanceof Coordinator) {
            coordinatorsObservableList.remove(selected);
            coordinatorsTableView.setItems(coordinatorsObservableList);
        } else if(selected instanceof Professor) {
            professorsObservableList.remove(selected);
            professorsTableView.setItems(professorsObservableList);
        } else {
            coursesObservableList.remove(selected);
            coursesTableView.setItems(coursesObservableList);
        }
    }

}
