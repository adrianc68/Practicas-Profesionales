package org.database.dao;

import org.domain.Company;
import org.domain.Coordinator;
import org.domain.Course;
import org.domain.Practitioner;
import org.domain.Project;
import org.domain.Sector;
import org.database.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PractitionerDAO implements IPractitionerDAO {
    /***
     * Constant for the connection to the database
     */
    private final Database database;
    /***
     * Query results
     */
    private ResultSet result;

    /***
     * PractitionerDAO constructor.
     * This constructor initialize a connection to the database.
     */
    public PractitionerDAO() {
        database = new Database();
    }

    /***
     * Add a Practitioner to database.
     * <p>
     * This method add a practitioner to database and return the rows number affected
     * by this method. This method is used by the coordinator when he need to add a practicing.
     * </p>
     * @param practitioner to be added to database
     * @return an int representing the rows number affected in database.
     */
    @Override
    public int addPractitioner(Practitioner practitioner) {
        int rowsAffected = 0;
        try(Connection conn = database.getConnection()){
            conn.setAutoCommit(false);
            String statement = "CALL addPractitioner(?, ?, ?, ?, ?)";
            PreparedStatement addPractitioner = conn.prepareStatement(statement);
            addPractitioner.setString(1, practitioner.getName() );
            addPractitioner.setString(2, practitioner.getPhoneNumber() );
            addPractitioner.setString(3, practitioner.getEmail() );
            addPractitioner.setInt(4, practitioner.getCourse().getId() );
            addPractitioner.setString(5, practitioner.getEnrollment() );
            rowsAffected = addPractitioner.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            Logger.getLogger(PractitionerDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return rowsAffected;
    }

    /***
     * Delete a Practitioner from database.
     * <p>
     * This method remove a practitioner by an id provided. It's used by the coordinator when
     * he needs to remove a practicing for any reason.
     * </p>
     * @param idPractitioner the practitioner's ID
     * @return an int representing the rows number affected in database.
     */
    @Override
    public int removePractitioner(int idPractitioner) {
        int rowsAffected = 0;
        try(Connection conn = database.getConnection()){
            conn.setAutoCommit(false);
            String statement = "DELETE FROM Practitioner WHERE id_person = ?";
            PreparedStatement removePractitioner = conn.prepareStatement(statement);
            removePractitioner.setInt(1, idPractitioner );
            rowsAffected = removePractitioner.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            Logger.getLogger(PractitionerDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return rowsAffected;
    }

    /***
     * Assign a project to a Practitioner in database.
     * <p>
     * This method update the assigned project of a practitioner. It's used by the coordinator when
     * he decided which project assign to a practitioner
     * </p>
     * @param idPractitioner the practitioner's ID
     * @param idProject the project's ID to be assigned to practitioner
     * @return an int representing the rows number affected in database.
     */
    @Override
    public int assignProject(int idPractitioner, int idProject) {
        int rowsAffected = 0;
        try(Connection conn = database.getConnection()){
            conn.setAutoCommit(false);
            String statement = "CALL assignProject(?,?)";
            PreparedStatement assignProject = conn.prepareStatement(statement);
            assignProject.setInt(1, idPractitioner );
            assignProject.setInt(2, idProject );
            rowsAffected = assignProject.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            Logger.getLogger(PractitionerDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return rowsAffected;
    }

    /***
     * Assign a professor to a Practitioner in the database.
     * <p>
     * This method assign a professor to a student. It's used by the coordinator when he decided assign
     * a professor to a practitioner.
     * </p>
     * @param idPractitioner the practitioner's ID
     * @param idProfessor the professor's id to be assigned to practitioner
     * @return an int representing the rows number affected in database.
     */
    @Override
    public int assignProfessor(int idPractitioner, int idProfessor) {
        int rowsAffected = 0;
        try(Connection conn = database.getConnection()){
            conn.setAutoCommit(false);
            String statement = "UPDATE Practitioner SET id_professor = ? WHERE id_person = ?";
            PreparedStatement assignProfessor = conn.prepareStatement(statement);
            assignProfessor.setInt(1, idProfessor );
            assignProfessor.setInt(2, idPractitioner );
            rowsAffected = assignProfessor.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            Logger.getLogger(PractitionerDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return  rowsAffected;
    }

    /***
     * Add in the datase a project selected by a Practitioner
     * <p>
     * This method add to a practitioner the projects selected by himself. This method is used by
     * the practitioner.
     * </p>
     * @param practitioner
     * @return an int representing the rows number affected in database.
     */
    @Override
    public int addSelectedProjectByPractitioner(Practitioner practitioner) {
        ArrayList<Project> selectedProjects = practitioner.getSelectedProjects();
        int rowsAffected = 0;
        try(Connection conn = database.getConnection()){
            conn.setAutoCommit(false);
            String statement = "CALL selectProject(?, ?)";
            PreparedStatement selectProject = conn.prepareStatement(statement);
            for(int i = 0; i < selectedProjects.size(); i++) {
                selectProject.setInt(1, practitioner.getId() );
                selectProject.setInt(2, selectedProjects.get(i).getId() );
            }
            rowsAffected = selectProject.executeUpdate();
            conn.commit();
        }catch (SQLException e) {
            Logger.getLogger(PractitionerDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return rowsAffected;
    }

    /***
     * Get the projects selected by a practitioner.
     * <p>
     * This method return a List, which contain the selected projects by a Practitioners. It's used by
     * the coordinator as a support to assign a project to a Practitioner.
     * </p>
     * @param idPractitioner the practitioner's ID
     * @return an List which contain the projects selected by a practitioner.
     */
    @Override
    public List<Project> getSelectedProjectsByIDPractitioner(int idPractitioner) {
        List<Project> projects = new ArrayList<>();
        Course course;
        Coordinator coordinator;
        Company company;
        Project project;
        try(Connection conn = database.getConnection() ) {
            String statement = "SELECT P.id_project, P.name, P.duration, P.schedule, P.general_purpose, P.general_description, P.id_company, P.charge_responsable, P.name_responsable, P.email_responsable, C.id_company, C.name, C.address, C.email, C.state, C.direct_users, C.indirect_users, C.sector, C.city, C.phoneNumber, CT.cubicle, CT.staff_number, Pe.id_person, Pe.name, Pe.phoneNumber, Pe.email, CS.id_course, CS.NRC, CS.period, CS.name FROM Project AS P INNER JOIN Practitioner_Selected_Projects AS PSP ON PSP.selected_project = P.id_project AND PSP.id_person = ? INNER JOIN Company AS C ON  P.id_company = C.id_company INNER JOIN Coordinator AS CT ON C.id_coordinator = CT.id_person INNER JOIN Person AS Pe ON C.id_coordinator = Pe.id_person INNER JOIN Course AS CS ON C.id_course = CS.id_course and (SELECT max(id_course) FROM Course)";
            PreparedStatement queryProjects = conn.prepareStatement(statement);
            queryProjects.setInt(1, idPractitioner);
            result = queryProjects.executeQuery();
            while( result.next() ) {
                course = new Course();
                course.setId(result.getInt("CS.id_course"));
                course.setNRC(result.getString("CS.NRC"));
                course.setPeriod(result.getString("CS.Period"));
                course.setName(result.getString("CS.name"));
                coordinator = new Coordinator();
                coordinator.setId(result.getInt("Pe.id_person"));
                coordinator.setName(result.getString("Pe.name"));
                coordinator.setPhoneNumber(result.getString("Pe.phoneNumber"));
                coordinator.setEmail(result.getString("Pe.email"));
                coordinator.setCourse(course);
                coordinator.setStaff_number(result.getString("CT.staff_number"));
                coordinator.setCubicle(result.getInt("CT.cubicle"));
                company = new Company();
                company.setId(result.getInt("C.id_company"));
                company.setName(result.getString("C.name"));
                company.setAddress(result.getString("C.address"));
                company.setEmail(result.getString("C.email"));
                company.setState(result.getString("C.state"));
                company.setPhoneNumber(result.getString("C.phoneNumber"));
                company.setDirectUsers(result.getInt("C.direct_users"));
                company.setIndirectUsers(result.getInt("C.indirect_users"));
                company.setSector(Sector.valueOf(result.getString("C.sector").toUpperCase()));
                company.setCity(result.getString("C.city"));
                company.setCoordinator(coordinator);
                company.setCourse(course);
                project = new Project();
                project.setId(result.getInt("P.id_project"));
                project.setName(result.getString("P.name"));
                project.setDuration(result.getFloat("P.duration"));
                project.setSchedule(result.getString("P.schedule"));
                project.setGeneralPurpose(result.getString("P.general_purpose"));
                project.setGeneralDescription(result.getString("P.general_description"));
                project.setCompany(company);
                project.setChargeResponsable(result.getString("P.charge_responsable"));
                project.setNameResponsable(result.getString("P.name_responsable"));
                project.setEmailResponsable(result.getString("P.email_responsable"));
                ArrayList<String> activities = new ArrayList<>();
                ArrayList<String> mediateObjetives = new ArrayList<>();
                ArrayList<String> methodologies = new ArrayList<>();
                ArrayList<String> resources = new ArrayList<>();
                ArrayList<String> responsabilities = new ArrayList<>();
                ArrayList<String> immediateObjetives = new ArrayList<>();
                Map<String, List<String>> multivaluedAttributesStatementsMap = new HashMap<>();
                multivaluedAttributesStatementsMap.put("SELECT * FROM Project_activities WHERE id_project = ?", activities);
                multivaluedAttributesStatementsMap.put("SELECT * FROM Project_mediate_objetives WHERE id_project = ?", mediateObjetives);
                multivaluedAttributesStatementsMap.put("SELECT * FROM Project_methodologies WHERE id_project = ?", methodologies);
                multivaluedAttributesStatementsMap.put("SELECT * FROM Project_resources WHERE id_project = ?", resources);
                multivaluedAttributesStatementsMap.put("SELECT * FROM Project_responsabilities WHERE id_project = ?", responsabilities);
                multivaluedAttributesStatementsMap.put("SELECT * FROM Project_immediate_objetives WHERE id_project = ?", immediateObjetives);
                PreparedStatement queryMultivaluedAttribute = null;
                for( Map.Entry<String, List<String>> entry : multivaluedAttributesStatementsMap.entrySet() ) {
                    queryMultivaluedAttribute = conn.prepareStatement( entry.getKey() );
                    queryMultivaluedAttribute.setInt(1, project.getId());
                    ResultSet resultMultivaluedAttribute = queryMultivaluedAttribute.executeQuery();
                    while( resultMultivaluedAttribute.next() ) {
                        entry.getValue().add(resultMultivaluedAttribute.getString(1));
                    }
                }
                project.setActivities(activities);
                project.setMediateObjectives(mediateObjetives);
                project.setMethodologies(methodologies);
                project.setResources(resources);
                project.setResponsibilities(responsabilities);
                project.setImmediateObjetives(immediateObjetives);
                projects.add(project);
            }
        } catch (SQLException e) {
            Logger.getLogger(PractitionerDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return projects;
    }

}
