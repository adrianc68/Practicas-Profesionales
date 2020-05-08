package org.database.dao;

import org.database.Database;
import org.domain.Course;
import org.domain.Delivery;
import org.domain.Practitioner;
import org.domain.Project;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
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
     * This method is used by the coordinator when he needs to add a practicing.
     * </p>
     * @param practitioner to be added to database
     * @return int the practitioner's id
     */
    @Override
    public int addPractitioner(Practitioner practitioner) {
        int idPractitioner = 0;
        try(Connection conn = database.getConnection()){
            conn.setAutoCommit(false);
            String statement = "CALL addPractitioner(?, ?, ?, ?, ?)";
            PreparedStatement addPractitioner = conn.prepareStatement(statement);
            addPractitioner.setString(1, practitioner.getName() );
            addPractitioner.setString(2, practitioner.getPhoneNumber() );
            addPractitioner.setString(3, practitioner.getEmail() );
            addPractitioner.setInt(4, practitioner.getCourse().getId() );
            addPractitioner.setString(5, practitioner.getEnrollment() );
            addPractitioner.executeUpdate();
            statement = "SELECT LAST_INSERT_ID()";
            addPractitioner = conn.prepareStatement(statement);
            result = addPractitioner.executeQuery();
            result.next();
            idPractitioner = result.getInt(1);
            conn.commit();
        } catch (SQLException | NullPointerException e) {
            Logger.getLogger(PractitionerDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return idPractitioner;
    }

    /***
     * Delete a Practitioner from database.
     * <p>
     * It's used by the coordinator whem he needs to remove a practicing for any reason.
     * </p>
     * @param idPractitioner the practitioner's ID
     * @return boolean true if 1 o more than 1 rows are affected
     */
    @Override
    public boolean removePractitioner(int idPractitioner) {
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
        return rowsAffected > 0;
    }

    /***
     * Assign a project to a Practitioner in database.
     * <p>
     * It's used by the coordinator when he decided which project assign to a practitioner
     * </p>
     * @param idPractitioner the practitioner's ID
     * @param idProject the project's ID to be assigned to practitioner
     * @return boolean true if project was assigned
     */
    @Override
    public boolean assignProjectToPractitioner(int idPractitioner, int idProject) {
        int idProjectAssigned = 0;
        try(Connection conn = database.getConnection()){
            conn.setAutoCommit(false);
            String statement = "{CALL assignProject(?, ?, ?)}";
            CallableStatement assignProject = conn.prepareCall(statement);
            assignProject.setInt(1, idPractitioner );
            assignProject.setInt(2, idProject );
            assignProject.registerOutParameter("idProject", Types.INTEGER);
            assignProject.execute();
            conn.commit();
            idProjectAssigned = assignProject.getInt("idProject");
        } catch (SQLException | NullPointerException e) {
            Logger.getLogger(PractitionerDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return idProjectAssigned != 0;
    }

    /***
     * Assign a professor to a Practitioner in the database.
     * <p>
     * It's used by the coordinator when he decided assign a professor to a practitioner.
     * </p>
     * @param idPractitioner the practitioner's ID
     * @param idProfessor the professor's id to be assigned to practitioner
     * @return boolean true if 1 o more than 1 rows are affected
     */
    @Override
    public boolean assignProfessorToPractitioner(int idPractitioner, int idProfessor) {
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
        return  rowsAffected > 0;
    }

    /***
     * Delete a assign of professor to a Practitioner in the database.
     * <p>
     * It's used by the coordinator when he decided to remove a assign of professor to a practitioner.
     * </p>
     * @param idPractitioner the practitioner's ID
     * @return boolean true if 1 o more than 1 rows are affected
     */
    @Override
    public boolean removeAssignedProfessorToPractitioner(int idPractitioner) {
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
        return  rowsAffected > 0;
    }

    /***
     * Get all practitioners and their assigned projects from the actual/last course
     * <p>
     * This method is used by the coordinator and professor.
     * Doesn't return with any activity delivery, selected project, assigned project or assigned professor
     * </p>
     * @return List<Practitioner> a list with practitioners of the actual/last course from database
     */
    @Override
    public List<Practitioner> getAllPractitionersFromLastCourse() {
        List<Practitioner> practitioners = new ArrayList<>();
        try(Connection conn = database.getConnection() ) {
            String statement = "SELECT PRAC.id_person, PRAC.enrollment, PERS.name, PERS.phoneNumber, PERS.email, COUR.id_course, COUR.NRC, COUR.period, COUR.name FROM Practitioner AS PRAC INNER JOIN Person AS PERS ON PRAC.id_person = PERS.id_person INNER JOIN Course AS COUR ON PERS.id_course = COUR.id_course AND COUR.id_course = (SELECT max(id_course) FROM Course)";
            PreparedStatement queryPractitioner = conn.prepareStatement(statement);
            result = queryPractitioner.executeQuery();
            while( result.next() ) {
                Course course = new Course();
                course.setId(result.getInt("COUR.id_course"));
                course.setName(result.getString("COUR.name"));
                course.setNRC(result.getString("COUR.NRC"));
                course.setPeriod(result.getString("COUR.period"));
                Practitioner practitioner = new Practitioner();
                practitioner.setName(result.getString("PERS.name"));
                practitioner.setPhoneNumber(result.getString("PERS.phoneNumber"));
                practitioner.setEmail(result.getString("PERS.email"));
                practitioner.setId(result.getInt("PRAC.id_person"));
                practitioner.setEnrollment(result.getString("PRAC.enrollment"));
                practitioner.setCourse(course);
                List<Project> selectedProjects = new ArrayList<>();
                List<Delivery> deliveries = new ArrayList<>();
                practitioner.setSelectedProjects(selectedProjects);
                practitioner.setDeliveries(deliveries);
                practitioner.setProfessor(null);
                practitioner.setProject(null);
                practitioners.add(practitioner);
            }
        } catch (SQLException | NullPointerException e) {
            Logger.getLogger(PractitionerDAO.class.getName()).log(Level.WARNING, null, e);
        }
        return practitioners;
    }

}
