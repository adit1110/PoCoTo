package view;

import controller.GameController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

/**
 * PauseMenu.java
 * Implements the Pause Menu UI with Resume, Save, and Main Menu buttons.
 * @author: Jayansh Bagga
 */
public class PauseMenu {

    private Stage pauseStage;
    private Runnable onResume;
    private Runnable onSave;
    private Runnable onMainMenu;

    /**
     * Constructor for PauseMenu
     * @param parentStage the main gameplay stage
     * @param onResume callback for resume
     * @param onSave callback for save
     * @param onMainMenu callback for returning to main menu
     */
    public PauseMenu(Stage parentStage, Runnable onResume, Runnable onSave, Runnable onMainMenu) {
        this.onResume = onResume;
        this.onSave = onSave;
        this.onMainMenu = onMainMenu;

        pauseStage = new Stage();
        pauseStage.initOwner(parentStage);
        pauseStage.initModality(Modality.WINDOW_MODAL);
        pauseStage.setTitle("Paused");

        // Main pause menu layout
        VBox layout = new VBox(18);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(30));
        layout.setStyle("-fx-background-color: #2C3E50;");

        // Title
        Label title = new Label("Game Paused");
        title.setStyle(
            "-fx-font-size: 28px;" +
            "-fx-font-weight: bold;" +
            "-fx-text-fill: white;"
        );

        // Blue underline
        Region underline = new Region();
        underline.setStyle(
            "-fx-background-color: #3498DB;" +
            "-fx-min-width: 60px;" +
            "-fx-max-width: 60px;" +
            "-fx-min-height: 3px;" +
            "-fx-max-height: 3px;"
        );

        // Buttons
        Button resumeButton = new Button("Resume Game");
        Button saveButton = new Button("Save Game");
        Button mainMenuButton = new Button("Main Menu");

        // Consistent button dimensions
        for (Button button : new Button[] {
            resumeButton, saveButton, mainMenuButton
        }) {
            button.setPrefWidth(210);
            button.setPrefHeight(45);
        }

        // Resume gameplay
        resumeButton.setOnAction(e -> {
            onResume.run();
            pauseStage.close();
        });

        // Save current bear
        saveButton.setOnAction(e -> onSave.run());

        // Return to main menu
        mainMenuButton.setOnAction(e -> {
            onMainMenu.run();
            pauseStage.close();
        });

        // Add all elements
        layout.getChildren().addAll(
            title,
            underline,
            resumeButton,
            saveButton,
            mainMenuButton
        );

        // Create a compact pause menu
        Scene scene = new Scene(layout, 360, 320);

        // Apply the same stylesheet used by the other screens
        var css = getClass().getResource("/styles.css");
        if (css != null) {
            scene.getStylesheets().add(css.toExternalForm());
        }

        pauseStage.setScene(scene);
        pauseStage.setResizable(false);
    }

    /**
     * Show the pause menu window
     */
    public void show() {
        pauseStage.showAndWait();
    }

    /**
     * Getter for resume action to use in test
     * @return onResume Runnable
     */
    public Runnable getResumeAction() {
        return onResume; // Added by Adit to help test the resume action
    }
}
