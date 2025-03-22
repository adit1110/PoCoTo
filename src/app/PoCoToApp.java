package app;

import java.net.URL;

import controller.BearController;
import model.Bear;
import model.Po;
import model.Co;
import model.To;
import view.GameplayScreen;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PoCoToApp extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        showMainMenu(primaryStage);
    }
    
    private void showMainMenu(Stage primaryStage) {
        Button newGameButton = new Button("New Game");
        Button loadGameButton = new Button("Load Game");
        Button settingsButton = new Button("Settings");
        Button exitButton = new Button("Exit");
        
        newGameButton.setOnAction(e -> showBearSelection(primaryStage));
        loadGameButton.setOnAction(e -> loadGame());
        settingsButton.setOnAction(e -> openSettings());
        exitButton.setOnAction(e -> primaryStage.close());
        
        VBox layout = new VBox(10);
        layout.getChildren().addAll(newGameButton, loadGameButton, settingsButton, exitButton);
        
        Scene scene = new Scene(layout, 800, 600);


        URL css = getClass().getResource("/styles.css");

        if (css != null) {
            scene.getStylesheets().add(css.toExternalForm());
        }

        primaryStage.setTitle("PoCoTo - Main Menu");
        primaryStage.setScene(scene);
        
        primaryStage.hide();
        primaryStage.show();
    }
    
    private void showBearSelection(Stage primaryStage) {
    Button poButton = new Button("Po (Panda)");
    Button coButton = new Button("Co (Polar Bear)");
    Button toButton = new Button("To (Grizzly Bear)");
    Button backButton = new Button("Back");

    poButton.setOnAction(e -> selectBear("Po", primaryStage));
    coButton.setOnAction(e -> selectBear("Co", primaryStage));
    toButton.setOnAction(e -> selectBear("To", primaryStage));
    backButton.setOnAction(e -> showMainMenu(primaryStage));

    VBox layout = new VBox(10);
    layout.getChildren().addAll(poButton, coButton, toButton, backButton);

    Scene scene = new Scene(layout, 800, 600);

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
    GameplayScreen screen = new GameplayScreen(controller);
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
