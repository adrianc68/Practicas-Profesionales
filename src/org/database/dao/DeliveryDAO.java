package org.database.dao;

import org.database.Database;
import org.domain.Activity;
import org.domain.Course;
import org.domain.Delivery;
import org.domain.Professor;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class DeliveryDAO implements IDeliveryDAO {
    private final Database database;

    /***
     * DeliveryDAO constructor.
     * This constructor initialize a connection to the database.
     */
    public DeliveryDAO() {
        database = new Database();
    }

    /***
     * Add a Delivery to database.
     * <p>
     * This method is used by the Practitioner when he/she needs to add a Delivery.
     * </p>
     * @param delivery, idActivity
     * @return int representing the delivery's id
     */
    @Override
    public int addDeliveryToActivity(Delivery delivery) throws SQLException {
        int idDelivery;
        try(Connection conn = database.getConnection() ){
            conn.setAutoCommit(false);
            String statement = "CALL addDelivery(?,?,?,?)";
            CallableStatement callableStatement = conn.prepareCall(statement);
            callableStatement.setInt(1, delivery.getActivity().getId() );
            callableStatement.setInt( 2, delivery.getPractitioner().getId() );
            callableStatement.setString(3, delivery.getDocumentPath() );
            callableStatement.registerOutParameter(4, Types.INTEGER);
            callableStatement.execute();
            idDelivery = callableStatement.getInt("last_id");
            conn.commit();
        } catch (SQLException sqlException) {
            throw sqlException;
        }
        return idDelivery;
    }

    /***
     * Evaluate an activity in database.
     * <p>
     * This method evaluate an activity saved in database.
     * </p>
     * @param idDelivery delivery's id to evaluate
     * @param  score the delivery score
     * @param observation the observations
     * @return boolean true if 1 o more than 1 rows are affected
     */
    @Override
    public boolean evaluateDeliveryOfActivity(int idDelivery, float score, String observation) throws SQLException {
        int rowsAffected = 0;
        try( Connection conn = database.getConnection() ){
            conn.setAutoCommit(false);
            String statement = "UPDATE Delivery SET observation = ?, score = ? WHERE id_delivery = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(statement);
            preparedStatement.setString(1, observation);
            preparedStatement.setFloat(2, score);
            preparedStatement.setInt(3, idDelivery);
            rowsAffected = preparedStatement.executeUpdate();
            conn.commit();
        } catch (SQLException sqlException) {
            throw sqlException;
        }
        return  rowsAffected > 0;
    }

    /***
     * Remove an delivery in database.
     * <p>
     * This method remove an activity saved in database. It's used by professor.
     * </p>
     * @param idDelivery delivery's id to remove
     * @return boolean true if delivery was removed and false if was not deleted.
     */
    @Override
    public boolean removeDeliveryById(int idDelivery) throws SQLException {
        int rowsAffected = 0;
        try( Connection conn = database.getConnection() ){
            conn.setAutoCommit(false);
            String statement = "DELETE FROM Delivery WHERE id_delivery = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(statement);
            preparedStatement.setInt(1, idDelivery);
            rowsAffected = preparedStatement.executeUpdate();
            conn.commit();
        } catch (SQLException sqlException) {
            throw sqlException;
        }
        return  rowsAffected > 0;
    }

    /***
     * Get a practitioner's delivery from database.
     * <p>
     * This method get the actual delivery from a activity.
     * It can be used when you need to replace a delivery.
     * </p>
     * @param idActivity activity's id to get the delivery
     * @param idPractitioner practitioner's id to get the delivery
     * @return Delivery representing the actual delivery of practitioner
     */
    @Override
    public Delivery getDeliveryByActivityAndPractitioner(int idActivity, int idPractitioner) throws SQLException {
        Delivery delivery = null;
        try(Connection conn = database.getConnection() ) {
            conn.setAutoCommit(false);
            String statement = "SELECT DEL.id_delivery, DEL.observation, DEL.score, DEL.file_path FROM Delivery AS DEL WHERE id_activity = ? AND id_practitioner = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(statement);
            preparedStatement.setInt(1, idActivity);
            preparedStatement.setInt(2, idPractitioner);
            ResultSet resultSet = preparedStatement.executeQuery();
            if ( resultSet.next() ) {
                delivery = new Delivery();
                delivery.setObservation( resultSet.getString("DEL.observation") );
                delivery.setScore( resultSet.getFloat("DEl.score") );
                delivery.setId( resultSet.getInt("DEL.id_delivery") );
                delivery.setDocumentPath( resultSet.getString("DEL.file_path") );
            }
            conn.commit();
        } catch (SQLException sqlException) {
            throw sqlException;
        }
        return delivery;
    }

    /***
     * Get all deliveries from activities from database.
     * <p>
     * This method return all deliveries by activity from database.
     * It's used by Professor.
     * </p>
     * @param idActivity
     * @return List<Delivery> A list containing deliveries
     */
    @Override
    public List<Delivery> getAllDeliveriesByActivity(int idActivity) throws SQLException {
        String statement = "SELECT DEL.id_delivery, DEL.observation, DEL.score, DEL.file_path, ACT.id_activity, ACT.name, ACT.description, ACT.deadline, COUR.id_course, COUR.NRC, COUR.name, COUR.PERIOD, PERSPROF.name, PERSPROF.phoneNumber, PERSPROF.email, PROF.cubicle, PROF.staff_number FROM Delivery AS DEL INNER JOIN Activity AS ACT ON ACT.id_activity = DEL.id_activity AND DEL.id_activity = ? INNER JOIN Professor AS PROF ON ACT.id_professor = PROF.id_person INNER JOIN Person AS PERSPROF ON PROF.id_person = PERSPROF.id_person INNER JOIN Course AS COUR ON PERSPROF.id_course = COUR.id_course";
        return getAllDeliveryByStatementAndID(statement, idActivity);
    }

    /***
     * Get activitie's delivery of practitioner from database.
     * <p>
     * This method return a delivery list of practitioner.
     * </p>
     * @param idPractitioner
     * @return List<Delivery> A delivery list of practitioner.
     */
    @Override
    public List<Delivery> getAllDeliveriesByPractitionerID(int idPractitioner) throws SQLException {
        String statement = "SELECT DEL.id_delivery, DEL.observation, DEL.score, DEL.file_path, ACT.id_activity, ACT.name, ACT.description, ACT.deadline, COUR.id_course, COUR.NRC, COUR.name, COUR.PERIOD, PERSPROF.name, PERSPROF.phoneNumber, PERSPROF.email, PROF.cubicle, PROF.staff_number FROM Delivery AS DEL INNER JOIN Activity AS ACT ON DEL.id_activity = ACT.id_activity AND DEL.id_practitioner = ? INNER JOIN Professor AS PROF ON ACT.id_professor = PROF.id_person INNER JOIN Person AS PERSPROF ON PROF.id_person = PERSPROF.id_person INNER JOIN Course AS COUR ON PERSPROF.id_course = COUR.id_course";
        return getAllDeliveryByStatementAndID(statement, idPractitioner);
    }

    private List<Delivery> getAllDeliveryByStatementAndID(String statement, int id) throws SQLException {
        List<Delivery> deliveries = new ArrayList<>();
        try(Connection conn = database.getConnection() ) {
            conn.setAutoCommit(false);
            PreparedStatement preparedStatement = conn.prepareStatement(statement);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while( resultSet.next() ) {
                Course course = new Course();
                course.setId(resultSet.getInt( "COUR.id_course") );
                course.setName(resultSet.getString( "COUR.name") );
                course.setNRC(resultSet.getString( "COUR.NRC") );
                course.setPeriod(resultSet.getString( "COUR.period") );
                Professor professor = new Professor();
                professor.setName(resultSet.getString( "PERSPROF.name") );
                professor.setPhoneNumber(resultSet.getString( "PERSPROF.phoneNumber") );
                professor.setEmail(resultSet.getString( "PERSPROF.email") );
                professor.setCourse(course);
                professor.setStaffNumber(resultSet.getString( "PROF.staff_number") );
                professor.setCubicle(resultSet.getInt( "PROF.cubicle") );
                Activity activity = new Activity();
                activity.setDeadline( resultSet.getString("ACT.deadline") );
                activity.setDescription(resultSet.getString( "ACT.description") );
                activity.setId(resultSet.getInt( "ACT.id_activity") );
                activity.setName(resultSet.getString( "ACT.name") );
                activity.setProfessor(professor);
                activity.setDeliveries(null);
                Delivery delivery = new Delivery();
                delivery.setActivity(activity);
                delivery.setPractitioner(null);
                delivery.setObservation( resultSet.getString("DEL.observation") );
                delivery.setScore( resultSet.getFloat("DEl.score") );
                delivery.setId( resultSet.getInt("DEL.id_delivery") );
                delivery.setDocumentPath( resultSet.getString("DEL.file_path") );
                deliveries.add(delivery);
                conn.commit();
            }
        } catch (SQLException sqlException) {
            throw sqlException;
        }
        return deliveries;
    }

}
