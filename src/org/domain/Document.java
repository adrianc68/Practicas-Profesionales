package org.domain;

public enum Document {
    MONTHLY_REPORT("Monthly report"),
    PARTIAL_REPORT("Partial report"),
    SCHEDULE("Schedule"),
    ASSIGNMENT_OFFICE("Assignment office"),
    ACCEPTANCE_OFFICE("Acceptance office"),
    SELF_APPRAISAL("Self appraisal"),
    COMPANY_EVALUATION("Company evaluation");

    private String document;

    Document(String document){
        this.document = document;
    }

    public String getDocument(){
        return document;
    }

}
