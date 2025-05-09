package id.alphareso.meidofx.base.titles;

import id.alphareso.meidofx.base.controls.WindowControlButtons;
import id.alphareso.meidofx.base.enums.TitleBarStyle;
import id.alphareso.meidofx.base.handlers.WindowDragHandler;
import id.alphareso.meidofx.base.stages.BaseStage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import id.alphareso.meidofx.util.FileResource; // Asumsi FileResource menangani path dengan benar

import java.io.InputStream;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A customizable title bar component for application windows.
 * Uses a BorderPane internally to manage left (icon, title) and right (controls) sections.
 */
public class TitleBar extends StackPane {

    private static final Logger LOGGER = Logger.getLogger(TitleBar.class.getName());

    // Konstanta untuk styling dan ukuran
    private static final double DEFAULT_SPACING = 10;
    private static final double DEFAULT_ICON_SIZE = 20;
    private static final double MIN_TITLE_BAR_HEIGHT = 30; // Ketinggian minimal TitleBar
    private static final double PREF_TITLE_BAR_HEIGHT = 60; // Ketinggian preferensi TitleBar

    private final BaseStage stage;
    private final BorderPane contentPane; // Menggantikan mainContainer HBox
    private final HBox leftContainer;
    private final HBox centerContainer;
    private final HBox rightContainer;
    private final Label titleLabel;
    private final ImageView iconView;
    private final WindowControlButtons controlButtons;
    private final WindowDragHandler dragRegion;

    private TitleBarStyle currentTitleBarStyle;

    /**
     * Creates a new title bar for the specified stage with default {@link TitleBarStyle#ALL} style.
     *
     * @param stage The stage this title bar will control
     */
    public TitleBar(BaseStage stage) {
        this(stage, TitleBarStyle.ALL);
    }

    /**
     * Creates a new title bar for the specified stage with the given style.
     *
     * @param stage The stage this title bar will control
     * @param initialStyle The initial title bar style to apply
     */
    public TitleBar(BaseStage stage, TitleBarStyle initialStyle) {
        Objects.requireNonNull(stage, "Stage cannot be null");
        Objects.requireNonNull(initialStyle, "Initial TitleBarStyle cannot be null");

        this.stage = stage;

        // Setup dasar TitleBar (StackPane ini sendiri)
        this.setMinHeight(MIN_TITLE_BAR_HEIGHT);
        this.setPrefHeight(PREF_TITLE_BAR_HEIGHT);
        this.getStyleClass().add("title-bar"); // Untuk styling via CSS

        // Content Pane menggunakan BorderPane
        contentPane = new BorderPane();
        contentPane.setBackground(new Background(new BackgroundFill(
                Color.rgb(240, 100, 20),
                new CornerRadii(10,10,0,0,false),
                Insets.EMPTY
        )));
        contentPane.setPadding(new Insets(5 ));

        // Left container (icon and title)
        leftContainer = new HBox();
        leftContainer.setAlignment(Pos.CENTER_LEFT);
        leftContainer.setPadding(new Insets(5));
        leftContainer.setSpacing(DEFAULT_SPACING);

        iconView = new ImageView();
        iconView.setFitWidth(DEFAULT_ICON_SIZE);
        iconView.setFitHeight(DEFAULT_ICON_SIZE);
        iconView.setPreserveRatio(true);

        titleLabel = new Label();
        titleLabel.getStyleClass().add("title-label"); // Untuk styling via CSS
        // Bind title label ke stage title
        titleLabel.textProperty().bind(stage.titleProperty());

        leftContainer.getChildren().addAll(iconView, titleLabel);
        contentPane.setLeft(leftContainer);

        // Center container
        centerContainer = new WindowDragHandler(stage);
        centerContainer.setMinWidth(Region.USE_COMPUTED_SIZE);
        centerContainer.setStyle("-fx-background-color: rgba(0,0,0,0.25);");
        // Enable window dragging on this center Container
        dragRegion = new WindowDragHandler(stage);
        dragRegion.enableDrag(centerContainer);

        centerContainer.getChildren().add(dragRegion);
        contentPane.setCenter(centerContainer);

        // Right container (window control buttons)
        rightContainer = new HBox();
        rightContainer.setAlignment(Pos.CENTER_RIGHT); // Kontrol di kanan dalam HBoxnya
        // Spacing di WindowControlButtons sudah diatur secara internal jika diperlukan
        rightContainer.setSpacing(DEFAULT_SPACING);

        controlButtons = new WindowControlButtons(stage);
        rightContainer.getChildren().add(controlButtons);
        contentPane.setRight(rightContainer);

        // Tambahkan contentPane ke StackPane (TitleBar ini)
        this.getChildren().add(contentPane);
        // Terapkan style awal
        setTitleBarStyle(initialStyle);
    }

    /**
     * Sets the title bar style, controlling visibility of left and right components.
     *
     * @param newStyle The title bar style to apply
     */
    public void setTitleBarStyle(TitleBarStyle newStyle) {
        Objects.requireNonNull(newStyle, "TitleBarStyle cannot be null");
        this.currentTitleBarStyle = newStyle;

        leftContainer.setVisible(currentTitleBarStyle.showLeftComponents());
        leftContainer.setManaged(currentTitleBarStyle.showLeftComponents());

        rightContainer.setVisible(currentTitleBarStyle.showRightComponents());
        rightContainer.setManaged(currentTitleBarStyle.showRightComponents());

        // Jika kedua komponen tidak ada, kita mungkin ingin memastikan titlebar tetap memiliki tinggi minimal
        // dan bisa di-drag. Ini sudah ditangani oleh setMinHeight pada TitleBar StackPane.
    }

    /**
     * Gets the current title bar style.
     *
     * @return The current title bar style
     */
    public TitleBarStyle getTitleBarStyle() {
        return currentTitleBarStyle;
    }

    /**
     * Sets the icon for the title bar from an {@link Image} object.
     *
     * @param image The icon image. If null, the icon will be cleared.
     */
    public void setIcon(Image image) {
        iconView.setImage(image);
        showIcon(image != null && currentTitleBarStyle.showLeftComponents()); // Hanya tampil jika style mengizinkan
    }

    /**
     * Sets the icon for the title bar from the specified resource path.
     * Pastikan path diawali dengan "/" untuk memuat dari root classpath jika menggunakan FileResource.
     *
     * @param path The resource path to the icon image (e.g., "/images/icon.png")
     */
    public void setIcon(String path) {
        if (path == null || path.trim().isEmpty()) {
            setIcon((Image) null);
            return;
        }
        try (InputStream imageStream = FileResource.loadStream(path)) { // Menggunakan FileResource
            if (imageStream != null) {
                setIcon(new Image(imageStream));
            } else {
                LOGGER.log(Level.WARNING, "Could not load icon from path: {0}. Stream is null.", path);
                setIcon((Image) null);
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error loading icon from path: " + path, e);
            setIcon((Image) null);
        }
    }

    /**
     * Shows or hides the icon in the title bar.
     * Visibility juga dipengaruhi oleh {@link TitleBarStyle}.
     *
     * @param show true to attempt to show the icon, false to hide it
     */
    public void showIcon(boolean show) {
        // Hanya tampilkan jika 'show' adalah true DAN style mengizinkan komponen kiri
        boolean actualVisibility = show && currentTitleBarStyle.showLeftComponents() && iconView.getImage() != null;
        iconView.setVisible(actualVisibility);
        iconView.setManaged(actualVisibility);
    }

    /**
     * Sets the title text directly on the stage, which will reflect in the titleLabel
     * due to binding.
     *
     * @param title The title text
     */
    public void setTitle(String title) {
        stage.setTitle(title);
    }

    /**
     * Shows or hides the title text.
     * Visibility juga dipengaruhi oleh {@link TitleBarStyle}.
     *
     * @param show true to attempt to show the title, false to hide it
     */
    public void showTitle(boolean show) {
        // Hanya tampilkan jika 'show' adalah true DAN style mengizinkan komponen kiri
        boolean actualVisibility = show && currentTitleBarStyle.showLeftComponents();
        titleLabel.setVisible(actualVisibility);
        titleLabel.setManaged(actualVisibility);
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
     * Sets the CSS style for the background of this TitleBar.
     * This directly calls {@link #setStyle(String)}.
     *
     * @param cssStyle CSS style string for the background (e.g., "-fx-background-color: #336699;")
     */
    public void setBackgroundCss(String cssStyle) {
        // this.setStyle(cssStyle) akan mengatur style pada StackPane (TitleBar ini)
        this.setStyle(cssStyle);
    }

    /**
     * Gets the title label UI component.
     *
     * @return The title {@link Label}
     */
    public Label getTitleLabel() {
        return titleLabel;
    }

    /**
     * Gets the icon view UI component.
     *
     * @return The icon {@link ImageView}
     */
    public ImageView getIconView() {
        return iconView;
    }
}