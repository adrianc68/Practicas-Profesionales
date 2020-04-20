package org.domain;

import java.util.List;

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
    private List<String> mediateObjectives;
    private List<String> immediateObjetives;
    private List<String> resources;
    private List<String> methodologies;
    private List<String> activities;
    private List<String> responsibilities;

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

    public List<String> getMediateObjectives() {
        return mediateObjectives;
    }

    public void setMediateObjectives(List<String> mediateObjectives) {
        this.mediateObjectives = mediateObjectives;
    }

    public List<String> getImmediateObjetives() {
        return immediateObjetives;
    }

    public void setImmediateObjetives(List<String> immediateObjetives) {
        this.immediateObjetives = immediateObjetives;
    }

    public List<String> getResources() {
        return resources;
    }

    public void setResources(List<String> resources) {
        this.resources = resources;
    }

    public List<String> getMethodologies() {
        return methodologies;
    }

    public void setMethodologies(List<String> methodologies) {
        this.methodologies = methodologies;
    }

    public List<String> getActivities() {
        return activities;
    }

    public void setActivities(List<String> activities) {
        this.activities = activities;
    }

    public List<String> getResponsibilities() {
        return responsibilities;
    }

    public void setResponsibilities(List<String> responsibilities) {
        this.responsibilities = responsibilities;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", duration=" + duration +
                ", name='" + name + '\'' +
                ", generalDescription='" + generalDescription + '\'' +
                ", generalPurpose='" + generalPurpose + '\'' +
                ", schedule='" + schedule + '\'' +
                ", chargeResponsable='" + chargeResponsable + '\'' +
                ", nameResponsable='" + nameResponsable + '\'' +
                ", emailResponsable='" + emailResponsable + '\'' +
                ", company=" + company +
                ", mediateObjectives=" + mediateObjectives +
                ", immediateObjetives=" + immediateObjetives +
                ", resources=" + resources +
                ", methodologies=" + methodologies +
                ", activities=" + activities +
                ", responsibilities=" + responsibilities +
                '}';
    }

}
