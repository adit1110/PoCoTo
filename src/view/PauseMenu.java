package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * PauseMenu.java
 * Implements the Pause Menu UI with Resume, Save, and Main Menu buttons.
 * Created by Jayansh Bagga
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
        layout.setPadding(new Insets(20));                          // Added by Jayansh Bagga - adds padding
        layout.setAlignment(Pos.CENTER);                            // Added by Jayansh Bagga - centers button layout

        Button resumeButton = new Button("Resume");
        Button saveButton = new Button("Save Game");
        Button mainMenuButton = new Button("Main Menu");

        // Added by Jayansh Bagga - uniform styling for buttons
        resumeButton.setPrefWidth(120);
        saveButton.setPrefWidth(120);
        mainMenuButton.setPrefWidth(120);

        // Button Actions
        resumeButton.setOnAction(e -> {
            onResume.run();                                         // Added by Jayansh Bagga - triggers resume logic passed from GameplayScreen
            pauseStage.close();                                     // Added by Jayansh Bagga - closes the pause menu
        });

        saveButton.setOnAction(e -> {
            onSave.run();
        });

        mainMenuButton.setOnAction(e -> {
            onMainMenu.run();
            pauseStage.close();
        });

        layout.getChildren().addAll(resumeButton, saveButton, mainMenuButton);  // Updated layout structure

        Scene scene = new Scene(layout, 300, 200);
        pauseStage.setScene(scene);
    }

    /**
     * Show the pause menu window
     */
    public void show() {
        pauseStage.showAndWait();
    }

    public Runnable getResumeAction() {
        return onResume;
    }
}
