package org.database.dao.accessaccountdao;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(value = Suite.class)
@Suite.SuiteClasses({
        ChangePasswordTest.class,
        GeneratePasswordRecoveryCodeTest.class,
        GetRecoveryCodeTest.class
})
public class AccessAccountDAOSuiteTest { }
