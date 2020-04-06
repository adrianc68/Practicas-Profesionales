package org.domain;

import java.util.ArrayList;

public class Practicing extends Person{
    private String enrollment;
    private Project project;
    private ArrayList<Project>  selectedProjects;
    private Professor professor;

    public String getEnrollment() {
        return enrollment;
    }

    public void setEnrollment(String enrollment) {
        this.enrollment = enrollment;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

}
