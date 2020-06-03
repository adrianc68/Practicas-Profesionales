package org.domain;

public class Professor extends Person {
    private int cubicle;
    private String staffNumber;

    public int getCubicle() {
        return cubicle;
    }

    public void setCubicle(int cubicle) {
        this.cubicle = cubicle;
    }

    public String getStaffNumber() {
        return staffNumber;
    }

    public void setStaffNumber(String staffNumber) {
        this.staffNumber = staffNumber;
    }

    @Override
    public String toString() {
        return "Professor{" +
                "cubicle=" + cubicle +
                ", staffNumber='" + staffNumber + '\'' +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", course=" + course +
                ", activityState='" + activityState + '\'' +
                '}';
    }

}
