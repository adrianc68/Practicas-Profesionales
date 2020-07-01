package org.gui.auth.resources.card;

import javafx.scene.layout.VBox;
import org.util.CSSProperties;

/***
 * This class is used to display information in it.
 * You can configure the subclass to display the information
 * as you want. And you should use the method setStyleClass()
 * to configure the theme selected.
 */
public class Card extends VBox {

    protected void setStyleClass() {
        String styleSheetPath = "/org/gui/auth/resources/" + CSSProperties.readTheme().getTheme() ;
        getStylesheets().clear();
        getStyleClass().clear();
        getStylesheets().add(styleSheetPath);
        getStyleClass().add("card");
    }

}
