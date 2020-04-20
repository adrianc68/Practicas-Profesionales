package org.domain;

import java.io.File;

public enum Document {
    PARTIAL_REPORT(new File("PartialReport.pdf")),
    MONTHLY_REPORT(new File("MonthlyReport.pdf")),
    SCHEDULE(new File("Schedule.pdf")),
    ASSIGNMENT_OFFICE(new File("AssignmentOffice.pdf")),
    ACCEPTANCE_OFFICE(new File("AcceptanceOffice.pdf")),
    SELF_APPRAISAL(new File("SelfAppraisal.pdf")),
    COMPANY_EVALUATION(new File("CompanyEvaluation.pdf"));

    private File document;

    Document(File document){
        this.document = document;
    }

    public File getDocument(){
        return document;
    }

    public void setFile(File document) {
        this.document = document;
    }

}
