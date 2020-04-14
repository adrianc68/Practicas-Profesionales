package org.dao;

import org.domain.Project;

import java.util.List;

public interface IProjectDAO {
    void addProject(Project project);
    void removeProjectByID(int idProject);
    void updateProjectInformation(Project project);
    List<Project> getAllAvailableProjectsFromLastCourse();
    List<Project> getAllProjectsFromLastCourse();

}
