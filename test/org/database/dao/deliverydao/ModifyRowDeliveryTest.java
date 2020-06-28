package org.database.dao.deliverydao;

import org.database.dao.DeliveryDAO;
import org.domain.Activity;
import org.domain.ActivityState;
import org.domain.Course;
import org.domain.Delivery;
import org.domain.Practitioner;
import org.domain.Professor;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.util.DateFormatter;
import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ModifyRowDeliveryTest {
    static int idDelivery;

    @Test
    public void addDeliveryToActivity() throws SQLException {
        Delivery delivery = new Delivery();
        delivery.setScore(0);
        delivery.setObservation(null);
        delivery.setDocumentPath("RL04_practica.pdf");
        Activity activity = new Activity();
        Professor professor = new Professor();
        professor.setCubicle(2);
        professor.setStaffNumber("233");
        professor.setActivityState(ActivityState.ACTIVE);
        Course course = new Course();
        course.setName("Practicas Profesionales");
        course.setNRC("N104543");
        course.setPeriod("AGO 2020 - DIC 2020");
        course.setId(1);
        professor.setCourse(course);
        professor.setEmail("p@hotmail.com");
        professor.setName("Juan Carlos");
        professor.setPhoneNumber("2232459685");
        professor.setId(5);
        activity.setProfessor(professor);
        activity.setDescription("Realizar los diagramas de secuencia");
        activity.setName("RL04 Lectura de comprensión del capitulo IX de Wiegers RL05 ");
        activity.setDeliveries(null);
        activity.setDeadline( DateFormatter.getTimeStampByString("2021-06-26 00:30:01") );
        delivery.setActivity(activity);
        Practitioner practitioner = new Practitioner();
        practitioner.setEnrollment("S19023349");
        practitioner.setSelectedProjects(null);
        practitioner.setDeliveries(null);
        practitioner.setActivityState(ActivityState.ACTIVE);
        practitioner.setCourse(course);
        practitioner.setEmail("asdf@hotmail.com");
        practitioner.setName("Roberto Sanchez");
        practitioner.setPhoneNumber("234223223");
        practitioner.setId(9);
        delivery.setPractitioner(practitioner);
        idDelivery = new DeliveryDAO().addDeliveryToActivity(delivery);
        delivery.setId(idDelivery);
        int unexpected = 0;
        int actual = delivery.getId();
        assertNotEquals(unexpected, actual);
    }

    @Test
    public void evaluateDeliveryOfActivity() throws SQLException {
        float score = 500;
        String observation = "La entrega ha sido revisada";
        boolean isEvaluated = new DeliveryDAO().evaluateDeliveryOfActivity(idDelivery, score, observation);
        assertTrue(isEvaluated);
    }

    @Test
    public void removeDeliveryById() throws SQLException {
        boolean isRemoved = new DeliveryDAO().removeDeliveryById(idDelivery);
        assertTrue(isRemoved);
    }

}
