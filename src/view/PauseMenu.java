package view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

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

        VBox layout = new VBox(15);
        layout.setPadding(new Insets(20));

        Button resumeButton = new Button("Resume");
        resumeButton.setOnAction(e -> {
            onResume.run();           // Added by Jayansh Bagga - triggers resume logic
            pauseStage.close();       // Closes the pause menu
        });

        Button saveButton = new Button("Save Game");
        Button mainMenuButton = new Button("Main Menu");

        saveButton.setOnAction(e -> {
            onSave.run();
        });

        mainMenuButton.setOnAction(e -> {
            onMainMenu.run();
            pauseStage.close();
        });

        layout.getChildren().addAll(resumeButton, saveButton, mainMenuButton);  // Updated layout structure

        Scene scene = new Scene(layout, 800, 600); // Changed by Adit to make the window bigger
        pauseStage.setScene(scene);
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
