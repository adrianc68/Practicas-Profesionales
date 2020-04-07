package org.dao;

import org.domain.Practicing;
import org.domain.Project;

import java.util.List;

public interface IPracticingDAO {
    void assignProject(int idPracticing, int idProject);
    void addSelectedProject(Practicing practicing);
    List<Project> getSelectedProjects(Practicing practicing);

}
