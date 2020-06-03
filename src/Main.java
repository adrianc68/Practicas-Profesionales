import javafx.application.Application;
import javafx.stage.Stage;
import org.gui.auth.LoginController;
import org.gui.auth.users.administrator.AdministratorController;
import org.gui.auth.users.administrator.update.ManagementController;
import org.gui.auth.users.administrator.update.add.AddController;
import org.gui.auth.users.administrator.update.add.addcoordinator.AddCoordinatorController;
import org.gui.auth.users.administrator.update.add.addcourse.AddCourseController;
import org.gui.auth.users.administrator.update.add.addprofessor.AddProfessorController;
import org.gui.auth.users.administrator.update.change.ChangeActivityStateController;
import org.gui.auth.users.administrator.update.remove.RemoveObjectController;
import org.gui.auth.users.coordinator.CoordinatorController;
import org.gui.auth.users.coordinator.practitioner.PractitionerController;
import org.gui.auth.users.coordinator.practitioner.addpractitioner.AddPractitionerController;
import org.gui.auth.users.coordinator.practitioner.assignprofessor.AssignProfessorController;
import org.gui.auth.users.coordinator.practitioner.assignproject.AssignProjectController;
import org.gui.auth.users.coordinator.practitioner.assignproject.assignother.AssignOtherProject;
import org.gui.auth.users.coordinator.practitioner.removepractitioner.RemovePractitionerController;
import org.gui.auth.users.coordinator.project.ProjectController;
import org.gui.auth.users.coordinator.project.editionproject.company.CompanyController;
import org.gui.auth.users.coordinator.project.editionproject.company.addcompany.AddCompanyController;
import org.gui.auth.users.coordinator.project.editionproject.screens.secondscreen.textinput.TextInputController;
import org.gui.auth.users.coordinator.project.registerproject.RegisterProjectController;
import org.gui.auth.users.coordinator.project.removeproject.RemoveProjectController;
import org.gui.auth.users.coordinator.project.updateproject.UpdateProjectController;
import org.gui.auth.util.changepassword.ChangePasswordController;
import org.gui.auth.util.recoverpassword.RecoveryPasswordController;
import org.gui.auth.util.theme.SelectThemeController;
import org.util.CSSProperties;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        if( CSSProperties.readConfiguredAppProperties() ) {
            SelectThemeController selectThemeController = new SelectThemeController();
            selectThemeController.showStage();
        }
        LoginController loginController = new LoginController();
        loginController.showStage();
//
//        CoordinatorController coordinatorController = new CoordinatorController();
//        coordinatorController.showStage();
//
//        AdministratorController administratorController = new AdministratorController();
//        administratorController.showStage();
//
//        RecoveryPasswordController recoveryPasswordController = new RecoveryPasswordController();
//        recoveryPasswordController.showStage();
//
//        ChangePasswordController changePasswordController = new ChangePasswordController();
//        changePasswordController.showStage();
//
//        AddPractitionerController addPractitionerController = new AddPractitionerController();
//        addPractitionerController.showStage();
//
//        AddCoordinatorController addCoordinatorController = new AddCoordinatorController();
//        addCoordinatorController.showStage();
//
//        AddProfessorController addProfessorController = new AddProfessorController();
//        addProfessorController.showStage();
//
//        AddCourseController addCourseController = new AddCourseController();
//        addCourseController.showStage();
//
//        TextInputController textInputController = new TextInputController();
//        textInputController.showStage();
//
//        AddCompanyController addCompanyController = new AddCompanyController();
//        addCompanyController.showStage();
//
//
//        RemoveObjectController removeObjectController = new RemoveObjectController();
//        removeObjectController.showStage();
//
//        ManagementController managementController = new ManagementController();
//        managementController.showStage();
//
//        PractitionerController practitionerController = new PractitionerController();
//        practitionerController.showStage();
//
//        AssignOtherProject assignOtherProject = new AssignOtherProject();
//        assignOtherProject.showStage();
//
//        AssignProfessorController assignProfessorController = new AssignProfessorController();
//        assignProfessorController.showStage();
//
//        RemovePractitionerController removePractitionerController = new RemovePractitionerController();
//        removePractitionerController.showStage();
//
//        AssignProjectController assignProjectController = new AssignProjectController();
//        assignProjectController.showStage();
//
//        ChangeActivityStateController changeActivityStateController = new ChangeActivityStateController();
//        changeActivityStateController.showStage();
//
//        AddController addController = new AddController();
//        addController.showStage();
//
//        ProjectController projectController = new ProjectController();
//        projectController.showStage();
//
//        RemoveProjectController removeProjectController = new RemoveProjectController();
//        removeProjectController.showStage();
//
//        RegisterProjectController registerProjectController = new RegisterProjectController();
//        registerProjectController.showStage();
//
//        UpdateProjectController updateProjectController = new UpdateProjectController();
//        updateProjectController.showStage();
//
//        CompanyController companyController = new CompanyController();
//        companyController.showStage();

    }

    public static void main(String[] args) {
        launch(args);
    }

}