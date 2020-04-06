package org.domain;

import java.io.File;
import java.util.HashMap;

public class Record {
    private int id;
    private String period;
    private Practicing practicing;
    private HashMap<File, String> document;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public Practicing getPracticing() {
        return practicing;
    }

    public void setPracticing(Practicing practicing) {
        this.practicing = practicing;
    }

    public HashMap<File, String> getDocument() {
        return document;
    }

    public void setDocument(HashMap<File, String> document) {
        this.document = document;
    }

}
