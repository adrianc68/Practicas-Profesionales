package org.database.dao;

import org.domain.Delivery;
import java.util.List;

public interface IDeliveryDAO {
    /***
     * Add a Delivery to database.
     * <p>
     * This method is used by the Practitioner when he/she needs to add a Delivery.
     * </p>
     * @param delivery, idActivity
     * @return int representing the delivery's id
     */
    int addDeliveryToActivity(Delivery delivery, int idActivity);

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
    boolean evaluateDeliveryOfActivity(int idDelivery, float score, String observation);

    /***
     * Get all deliveries from activities from database.
     * <p>
     * This method return all deliveries by activity from database.
     * It's used by Professor.
     * </p>
     * @param idActivity
     * @return List<Delivery> A list containing deliveries
     */
    List<Delivery> getAllDeliveriesByActivity(int idActivity);

    /***
     * Get activitie's delivery of practitioner from database.
     * <p>
     * This method return a delivery list of practitioner.
     * </p>
     * @param idPractitioner
     * @return List<Delivery> A delivery list of practitioner.
     */
    List<Delivery> getAllDeliveriesByPractitionerID(int idPractitioner);

}
