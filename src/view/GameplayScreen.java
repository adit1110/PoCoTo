package view;


import controller.BearController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GameplayScreen {

    private BearController bearController;

    private ProgressBar hungerBar;
    private ProgressBar healthBar;
    private ProgressBar sleepBar;
    private ProgressBar happinessBar;
    private Text bearStatusText;
    private ImageView bearImage;

    public GameplayScreen(BearController bearController) {
        this.bearController = bearController;
    }

    public void start(Stage primaryStage) {
        
        // Labels and Progress Bars
        VBox statsBox = new VBox(10);
        statsBox.setPadding(new Insets(10));

        hungerBar = createLabeledBar("Hunger");
        healthBar = createLabeledBar("Health");
        sleepBar = createLabeledBar("Sleep");
        happinessBar = createLabeledBar("Happiness");


        statsBox.getChildren().addAll(hungerBar, healthBar, sleepBar, happinessBar);

        // Bear Image (find Bear sprite images)

        bearImage = new ImageView(new Image("/assets/bear_idle.png")); // Placeholder image
        bearImage.setFitWidth(150);
        bearImage.setPreserveRatio(true);

        // Bear Status Text
        bearStatusText = new Text("Bear is calm.");

        // Gameplay buttons
        Button feedButton = new Button("Feed");
        Button sleepButton = new Button("Sleep");
        Button playButton = new Button("Play");
        Button healButton = new Button("Heal");

        HBox buttonBox = new HBox(15, feedButton, sleepButton, playButton, healButton);
        buttonBox.setPadding(new Insets(10));

        // Button Actions
        feedButton.setOnAction(e -> {
            bearController.feedBear();
            updateUI();
        });

        playButton.setOnAction(e -> {
            bearController.playWithBear();
            updateUI();
        });

        sleepButton.setOnAction(e -> {
            bearController.putBearToSleep();
            updateUI();
        });

        healButton.setOnAction(e -> {
            bearController.healBear();
            updateUI();
        });

        VBox root = new VBox(20, bearImage, statsBox, buttonBox, bearStatusText);
        root.setPadding(new Insets(10));

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("PoCoTo - Gameplay");
        primaryStage.setScene(scene);
        primaryStage.show();

        updateUI();
    }

    private ProgressBar createLabeledBar(String labelText) {
    ProgressBar bar = new ProgressBar(1.0);
    return bar;
    }

    private void updateUI() {
        hungerBar.setProgress(bearController.getHungerPercent());
        healthBar.setProgress(bearController.getHealthPercent());
        sleepBar.setProgress(bearController.getSleepPercent());
        happinessBar.setProgress(bearController.getHappinessPercent());

        // Dynamic status label
        if (bearController.isDead()) {
            bearStatusText.setText("Bear has died ☠");
            bearImage.setImage(new Image("/assets/bear_dead.png"));
        } else if (bearController.isAngry()) {
            bearStatusText.setText("Bear is angry 😠");
            bearImage.setImage(new Image("/assets/bear_angry.png"));
        } else {
            bearStatusText.setText("Bear is calm.");
            bearImage.setImage(new Image("/assets/bear_idle.png"));
        }
    }
    
}
