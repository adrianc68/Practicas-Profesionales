package org.domain;

public enum Sector {
        PRIMARY("Primary"),
        SECONDARY("Secondary"),
        TERTIARY("Tertiary");

        private String sector;

        Sector(String sector) {
            this.sector = sector;
        }

        public String getSector() {
            return sector;
        }

}
