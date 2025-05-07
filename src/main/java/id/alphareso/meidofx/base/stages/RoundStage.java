package id.alphareso.meidofx.base.stages;

import id.alphareso.meidofx.base.handlers.WindowDragHandler;
import id.alphareso.meidofx.base.handlers.WindowResizeHandler;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.StageStyle; /**
 * A specialized stage with rounded corners, supporting title bar, sidebar, and content area.
 */
public class RoundStage extends BaseStage {

    private final StackPane contentArea;
    private final BorderPane layout;
    private WindowResizeHandler resizeHandler;
    private final double cornerRadius;

    /**
     * Creates a new round stage with default corner radius (20px).
     */
    public RoundStage() {
        this(300, 200, 20);
    }

    /**
     * Creates a new round stage with specified minimum dimensions and corner radius.
     *
     * @param minWidth The minimum width of the stage
     * @param minHeight The minimum height of the stage
     * @param cornerRadius The corner radius in pixels
     */
    public RoundStage(double minWidth, double minHeight, double cornerRadius) {
        super(minWidth, minHeight);
        this.cornerRadius = cornerRadius;

        // Initialize with transparent style for rounded corners
        this.initStyle(StageStyle.TRANSPARENT);

        // Set up the layout
        layout = new BorderPane();

        // Content area (center)
        contentArea = new StackPane();
        contentArea.setBackground(new Background(new BackgroundFill(
                Color.WHITE,
                CornerRadii.EMPTY,
                Insets.EMPTY
        )));
        layout.setCenter(contentArea);

        // Apply rounded corners to the root
        root.setBackground(new Background(new BackgroundFill(
                Color.rgb(240, 240, 240),
                new CornerRadii(cornerRadius),
                Insets.EMPTY
        )));

        // Add layout to root
        root.getChildren().clear();
        root.getChildren().add(layout);

        // Apply css to scene
        scene.setFill(Color.TRANSPARENT);

        // Add resize handler in the bottom-right corner
        resizeHandler = new WindowResizeHandler(this, root);

    }

    /**
     * Sets the title bar component at the top of the layout.
     *
     * @param titleBar The title bar node
     */
    public void setTitleBar(Region titleBar) {
        layout.setTop(titleBar);

        // Enable window dragging on the title bar
        WindowDragHandler dragHandler = new WindowDragHandler(this);
        dragHandler.enableDrag(titleBar);
    }

    /**
     * Sets the sidebar component at the left of the layout.
     *
     * @param sidebar The sidebar node
     */
    public void setSidebar(Region sidebar) {
        layout.setLeft(sidebar);
    }

    /**
     * Sets the content in the main content area.
     *
     * @param content The content node
     */
    public void setContent(Region content) {
        contentArea.getChildren().clear();
        contentArea.getChildren().add(content);
    }

    /**
     * Sets the right sidebar component.
     *
     * @param rightSidebar The right sidebar node
     */
    public void setRightSidebar(Region rightSidebar) {
        layout.setRight(rightSidebar);
    }

    /**
     * Gets the content area StackPane.
     *
     * @return The content area
     */
    public StackPane getContentArea() {
        return contentArea;
    }

    /**
     * Sets the background color of the main window.
     *
     * @param color The background color
     */
    @Override
    public void setBackgroundColor(Color color) {
        root.setBackground(new Background(new BackgroundFill(
                color,
                new CornerRadii(cornerRadius),
                Insets.EMPTY
        )));
    }

    /**
     * Sets the background color of the content area.
     *
     * @param color The content area background color
     */
    public void setContentBackgroundColor(Color color) {
        contentArea.setBackground(new Background(new BackgroundFill(
                color,
                CornerRadii.EMPTY,
                Insets.EMPTY
        )));
    }

    /**
     * Gets the layout container.
     *
     * @return The BorderPane layout
     */
    public BorderPane getLayout() {
        return layout;
    }

    /**
     * Gets the resize handler for this stage.
     *
     * @return The resize handler
     */
    public WindowResizeHandler getResizeHandler() {
        return resizeHandler;
    }
}
