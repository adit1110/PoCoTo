package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.Parent;
import app.PoCoToApp;

/**
 * The ParentalControlScreen class represents the parental control settings UI.
 * It allows administrators to set playtime limits, toggle character revival, and view user statistics.
 * Users must enter the correct admin password to save settings.
 *
 * The UI follows a structured layout with a password field, dropdown, toggle button,
 * statistics display, and action buttons.
 *
 * @author Krish Patel
 */
public class ParentalControlScreen {
    private final PoCoToApp app;
    private final Stage primaryStage;
    private final PasswordField passwordField;
    private final ComboBox<String> playtimeLimitDropdown;
    private final ToggleButton revivalToggleButton;
    private final Text userStatsDisplay;
    private final VBox root;
    
    /**
     * Constructs the ParentalControlScreen UI components, including the password field,
     * playtime limit dropdown, revival toggle, user statistics display, and action buttons.
     *
     * @param primaryStage The primary stage to display the UI.
     * @param app Reference to the main application class.
     */
    public ParentalControlScreen(Stage primaryStage, PoCoToApp app) {
        this.primaryStage = primaryStage;
        this.app = app;

        passwordField = new PasswordField();
        passwordField.setPromptText("Enter Admin Password");

        playtimeLimitDropdown = new ComboBox<>();
        playtimeLimitDropdown.getItems().addAll("1 Hour", "2 Hours", "3 Hours", "Unlimited");
        playtimeLimitDropdown.setValue("Unlimited");

        revivalToggleButton = new ToggleButton("Enable Bear Revival");
        revivalToggleButton.setSelected(true);

        userStatsDisplay = new Text("User Statistics:\nTotal Playtime: 0 hours\nTotal Sessions: 0");
        userStatsDisplay.getStyleClass().add("user-stats"); // added by Adit to implement styling on the user stats display text

        Button saveButton = new Button("Save");
        Button cancelButton = new Button("Cancel");
        Button mainMenuButton = new Button("Back to Main Menu");

        saveButton.setOnAction(event -> saveSettings());
        cancelButton.setOnAction(event -> app.showMainMenu(primaryStage)); // modified by Adit to ensure that pressing the cancel button goes back to the main menu
        mainMenuButton.setOnAction(e -> app.showMainMenu(primaryStage));

        root = new VBox(15, passwordField, playtimeLimitDropdown, revivalToggleButton, userStatsDisplay, saveButton, cancelButton, mainMenuButton);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

        primaryStage.setTitle("Parental Controls");
        primaryStage.setScene(scene);

        playtimeLimitDropdown.setButtonCell(new ListCell<>() { // added by Adit to implement some styling into the screen
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(item);
                setStyle("-fx-text-fill: black; fx-background-color: white;");
            }
        });
    
        playtimeLimitDropdown.setCellFactory(listView -> new ListCell<>() { // added by Adit to implement some styling into the screen
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(item);
                setStyle("-fx-text-fill: black; fx-background-color: white;");
            }
        });

        primaryStage.show();
    }

    /**
     * Handles saving the parental control settings.
     * Validates the password and updates the settings for playtime limits,
     * character revival, and displays updated user statistics.
     */
    private void saveSettings() {
        if ("admin".equals(passwordField.getText())) {
            String playtimeLimit = playtimeLimitDropdown.getValue();
            boolean isRevivalEnabled = revivalToggleButton.isSelected();

            userStatsDisplay.setText(String.format("Settings Saved:\nPlaytime Limit: %s\nRevival: %s", playtimeLimit, isRevivalEnabled ? "Enabled" : "Disabled"));
            System.out.println("Settings Saved: " + playtimeLimit + ", Revival: " + isRevivalEnabled);
        } else {
            userStatsDisplay.setText("Incorrect Password. Try Again.");
        }
    }

    /**
     * Handles the cancellation of settings.
     * Resets all changes made and closes the parental control screen.
     */
    private void cancelSettings() {
        passwordField.clear();
        playtimeLimitDropdown.setValue("Unlimited");
        revivalToggleButton.setSelected(true);
        userStatsDisplay.setText("User Statistics:\nTotal Playtime: 0 hours\nTotal Sessions: 0");
        primaryStage.close();
    }

    /**
     * Provides the root UI element for this screen.
     * @return The root VBox containing the UI elements.
     */
    public Parent getRoot() {
        return root;
    }
}