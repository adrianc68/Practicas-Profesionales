package org.domain;

import java.util.ArrayList;

public class Practicing extends Person{
    private String enrollment;
    private Project project;
    private Professor professor;
    private Record record;
    private ArrayList<Project>  selectedProjects;

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

    public Record getRecord() {
        return record;
    }

    public void setRecord(Record record) {
        this.record = record;
    }

    public ArrayList<Project> getSelectedProjects() {
        return selectedProjects;
    }

    public void setSelectedProjects(ArrayList<Project> selectedProjects) {
        this.selectedProjects = selectedProjects;
    }
}
