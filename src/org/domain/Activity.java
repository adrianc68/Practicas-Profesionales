package org.domain;

import java.sql.Date;
import java.util.List;

public class Activity {
    private int id;
    private String name;
    private String description;
    private Date deadline;
    private Professor professor;
    private List<Delivery> deliveries;
    private List<Document> documents;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public List<Delivery> getDeliveries() {
        return deliveries;
    }

    public void setDocuments(List<Delivery> deliveries) {
        this.deliveries = deliveries;
    }

    public List<Document> getDocuments() {
        return documents;
    }

    public void setDeliveries(List<Document> documents) {
        this.documents = documents;
    }

    @Override
    public String toString() {
        return "Activity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", deadline=" + deadline +
                ", professor=" + professor +
                ", deliveries=" + deliveries +
                '}';
    }

}
