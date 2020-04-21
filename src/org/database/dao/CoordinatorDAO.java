package org.database.dao;

import org.domain.Coordinator;
import java.util.List;

public class CoordinatorDAO implements ICoordinatorDAO{
    @Override
    public boolean addCoordinator(Coordinator coordinator) {
        return true;
    }

    @Override
    public boolean removeCoordinator(Coordinator coordinator) {
        return true;
    }

    @Override
    public List<Coordinator> getAllCoordinators() {
        return null;
    }
}
