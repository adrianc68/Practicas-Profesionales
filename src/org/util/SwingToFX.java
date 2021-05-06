package org.util;

import javafx.embed.swing.SwingNode;
import javafx.scene.layout.Pane;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;

public class SwingToFX {

    /***
     * This method add a Swing component to a Pane (AnchorPane, StackPane, and so on)
     * <p>
     * It's used by two controllers AddDeliveryController and EvaluateDeliveryController
     * </p>
     * @param jComponent to set in pane
     * @param pane the root
     */
    public static void addJComponentToPane(JComponent jComponent, Pane pane) {
        SwingNode swingNode = new SwingNode();
        SwingUtilities.invokeLater( () -> {
            swingNode.setContent(jComponent);
        });
        pane.getChildren().add(swingNode);
    }

    /***
     * This method convert a Swing component to a JavaFX component
     * <p>
     * It's used by two controllers AddDeliveryController and EvaluateDeliveryController
     * </p>
     * @param jComponent
     */
    public static SwingNode convertJComponentToNode(JComponent jComponent) {
        SwingNode swingNode = new SwingNode();
        SwingUtilities.invokeLater( () -> {
            swingNode.setContent(jComponent);
        });
        return swingNode;
    }

}
