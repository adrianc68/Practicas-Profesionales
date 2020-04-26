package org.database.dao;

import org.domain.Coordinator;
import java.util.List;

public interface ICoordinatorDAO {
    boolean addCoordinator(Coordinator coordinator);
    boolean removeCoordinatorByID(int idCoordinator);
    List<Coordinator> getAllCoordinators();
}
