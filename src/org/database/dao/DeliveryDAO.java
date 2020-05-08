package org.database.dao;

import org.database.Database;
import org.domain.Activity;
import org.domain.Course;
import org.domain.Delivery;
import org.domain.Professor;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DeliveryDAO implements IDeliveryDAO {
    private final Database database;
    private ResultSet resultSet;

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
    public int addDeliveryToActivity(Delivery delivery, int idActivity) {
        int idDelivery = 0;
        try(Connection conn = database.getConnection() ){
            conn.setAutoCommit(false);
            String statement = "CALL addDelivery(?,?,?,?)";
            PreparedStatement addDelivery = conn.prepareStatement(statement);
            addDelivery.setInt(1,idActivity);
            addDelivery.setInt( 2, delivery.getPractitioner().getId() );
            File file = delivery.getDocument().getDocument();
            FileInputStream fileInputStream = new FileInputStream(file);
            addDelivery.setBinaryStream(3, fileInputStream);
            addDelivery.setString(4, delivery.getDocument().name() );
            addDelivery.executeUpdate();
            statement = "SELECT LAST_INSERT_ID()";
            addDelivery = conn.prepareStatement(statement);
            resultSet = addDelivery.executeQuery();
            resultSet.next();
            idDelivery = resultSet.getInt(1);
            conn.commit();
        } catch (SQLException | NullPointerException | FileNotFoundException e) {
            Logger.getLogger(DeliveryDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return idDelivery;
    }

    /***
     * Evaluate an activity in database.
     * <p>
     * This method is used by Professor when he/she needs evaluate an activity saved in database.
     * </p>
     * @param idDelivery delivery's id to evaluate
     * @param  score the delivery score
     * @param observation the observations
     * @return boolean true if 1 o more than 1 rows are affected
     */
    @Override
    public boolean evaluateDeliveryOfActivity(int idDelivery, float score, String observation) {
        int rowsAffected = 0;
        try(Connection conn = database.getConnection()){
            conn.setAutoCommit(false);
            String statement = "UPDATE Delivery SET observation = ?, score = ? WHERE id_delivery = ?";
            PreparedStatement updateDeliveryInformation = conn.prepareStatement(statement);
            updateDeliveryInformation.setString(1, observation);
            updateDeliveryInformation.setFloat(2, score);
            updateDeliveryInformation.setInt(3, idDelivery);
            rowsAffected = updateDeliveryInformation.executeUpdate();
            conn.commit();
        } catch (SQLException | NullPointerException e) {
            Logger.getLogger(DeliveryDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return  rowsAffected > 0;
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
    public List<Delivery> getAllDeliveriesByActivity(int idActivity) {
        String statement = "SELECT DEL.id_delivery, DEL.observation, DEL.score, DEL.file, DEL.filename, ACT.id_activity, ACT.name, ACT.description, ACT.deadline, COUR.id_course, COUR.NRC, COUR.name, COUR.PERIOD, PERSPROF.name, PERSPROF.phoneNumber, PERSPROF.email, PROF.cubicle, PROF.staff_number FROM delivery AS DEL INNER JOIN Activity AS ACT ON DEL.id_activity = ? INNER JOIN Professor AS PROF ON ACT.id_professor = PROF.id_person INNER JOIN Person AS PERSPROF ON PROF.id_person = PERSPROF.id_person INNER JOIN Course AS COUR ON PERSPROF.id_course = COUR.id_course";
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
    public List<Delivery> getAllDeliveriesByPractitionerID(int idPractitioner) {
        String statement = "SELECT DEL.id_delivery, DEL.observation, DEL.score, DEL.file, DEL.filename, ACT.id_activity, ACT.name, ACT.description, ACT.deadline, COUR.id_course, COUR.NRC, COUR.name, COUR.PERIOD, PERSPROF.name, PERSPROF.phoneNumber, PERSPROF.email, PROF.cubicle, PROF.staff_number FROM delivery AS DEL INNER JOIN Activity AS ACT ON DEL.id_activity = ACT.id_activity AND DEL.id_practitioner = ? INNER JOIN Professor AS PROF ON ACT.id_professor = PROF.id_person INNER JOIN Person AS PERSPROF ON PROF.id_person = PERSPROF.id_person INNER JOIN Course AS COUR ON PERSPROF.id_course = COUR.id_course";
        return getAllDeliveryByStatementAndID(statement, idPractitioner);
    }

    private List<Delivery> getAllDeliveryByStatementAndID(String statement, int id) {
        List<Delivery> deliveries = new ArrayList<>();
        try(Connection conn = database.getConnection() ) {
            conn.setAutoCommit(false);
            PreparedStatement preparedStatement = conn.prepareStatement(statement);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            while( resultSet.next() ) {
                Course course = new Course();
                course.setId(resultSet.getInt( "COUR.id_course") );
                course.setName(resultSet.getString( "COUR.name") );
                course.setNRC(resultSet.getString( "COUR.NRC") );
                course.setPeriod(resultSet.getString( "COUR.period") );
                Professor professor = new Professor();
                professor.setName(resultSet.getString( "PERSPROF.name") );
                professor.setPhoneNumber(resultSet.getString( "PERSPROF.phoneNumber") );
                professor.setEmail(resultSet.getString( "PERSPROF.email"));
                professor.setCourse(course);
                professor.setStaffNumber(resultSet.getString( "PROF.staff_number") );
                professor.setCubicle(resultSet.getInt( "PROF.cubicle") );
                Activity activity = new Activity();
                activity.setDeadline(resultSet.getDate( "ACT.deadline") );
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
//                Document document = Document.valueOf( resultSet.getString("DEL.filename").toUpperCase() );
//                File file = new File("hola.txt");
//                FileOutputStream fileOutputStream = new FileOutputStream(file);
//                InputStream input = resultSet.getBinaryStream("DEL.file");
//                byte[] buffer = new byte[1024];
//                while (input.read(buffer) > 0) {
//                    fileOutputStream.write(buffer);
//                }
//                document.setFile(file);
                delivery.setDocument(null);
                deliveries.add(delivery);
                conn.commit();
            }
        } catch (SQLException | NullPointerException e) {
            Logger.getLogger(DeliveryDAO.class.getName()).log(Level.WARNING, null, e);
        }
        return deliveries;
    }



}
