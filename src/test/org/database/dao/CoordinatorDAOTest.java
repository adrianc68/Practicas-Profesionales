package test.org.database.dao;

import org.database.DatabaseProperties;
import org.database.dao.CoordinatorDAO;
import org.database.dao.CourseDAO;
import org.domain.Coordinator;
import org.domain.Course;
import org.junit.*;
import org.junit.runners.MethodSorters;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CoordinatorDAOTest {
    private DatabaseProperties databaseProperties;
    private Course course;
    private Coordinator coordinator;

    public CoordinatorDAOTest() {
        databaseProperties = new DatabaseProperties("database.properties");
    }

    @After
    public void after() {
        databaseProperties.writeProperties("anonimo", "12345", "jdbc:mysql://localhost:3306/Practices?useTimezone=true&serverTimezone=UTC");
    }

    @Before
    public void before() {
        databaseProperties.writeProperties("anonimo", "12345", "jdbc:mysql://localhost:3306/PracticesTest?useTimezone=true&serverTimezone=UTC");

    }

    @Test
    public void aAddCoordinator() {
        coordinator = new Coordinator();
        coordinator.setName("Rafael Lira Santos");
        coordinator.setPhoneNumber("2281569492");
        coordinator.setEmail("rafaellirasa@hotmail.com");
        coordinator.setCubicle(25);
        coordinator.setStaffNumber("0001-A1");
        Course courses = new Course();
        courses.setId(1);
        coordinator.setCourse(courses);
        CoordinatorDAO coordinatorDAO = new CoordinatorDAO();
        int unexpected = 0;
        int actual = coordinatorDAO.addCoordinator(coordinator);
        coordinator.setId(actual);
        assertNotEquals(unexpected, actual);
    }

    @Test
    public void bGetAllCoordinators() {
        CoordinatorDAO coordinatorDAO = new CoordinatorDAO();
        List<Coordinator> coordinators = coordinatorDAO.getAllCoordinatorsByCourseID(1);
        int actual = coordinators.size();
        int unexpected = 0;
        assertNotEquals(unexpected, actual);
    }

    @Test
    public void cRemoveCoordinator() {
        CoordinatorDAO coordinatorDAO = new CoordinatorDAO();
        boolean actual = coordinatorDAO.removeCoordinatorByID(coordinator.getId());
        assertTrue(actual);
    }

}