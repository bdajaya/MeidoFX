package id.alphareso.meidofx;

import id.alphareso.meidofx.base.enums.TitleBarStyle;
import id.alphareso.meidofx.base.stages.RoundStage;
import id.alphareso.meidofx.base.titles.TitleBar;
import id.alphareso.meidofx.ui.pages.HelpView;
import id.alphareso.meidofx.util.FileResource;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MeidoFXDemo extends Application {

    public static RoundStage stage;

    @Override
    public void start(Stage primaryStage) {
        stage = new RoundStage(800, 600, 20);
        stage.setTitle("MeidoFX Demo Application");

        TitleBar titleBar = new TitleBar(stage, TitleBarStyle.ALL);
        //titleBar.setBackgroundCss("-fx-background-color: #3498db;");
        titleBar.getTitleLabel().setStyle("-fx-text-fill: white; -fx-font-weight: bold;");

        // Show icon in title bar
        Image appIcon = new Image(FileResource.load("/images/icon.png"));
        stage.getIcons().add(appIcon);
        titleBar.setIcon(appIcon);
        stage.setTitleBar(titleBar);

        // Membuat area navigasi dengan tombol navigasi
        VBox navigationMenu = new VBox();
        navigationMenu.setPadding(new Insets(10));
        navigationMenu.setSpacing(10);
        navigationMenu.setStyle("-fx-background-color: #f0f0f0;");

        // Tombol navigasi dengan aksi untuk mengganti konten utama
        Button dashboardButton = new Button("Dashboard");
        dashboardButton.setOnAction(event -> setContent("Dashboard Content"));

        Button settingsButton = new Button("Settings");
        settingsButton.setOnAction(event -> setContent("Settings Content"));

        Button helpButton = new Button("Help");
        helpButton.setOnAction(event -> {
            RoundStage dialog = new RoundStage(400, 300, 20);
            dialog.setTitle("Help");

            TitleBar dialogTitleBar = new TitleBar(dialog, TitleBarStyle.NO_LEFT);
            dialogTitleBar.setBackgroundCss("-fx-background-color: #e74c3c;");
            dialogTitleBar.getTitleLabel().setStyle("-fx-text-fill: white; -fx-font-weight: bold;");
            dialog.setTitleBar(dialogTitleBar);

            StackPane dialogContent = new StackPane();
            dialogContent.setPadding(new Insets(20));

            Label dialogLabel = new Label("This is a dialog window created with RoundStage.");
            dialogLabel.setWrapText(true);

            dialogContent.getChildren().add(dialogLabel);
            dialog.setContent(dialogContent);

            dialog.initOwner(stage);
            dialog.show();
        });

        // Menambahkan semua tombol ke dalam menu navigasi
        navigationMenu.getChildren().addAll(dashboardButton, settingsButton, helpButton);

        // Menambahkan menu navigasi ke navigationArea di RoundStage
        stage.addNavigationContent(navigationMenu);

        // Menambahkan konten awal ke area konten utama
        setContent("Welcome to MeidoFX Demo!");

        // Menampilkan stage
        stage.show();
    }

    private void setContent(String contentText) {
        StackPane contentStack = new StackPane();
        contentStack.setPadding(new Insets(20));

        VBox content = new VBox(10);
        content.setPadding(new Insets(20));
        content.getStyleClass().add("content-panel");
        content.setStyle("-fx-background-color: white; -fx-background-radius: 10;");

        Label title = new Label(contentText);
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        Label description = new Label("This is a demonstration of the MeidoFX custom window management system. It features a round stage with a title bar, sidebar, and content area.");
        description.setWrapText(true);

        Button testButton = new Button("Test Button");
        testButton.setOnAction(event -> {
            RoundStage dialog = new RoundStage(400, 300, 20);
            dialog.setTitle("Test Dialog Content");

            TitleBar dialogTitleBar = new TitleBar(dialog, TitleBarStyle.ALL);
            dialogTitleBar.setBackgroundCss("-fx-background-color: #e74c3c;");
            dialogTitleBar.getTitleLabel().setStyle("-fx-text-fill: white; -fx-font-weight: bold;");
            dialog.setTitleBar(dialogTitleBar);

            StackPane dialogContent = new StackPane();
            dialogContent.setPadding(new Insets(20));

            Label dialogLabel = new Label("This is a dialog window created with RoundStage.");
            dialogLabel.setWrapText(true);

            dialogContent.getChildren().add(dialogLabel);
            dialog.setContent(dialogContent);

            dialog.initOwner(stage);
            dialog.show();
        });

        content.getChildren().addAll(title, description, testButton);
        contentStack.getChildren().add(content);
        stage.setContent(contentStack);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
