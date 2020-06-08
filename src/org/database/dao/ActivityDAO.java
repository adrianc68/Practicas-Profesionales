package org.database.dao;

import org.database.Database;
import org.domain.Activity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ActivityDAO implements IActivityDAO {
    private final Database database;

    /***
     * ActivityDAO constructor.
     * This constructor initialize a connection to the database.
     */
    public ActivityDAO() {
        this.database = new Database();
    }

    /***
     * Add an activity to database.
     * <p>
     * This method is used by the Practitioner when he/she needs to add an activity.
     * </p>
     * @param activity to be added to database.
     * @return int representing the activity's id.
     */
    @Override
    public int addActivity(Activity activity) throws SQLException {
        int idActivity = 0;
        try(Connection conn = database.getConnection() ){
            conn.setAutoCommit(false);
            String statement = "INSERT INTO Activity(name, description, deadline, id_professor) VALUES (?,?,?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(statement);
            preparedStatement.setString(1, activity.getName());
            preparedStatement.setString(2, activity.getDescription());
            preparedStatement.setDate(3, activity.getDeadline());
            preparedStatement.setInt(4, activity.getProfessor().getId());
            preparedStatement.executeUpdate();
            statement = "SELECT LAST_INSERT_ID()";
            preparedStatement = conn.prepareStatement(statement);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            idActivity = resultSet.getInt(1);
            conn.commit();
        } catch (SQLException sqlException) {
            throw sqlException;
        }
        return idActivity;
    }

    /***
     * Remove an activity from database.
     * <p>
     * This method is used by Professor when he needs to remove an activity.
     * </p>
     * @param idActivity to be removed to database
     * @return boolean true if 1 o more than 1 rows are affected
     */
    @Override
    public boolean removeActivityByID(int idActivity) throws SQLException {
        int rowsAffected = 0;
        try(Connection conn = database.getConnection()){
            conn.setAutoCommit(false);
            String statement = "DELETE FROM Activity WHERE id_activity = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(statement);
            preparedStatement.setInt(1, idActivity );
            rowsAffected = preparedStatement.executeUpdate();
            conn.commit();
        } catch (SQLException sqlException) {
            throw sqlException;
        }
        return rowsAffected > 0;
    }

    /***
     * Get all activities from the actual/last course
     * <p>
     * This method is used by the Professor when he needs to look up all the activities
     * </p>
     * @return List<activities> a list with activities of the actual/last course from database
     */
    @Override
    public List<Activity> getAllActivitiesFromLastCourse() throws SQLException {
        List<Activity> activities = new ArrayList<>();
        try(Connection conn = database.getConnection() ) {
            String statement = "SELECT ACT.id_activity, ACT.name, ACT.description, ACT.deadline FROM activity AS ACT INNER JOIN professor AS PROF ON ACT.id_professor = PROF.id_person INNER JOIN person AS PER ON PER.id_person = PROF.id_person INNER JOIN course AS COUR ON COUR.id_course = PER.id_course AND COUR.id_course = (SELECT max(id_course) FROM Course)";
            PreparedStatement preparedStatement = conn.prepareStatement(statement);
            ResultSet resultSet = preparedStatement.executeQuery();
            while( resultSet.next() ) {
                Activity activity = new Activity();
                activity.setName(resultSet.getString("ACT.name"));
                activity.setDescription(resultSet.getString("ACT.description"));
                activity.setDeadline(resultSet.getDate("ACT.deadline"));
                activity.setId((resultSet.getInt("ACT.id_activity")));
                activity.setDeliveries(null);
                activity.setProfessor(null);
                activities.add(activity);
            }
        } catch (SQLException sqlException) {
            throw sqlException;
        }
        return activities;
    }

    /***
     * Get all activities looking for Professor.
     * <p>
     * This method is used by the Practitioner and Professor.
     * </p>
     * @param idProfessor the professor's id
     * @return List<activities> a list with activities looking for Professor.
     */
    @Override
    public List<Activity> getAllActivitiesByProfessorID(int idProfessor) throws SQLException {
        List<Activity> activities = new ArrayList<>();
        try(Connection conn = database.getConnection() ) {
            String statement = "SELECT ACT.id_activity, ACT.name, ACT.description, ACT.deadline FROM Activity AS ACT WHERE ACT.id_professor = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(statement);
            preparedStatement.setInt(1, idProfessor );
            ResultSet result = preparedStatement.executeQuery();
            while( result.next() ) {
                Activity activity = new Activity();
                activity.setName(result.getString("ACT.name"));
                activity.setDescription(result.getString("ACT.description"));
                activity.setDeadline(result.getDate("ACT.deadline"));
                activity.setId((result.getInt("ACT.id_activity")));
                activity.setDeliveries(null);
                activity.setProfessor(null);
                activities.add(activity);
            }
        } catch (SQLException sqlException) {
            throw sqlException;
        }
        return activities;
    }

}
