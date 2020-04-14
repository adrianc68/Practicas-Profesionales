package org.dao;

import org.domain.Practicing;
import org.domain.Project;
import java.util.List;

public interface IPracticingDAO {
    void addPracticing(Practicing practicing);
    void removePracticing(int idPracticing);
    void assignProject(int idPracticing, int idProject);
    void assignProfessor(int idPracticing, int idProfessor);
    void addSelectedProjectByPracticing(Practicing practicing);
    List<Project> getSelectedProjectsByIDPracticing(int idPracticing);

}
