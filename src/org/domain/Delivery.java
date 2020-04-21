package org.domain;

public class Delivery {
    private float score;
    private String observation;
    private Document document;
    private Practitioner practitioner;
    private Activity activity;

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
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
                "score=" + score +
                ", observation='" + observation + '\'' +
                ", document=" + document +
                ", practitioner=" + practitioner +
                ", activity=" + activity +
                '}';
    }

}
