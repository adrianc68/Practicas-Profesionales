package org.database.dao;

import org.domain.*;
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
        } catch (SQLException | NullPointerException e) {
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
        } catch (SQLException | NullPointerException e) {
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
        } catch (SQLException | NullPointerException e) {
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
        } catch (SQLException | NullPointerException e) {
            Logger.getLogger(PractitionerDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return  rowsAffected;
    }

    /***
     * Delete a assign of professor to a Practitioner in the database.
     * <p>
     * This method remove a assign of professor to a student. It's used by the coordinator when
     * he decided to remove a assign of professor to a practitioner.
     * </p>
     * @param idPractitioner the practitioner's ID
     * @return an int representing the rows number affected in database.
     */
    @Override
    public int removeAssignProfessor(int idPractitioner) {
        int rowsAffected = 0;
        try(Connection conn = database.getConnection()){
            conn.setAutoCommit(false);
            String statement = "UPDATE Practitioner SET id_professor = NULL WHERE id_person = ?";
            PreparedStatement removeAssignProfessor = conn.prepareStatement(statement);
            removeAssignProfessor.setInt(1, idPractitioner );
            rowsAffected = removeAssignProfessor.executeUpdate();
            conn.commit();
        } catch (SQLException | NullPointerException e) {
            Logger.getLogger(PractitionerDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return  rowsAffected;
    }

    /***
     * Get all practitioners from the actual/last course
     * <p>
     * This method get the practitioners and their assigned projects.
     * This method is used by the coordinator and professor.
     * Doesn't return any selected project and any activity delivery.
     * </p>
     * @return List<Practitioner> a list with practitioners of the actual/last course from database
     */
    @Override
    public List<Practitioner> getAllPractitionersFromLastCourse() {
        List<Practitioner> practitioners = new ArrayList<>();
        try(Connection conn = database.getConnection() ) {
            String statement = "SELECT PRAC.id_person, PRAC.enrollment, PRAC.id_project, PRAC.id_professor, PERS.name, PERS.phoneNumber, PERS.email, COUR.id_course, COUR.NRC, COUR.period, COUR.name, PROF.cubicle, PROF.staff_number, PERSPROF.name, PERSPROF.phoneNumber, PERSPROF.email, PROJ.name, PROJ.duration, PROJ.schedule, PROJ.general_purpose, PROJ.general_description, PROJ.id_company, PROJ.charge_responsable, PROJ.name_responsable, PROJ.email_responsable, COMP.name, COMP.address, COMP.email, COMP.state, COMP.phoneNumber, COMP.direct_users, COMP.indirect_users, COMP.sector, COMP.city, CORD.cubicle, CORD.staff_number, PERSCORD.id_person, PERSCORD.name, PERSCORD.phoneNumber, PERSCORD.email FROM Practitioner AS PRAC INNER JOIN Person AS PERS ON PRAC.id_person = PERS.id_person INNER JOIN Course AS COUR ON PERS.id_course = COUR.id_course AND COUR.id_course = (SELECT max(id_course) FROM Course) LEFT JOIN Professor AS PROF ON PRAC.id_professor = PROF.id_person LEFT JOIN Person AS PERSPROF ON PROF.id_person = PERSPROF.id_person LEFT JOIN Project AS PROJ ON PRAC.id_project = PROJ.id_project LEFT JOIN Company AS COMP ON PROJ.id_company = COMP.id_company LEFT JOIN Coordinator AS CORD ON COMP.id_coordinator = CORD.id_person LEFT JOIN Person AS PERSCORD ON CORD.id_person = PERSCORD.id_person;";
            PreparedStatement queryPractitioner = conn.prepareStatement(statement);
            result = queryPractitioner.executeQuery();
            while( result.next() ) {
                Course course = new Course();
                course.setId(result.getInt("COUR.id_course"));
                course.setName(result.getString("COUR.name"));
                course.setNRC(result.getString("COUR.NRC"));
                course.setPeriod(result.getString("COUR.period"));
                Professor professor = new Professor();
                professor.setId(result.getInt("PRAC.id_professor"));
                professor.setName(result.getString("PERSPROF.name"));
                professor.setPhoneNumber(result.getString("PERSPROF.phoneNumber"));
                professor.setEmail(result.getString("PERSPROF.email"));
                professor.setCourse(course);
                professor.setStaff_number(result.getString("PROF.staff_number"));
                professor.setCubicle(result.getInt("PROF.cubicle"));
                Practitioner practitioner = new Practitioner();
                practitioner.setName(result.getString("PERS.name"));
                practitioner.setPhoneNumber(result.getString("PERS.phoneNumber"));
                practitioner.setEmail(result.getString("PERS.email"));
                practitioner.setId(result.getInt("PRAC.id_person"));
                practitioner.setEnrollment(result.getString("PRAC.enrollment"));
                practitioner.setCourse(course);
                practitioner.setProfessor(professor);
                Coordinator coordinator = new Coordinator();
                coordinator.setId(result.getInt("PERSCORD.id_person"));
                coordinator.setName(result.getString("PERSCORD.name"));
                coordinator.setPhoneNumber(result.getString("PERSCORD.phoneNumber"));
                coordinator.setEmail(result.getString("PERSCORD.email"));
                coordinator.setCubicle(result.getInt("CORD.cubicle"));
                coordinator.setStaff_number(result.getString("CORD.staff_number"));
                coordinator.setCourse(course);
                Company company = new Company();
                company.setId(result.getInt("PROJ.id_company"));
                company.setName(result.getString("COMP.name"));
                company.setIndirectUsers(result.getInt("COMP.indirect_users"));
                company.setDirectUsers(result.getInt("COMP.direct_users"));
                company.setState(result.getString("COMP.state"));
                String sector = result.getString("COMP.sector");
                company.setSector( ( ( sector != null ) ? Sector.valueOf( sector.toUpperCase() ) : null ) );
                company.setCity(result.getString("COMP.city"));
                company.setAddress(result.getString("COMP.address"));
                company.setEmail(result.getString("COMP.email"));
                company.setPhoneNumber(result.getString("COMP.phoneNumber"));
                company.setCoordinator(coordinator);
                company.setCourse(course);
                Project project = new Project();
                project.setId(result.getInt("PRAC.id_project"));
                project.setName(result.getString("PROJ.name"));
                project.setDuration(result.getFloat("PROJ.duration"));
                project.setSchedule(result.getString("PROJ.schedule"));
                project.setGeneralPurpose(result.getString("PROJ.general_purpose"));
                project.setGeneralDescription(result.getString("PROJ.general_description"));
                project.setChargeResponsable(result.getString("PROJ.charge_responsable"));
                project.setNameResponsable(result.getString("PROJ.name_responsable"));
                project.setEmailResponsable(result.getString("PROJ.email_responsable"));
                project.setCompany(company);
                practitioner.setProject(project);
                practitioner.setDeliveries(null);
                practitioner.setSelectedProjects(null);
                List<String> activities = new ArrayList<>();
                List<String> mediateObjetives = new ArrayList<>();
                List<String> methodologies = new ArrayList<>();
                List<String> resources = new ArrayList<>();
                List<String> responsabilities = new ArrayList<>();
                List<String> immediateObjetives = new ArrayList<>();
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
                practitioners.add(practitioner);
            }
        } catch (SQLException | NullPointerException e) {
            Logger.getLogger(PractitionerDAO.class.getName()).log(Level.WARNING, null, e);
        }
        return practitioners;
    }





}
