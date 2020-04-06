package org.domain;

import java.util.ArrayList;

public class Project {
    private int id;
    private float duration;
    private String name;
    private String generalDescription;
    private String generalPurpose;
    private String schedule;
    private String chargeResponsable;
    private String nameResponsable;
    private String emailResponsable;
    private Company company;
    private ArrayList<String> mediateObjectives;
    private ArrayList<String> resources;
    private ArrayList<String> methodologies;
    private ArrayList<String> activities;
    private ArrayList<String> responsibilities;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getDuration() {
        return duration;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGeneralDescription() {
        return generalDescription;
    }

    public void setGeneralDescription(String generalDescription) {
        this.generalDescription = generalDescription;
    }

    public String getGeneralPurpose() {
        return generalPurpose;
    }

    public void setGeneralPurpose(String generalPurpose) {
        this.generalPurpose = generalPurpose;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public String getChargeResponsable() {
        return chargeResponsable;
    }

    public void setChargeResponsable(String chargeResponsable) {
        this.chargeResponsable = chargeResponsable;
    }

    public String getNameResponsable() {
        return nameResponsable;
    }

    public void setNameResponsable(String nameResponsable) {
        this.nameResponsable = nameResponsable;
    }

    public String getEmailResponsable() {
        return emailResponsable;
    }

    public void setEmailResponsable(String emailResponsable) {
        this.emailResponsable = emailResponsable;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public ArrayList<String> getMediateObjectives() {
        return mediateObjectives;
    }

    public void setMediateObjectives(ArrayList<String> mediateObjectives) {
        this.mediateObjectives = mediateObjectives;
    }

    public ArrayList<String> getResources() {
        return resources;
    }

    public void setResources(ArrayList<String> resources) {
        this.resources = resources;
    }

    public ArrayList<String> getActivities() {
        return activities;
    }

    public void setActivities(ArrayList<String> activities) {
        this.activities = activities;
    }

    public ArrayList<String> getResponsibilities() {
        return responsibilities;
    }

    public void setResponsibilities(ArrayList<String> responsibilities) {
        this.responsibilities = responsibilities;
    }

    public ArrayList<String> getMethodologies() {
        return methodologies;
    }

    public void setMethodologies(ArrayList<String> methodologies) {
        this.methodologies = methodologies;
    }

}
