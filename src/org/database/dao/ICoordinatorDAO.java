package org.database.dao;

import org.domain.Coordinator;

public interface ICoordinatorDAO {
    int addCoordinator(Coordinator coordinator);
    int removeCoordinator(Coordinator coordinator);


}
