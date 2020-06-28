package org.database.dao.deliverydao;

import org.database.dao.DeliveryDAO;
import org.domain.Delivery;
import org.junit.Test;
import java.sql.SQLException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetAllDeliveryTest {

    @Test
    public void getAllDeliveriesByActivity() throws SQLException {
        List<Delivery> deliveryList = new DeliveryDAO().getAllDeliveriesByActivity(9);
        int expected = 1;
        int actual = deliveryList.size();
        assertEquals(expected, actual);
    }

    @Test
    public void getAllDeliveriesByNoExistingActivity() throws SQLException {
        List<Delivery> deliveryList = new DeliveryDAO().getAllDeliveriesByActivity(999);
        int expected = 0;
        int actual = deliveryList.size();
        assertEquals(expected, actual);
    }

    @Test
    public void getAllDeliveriesByPractitionerID() throws SQLException {
        List<Delivery> deliveryList = new DeliveryDAO().getAllDeliveriesByPractitionerID(9);
        int expected = 1;
        int actual = deliveryList.size();
        assertEquals(expected, actual);
    }

    @Test
    public void getAllDeliveriesByNoExistingPractitionerID() throws SQLException {
        List<Delivery> deliveryList = new DeliveryDAO().getAllDeliveriesByPractitionerID(49);
        int expected = 0;
        int actual = deliveryList.size();
        assertEquals(expected, actual);
    }

}
