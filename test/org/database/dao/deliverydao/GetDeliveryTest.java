package org.database.dao.deliverydao;

import org.database.dao.DeliveryDAO;
import org.domain.Delivery;
import org.junit.Test;
import java.sql.SQLException;
import java.util.List;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GetDeliveryTest {

    @Test
    public void getAllDeliveriesByActivity() throws SQLException {
        int idActivity = 9;
        List<Delivery> deliveryList = new DeliveryDAO().getAllDeliveriesByActivity(idActivity);
        int expected = 1;
        int actual = deliveryList.size();
        assertEquals(expected, actual);
    }

    @Test
    public void getAllDeliveriesByNoExistingActivity() throws SQLException {
        int idActivity = 999;
        List<Delivery> deliveryList = new DeliveryDAO().getAllDeliveriesByActivity(idActivity);
        int expected = 0;
        int actual = deliveryList.size();
        assertEquals(expected, actual);
    }

    @Test
    public void getAllDeliveriesByPractitionerID() throws SQLException {
        int idPractitioner = 9;
        List<Delivery> deliveryList = new DeliveryDAO().getAllDeliveriesByPractitionerID(idPractitioner);
        int expected = 1;
        int actual = deliveryList.size();
        assertEquals(expected, actual);
    }

    @Test
    public void getAllDeliveriesByNoExistingPractitionerID() throws SQLException {
        int idPractitioner = 4999999;
        List<Delivery> deliveryList = new DeliveryDAO().getAllDeliveriesByPractitionerID(idPractitioner);
        int expected = 0;
        int actual = deliveryList.size();
        assertEquals(expected, actual);
    }

    @Test
    public void getDeliveryByActivityAndPractitioner() throws SQLException {
        int idPractitioner = 9;
        int idActivity = 9;
        Delivery delivery = new DeliveryDAO().getDeliveryByActivityAndPractitioner(idActivity, idPractitioner);
        assertNotNull(delivery);
    }

    @Test
    public void getDeliveryByNoexistingActivityAndPractitioner() throws SQLException {
        int idPractitioner = 9999;
        int idActivity = 9999;
        Delivery delivery = new DeliveryDAO().getDeliveryByActivityAndPractitioner(idActivity, idPractitioner);
        assertNull(delivery);
    }

}
