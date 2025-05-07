package id.alphareso.meidofx.base.handlers;

import javafx.scene.Node;
import javafx.stage.Stage;

/**
 * Handler for window dragging functionality, enables moving the window by dragging the title bar.
 */
public class WindowDragHandler {
    private double xOffset = 0;
    private double yOffset = 0;
    private final Stage stage;

    /**
     * Creates a new WindowDragHandler for the specified stage.
     *
     * @param stage The stage to be dragged
     */
    public WindowDragHandler(Stage stage) {
        this.stage = stage;
    }

    /**
     * Applies drag functionality to the specified node, typically a title bar.
     *
     * @param node The node that will be used as a draggable area
     */
    public void enableDrag(Node node) {
        node.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        node.setOnMouseDragged(event -> {
            // Don't allow dragging when maximized
            if (!stage.isMaximized()) {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            }
        });

        // Double click to maximize/restore
        node.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                stage.setMaximized(!stage.isMaximized());
            }
        });
    }
}