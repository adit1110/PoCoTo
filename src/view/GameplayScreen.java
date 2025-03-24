package view;

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
import app.PoCoToApp;

public class GameplayScreen {

    private BearController bearController;

    private PoCoToApp app;

    private ProgressBar hungerBar;
    private ProgressBar healthBar;
    private ProgressBar sleepBar;
    private ProgressBar happinessBar;
    private Text bearStatusText;
    private ImageView bearImage;

    private Label bearNameLabel;
    private Label hungerLabel;
    private Label healthLabel;
    private Label sleepLabel;
    private Label happinessLabel;

    public GameplayScreen(BearController controller, PoCoToApp app) {
        this.bearController = controller;
        this.app = app;
    }

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

        // Bear image
        bearImage = new ImageView(getBearImage(bearController.getBear()));
        bearImage.setFitWidth(150);
        bearImage.setFitHeight(150);
        bearImage.setPreserveRatio(true);

        // Status
        bearStatusText = new Text("Bear is calm.");
        bearStatusText.getStyleClass().add("text");

        // Buttons
        Button feedBtn = new Button("Feed");
        Button playBtn = new Button("Play");
        Button sleepBtn = new Button("Sleep");
        Button healBtn = new Button("Heal");

        HBox buttonBox = new HBox(15, feedBtn, playBtn, sleepBtn, healBtn);
        buttonBox.setPadding(new Insets(10));
        buttonBox.setAlignment(Pos.CENTER);

        //Save/Load stub buttons
        Button saveButton = new Button("Save Game");
        Button loadButton = new Button("Load Game");
        HBox saveLoadBox = new HBox(15, saveButton, loadButton);
        saveLoadBox.setAlignment(Pos.CENTER);

        // Back to Main Menu button
        Button backButton = new Button("Back to Main Menu");
        backButton.setOnAction(e -> app.showMainMenu(primaryStage));

        // Button actions
        feedBtn.setOnAction(e -> {
            bearController.feedBear();
            updateUI();
        });

        playBtn.setOnAction(e -> {
            bearController.playWithBear();
            updateUI();
        });

        sleepBtn.setOnAction(e -> {
            bearController.putBearToSleep();
            updateUI();
        });

        healBtn.setOnAction(e -> {
            bearController.healBear();
            updateUI();
        });

        VBox.setMargin(bearImage, new Insets(10, 0, 10, 0));
        VBox root = new VBox(25, title, bearNameLabel, bearImage, statsBox, buttonBox, saveLoadBox, bearStatusText, backButton);
        root.setPadding(new Insets(30));
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 800, 600);

        // Apply styles.css
        var css = getClass().getResource("/styles.css");
        if (css != null) {
            scene.getStylesheets().add(css.toExternalForm());
        }

        primaryStage.setTitle("PoCoTo - Gameplay");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

        updateUI();
    }

    private ProgressBar createLabeledBar(String labelText) {
        return new ProgressBar(1.0);
    }

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

        if (bearController.isDead()) {
            bearStatusText.setText("Bear has died ☠");
            bearImage.setImage(new Image("file:assets/" + type + "_dead.png"));
        } else if (bearController.isAngry()) {
            bearStatusText.setText("Bear is angry 😠");
            bearImage.setImage(new Image("file:assets/" + type + "_angry.png"));
        } else {
            bearStatusText.setText("Bear is calm.");
            bearImage.setImage(new Image("file:assets/" + type + "_idle.png"));
        }
    }

    private Image getBearImage(Bear bear) {
        String type = bear.getClass().getSimpleName().toLowerCase(); // "po", "co", "to"
        return new Image("file:assets/" + type + "_idle.png");
    }

    private void saveGame() {
        System.out.println("Saving game...");
    }

    private void loadGame() {
        System.out.println("Loading game...");
    }
}
