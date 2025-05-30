/** PoCoToApp.Java 
 * @author: Adit Bhimani
 * PoCoToApp.java is the main app file of the game. It is the entry point of the game.
 * It contains the main method and the start method. The start method is responsible for
 * displaying the main menu of the game. The main menu contains buttons for starting a new game,
 * loading a game, opening settings, and exiting the game. The start method also sets the scene
 * for the main menu and sets the title of the stage. The main method calls the launch method
 * to start the game.
 * 
 * */ 
package app;

import java.net.URL;

import controller.BearController;
import controller.GameController;
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
import view.HelpScreen;
import view.ParentalControlScreen;
import view.SettingsScreen;
import view.MiniGameScreen;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.Priority;


/**
 * PoCoToApp is the main application class for the PoCoTo game.
 * It initializes the JavaFX application and displays the main menu.
 * The main menu allows users to start a new game, load a game, open settings, or exit the game.
 */

public class PoCoToApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        showMainMenu(primaryStage);
    }

    /**
     * Displays the main menu UI with all navigation buttons
     * 
     * @param primaryStage the window to render content into
     */

    public void showMainMenu(Stage primaryStage) {
        // title label for the game
        Label title = new Label("PoCoTo");
        title.getStyleClass().add("title");

        // Menu buttons
        Button newGameButton = new Button("New Game");
        Button loadGameButton = new Button("Load Game");
        Button tutorialButton = new Button("Tutorial / Instructions");
        Button parentalControlsButton = new Button("Parental Controls");
        Button minigameButton = new Button("Minigame");
        Button settingsButton = new Button("Settings");
        Button exitButton = new Button("Exit");

        // Create the main menu buttons
        newGameButton.setOnAction(e -> showBearSelection(primaryStage));
        loadGameButton.setOnAction(e -> loadGame(primaryStage));
        tutorialButton.setOnAction(e -> {
            HelpScreen help = new HelpScreen();
            help.show();
        });
        parentalControlsButton.setOnAction(e -> {
            new ParentalControlScreen(primaryStage, this);
        });
        settingsButton.setOnAction(e -> {
            SettingsScreen settingsScreen = new SettingsScreen(this);
            settingsScreen.display(primaryStage);
        });
        minigameButton.setOnAction(e -> {
            MiniGameScreen minigame = new MiniGameScreen();
            minigame.setResultHandler(result -> System.out.println("Minigame Result: " + result));
            minigame.show();
        });
        exitButton.setOnAction(e -> primaryStage.close());

        // Stack menu buttons vertically
        VBox menuBox = new VBox(15, newGameButton, loadGameButton, minigameButton, tutorialButton, parentalControlsButton, settingsButton, exitButton);
        menuBox.setAlignment(Pos.CENTER);


        // Credits for the team
        Label teamLabel = new Label("Created by: Adit Bhimani, Jayansh Bagga, Bhavya Sharma, Jeremy Ro, Krish Patel");
        Label teamInfoLabel = new Label("Team 50");
        Label courseLabel = new Label("Created for CS2212 at Western University - Winter 2025");
        VBox creditsBox = new VBox(2, teamLabel, teamInfoLabel, courseLabel);
        creditsBox.setAlignment(Pos.CENTER);
        creditsBox.getStyleClass().add("credits");

        
        // Game title at the top
        VBox topBox = new VBox(10, title);
        topBox.setAlignment(Pos.CENTER);
        topBox.setPadding(new Insets(20));

        // Combine all sections into a layout
        BorderPane root = new BorderPane();
        root.setTop(topBox);
        root.setCenter(menuBox);
        root.setBottom(creditsBox);
        BorderPane.setMargin(creditsBox, new Insets(20));

        
        Scene scene = new Scene(root, 800, 600); // screen size
        
        // load the css style sheet
        URL css = getClass().getResource("/styles.css");
        if (css != null) {
            scene.getStylesheets().add(css.toExternalForm());
        }

        primaryStage.setTitle("PoCoTo - Main Menu");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Shows the bear selection screen for starting a new game.
     * 
     * @param primaryStage the window to render content into
     */
    private void showBearSelection(Stage primaryStage) {
        Label title = new Label("PoCoTo");
        title.getStyleClass().add("title");

        // Buttons for bear type selection
        Button poButton = new Button("Po (Panda)");
        Button coButton = new Button("Co (Polar Bear)");
        Button toButton = new Button("To (Grizzly Bear)");
        Button backButton = new Button("Back");

        
        // Button actions
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

    /**
     * Starts the game using the selected bear.
     * 
     * @param bearName name identifier for the bear type
     * @param primaryStage the main game window
     */
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

        GameController.setCurrentBear(bear); // Added by Jayansh Bagga - this should set currentBear to save
        

        BearController controller = new BearController(bear);
        GameplayScreen screen = new GameplayScreen(controller, this);
        screen.start(primaryStage);

        primaryStage.setWidth(800);
        primaryStage.setHeight(640);
        primaryStage.setResizable(false);
    }

    /**
     * 
     * Loads a previously saved bear and starts the game.
     * 
     * @param primaryStage the main game window
     */
    private void loadGame(Stage primaryStage) {
        Bear loadedBear = GameController.loadGame();
        if (loadedBear != null) {
            GameController.setCurrentBear(loadedBear);
            GameplayScreen screen = new GameplayScreen(new BearController(loadedBear), this);
            screen.start(primaryStage);
        }

    }

    public static void main(String[] args) {
        launch(args);
    }
}
