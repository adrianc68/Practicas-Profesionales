package org.database.dao;

import org.domain.Delivery;
import java.util.List;

public interface IDeliveryDAO {
    boolean addDeliveryActivity();
    boolean evaluateActivityDelivery(int idActivity, float score, String observation);
    List<Delivery> getAllDeliveriesByActivity();

    /***
     * Get activitie's delivery of practitioner from database.
     * <p>
     * This method return a delivery list of practitioner.
     * </p>
     * @param idPractitioner
     * @return List<Delivery> A delivery list of practitioner.
     */
    List<Delivery> getAllDeliveriesByPractitionerID(int idPractitioner);
    List<Delivery> getAllDeliveriesFromLastCourse();

}
