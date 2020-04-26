package org.database.dao;

import org.database.Database;
import org.domain.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
    private ResultSet result;

    public DeliveryDAO() {
        database = new Database();
    }

    @Override
    public boolean addDeliveryActivity() {
        return true;
    }

    @Override
    public boolean evaluateActivityDelivery(int idActivity, float score, String observation) {
        return true;
    }

    @Override
    public List<Delivery> getAllDeliveriesByActivity() {
        return null;
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
        List<Delivery> deliveries = new ArrayList<>();
        try(Connection conn = database.getConnection() ) {
            String statement = "SELECT DEL.observation, DEL.score, DEL.file, DEL.filename, ACT.id_activity, ACT.name, ACT.description, ACT.deadline, COUR.id_course, COUR.NRC, COUR.name, COUR.PERIOD, PERSPROF.name, PERSPROF.phoneNumber, PERSPROF.email, PROF.cubicle, PROF.staff_number FROM delivery AS DEL INNER JOIN Activity AS ACT ON DEL.id_activity = ACT.id_activity AND DEL.id_practitioner = ? INNER JOIN Professor AS PROF ON ACT.id_professor = PROF.id_person INNER JOIN Person AS PERSPROF ON PROF.id_person = PERSPROF.id_person INNER JOIN Course AS COUR ON PERSPROF.id_course = COUR.id_course";
            PreparedStatement preparedStatement = conn.prepareStatement(statement);
            preparedStatement.setInt(1, idPractitioner);
            result = preparedStatement.executeQuery();
            while( result.next() ) {
                Course course = new Course();
                course.setId(result.getInt("COUR.id_course"));
                course.setName(result.getString("COUR.name"));
                course.setNRC(result.getString("COUR.NRC"));
                course.setPeriod(result.getString("COUR.period"));
                Professor professor = new Professor();
                professor.setName(result.getString("PERSPROF.name"));
                professor.setPhoneNumber(result.getString("PERSPROF.phoneNumber"));
                professor.setEmail(result.getString("PERSPROF.email"));
                professor.setCourse(course);
                professor.setStaffNumber(result.getString("PROF.staff_number"));
                professor.setCubicle(result.getInt("PROF.cubicle"));
                Activity activity = new Activity();
                activity.setDeadline(result.getDate("ACT.deadline"));
                activity.setDescription(result.getString("ACT.description"));
                activity.setId(result.getInt("ACT.id_activity"));
                activity.setName(result.getString("ACT.name"));
                activity.setProfessor(professor);
                activity.setDeliveries(null);
                Delivery delivery = new Delivery();
                delivery.setActivity(activity);
                Document document = Document.valueOf(result.getString("DEL.filename").toUpperCase());
                InputStream input = result.getBinaryStream("DEL.file");
                File file = readBlobFromDatabase(document.toString(), input);
                document.setFile(file);
                delivery.setDocument(document);
                delivery.setObservation(result.getString("DEL.observation"));
                delivery.setPractitioner(null);
                delivery.setScore(result.getFloat("DEL.score"));
                deliveries.add(delivery);
            }
        } catch (SQLException | NullPointerException | IOException e) {
            Logger.getLogger(DeliveryDAO.class.getName()).log(Level.WARNING, null, e);
        }
        return deliveries;
    }

    @Override
    public List<Delivery> getAllDeliveriesFromLastCourse() {
        return null;
    }

    private File readBlobFromDatabase(String filename, InputStream input) throws IOException, SQLException {
        File file = new File(filename.toString());
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        if(input != null){
            byte[] buffer = new byte[1024];
            while (input.read(buffer) > 0) {
                fileOutputStream.write(buffer);
            }
        }
        return file;
    }

}
