package id.alphareso.meidofx.base.stages;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Base stage class for all application windows, providing common functionality.
 */
public class BaseStage extends Stage {
    private static final Color DEFAULT_BACKGROUND_COLOR = Color.rgb(240, 240, 240);

    protected final StackPane root;
    protected final Scene scene;
    protected final double minWidth;
    protected final double minHeight;

    /**
     * Creates a new base stage with default minimum dimensions.
     */
    public BaseStage() {
        this(300, 200);
    }

    /**
     * Creates a new base stage with specified minimum dimensions.
     *
     * @param minWidth The minimum width of the stage
     * @param minHeight The minimum height of the stage
     */
    public BaseStage(double minWidth, double minHeight) {
        this.minWidth = minWidth;
        this.minHeight = minHeight;

        // Initialize the root container
        root = new StackPane();
        root.setBackground(new Background(new BackgroundFill(
                DEFAULT_BACKGROUND_COLOR,
                CornerRadii.EMPTY,
                Insets.EMPTY
        )));

        // Create scene
        scene = new Scene(root, minWidth, minHeight);

        // Configure stage
        this.setScene(scene);
        this.setMinWidth(minWidth);
        this.setMinHeight(minHeight);

        // Add CSS stylesheets if needed
        //scene.getStylesheets().add(getClass().getResource("styles/base-stage.css").toExternalForm());
    }

    /**
     * Gets the root container of this stage.
     *
     * @return The root StackPane
     */
    public StackPane getRoot() {
        return root;
    }

    /**
     * Sets the background color of the root container.
     *
     * @param color The background color
     */
    public void setBackgroundColor(Color color) {
        root.setBackground(new Background(new BackgroundFill(
                color,
                root.getBackground().getFills().get(0).getRadii(),
                Insets.EMPTY
        )));
    }

    /**
     * Sets the content of this stage, replacing any existing content.
     *
     * @param content The content node
     */
    public void setContent(Region content) {
        root.getChildren().setAll(content);
    }
}

