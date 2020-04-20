package org.database.dao;

import org.domain.Delivery;

import java.util.List;

public interface IDeliveryDAO {
    int evaluateActivityDelivery(int idActivity, float score);

    List<Delivery> getAllDeliveriesByActivityFromLastCourse();

}
