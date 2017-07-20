package gui.util;

import javafx.scene.control.Alert;

public class GUIUtil {

    public static void showAlert(final Alert.AlertType type, final String title, final String message)
    {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }
}
