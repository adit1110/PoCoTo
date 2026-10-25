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
import javafx.scene.control.ScrollPane;
import java.nio.file.Path;
import service.ParentalSettings;
import javafx.scene.layout.Region;

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
    private Label scoreLabel;
    
    // Services and helper classes
    private NotificationService notificationService; // added by Bhavya Sharma
    private SaveLoadController saveLoadController; // added by Bhavya Sharma
    private Timeline autoDecayTimer;
    private Timeline parentalClock;
    private final ParentalSettings parentalSettings = ParentalSettings.getDefault();
    private boolean playtimeExpired;
    private Stage gameplayStage;
    private final Path saveFile;

    private Inventory inventory = new Inventory(); // Inventory instance for the inventory

    /**
     * Constructor to initialize the GameplayScreen with a BearController and PoCoToApp instance.
     * This constructor sets up the UI components and their layout.
     * @param controller the controller handling the current bear's logic
     * @param app reference to the main app file for screen navigation
     */
    public GameplayScreen(BearController controller, PoCoToApp app) {
        this(controller, app, Path.of("bear_save.dat"));
    }

    /** Allows integration tests to use a temporary save file. */
    public GameplayScreen(BearController controller, PoCoToApp app, Path saveFile) {
        this.bearController = controller;
        this.app = app;
        this.saveFile = saveFile;
    }

    /**
     * Launches the gameplay UI with stat bars, bear image, interaction buttons, and timers.
     *
     * @param primaryStage the main stage passed from the application entry point
     */
    public void start(Stage primaryStage) {
        gameplayStage = primaryStage;
        parentalSettings.flushElapsedTime();
        if (!parentalSettings.canPlay()) {
            app.showMainMenu(primaryStage);
            new Alert(Alert.AlertType.WARNING, "Playtime limit reached. Ask an administrator to change the limit.").showAndWait();
            return;
        }
        parentalSettings.beginSession();
        parentalClock = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            parentalSettings.flushElapsedTime();
            enforcePlaytimeLimit();
        }));
        parentalClock.setCycleCount(Timeline.INDEFINITE);
        parentalClock.play();
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

        // Keep the stats section centered and consistently sized.
        VBox statsBox = new VBox(10, hungerBox, healthBox, sleepBox, happinessBox);
        statsBox.setPadding(new Insets(20));
        statsBox.setAlignment(Pos.CENTER);
        statsBox.setMaxWidth(480);

        // Align all progress bars vertically.
        hungerBox.setAlignment(Pos.CENTER_LEFT);
        healthBox.setAlignment(Pos.CENTER_LEFT);
        sleepBox.setAlignment(Pos.CENTER_LEFT);
        happinessBox.setAlignment(Pos.CENTER_LEFT);

        // Give each stat label the same width.
        hungerLabel.setMinWidth(160);
        healthLabel.setMinWidth(160);
        sleepLabel.setMinWidth(160);
        happinessLabel.setMinWidth(160);

        // Give each progress bar the same width.
        hungerBar.setPrefWidth(230);
        healthBar.setPrefWidth(230);
        sleepBar.setPrefWidth(230);
        happinessBar.setPrefWidth(230);

        scoreLabel = new Label();
        scoreLabel.setStyle("-fx-text-fill: #ffdc85; -fx-font-size: 18px; -fx-font-weight: bold;");

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
        Button reviveBtn = new Button("Revive Bear");
        Button pauseBtn = new Button("Pause");
        Button miniGameBtn = new Button("Minigame"); //Added by Jayansh Bagga
        Button saveButton = new Button("Save Game"); // moved up here by Adit
        Button loadButton = new Button("Load Game"); // moved up here by Adit
        Button backButton = new Button("Back to Main Menu"); // moved up here by Adit
        Button InventoryBtn = new Button("Inventory"); // added by Adit to display the Inventory button

        // Set preferred widths for consistent button sizing
        feedBtn.setPrefWidth(120);
        playBtn.setPrefWidth(120);
        sleepBtn.setPrefWidth(120);
        healBtn.setPrefWidth(120);
        pauseBtn.setMinWidth(120);
        pauseBtn.setPrefWidth(120);
        miniGameBtn.setMinWidth(140);
        miniGameBtn.setPrefWidth(140);
        InventoryBtn.setMinWidth(130);
        InventoryBtn.setPrefWidth(130);
        saveButton.setPrefWidth(140);
        loadButton.setPrefWidth(140);
        backButton.setMinWidth(210);
        backButton.setPrefWidth(210);


        HBox topButtons = new HBox(15, feedBtn, playBtn, sleepBtn, healBtn, InventoryBtn);
        topButtons.setPadding(new Insets(10));
        topButtons.setAlignment(Pos.CENTER);

        reviveBtn.setOnAction(e -> {
            if (parentalSettings.revive(bearController.getBear())) {
                updateUI();
            }
        });
        HBox saveLoadBox = new HBox(15, saveButton, loadButton, reviveBtn);
        saveLoadBox.setAlignment(Pos.CENTER);

        Label saveLoadFeedback = new Label();
        saveLoadFeedback.setStyle("-fx-text-fill: white; -fx-font-size: 14px;");

        // Center the Pause and Minigame buttons beneath the main actions.
        HBox pauseBox = new HBox(15, pauseBtn, miniGameBtn);
        pauseBox.setAlignment(Pos.CENTER);
        pauseBox.setPadding(new Insets(10));
        pauseBox.setMaxWidth(Region.USE_PREF_SIZE);

        VBox root = new VBox(25, title, bearNameLabel, scoreLabel, bearImage, statsBox, topButtons, pauseBox, saveLoadBox, saveLoadFeedback, bearStatusText, backButton);
        root.setAlignment(Pos.CENTER);

        // Back to Main Menu button
        backButton.setOnAction(e -> {
            stopAutoDecay();
            stopParentalClock();
            app.showMainMenu(primaryStage);
        });
        VBox.setMargin(backButton, new Insets(10, 0, 30, 0));

        // Button logic for user interactions
        feedBtn.setOnAction(e -> {
            Bear activeBear = bearController.getBear();
            int before = activeBear.getHunger();
            bearController.feedBear();
            activeBear.awardCarePoints(before, activeBear.getHunger());
            updateUI();
            notificationService.checkBearStatus(bearController.getBear()); // added by Bhavya Sharma
        });

        playBtn.setOnAction(e -> {
            Bear activeBear = bearController.getBear();
            int before = activeBear.getHappiness();
            bearController.playWithBear();
            activeBear.awardCarePoints(before, activeBear.getHappiness());
            updateUI();
            notificationService.checkBearStatus(bearController.getBear()); // added by Bhavya Sharma
        });

        sleepBtn.setOnAction(e -> {
            Bear activeBear = bearController.getBear();
            int before = activeBear.getSleep();
            bearController.putBearToSleep();
            activeBear.awardCarePoints(before, activeBear.getSleep());
            updateUI();
            notificationService.checkBearStatus(bearController.getBear()); // added by Bhavya Sharma
        });

        healBtn.setOnAction(e -> {
            Bear activeBear = bearController.getBear();
            int before = activeBear.getHealth();
            bearController.healBear();
            activeBear.awardCarePoints(before, activeBear.getHealth());
            updateUI();
            notificationService.checkBearStatus(bearController.getBear()); // added by Bhavya Sharma
        });

        // Save using the same Java serialization format as the Pause menu.
        saveButton.setOnAction(e -> {
            boolean success = GameController.saveGame(bearController.getBear(), saveFile);
            saveLoadFeedback.setText(success ? "Game saved successfully." : "Could not save game.");
        });

        // Replace the active controller so all gameplay actions use the loaded bear.
        loadButton.setOnAction(e -> {
            Bear loadedBear = GameController.loadGame(saveFile);
            if (loadedBear == null) {
                saveLoadFeedback.setText("Could not load game. No valid save found.");
                return;
            }
            bearController = new BearController(loadedBear);
            saveLoadController = new SaveLoadController(loadedBear);
            updateUI();
            saveLoadFeedback.setText("Game loaded successfully.");
        });

        miniGameBtn.setOnAction(e -> {
            // Pause decay while the minigame's separate window is open.
            if (autoDecayTimer != null) autoDecayTimer.pause();
            pauseParentalClock();
            view.MiniGameScreen miniGame = new view.MiniGameScreen();
            miniGame.initOwner(primaryStage);
            miniGame.initModality(javafx.stage.Modality.WINDOW_MODAL);
            miniGame.setResultHandler(result -> {
                int earned = bearController.getBear().rewardMiniGame(result);
                saveLoadFeedback.setText(earned > 0
                        ? "Minigame: +" + earned + " points!" : "Minigame finished. No points earned.");
                updateUI();
            });
            miniGame.showAndWait();
            resumeParentalClock();
            if (autoDecayTimer != null &&
                    autoDecayTimer.getStatus() == javafx.animation.Animation.Status.PAUSED) {
                autoDecayTimer.play();
            }
        });

        pauseBtn.setOnAction(e -> {
            pauseParentalClock();
            // Pause automatic stat decay while the pause menu is open.
            if (autoDecayTimer != null) {
                autoDecayTimer.pause();
            }

            PauseMenu pauseMenu = new PauseMenu(
                primaryStage,

                // Resume
                () -> System.out.println("Game resumed."),

                // Save
                () -> GameController.saveGame(bearController.getBear()),

                // Return to main menu
                () -> {
                    stopAutoDecay();
                    stopParentalClock();
                    app.showMainMenu(primaryStage);
                }
            );

            // This waits until the Pause window closes.
            pauseMenu.show();
            resumeParentalClock();

            // Resume the existing timer, including when the window
            // is closed with X instead of the Resume button.
            if (autoDecayTimer != null &&
                autoDecayTimer.getStatus() == javafx.animation.Animation.Status.PAUSED) {
                autoDecayTimer.play();
            }
        });


        InventoryBtn.setOnAction(e -> {
            stopAutoDecay();
            stopParentalClock();

            new InventoryScreen(primaryStage, bearController, app,
                () -> new GameplayScreen(bearController, app, saveFile).start(primaryStage));
        });



        VBox.setMargin(bearImage, new Insets(10, 0, 10, 0));
        root.setPadding(new Insets(30));
        root.setAlignment(Pos.CENTER);

        

        ScrollPane scrollPane = new ScrollPane(root);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        scrollPane.setStyle(
            "-fx-background: #2c3e50;" +
            "-fx-background-color: #2c3e50;" +
            "-fx-border-color: transparent;"
        );

        Scene scene = new Scene(scrollPane, 800, 700);

        var css = getClass().getResource("/styles.css");
        if (css != null) {
            scene.getStylesheets().add(css.toExternalForm());
        }

        app.switchScene(
            primaryStage,
            scene,
            "PoCoTo - Gameplay"
        );

        // Expose revival only for a dead bear when the guardian allows it.
        reviveBtn.visibleProperty().bind(javafx.beans.binding.Bindings.createBooleanBinding(
                () -> parentalSettings.canRevive(bearController.getBear()),
                healthBar.progressProperty()));
        reviveBtn.managedProperty().bind(reviveBtn.visibleProperty());
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
        scoreLabel.setText("Score: " + bearController.getBear().getScore());
        
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
     * Stops automatic stat decay when leaving gameplay.
     */
    private void enforcePlaytimeLimit() {
        if (playtimeExpired || parentalSettings.canPlay()) return;
        playtimeExpired = true;
        stopAutoDecay();
        stopParentalClock();
        app.showMainMenu(gameplayStage);
        new Alert(Alert.AlertType.WARNING,
                "Your playtime limit has been reached. Gameplay is now locked.").showAndWait();
    }

    private void pauseParentalClock() {
        if (parentalClock != null) parentalClock.pause();
        parentalSettings.endSession();
    }

    private void resumeParentalClock() {
        if (parentalClock != null && !playtimeExpired) {
            parentalSettings.beginSession();
            parentalClock.play();
        }
    }

    private void stopParentalClock() {
        parentalSettings.endSession();
        if (parentalClock != null) {
            parentalClock.stop();
            parentalClock = null;
        }
    }

    private void stopAutoDecay() {
        if (autoDecayTimer != null) {
            autoDecayTimer.stop();
            autoDecayTimer = null;
        }
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
