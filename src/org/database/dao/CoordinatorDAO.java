package org.database.dao;

import org.domain.Coordinator;
import java.util.List;

public class CoordinatorDAO implements ICoordinatorDAO{
    @Override
    public boolean addCoordinator(Coordinator coordinator) {
        return true;
    }

    @Override
    public boolean removeCoordinatorByID(int idCoordinator) {
        return true;
    }

    @Override
    public List<Coordinator> getAllCoordinators() {
        return null;
    }
}
