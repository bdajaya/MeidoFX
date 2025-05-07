package id.alphareso.meidofx.base.titles;

import id.alphareso.meidofx.base.controls.WindowControlButtons;
import id.alphareso.meidofx.base.enums.TitleBarStyle;
import id.alphareso.meidofx.base.handlers.WindowDragHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.Objects;

/**
 * A customizable title bar component for application windows.
 */
public class TitleBar extends StackPane {

    private final Stage stage;
    private final HBox leftContainer;
    private final HBox rightContainer;
    private final Label titleLabel;
    private final ImageView iconView;
    private final WindowControlButtons controlButtons;
    private TitleBarStyle style;

    /**
     * Creates a new title bar for the specified stage with default ALL style.
     *
     * @param stage The stage this title bar will control
     */
    public TitleBar(Stage stage) {
        this(stage, TitleBarStyle.ALL);
    }

    /**
     * Creates a new title bar for the specified stage with the given style.
     *
     * @param stage The stage this title bar will control
     * @param style The title bar style to apply
     */
    public TitleBar(Stage stage, TitleBarStyle style) {
        this.stage = stage;
        this.style = style;

        // Create containers
        HBox mainContainer = new HBox();
        mainContainer.setAlignment(Pos.CENTER_LEFT);
        mainContainer.setPadding(new Insets(5, 10, 5, 10));
        mainContainer.setSpacing(10);

        // Left container (icon and title)
        leftContainer = new HBox();
        leftContainer.setAlignment(Pos.CENTER_LEFT);
        leftContainer.setSpacing(10);

        // Create icon
        iconView = new ImageView();
        iconView.setFitWidth(20);
        iconView.setFitHeight(20);
        iconView.setPreserveRatio(true);

        // Create title label
        titleLabel = new Label();
        titleLabel.getStyleClass().add("title-label");

        // Add icon and title to left container
        leftContainer.getChildren().addAll(iconView, titleLabel);

        // Right container (window control buttons)
        rightContainer = new HBox();
        rightContainer.setAlignment(Pos.CENTER_RIGHT);

        // Create window control buttons
        controlButtons = new WindowControlButtons(stage);
        rightContainer.getChildren().add(controlButtons);

        // Set up HBox growth
        HBox.setHgrow(leftContainer, Priority.ALWAYS);

        // Add left and right containers to main container
        mainContainer.getChildren().addAll(leftContainer, rightContainer);

        // Add main container to stack pane
        this.getChildren().add(mainContainer);

        // Set up styling
        this.getStyleClass().add("title-bar");
        this.setMinHeight(30);
        this.setPrefHeight(40);

        // Update title from stage
        titleLabel.setText(stage.getTitle());
        stage.titleProperty().addListener((observable, oldValue, newValue) ->
                titleLabel.setText(newValue)
        );

        // Enable window dragging on this title bar
        WindowDragHandler dragHandler = new WindowDragHandler(stage);
        dragHandler.enableDrag(this);

        // Apply the specified style
        applyStyle(style);
    }

    /**
     * Sets the title bar style.
     *
     * @param style The title bar style to apply
     */
    public void setStyle(TitleBarStyle style) {
        this.style = style;
        applyStyle(style);
    }

    /**
     * Applies the specified title bar style.
     *
     * @param style The title bar style to apply
     */
    private void applyStyle(TitleBarStyle style) {
        leftContainer.setVisible(style.showLeftComponents());
        leftContainer.setManaged(style.showLeftComponents());

        rightContainer.setVisible(style.showRightComponents());
        rightContainer.setManaged(style.showRightComponents());
    }

    /**
     * Sets the icon for the title bar.
     *
     * @param image The icon image
     */
    public void setIcon(Image image) {
        iconView.setImage(image);
    }

    /**
     * Sets the icon for the title bar from the specified path.
     *
     * @param path The resource path to the icon image
     */
    public void setIcon(String path) {
        try {
            Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/" + path)));
            iconView.setImage(image);
        } catch (Exception e) {
            System.err.println("Could not load icon: " + path);
        }
    }

    /**
     * Shows or hides the icon in the title bar.
     *
     * @param show true to show the icon, false to hide it
     */
    public void showIcon(boolean show) {
        iconView.setVisible(show);
        iconView.setManaged(show);
    }

    /**
     * Sets the title text.
     *
     * @param title The title text
     */
    public void setTitle(String title) {
        titleLabel.setText(title);
        stage.setTitle(title);
    }

    /**
     * Shows or hides the title text.
     *
     * @param show true to show the title, false to hide it
     */
    public void showTitle(boolean show) {
        titleLabel.setVisible(show);
        titleLabel.setManaged(show);
    }

    /**
     * Gets the window control buttons component.
     *
     * @return The window control buttons
     */
    public WindowControlButtons getControlButtons() {
        return controlButtons;
    }

    /**
     * Gets the current title bar style.
     *
     * @return The current title bar style
     */
    // getStyle()
    public TitleBarStyle getTitleBarStyle() {
        return style;
    }

    /**
     * Sets the background style of the title bar.
     *
     * @param style CSS style for the background
     */
    public void setBackgroundStyle(String style) {
        this.setStyle(style);
    }

    /**
     * Gets the title label.
     *
     * @return The title label
     */
    public Label getTitleLabel() {
        return titleLabel;
    }

    /**
     * Gets the icon view.
     *
     * @return The icon view
     */
    public ImageView getIconView() {
        return iconView;
    }
}