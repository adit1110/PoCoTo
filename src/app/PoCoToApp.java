package app;

import java.net.URL;

import controller.BearController;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Bear;
import model.Co;
import model.Po;
import model.To;
import view.GameplayScreen;

public class PoCoToApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        showMainMenu(primaryStage);
    }

    public void showMainMenu(Stage primaryStage) {
        Label title = new Label("PoCoTo");
        title.getStyleClass().add("title");

        Button newGameButton = new Button("New Game");
        Button loadGameButton = new Button("Load Game");
        Button settingsButton = new Button("Settings");
        Button exitButton = new Button("Exit");

        newGameButton.setOnAction(e -> showBearSelection(primaryStage));
        loadGameButton.setOnAction(e -> loadGame());
        settingsButton.setOnAction(e -> openSettings());
        exitButton.setOnAction(e -> primaryStage.close());

        VBox menuBox = new VBox(15, newGameButton, loadGameButton, settingsButton, exitButton);
        menuBox.setAlignment(Pos.CENTER);

        VBox root = new VBox(30, title, menuBox);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(40));

        Scene scene = new Scene(root, 800, 600);
        URL css = getClass().getResource("/styles.css");
        if (css != null) {
            scene.getStylesheets().add(css.toExternalForm());
        }

        primaryStage.setTitle("PoCoTo - Main Menu");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showBearSelection(Stage primaryStage) {
        Label title = new Label("PoCoTo");
        title.getStyleClass().add("title");

        Button poButton = new Button("Po (Panda)");
        Button coButton = new Button("Co (Polar Bear)");
        Button toButton = new Button("To (Grizzly Bear)");
        Button backButton = new Button("Back");

        poButton.setOnAction(e -> selectBear("Po", primaryStage));
        coButton.setOnAction(e -> selectBear("Co", primaryStage));
        toButton.setOnAction(e -> selectBear("To", primaryStage));
        backButton.setOnAction(e -> showMainMenu(primaryStage));

        VBox buttonBox = new VBox(15, poButton, coButton, toButton, backButton);
        buttonBox.setAlignment(Pos.CENTER);

        VBox root = new VBox(30, title, buttonBox);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(40));

        Scene scene = new Scene(root, 800, 600);
        URL css = getClass().getResource("/styles.css");
        if (css != null) {
            scene.getStylesheets().add(css.toExternalForm());
        }

        primaryStage.setTitle("Select Your Bear");
        primaryStage.setScene(scene);
    }

    private void selectBear(String bearName, Stage primaryStage) {
        Bear bear;
        switch (bearName) {
            case "Po":
                bear = new Po("Po");
                break;
            case "Co":
                bear = new Co("Co");
                break;
            case "To":
                bear = new To("To");
                break;
            default:
                return; // fail silently
        }

        BearController controller = new BearController(bear);
        GameplayScreen screen = new GameplayScreen(controller, this);
        screen.start(primaryStage);

        primaryStage.setWidth(800);
        primaryStage.setHeight(600);
        primaryStage.setResizable(false);
    }

    private void loadGame() {
        System.out.println("Loading game...");
        // Implement game loading functionality
    }

    private void openSettings() {
        System.out.println("Opening settings...");
        // Implement settings UI
    }

    public static void main(String[] args) {
        launch(args);
    }
}
