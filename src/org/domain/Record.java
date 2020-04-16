package org.domain;

import java.io.File;
import java.util.HashMap;

public class Record {
    private Practicing practicing;
    private HashMap<File, Document> document;

    public Practicing getPracticing() {
        return practicing;
    }

    public void setPracticing(Practicing practicing) {
        this.practicing = practicing;
    }

    public HashMap<File, Document> getDocument() {
        return document;
    }

    public void setDocument(HashMap<File, Document> document) {
        this.document = document;
    }
}
