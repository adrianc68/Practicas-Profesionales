package org.database.dao;

import org.domain.Coordinator;
import java.util.List;

public interface ICoordinatorDAO {
    boolean addCoordinator(Coordinator coordinator);
    boolean removeCoordinator(Coordinator coordinator);
    List<Coordinator> getAllCoordinators();
}
