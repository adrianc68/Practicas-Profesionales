package org.domain;

import java.util.List;

public class Practitioner extends Person{
    private String enrollment;
    private Project project;
    private Professor professor;
    private List<Project> selectedProjects;
    private List<Delivery> deliveries;

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

    public List<Project> getSelectedProjects() {
        return selectedProjects;
    }

    public void setSelectedProjects(List<Project> selectedProjects) {
        this.selectedProjects = selectedProjects;
    }

    public List<Delivery> getDeliveries() {
        return deliveries;
    }

    public void setDeliveries(List<Delivery> deliveries) {
        this.deliveries = deliveries;
    }

    @Override
    public String toString() {
        return "Practitioner{" +
                "enrollment='" + enrollment + '\'' +
                ", project=" + project +
                ", professor=" + professor +
                ", selectedProjects=" + selectedProjects +
                ", deliveries=" + deliveries +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", course=" + course +
                ", activityState='" + activityState + '\'' +
                '}';
    }

}
