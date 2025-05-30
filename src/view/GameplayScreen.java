/**
 * @author: Adit Bhimani
 * GameplayScreen.java represents the main gameplay screen.
 * It displays the bear's stats, image, and buttons to interact with the bear.
 * It also displays a pause button to pause the game.
 * It also displays a save and load button to save and load the game.
 */

package view;

import view.PauseMenu;
import controller.GameController;
import controller.BearController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView; 
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Bear;
import service.NotificationService; // added by Bhavya Sharma
import app.PoCoToApp;
import controller.GameController;
import view.PauseMenu;
import controller.SaveLoadController; // added by Bhavya Sharma
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import model.Po;
import model.Co;
import model.To;
import model.Inventory;
import javafx.scene.control.Alert;
import java.util.List;
import view.GameplayScreen;

/**
 * GameplayScreen class represents the main gameplay screen of the application.
 * Handles UI layout, bear interaction buttons, visual updates, and decay logic.
 */

public class GameplayScreen {

    // Controllers and app references
    private BearController bearController;
    private PoCoToApp app;

    // UI components for bear stats
    private ProgressBar hungerBar;
    private ProgressBar healthBar;
    private ProgressBar sleepBar;
    private ProgressBar happinessBar;
    
    private Text bearStatusText;
    private ImageView bearImage;

    // Labels for bear names and stats
    private Label bearNameLabel;
    private Label hungerLabel;
    private Label healthLabel;
    private Label sleepLabel;
    private Label happinessLabel;
    
    // Services and helper classes
    private NotificationService notificationService; // added by Bhavya Sharma
    private SaveLoadController saveLoadController; // added by Bhavya Sharma
    private Timeline autoDecayTimer;

    private Inventory inventory = new Inventory(); // Inventory instance for the inventory

    /**
     * Constructor to initialize the GameplayScreen with a BearController and PoCoToApp instance.
     * This constructor sets up the UI components and their layout.
     * @param controller the controller handling the current bear's logic
     * @param app reference to the main app file for screen navigation
     */
    public GameplayScreen(BearController controller, PoCoToApp app) {
        this.bearController = controller;
        this.app = app;
    }

    /**
     * Launches the gameplay UI with stat bars, bear image, interaction buttons, and timers.
     *
     * @param primaryStage the main stage passed from the application entry point
     */
    public void start(Stage primaryStage) {
        // Title label
        Label title = new Label("PoCoTo");
        title.getStyleClass().add("title");

        bearNameLabel = new Label(bearController.getBear().getLabel());
        bearNameLabel.getStyleClass().add("bear-name");


        

        // Stat bars with labels
        hungerLabel = new Label("Hunger");
        hungerBar = createLabeledBar("Hunger");
        HBox hungerBox = new HBox(10, hungerLabel, hungerBar);

        healthLabel = new Label("Health");
        healthBar = createLabeledBar("Health");
        HBox healthBox = new HBox(10, healthLabel, healthBar);

        sleepLabel = new Label("Sleep");
        sleepBar = createLabeledBar("Sleep");
        HBox sleepBox = new HBox(10, sleepLabel, sleepBar);
        
        happinessLabel = new Label("Happiness");
        happinessBar = createLabeledBar("Happiness");
        HBox happinessBox = new HBox(10, happinessLabel, happinessBar);

        VBox statsBox = new VBox(10, hungerBox, healthBox, sleepBox, happinessBox);
        statsBox.setPadding(new Insets(20));
        statsBox.setAlignment(Pos.CENTER);

        // Bear image setup
        bearImage = new ImageView(getBearImage(bearController.getBear()));
        bearImage.setFitWidth(100);
        bearImage.setFitHeight(100);
        bearImage.setPreserveRatio(true);

        // Status text setup
        bearStatusText = new Text("Bear is calm.");
        saveLoadController = new SaveLoadController(bearController.getBear()); // added by Bhavya Sharma
        bearStatusText.getStyleClass().add("text"); // added by Bhavya Sharma

        this.notificationService = new NotificationService(bearStatusText); // implemented by Bhavya Sharma (moved by Adit)

        // Buttons
        Button feedBtn = new Button("Feed");
        Button playBtn = new Button("Play");
        Button sleepBtn = new Button("Sleep");
        Button healBtn = new Button("Heal");
        Button pauseBtn = new Button("Pause"); //Added by Jayansh Bagga
        Button saveButton = new Button("Save Game"); // moved up here by Adit
        Button loadButton = new Button("Load Game"); // moved up here by Adit
        Button backButton = new Button("Back to Main Menu"); // moved up here by Adit
        Button InventoryBtn = new Button("Inventory"); // added by Adit to display the Inventory button

        // Set preferred widths for consistent button sizing
        feedBtn.setPrefWidth(120);
        playBtn.setPrefWidth(120);
        sleepBtn.setPrefWidth(120);
        healBtn.setPrefWidth(120);
        pauseBtn.setPrefWidth(120);
        InventoryBtn.setPrefWidth(120);
        saveButton.setPrefWidth(140);
        loadButton.setPrefWidth(140);
        backButton.setPrefWidth(180);


        HBox topButtons = new HBox(15, feedBtn, playBtn, sleepBtn, healBtn, InventoryBtn);
        topButtons.setPadding(new Insets(10));
        topButtons.setAlignment(Pos.CENTER);

        HBox saveLoadBox = new HBox(15, saveButton, loadButton);
        saveLoadBox.setAlignment(Pos.CENTER);

        HBox pauseBox = new HBox(pauseBtn);
        pauseBox.setAlignment(Pos.BOTTOM_RIGHT);
        pauseBox.setPadding(new Insets(10, 20, 10, 10));

        VBox root = new VBox(25, title, bearNameLabel, bearImage, statsBox, topButtons, saveLoadBox, bearStatusText, backButton);
        root.setAlignment(Pos.CENTER);

        // Back to Main Menu button
        backButton.setOnAction(e -> app.showMainMenu(primaryStage));
        VBox.setMargin(backButton, new Insets(10, 0, 30, 0));

        // Button logic for user interactions
        feedBtn.setOnAction(e -> {
            bearController.feedBear();
            updateUI();
            notificationService.checkBearStatus(bearController.getBear()); // added by Bhavya Sharma
        });

        playBtn.setOnAction(e -> {
            bearController.playWithBear();
            updateUI();
            notificationService.checkBearStatus(bearController.getBear()); // added by Bhavya Sharma
        });

        sleepBtn.setOnAction(e -> {
            bearController.putBearToSleep();
            updateUI();
            notificationService.checkBearStatus(bearController.getBear()); // added by Bhavya Sharma
        });

        healBtn.setOnAction(e -> {
            bearController.healBear();
            updateUI();
            notificationService.checkBearStatus(bearController.getBear()); // added by Bhavya Sharma
        });

        pauseBtn.setOnAction(e -> {   //By Jayansh Bagga- Pause Button Action 
            PauseMenu pauseMenu = new PauseMenu(
                primaryStage, // if needed, pass primaryStage directly (changed to primaryStage by Adit)
                () -> {
                    System.out.println("Game resumed."); //resume action 
                }, 
                () -> GameController.saveGame(bearController.getBear()), // Save (modified by Adit to pass the bear into the method call)
                () -> app.showMainMenu(primaryStage) // Return to main menu
            );
            pauseMenu.show();
        });


        InventoryBtn.setOnAction(e -> { // added by Adit to show the Inventory Button
            InventoryScreen inventoryScreen = new InventoryScreen(primaryStage, bearController, app);
        });



        VBox.setMargin(bearImage, new Insets(10, 0, 10, 0));
        root.setPadding(new Insets(30));
        root.setAlignment(Pos.CENTER);

        

        Scene scene = new Scene(root, 800, 700); // game screen dimensions

        // Apply styles.css
        var css = getClass().getResource("/styles.css");
        if (css != null) {
            scene.getStylesheets().add(css.toExternalForm());
        }

        primaryStage.setTitle("PoCoTo - Gameplay");
        primaryStage.setScene(scene);
        primaryStage.setWidth(800);
        primaryStage.setHeight(640);
        primaryStage.setResizable(false);
        primaryStage.show();

        updateUI();
        startAutoDecay();
    }

    /**
     * Creates a progress bar for a given stat.
     *
     * @param labelText the stat name
     * @return ProgressBar with full value (1.0)
     */
    private ProgressBar createLabeledBar(String labelText) {
        return new ProgressBar(1.0);
    }

    /**
     * Updates all bear stats on the UI and the corresponding bear sprite/status
     */
    private void updateUI() {

        bearNameLabel.setText(bearController.getBear().getLabel());
        
        double hunger = bearController.getHungerPercent();
        double health = bearController.getHealthPercent();
        double sleep = bearController.getSleepPercent();
        double happiness = bearController.getHappinessPercent();

        hungerBar.setProgress(hunger);
        healthBar.setProgress(health);
        sleepBar.setProgress(sleep);
        happinessBar.setProgress(happiness);

        hungerLabel.setText("Hunger: " + Math.round(hunger * 100) + "%");
        healthLabel.setText("Health: " + Math.round(health * 100) + "%");
        sleepLabel.setText("Sleep: " + Math.round(sleep * 100) + "%");
        happinessLabel.setText("Happiness: " + Math.round(happiness * 100) + "%");

        String type = bearController.getBear().getClass().getSimpleName().toLowerCase();
        Bear currentBear = bearController.getBear();
        bearImage.setImage(new Image("file:" + getBearSpritePath(currentBear)));

        // text to pop up based on the bear's status
        if (currentBear.isDead()) {
            bearStatusText.setText("Bear has died");
        } else if (currentBear.isAngry()) {
            bearStatusText.setText("Bear is angry");
        } else if (currentBear.isHungry()) {
            bearStatusText.setText("Bear is hungry");
        } else if (currentBear.isTired()) {
            bearStatusText.setText("Bear is tired");
        } else {
            bearStatusText.setText("Bear is calm");
        }
    }

    /**
     * Determines the path of the bear's sprite based on type of bear and the status of the bear
     * 
     * @param bear the bear object
     * @return path to the sprite image file
     */
    
    private String getBearSpritePath(Bear bear) {
        if (bear instanceof Po) {
            if(bear.isTired()) {
                return "assets/po_sleep.png";
            }
            if(bear.isHungry()) {
                return "assets/po_hungry.png";
            }
            return "assets/po_play.png";
        } else if (bear instanceof Co) {
            if(bear.isTired()) {
                return "assets/co_sleep.png";
            }
            if(bear.isHungry()) {
                return "assets/co_hungry.png";
            }
            return "assets/co_play.png";
        }else if (bear instanceof To) {
            if(bear.isTired()) {
                return "assets/to_sleep.png";
            }
            if(bear.isHungry()) {
                return "assets/to_hungry.png";
            }
            return "assets/to_play.png";
        }
        throw new IllegalArgumentException("Unknown bear type: " + bear.getClass().getSimpleName()); // default fallback if needed
    }

    
    /**
     * Gets the default idle image for the bear when game starts.
     *
     * @param bear the bear object
     * @return Image representing the idle sprite
     */
    private Image getBearImage(Bear bear) {
        String type = bear.getClass().getSimpleName().toLowerCase(); // "po", "co", "to"
        return new Image("file:assets/" + type + "_idle.png");
    }

    /**
     * Triggers dummy save logic
     */
    private void saveGame() {
        System.out.println("Saving game...");
    }

    /**
     * Triggers dummy load logic
     */
    private void loadGame() {
        System.out.println("Loading game...");
    }

    /**
     * Starts automatic stat decay using a Timeline that runs every 5 seconds.
     */
    private void startAutoDecay() {
    autoDecayTimer = new Timeline(new KeyFrame(Duration.seconds(5), e -> {
        bearController.updateBear();
        updateUI();
    }));
    autoDecayTimer.setCycleCount(Timeline.INDEFINITE);
    autoDecayTimer.play();
}

}
