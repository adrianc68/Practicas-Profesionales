package org.database.dao;

import org.database.Database;
import org.domain.ActivityState;
import org.domain.Coordinator;
import org.domain.Course;
import org.domain.Person;
import org.domain.Practitioner;
import org.domain.Professor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PersonDAO implements IPersonDAO {
    private Database database;

    /***
     * PersonDAO constructor.
     * This constructor initialize a connection to the database.
     */
    public PersonDAO() {
        database = new Database();
    }

    /***
     * Get the activity state by email and password.
     * <p>
     * This method it's used by system when it needs to check if person has
     * an activity state as active to allow the user access to system.
     * </p>
     * @param idPerson the user to get their activity state
     * @return ActivityState representing the activity state.
     */
    public ActivityState getActivityStateByID(int idPerson) {
        ActivityState activityState = null;
        try( Connection conn = database.getConnection() ) {
            String statement = "SELECT activity_state FROM Person WHERE id_person = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(statement);
            preparedStatement.setInt(1, idPerson);
            ResultSet resultSet = preparedStatement.executeQuery();
            if( resultSet.next() ) {
                activityState = ActivityState.valueOf( resultSet.getString("activity_state").toUpperCase() );
            }
        } catch (SQLException e) {
            Logger.getLogger( PersonDAO.class.getName() ).log(Level.SEVERE, null, e);
        }
        return activityState;
    }

    /***
     * Change activity state to person.
     * <p>
     * This method changes the activity state to person. It is used to
     * avoid the access to person.
     * </p>
     * @param idPractitioner
     * @return true if any row was affected, and false if it was any row not affected.
     */
    public boolean changeActivityStateByID(int idPractitioner) {
        int rowsAffected = 0;
        try( Connection conn = database.getConnection() ) {
            String statement = "UPDATE Person SET activity_state = CASE WHEN (activity_state = 1) THEN 2 ELSE 1 END WHERE id_person = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(statement);
            preparedStatement.setInt(1, idPractitioner);
            rowsAffected = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger( PersonDAO.class.getName() ).log(Level.SEVERE, null, e);
        }
        System.out.println(rowsAffected);
        return rowsAffected > 0;
    }

    /***
     * Return a person from database.
     * <p>
     * This method its used by Auth class and return a object person
     * that can be cast to coordinator or professor or practitioner.
     * </p>
     * @param email the email to be compared with data from database
     * @param password the password to be compared with data from database
     * @return Person an object Person.
     */
    @Override
    public Person getPersonByEmailAndPassword(String email, String password) {
        Person person = null;
        try( Connection conn = database.getConnection() ) {
            String statement = "SELECT PERS.id_person, PERS.name, PERS.email, PERS.phoneNumber, PERS.activity_state, COUR.id_course, COUR.name, COUR.NRC, COUR.period, CORD.cubicle, CORD.staff_number, PROF.cubicle, PROF.staff_number, PRAC.enrollment FROM AccessAccount AS ACA INNER JOIN Person AS PERS ON ACA.id_user = PERS.id_person AND ACA.email = ? AND ACA.password = ? AND PERS.activity_state = 1 INNER JOIN Course AS COUR ON PERS.id_course =  COUR.id_course AND COUR.id_course = (SELECT max(id_course) FROM Course) LEFT JOIN Coordinator AS CORD ON PERS.id_person = CORD.id_person LEFT JOIN Professor AS PROF ON PERS.id_person = PROF.id_person LEFT JOIN Practitioner AS PRAC ON PERS.id_person = PRAC.id_person";
            PreparedStatement preparedStatement = conn.prepareStatement(statement);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if( resultSet.next() ) {
                person = getInstanceOfResultSet(resultSet);
                Course course = getCourseOfResultSet(resultSet);
                person.setName( resultSet.getString("PERS.name") );
                person.setPhoneNumber( resultSet.getString("PERS.phoneNumber") );
                person.setEmail( resultSet.getString("PERS.email") );
                person.setId( resultSet.getInt("PERS.id_person") );
                person.setActivityState( resultSet.getString("PERS.activity_state") );
                person.setCourse(course);
            }
        } catch (SQLException e) {
            Logger.getLogger( PersonDAO.class.getName() ).log(Level.SEVERE, null, e);
        }
        return person;
    }

    private Course getCourseOfResultSet(ResultSet resultSet) throws SQLException {
        Course course = null;
        if(resultSet.getString("COUR.name") != null) {
            course = new Course();
            course.setId( resultSet.getInt("COUR.id_course") );
            course.setName( resultSet.getString("COUR.name") );
            course.setNRC( resultSet.getString("COUR.NRC") );
            course.setPeriod( resultSet.getString("COUR.period") );
        }
        return course;
    }

    // BAD USER MANAGER ??? <---- FIX THIS!!!!
    private Person getInstanceOfResultSet(ResultSet resultSet) throws SQLException {
        Person person = new Person();
        if( resultSet.getString("CORD.cubicle") != null) {
            Coordinator coordinator = new Coordinator();
            coordinator.setCubicle( resultSet.getInt("CORD.cubicle") );
            coordinator.setStaffNumber( resultSet.getString("CORD.staff_number") );
            person = coordinator;
        } else if( resultSet.getString("PROF.cubicle") != null) {
            Professor professor = new Professor();
            professor.setCubicle( resultSet.getInt("PROF.cubicle") );
            professor.setStaffNumber( resultSet.getString("PROF.staff_number") );
            person = professor;
        } else if( resultSet.getString("PRAC.enrollment") != null ){
            Practitioner practitioner = new Practitioner();
            practitioner.setEnrollment( resultSet.getString("PRAC.enrollment") );
            person = practitioner;
        }
        return person;
    }

}
