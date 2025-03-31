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