package org.util;

public enum Theme {
    LIGHT_THEME("stylesheet/whitetheme/stylesheet.css"),
    DARK_THEME("stylesheet/darktheme/stylesheet.css"),
    UV_THEME("stylesheet/uv/stylesheet.css");

    private String theme;

    Theme(String theme) {
        this.theme = theme;
    }

    public String getTheme() {
        return theme;
    }

}
