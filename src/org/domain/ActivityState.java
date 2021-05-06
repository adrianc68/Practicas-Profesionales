package org.domain;

public enum ActivityState {
    ACTIVE("Activo"),
    INACTIVE("Inactivo");

    private String status;

    ActivityState(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }

}
