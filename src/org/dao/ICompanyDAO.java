package org.dao;

import org.domain.Company;
import org.domain.Course;

import java.util.List;

public interface ICompanyDAO {
    void addCompany(Company company);
    int getIDLastCompanyAdded();
    int getIDCompany(String name, int id_course);
    List<Company> getAllCompanies();

}
