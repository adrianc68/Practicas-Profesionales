package org.domain;

public enum Sector {
        PRIMARY("Primario"),
        SECONDARY("Secundario"),
        TERTIARY("Terciario");

        private String sector;

        Sector(String sector) {
            this.sector = sector;
        }

        public String getSector() {
            return sector;
        }

}
