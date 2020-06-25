package org.gui.auth.users.administrator.update;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.fxml.Initializable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import org.database.dao.CoordinatorDAO;
import org.database.dao.CourseDAO;
import org.database.dao.ProfessorDAO;
import org.domain.Coordinator;
import org.domain.Course;
import org.domain.Person;
import org.domain.Professor;
import org.gui.Controller;
import org.gui.auth.resources.alerts.OperationAlert;
import org.gui.auth.users.administrator.update.add.AddController;
import org.gui.auth.users.administrator.update.change.ChangeActivityStateController;
import org.gui.auth.users.administrator.update.remove.RemoveObjectController;
import org.util.CSSProperties;

public class ManagementController extends Controller implements Initializable {
    private Object selected;
    private ObservableList<Coordinator> coordinatorsObservableList;
    private ObservableList<Professor> professorsObservableList;
    private ObservableList<Course> coursesObservableList;
    @FXML private TableView<Course> coursesTableView;
    @FXML private TableColumn<Course, String> courseNRCTableColumn;
    @FXML private TableColumn<Course, String> coursePeriodTableColum;
    @FXML private TableColumn<Course, String> courseNameTableColumn;
    @FXML private TableView<Coordinator> coordinatorsTableView;
    @FXML private TableColumn<Coordinator, String> coordinatorNameTableColum;
    @FXML private TableColumn<Coordinator, String> coordinatorPhoneNumberTableColum;
    @FXML private TableColumn<Coordinator, String> coordinatorEmailTableColum;
    @FXML private TableColumn<Coordinator, String> coordinatorStaffNumberTableColum;
    @FXML private TableColumn<Coordinator, Integer> coordinatorCubicleTableColum;
    @FXML private TableColumn<Coordinator, String> coordinatorActivityStateTableColumn;
    @FXML private TableView<Professor> professorsTableView;
    @FXML private TableColumn<Professor, String> professorNameTableColumn;
    @FXML private TableColumn<Professor, String> professorPhoneNumberTableColumn;
    @FXML private TableColumn<Professor, String> professorEmailTableColumn;
    @FXML private TableColumn<Professor, String> professorStaffNumberTableColumn;
    @FXML private TableColumn<Professor, Integer> professorCubicleTableColumn;
    @FXML private TableColumn<Professor, Integer> professorActivityStateTableColumn;
    @FXML private AnchorPane rootStage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setStyleClass(rootStage, getClass().getResource("../../../resources/" + CSSProperties.readTheme().getTheme() ).toExternalForm() );
        setTableComponents();
        try {
            updateDataFromTablesView();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    public void showStage() {
        loadFXMLFile( getClass().getResource("/org/gui/auth/users/administrator/update/ManagementPersonalVista.fxml"), this);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
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

    @FXML
    protected void changeActivityStateButtonPressed(ActionEvent event) throws SQLException {
        if( selected != null &&  !(selected instanceof Course) ) {
            ChangeActivityStateController changeActivityStateController = new ChangeActivityStateController();
            changeActivityStateController.setUser( (Person) selected );
            changeActivityStateController.showStage();
            if( changeActivityStateController.getChangeStatusOperation() ) {
                updateDataFromTablesView();
            }
        }
        selected = null;
    }

    @FXML
    protected void addButtonPressed(ActionEvent event) throws SQLException {
        AddController addController = new AddController();
        addController.showStage();
        if( addController.getAddOperationStatus() ) {
            updateDataFromTablesView();
        }
    }

    @FXML
    protected void removeButtonPressed(ActionEvent event) {
        if(selected != null) {
            RemoveObjectController removeObjectController = new RemoveObjectController();
            removeObjectController.setObject(selected);
            removeObjectController.showStage();
            if( removeObjectController.getStatusRemoveOperation() ) {
                removeObjectFromTableView();
            }
        }
        selected = null;
    }

    private void updateDataFromTablesView() throws SQLException {
        setDataToCourseTableView();
        setDataToProfessorTableView();
        setDataToCoordinatorTableView();
    }

    private void setDataToCoordinatorTableView() throws SQLException {
        CoordinatorDAO coordinatorDAO = new CoordinatorDAO();
        coordinatorsObservableList = FXCollections.observableArrayList();
        coordinatorsObservableList.addAll( coordinatorDAO.getAllCoordinators() );
        coordinatorsTableView.setItems(coordinatorsObservableList);
    }

    private void setDataToProfessorTableView() {
        ProfessorDAO professorDAO = new ProfessorDAO();
        professorsObservableList = FXCollections.observableArrayList();
        try {
            professorsObservableList.addAll( professorDAO.getAllProfessors() );
        } catch (SQLException sqlException) {
            OperationAlert.showLostConnectionAlert();
            Logger.getLogger( ManagementController.class.getName() ).log(Level.WARNING, null, sqlException);
        }
        professorsTableView.setItems(professorsObservableList);
    }

    private void setDataToCourseTableView() {
        CourseDAO courseDAO = new CourseDAO();
        coursesObservableList = FXCollections.observableArrayList();
        try {
            coursesObservableList.addAll( courseDAO.getAllCourses() );
        } catch (SQLException sqlException) {
            OperationAlert.showLostConnectionAlert();
            Logger.getLogger( ManagementController.class.getName() ).log(Level.WARNING, null, sqlException);
        }
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

    private void setListenersToTableView() {
        coursesTableView.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue) {
                selected = coursesTableView.getSelectionModel().getSelectedItem();
            }
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

    private void setTableComponents() {
        coordinatorCubicleTableColum.setCellValueFactory( new PropertyValueFactory<>("cubicle") );
        coordinatorEmailTableColum.setCellValueFactory( new PropertyValueFactory<>("email") );
        coordinatorNameTableColum.setCellValueFactory( new PropertyValueFactory<>("name") );
        coordinatorPhoneNumberTableColum.setCellValueFactory( new PropertyValueFactory<>("phoneNumber") );
        coordinatorStaffNumberTableColum.setCellValueFactory( new PropertyValueFactory<>("staffNumber") );
        coordinatorActivityStateTableColumn.setCellValueFactory( new PropertyValueFactory<>("activityState") );
        courseNRCTableColumn.setCellValueFactory( new PropertyValueFactory<>("NRC") );
        courseNameTableColumn.setCellValueFactory( new PropertyValueFactory<>("name") );
        coursePeriodTableColum.setCellValueFactory( new PropertyValueFactory<>("period") );
        professorCubicleTableColumn.setCellValueFactory( new PropertyValueFactory<>("cubicle") );
        professorEmailTableColumn.setCellValueFactory( new PropertyValueFactory<>("email") );
        professorNameTableColumn.setCellValueFactory( new PropertyValueFactory<>("name") );
        professorPhoneNumberTableColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber") );
        professorStaffNumberTableColumn.setCellValueFactory(new PropertyValueFactory<>("staffNumber") );
        professorActivityStateTableColumn.setCellValueFactory(new PropertyValueFactory<>("activityState") );
        setListenersToTableView();
    }

}
