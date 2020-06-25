package org.database.dao.projectdao;

import org.database.dao.professordao.ModifyRowProfessorTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        GetProjectTest.class,
        ModifyRowProjectTest.class
})
public class ProjectDAOSuiteTest {
}
