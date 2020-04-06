package org.domain;

public class Coordinator extends Person {
    private int cubicle;
    private String staff_number;

    public int getCubicle() {
        return cubicle;
    }

    public void setCubicle(int cubicle) {
        this.cubicle = cubicle;
    }

    public String getStaff_number() {
        return staff_number;
    }

    public void setStaff_number(String staff_number) {
        this.staff_number = staff_number;
    }

    @Override
    public String toString() {
        return "Coordinator{" +
                "cubicle=" + cubicle +
                ", staff_number='" + staff_number + '\'' +
                '}';
    }

}
