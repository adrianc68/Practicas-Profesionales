package org.util;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class CSSPropertiesTest {

    @Test
    public void readTheme() {
        Theme expected = Theme.DARK_THEME;
        Theme actual = CSSProperties.readTheme();
        assertEquals(expected, actual);
    }

    @Test
    public void writeThemeProperties() {
        boolean expected = true;
        boolean actual = CSSProperties.writeThemeProperties(Theme.DARK_THEME);
        assertEquals(expected, actual);
    }

    @Test
    public void readConfiguredAppProperties() {
        boolean expected = false;
        boolean actual = CSSProperties.readConfiguredAppProperties();
        assertEquals(expected,actual);
    }

    @Test
    public void writeConfiguredAppProperties() {
        boolean expected = true;
        boolean actual = CSSProperties.writeConfiguredAppProperties();
        assertEquals(expected, actual);
    }

}