package test.org.database.dao;

import org.database.dao.DeliveryDAO;
import org.domain.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeliveryDAOTest {

    private final int idPractitioner = 1;
    private final int idActivity = 1;
    private final int idDelivery = 1;

    @Test
    void addDeliveryActivity() {
        DeliveryDAO deliveryDAO = new DeliveryDAO();
        Course course = new Course();
        course.setId(1);
        Practitioner practitioner = new Practitioner();
        practitioner.setCourse(course);
        practitioner.setId(1);
        Professor professor = new Professor();
        professor.setId(2);
        Activity activity = new Activity();
        activity.setId(1);
        activity.setProfessor(professor);
        Document document = Document.MONTHLY_REPORT;
        Delivery delivery = new Delivery();
        delivery.setDocument(document);
        delivery.setActivity(activity);
        delivery.setPractitioner(practitioner);
        int actual = deliveryDAO.addDeliveryToActivity(delivery, idActivity);
        assertNotNull(actual);
    }

    @Test
    void evaluateActivityDelivery() {
        DeliveryDAO deliveryDAO = new DeliveryDAO();
        String  observation = "Sin observaciones";
        float score = 9;
        assertTrue( deliveryDAO.evaluateDeliveryOfActivity(idDelivery, score, observation) );
    }

    @Test
    void getAllDeliveriesByActivity() {
        DeliveryDAO deliveryDAO = new DeliveryDAO();
        int expected = 1;
        int actual = deliveryDAO.getAllDeliveriesByActivity(idActivity).size();
        assertEquals(expected, actual);
    }

    @Test
    void getAllDeliveriesByPractitionerID() {
        DeliveryDAO deliveryDAO = new DeliveryDAO();
        int expected = 1;
        int actual = deliveryDAO.getAllDeliveriesByPractitionerID(idPractitioner).size();
        assertEquals(expected, actual);
    }

}