package org.domain;

import java.util.ArrayList;

public class Company {
    private int id;
    private String name;
    private String address;
    private String city;
    private String state;
    private String email;
    private String phoneNumber;
    private int directUsers;
    private int indirectUsers;
    private Sector sector;
    private Course course;
    private Coordinator coordinator;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getDirectUsers() {
        return directUsers;
    }

    public void setDirectUsers(int directUsers) {
        this.directUsers = directUsers;
    }

    public int getIndirectUsers() {
        return indirectUsers;
    }

    public void setIndirectUsers(int indirectUsers) {
        this.indirectUsers = indirectUsers;
    }

    public Sector getSector() {
        return sector;
    }

    public void setSector(Sector sector) {
        this.sector = sector;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Coordinator getCoordinator() {
        return coordinator;
    }

    public void setCoordinator(Coordinator coordinator) {
        this.coordinator = coordinator;
    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", directUsers=" + directUsers +
                ", indirectUsers=" + indirectUsers +
                ", sector=" + sector +
                ", course=" + course +
                ", coordinator=" + coordinator +
                '}';
    }
}
