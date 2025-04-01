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
import javafx.scene.layout.VBox; // added by Adit
import javafx.scene.layout.HBox; // added by Adit
import javafx.geometry.Pos; // added by Adit

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
        titleLabel.getStyleClass().add("title"); // added by Adit to center it
        HBox titleBox = new HBox(titleLabel);
        titleBox.setAlignment(Pos.CENTER); // added by Adit to center the settings label

        // Volume Control
        Label volumeLabel = new Label("Volume:");
        Slider volumeSlider = new Slider(0, 100, 50);
        volumeSlider.setShowTickLabels(true);
        volumeSlider.setShowTickMarks(true);
        HBox volumeBox = new HBox(10, volumeLabel, volumeSlider);
        volumeBox.setAlignment(Pos.CENTER); // added by Adit to center the volume label and the volume slider

        // Dark Mode Toggle
        Label darkModeLabel = new Label("Dark Mode:");
        CheckBox darkModeCheckBox = new CheckBox();
        HBox darkModeBox = new HBox(10, darkModeLabel, darkModeCheckBox); // added by Adit to clean up the dark mode settings
        darkModeBox.setAlignment(Pos.CENTER); // added by Adit to center the dark mode settings

        // Save Button
        Button saveButton = new Button("Save");
        saveButton.setOnAction(e -> {
            // Handle save logic here
            System.out.println("Settings saved:");
            System.out.println("Volume: " + volumeSlider.getValue());
            System.out.println("Dark Mode: " + darkModeCheckBox.isSelected());
        });

        // Back to Main Menu Button
        Button backButton = new Button("Back to Main Menu");
        backButton.setOnAction(e -> {
            app.showMainMenu(primaryStage);
        });

        // group up save and back button and center it (added by Adit)
        HBox buttonBox = new HBox(20, saveButton, backButton);
        buttonBox.setAlignment(Pos.CENTER);
        
        // Wrap everything up in a VBox (added by Adit)
        VBox root = new VBox(25, titleBox, volumeBox, darkModeBox, buttonBox);
        root.setPadding(new Insets(40));
        root.setAlignment(Pos.CENTER);

        Scene settingsScene = new Scene(root, 800, 600); // modified from grid to root to use the new settings by Adit

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