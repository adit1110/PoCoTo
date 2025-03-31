/**
 * SettingsScreen.java
 * 
 * This class implements the settings screen for the PoCoTo game using JavaFX.
 * The screen contains three tabs: General Settings, Parental Controls, and Help.
 * It provides controls for adjusting the volume, language, display resolution,
 * as well as options for enabling parental controls and viewing help content.
 * 
 * This screen is intended to be launched as a standalone window for testing or integrated into the main game flow.
 * 
 * @author Jeremy Ro
 */

package view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import app.PoCoToApp; // added by Adit

    /**
     * Constructs a new SettingsScreen.
     * Initializes the user interface by creating three tabs:
     * - General Settings: Adjust volume, language, and display resolution.
     * - Parental Controls: Enable/disable parental controls, set a playtime limit, and enter a password.
     * - Help: Displays basic help and tutorial information.
     * 
     * Event handling for the Apply, Cancel, and Reset Play Time buttons is set up in the constructor.
     */


public class SettingsScreen {

    private PoCoToApp app; // added by Adit

    public SettingsScreen(PoCoToApp app) {
        this.app = app;
    }



    public void display(Stage primaryStage) {
        // Create the layout
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(10);
        grid.setHgap(10);

        // Title Label
        Label titleLabel = new Label("Settings");
        GridPane.setConstraints(titleLabel, 0, 0);

        // Volume Control
        Label volumeLabel = new Label("Volume:");
        GridPane.setConstraints(volumeLabel, 0, 1);

        Slider volumeSlider = new Slider(0, 100, 50);
        volumeSlider.setShowTickLabels(true);
        volumeSlider.setShowTickMarks(true);
        GridPane.setConstraints(volumeSlider, 1, 1);

        // Dark Mode Toggle
        Label darkModeLabel = new Label("Dark Mode:");
        GridPane.setConstraints(darkModeLabel, 0, 2);

        CheckBox darkModeCheckBox = new CheckBox();
        GridPane.setConstraints(darkModeCheckBox, 1, 2);

        // Save Button
        Button saveButton = new Button("Save");
        GridPane.setConstraints(saveButton, 0, 3);
        saveButton.setOnAction(e -> {
            // Handle save logic here
            System.out.println("Settings saved:");
            System.out.println("Volume: " + volumeSlider.getValue());
            System.out.println("Dark Mode: " + darkModeCheckBox.isSelected());
        });

        // Back to Main Menu Button
        Button backButton = new Button("Back to Main Menu");
        GridPane.setConstraints(backButton, 1, 3);
        backButton.setOnAction(e -> {
            app.showMainMenu(primaryStage);
        });

        // Add all elements to the grid
        grid.getChildren().addAll(titleLabel, volumeLabel, volumeSlider, darkModeLabel, darkModeCheckBox, saveButton, backButton);


        Scene settingsScene = new Scene(grid, 800, 600);

        var css = getClass().getResource("/styles.css"); // added by Adit
        if (css != null) {
            settingsScene.getStylesheets().add(css.toExternalForm());
        }

        // Create and set the scene
        primaryStage.setScene(settingsScene);
        primaryStage.setTitle("Settings");
        primaryStage.show();
    }
}