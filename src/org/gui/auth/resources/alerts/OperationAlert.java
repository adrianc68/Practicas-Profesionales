package org.gui.auth.resources.alerts;

import javafx.scene.control.Alert;

public class OperationAlert {
    public static void showSuccessfullAlert(String title, String contentText) {
        showAlert(Alert.AlertType.INFORMATION, title, contentText);
    }

    public static void showUnsuccessfullAlert(String title, String contentText) {
        showAlert(Alert.AlertType.ERROR, title, contentText);
    }

    public static void showLostConnectionAlert() {
        String title = "¡Error al intentar realizar una conexión a la base de datos";
        String contentText = "No se pudo realizar la conexión con la base de datos, intentalo más tarde...";
        showAlert(Alert.AlertType.ERROR, title, contentText);
    }

    private static void showAlert(Alert.AlertType alertType, String title, String contextText) {
        Alert alert = new Alert(alertType);
        alert.setHeaderText(null);
        alert.setTitle(title);
        alert.setContentText(contextText);
        alert.showAndWait();
    }

}
