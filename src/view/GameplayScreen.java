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

public class GameplayScreen {

    private BearController bearController;

    private ProgressBar hungerBar;
    private ProgressBar healthBar;
    private ProgressBar sleepBar;
    private ProgressBar happinessBar;
    private Text bearStatusText;
    private ImageView bearImage;

    public GameplayScreen(BearController controller) {
        this.bearController = controller;
    }

    public void start(Stage primaryStage) {
        // Title label
        Label title = new Label("PoCoTo");
        title.getStyleClass().add("title");

        // Stat bars
        hungerBar = createLabeledBar("Hunger");
        healthBar = createLabeledBar("Health");
        sleepBar = createLabeledBar("Sleep");
        happinessBar = createLabeledBar("Happiness");

        VBox statsBox = new VBox(10);
        statsBox.setPadding(new Insets(20));
        statsBox.getChildren().addAll(hungerBar, healthBar, sleepBar, happinessBar);
        statsBox.setAlignment(Pos.CENTER);

        // Bear image
        bearImage = new ImageView(getBearImage(bearController.getBear()));
        bearImage.setFitWidth(150);
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

        VBox root = new VBox(20, title, bearImage, statsBox, buttonBox, bearStatusText);
        root.setPadding(new Insets(30));
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 800, 600);

        // Apply styles.css if present
        var css = getClass().getResource("/styles.css");
        if (css != null) {
            scene.getStylesheets().add(css.toExternalForm());
        }

        primaryStage.setTitle("PoCoTo - Gameplay");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

        updateUI(); // Initialize bars
    }

    private ProgressBar createLabeledBar(String labelText) {
        return new ProgressBar(1.0);
    }

    private void updateUI() {
        hungerBar.setProgress(bearController.getHungerPercent());
        healthBar.setProgress(bearController.getHealthPercent());
        sleepBar.setProgress(bearController.getSleepPercent());
        happinessBar.setProgress(bearController.getHappinessPercent());

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
}
