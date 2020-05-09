package org.domain;

public class Delivery {
    private int id;
    private float score;
    private String observation;
    private String documentPath;
    private Practitioner practitioner;
    private Activity activity;

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public String getDocumentPath() {
        return documentPath;
    }

    public void setDocumentPath(String documentPath) {
        this.documentPath = documentPath;
    }

    public Practitioner getPractitioner() {
        return practitioner;
    }

    public void setPractitioner(Practitioner practitioner) {
        this.practitioner = practitioner;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    @Override
    public String toString() {
        return "Delivery{" +
                "id=" + id +
                ", score=" + score +
                ", observation='" + observation + '\'' +
                ", documentPath='" + documentPath + '\'' +
                ", practitioner=" + practitioner +
                ", activity=" + activity +
                '}';
    }
}
