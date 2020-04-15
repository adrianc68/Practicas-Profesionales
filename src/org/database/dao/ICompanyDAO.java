package org.database.dao;

import org.domain.Company;

import java.util.List;

public interface ICompanyDAO {
    void addCompany(Company company);
    int getIDCompany(String name, int id_course);
    List<Company> getAllCompaniesFromLastCourse();

}
