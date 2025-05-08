package id.alphareso.meidofx.ui.pages;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * Simple help view component for the MeidoFX application.
 */
public class HelpView extends VBox {

    public HelpView() {
        setSpacing(10);
        setPadding(new Insets(20));
        getStyleClass().add("help-view");

        Label title = new Label("Help & Support");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Label description = new Label("This dialog provides information and guidance about using the MeidoFX application.");
        description.setWrapText(true);

        getChildren().addAll(title, description);
    }
}
