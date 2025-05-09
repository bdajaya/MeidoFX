package id.alphareso.meidofx.base.controls;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.Objects;

/**
 * Provides customizable window control buttons (minimize, maximize, close)
 * that can be used independently throughout the application.
 */
public class WindowControlButtons extends HBox {
    private static final String MINIMIZE_ICON_PATH = "images/minimize-light.png"; // Path harus konsisten
    private static final String MAXIMIZE_ICON_PATH = "images/maximize-light.png";
    private static final String CLOSE_ICON_PATH = "images/close-light.png";
    private static final double ICON_WIDTH = 16;
    private static final double ICON_HEIGHT = 16;
    private static final double DEFAULT_SPACING = 5;
    private static final double DEFAULT_PADDING = 5;

    private final Button minimizeButton;
    private final Button maximizeButton;
    private final Button closeButton;
    private final Stage stage;

    /**
     * Creates window control buttons attached to the specified stage.
     *
     * @param stage The stage these buttons will control
     */
    public WindowControlButtons(Stage stage) {
        this.stage = stage;
        this.setSpacing(DEFAULT_SPACING);
        this.setPadding(new Insets(DEFAULT_PADDING));
        this.setAlignment(Pos.CENTER_RIGHT);

        // Initialize buttons
        minimizeButton = createButton(MINIMIZE_ICON_PATH, ICON_WIDTH, ICON_HEIGHT);
        maximizeButton = createButton(MAXIMIZE_ICON_PATH, ICON_WIDTH, ICON_HEIGHT);
        closeButton = createButton(CLOSE_ICON_PATH, ICON_WIDTH, ICON_HEIGHT);

        // Set button actions
        setupButtonActions();

        // Add buttons to the container
        this.getChildren().addAll(minimizeButton, maximizeButton, closeButton);
    }

    /**
     * Creates a control button with the specified image.
     *
     * @param imagePath Path to the button image
     * @param width Width of the button image
     * @param height Height of the button image
     * @return A styled button with the specified image
     */
    private Button createButton(String imagePath, double width, double height) {
        Button button = new Button();
        try {
            Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/" + imagePath)));
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(width);
            imageView.setFitHeight(height);
            button.setGraphic(imageView);
        } catch (Exception e) {
            System.err.println("Could not load image: " + imagePath);
            button.setText(imagePath.contains("close") ? "X" :
                    imagePath.contains("minimize") ? "_" : "□");
        }

        button.getStyleClass().add("window-control-button");
        button.setFocusTraversable(false);

        return button;
    }

    /**
     * Sets up actions for each window control button.
     */
    private void setupButtonActions() {
        minimizeButton.setOnAction(event -> stage.setIconified(true));

        maximizeButton.setOnAction(event -> {
            if (stage.isMaximized()) {
                stage.setMaximized(false);
                // Option to update the maximize button image here if desired
            } else {
                stage.setMaximized(true);
                // Option to update the maximize button image here if desired
            }
        });

        closeButton.setOnAction(event -> stage.close());
    }

    /**
     * Sets the visibility of minimize button.
     *
     * @param visible true to show the button, false to hide it
     */
    public void setMinimizeButtonVisible(boolean visible) {
        minimizeButton.setVisible(visible);
        minimizeButton.setManaged(visible);
    }

    /**
     * Sets the visibility of maximize button.
     *
     * @param visible true to show the button, false to hide it
     */
    public void setMaximizeButtonVisible(boolean visible) {
        maximizeButton.setVisible(visible);
        maximizeButton.setManaged(visible);
    }

    /**
     * Sets the visibility of close button.
     *
     * @param visible true to show the button, false to hide it
     */
    public void setCloseButtonVisible(boolean visible) {
        closeButton.setVisible(visible);
        closeButton.setManaged(visible);
    }

    /**
     * Gets the minimize button.
     *
     * @return The minimize button
     */
    public Button getMinimizeButton() {
        return minimizeButton;
    }

    /**
     * Gets the maximize button.
     *
     * @return The maximize button
     */
    public Button getMaximizeButton() {
        return maximizeButton;
    }

    /**
     * Gets the close button.
     *
     * @return The close button
     */
    public Button getCloseButton() {
        return closeButton;
    }
}