package org.database.dao;

import org.database.Database;
import org.domain.Activity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ActivityDAO implements IActivityDAO {
    /***
     * Constant for the connection to the database
     */
    private final Database database;
    /***
     * Query results
     */
    private ResultSet resultSet;

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
     * This method is used by Professor when he/she needs to add an activity.
     * </p>
     * @param activity to be added to database.
     * @return int representing the activity's id.
     */
    @Override
    public int addActivity(Activity activity) {
        int idActivity = 0;
        try(Connection conn = database.getConnection() ){
            conn.setAutoCommit(false);
            String statement = "INSERT INTO Activity(name, description, deadline, id_professor) VALUES (?,?,?,?)";
            PreparedStatement insertActivity = conn.prepareStatement(statement);
            insertActivity.setString(1, activity.getName());
            insertActivity.setString(2, activity.getDescription());
            insertActivity.setDate(3, activity.getDeadline());
            insertActivity.setInt(4, activity.getProfessor().getId());
            insertActivity.executeUpdate();
            statement = "SELECT LAST_INSERT_ID()";
            insertActivity = conn.prepareStatement(statement);
            resultSet = insertActivity.executeQuery();
            resultSet.next();
            idActivity = resultSet.getInt(1);
            conn.commit();
        } catch (SQLException | NullPointerException e) {
            Logger.getLogger(ActivityDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return idActivity;
    }

    /***
     * Remove an activity from database.
     * <p>
     * This method is used by Professor when he/she needs to remove an activity.
     * </p>
     * @param idActivity to be removed to database
     * @return boolean true if 1 o more than 1 rows are affected
     */
    @Override
    public boolean removeActivityByID(int idActivity) {
        int rowsAffected = 0;
        try(Connection conn = database.getConnection()){
            conn.setAutoCommit(false);
            String statement = "DELETE FROM Activity WHERE id_activity = ?";
            PreparedStatement removeActivity = conn.prepareStatement(statement);
            removeActivity.setInt(1, idActivity );
            rowsAffected = removeActivity.executeUpdate();
            conn.commit();
        } catch (SQLException | NullPointerException e) {
            Logger.getLogger(ActivityDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return rowsAffected > 0;
    }

    /***
     * Get all activities from the actual/last course
     * <p>
     * This method is used by the Professor when he/she needs to look up all the activities
     * </p>
     * @return List<activities> a list with activities of the actual/last course from database
     */
    @Override
    public List<Activity> getAllActivitiesFromLastCourse() {
        List<Activity> activities = new ArrayList<>();
        try(Connection conn = database.getConnection() ) {
            String statement = "SELECT ACT.id_activity, ACT.name, ACT.description, ACT.deadline FROM activity AS ACT INNER JOIN professor AS PROF ON ACT.id_professor = PROF.id_person INNER JOIN person AS PER ON PER.id_person = PROF.id_person INNER JOIN course AS COUR ON COUR.id_course = PER.id_course AND COUR.id_course = (SELECT max(id_course) FROM Course)";
            PreparedStatement queryActivity = conn.prepareStatement(statement);
            resultSet = queryActivity.executeQuery();
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
        } catch (SQLException | NullPointerException e) {
            Logger.getLogger(ActivityDAO.class.getName()).log(Level.WARNING, null, e);
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
    public List<Activity> getAllActivitiesByProfessorID(int idProfessor){
        List<Activity> activities = new ArrayList<>();
        try(Connection conn = database.getConnection() ) {
            String statement = "SELECT ACT.id_activity, ACT.name, ACT.description, ACT.deadline FROM Activity AS ACT WHERE ACT.id_professor = ?";
            PreparedStatement queryActivity = conn.prepareStatement(statement);
            queryActivity.setInt(1, idProfessor );
            ResultSet result = queryActivity.executeQuery();
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
        } catch (SQLException | NullPointerException e) {
            Logger.getLogger(ActivityDAO.class.getName()).log(Level.WARNING, null, e);
        }
        return activities;
    }

}
