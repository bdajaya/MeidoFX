package id.alphareso.meidofx.base.handlers;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;

/**
 * Handler for window resizing functionality, enables resizing the window from the bottom-right corner.
 */
public class WindowResizeHandler {
    private double xOffset = 0;
    private double yOffset = 0;
    private final Stage stage;
    private final double resizeMargin = 5.0;
    private final Node resizeHandle;

    /**
     * Creates a new WindowResizeHandler for the specified stage with a custom resize handle.
     *
     * @param stage The stage to be resized
     * @param container The container where the resize handle will be added
     */
    public WindowResizeHandler(Stage stage, StackPane container) {
        this.stage = stage;

        // Create the resize handle with SVG path
        SVGPath svgPath = new SVGPath();
        svgPath.setContent("M542.72 884.053333l341.333333-341.333333a32 32 0 0 1 47.445334 42.816l-2.197334 2.432-341.333333 341.333333a32 32 0 0 1-47.466667-42.837333l2.197334-2.432 341.333333-341.333333-341.333333 341.333333z m-437.333333-10.666666l778.666666-778.666667a32 32 0 0 1 47.445334 42.816l-2.197334 2.432-778.666666 778.666667a32 32 0 0 1-47.466667-42.837334l2.197333-2.432 778.666667-778.666666-778.666667 778.666666z");
        svgPath.setStyle("-fx-fill: #9E9E9E;");

        Group iconGroup = new Group(svgPath);
        iconGroup.setScaleX(0.015); // Kecilkan
        iconGroup.setScaleY(0.015);

        // Create a container for the resize handle
        StackPane resizeIcon = new StackPane(iconGroup);
        resizeIcon.setPrefSize(10, 10);
        resizeIcon.setMaxSize(5, 5);
        resizeIcon.setMinSize(10, 10);
        resizeIcon.setCursor(Cursor.SE_RESIZE);
        resizeIcon.setStyle("-fx-background-color: green;");

        // Position the resize handle in the bottom-right corner
        resizeIcon.setTranslateX(5);
        resizeIcon.setTranslateY(5);

        // Add resize functionality
        resizeIcon.setOnMousePressed(this::handleMousePressed);
        resizeIcon.setOnMouseDragged(this::handleMouseDragged);

        this.resizeHandle = resizeIcon;

        // Add the resize handle to the container
        container.getChildren().add(resizeIcon);

        // Position it at the bottom-right corner
        StackPane.setAlignment(resizeIcon, javafx.geometry.Pos.BOTTOM_RIGHT);
    }

    /**
     * Handle mouse pressed event for resizing.
     *
     * @param event The mouse event
     */
    private void handleMousePressed(MouseEvent event) {
        xOffset = stage.getWidth() - event.getSceneX();
        yOffset = stage.getHeight() - event.getSceneY();
        event.consume();
    }

    /**
     * Handle mouse dragged event for resizing.
     *
     * @param event The mouse event
     */
    private void handleMouseDragged(MouseEvent event) {
        if (stage.isMaximized()) {
            return; // Don't resize when maximized
        }

        double newWidth = event.getSceneX() + xOffset;
        double newHeight = event.getSceneY() + yOffset;

        // Set minimum size
        if (newWidth >= stage.getMinWidth()) {
            stage.setWidth(newWidth);
        }

        if (newHeight >= stage.getMinHeight()) {
            stage.setHeight(newHeight);
        }

        event.consume();
    }

    /**
     * Gets the resize handle node.
     *
     * @return The resize handle node
     */
    public Node getResizeHandle() {
        return resizeHandle;
    }

    /**
     * Enables border resize functionality for the specified region.
     * This allows resizing from any edge of the window.
     *
     * @param region The region to enable border resizing on
     */
    public void enableBorderResize(Region region) {
        // Store initial cursor
        final Cursor originalCursor = region.getCursor();
        final boolean[] resizing = {false};
        final int[] border = {0}; // 1=N, 2=E, 3=S, 4=W, 5=NE, 6=SE, 7=SW, 8=NW

        EventHandler<MouseEvent> mouseMoveHandler = event -> {
            if (!resizing[0]) {
                // Determine which border is being hovered
                double x = event.getX();
                double y = event.getY();
                double width = region.getWidth();
                double height = region.getHeight();

                boolean north = y < resizeMargin;
                boolean east = x > width - resizeMargin;
                boolean south = y > height - resizeMargin;
                boolean west = x < resizeMargin;

                if (north && east) {
                    region.setCursor(Cursor.NE_RESIZE);
                    border[0] = 5;
                } else if (south && east) {
                    region.setCursor(Cursor.SE_RESIZE);
                    border[0] = 6;
                } else if (south && west) {
                    region.setCursor(Cursor.SW_RESIZE);
                    border[0] = 7;
                } else if (north && west) {
                    region.setCursor(Cursor.NW_RESIZE);
                    border[0] = 8;
                } else if (north) {
                    region.setCursor(Cursor.N_RESIZE);
                    border[0] = 1;
                } else if (east) {
                    region.setCursor(Cursor.E_RESIZE);
                    border[0] = 2;
                } else if (south) {
                    region.setCursor(Cursor.S_RESIZE);
                    border[0] = 3;
                } else if (west) {
                    region.setCursor(Cursor.W_RESIZE);
                    border[0] = 4;
                } else {
                    region.setCursor(originalCursor);
                    border[0] = 0;
                }
            }
        };

        EventHandler<MouseEvent> mousePressedHandler = event -> {
            if (border[0] != 0 && !stage.isMaximized()) {
                resizing[0] = true;
                event.consume();
            }
        };

        EventHandler<MouseEvent> mouseDraggedHandler = event -> {
            if (resizing[0] && !stage.isMaximized()) {
                double deltaX = event.getScreenX() - event.getSceneX();
                double deltaY = event.getScreenY() - event.getSceneY();

                switch (border[0]) {
                    case 1: // N
                        resizeNorth(deltaY);
                        break;
                    case 2: // E
                        resizeEast(event.getSceneX());
                        break;
                    case 3: // S
                        resizeSouth(event.getSceneY());
                        break;
                    case 4: // W
                        resizeWest(deltaX);
                        break;
                    case 5: // NE
                        resizeNorth(deltaY);
                        resizeEast(event.getSceneX());
                        break;
                    case 6: // SE
                        resizeSouth(event.getSceneY());
                        resizeEast(event.getSceneX());
                        break;
                    case 7: // SW
                        resizeSouth(event.getSceneY());
                        resizeWest(deltaX);
                        break;
                    case 8: // NW
                        resizeNorth(deltaY);
                        resizeWest(deltaX);
                        break;
                }
                event.consume();
            }
        };

        EventHandler<MouseEvent> mouseReleasedHandler = event -> {
            resizing[0] = false;
            region.setCursor(originalCursor);
        };

        region.addEventHandler(MouseEvent.MOUSE_MOVED, mouseMoveHandler);
        region.addEventHandler(MouseEvent.MOUSE_PRESSED, mousePressedHandler);
        region.addEventHandler(MouseEvent.MOUSE_DRAGGED, mouseDraggedHandler);
        region.addEventHandler(MouseEvent.MOUSE_RELEASED, mouseReleasedHandler);
    }

    private void resizeNorth(double deltaY) {
        double newHeight = stage.getHeight() + (stage.getY() - deltaY);
        if (newHeight >= stage.getMinHeight()) {
            stage.setHeight(newHeight);
            stage.setY(deltaY);
        }
    }

    private void resizeEast(double x) {
        double newWidth = x;
        if (newWidth >= stage.getMinWidth()) {
            stage.setWidth(newWidth);
        }
    }

    private void resizeSouth(double y) {
        double newHeight = y;
        if (newHeight >= stage.getMinHeight()) {
            stage.setHeight(newHeight);
        }
    }

    private void resizeWest(double deltaX) {
        double newWidth = stage.getWidth() + (stage.getX() - deltaX);
        if (newWidth >= stage.getMinWidth()) {
            stage.setWidth(newWidth);
            stage.setX(deltaX);
        }
    }
}