package test.org.database.dao;

import org.database.dao.DeliveryDAO;
import org.domain.Delivery;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class DeliveryDAOTest {

    @Test
    void addDeliveryActivity() {
    }

    @Test
    void evaluateActivityDelivery() {
    }

    @Test
    void getAllDeliveriesByActivity() {
    }

    @Test
    void getAllDeliveriesByPractitionerID() throws SQLException {
        DeliveryDAO deliveryDAO = new DeliveryDAO();
        int expected = 3;
        int result = deliveryDAO.getAllDeliveriesByPractitionerID(3).size();
        assertEquals(expected, result);
    }

    @Test
    void getAllDeliveriesFromLastCourse() {
    }
}