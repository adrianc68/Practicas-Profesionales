package org.database.dao.coordinatordao;

import org.database.dao.CoordinatorDAO;
import org.domain.Coordinator;
import org.junit.Test;
import java.sql.SQLException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GetCoordinatorTest {
    @Test
    public void getAllCoordinatorsByCourseID() throws SQLException {
        int idCourse = 1;
        List<Coordinator> coordinatorList = new CoordinatorDAO().getAllCoordinatorsByCourseID(idCourse);
        assertNotNull(coordinatorList);
    }

    @Test
    public void getAllCoordinators() throws SQLException {
        List<Coordinator> coordinatorList = new CoordinatorDAO().getAllCoordinators();
        int expected = 6;
        int actual = coordinatorList.size();
        assertEquals(expected,actual);
    }

}