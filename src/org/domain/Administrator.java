package org.domain;

public class Administrator extends Person {
    private String staffNumber;

    public String getStaffNumber() {
        return staffNumber;
    }

    public void setStaffNumber(String staffNumber) {
        this.staffNumber = staffNumber;
    }

    @Override
    public String toString() {
        return "Administrator{" +
                "staffNumber='" + staffNumber + '\'' +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", course=" + course +
                ", activityState=" + activityState +
                '}';
    }

}
