/**
 * The ParentalControlScreen class provides a user interface for guardians to manage parental controls.
 * It includes password-protected access, playtime limit settings, character revival toggle, 
 * and user statistics display. Additionally, it features "Save" and "Cancel" buttons to 
 * confirm or cancel the settings.
 * 
 * @author: Krish Patel
 */

package view;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.VBox;
import javafx.scene.control.ComboBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class ParentalControlScreen {

    private PasswordField passwordField;
    private ComboBox<String> playtimeLimitDropdown;
    private ToggleButton revivalToggleButton;
    private Text userStatsDisplay;
    private Button saveButton;
    private Button cancelButton;

    private Stage primaryStage;


    /**
     * Constructs the ParentalControlScreen UI components, including the password field,
     * playtime limit dropdown, revival toggle, user statistics display, and action buttons.
     * 
     * @param primaryStage The primary stage to display the UI.
     */
    public ParentalControlScreen(Stage primaryStage) {
        this.primaryStage = primaryStage;

        passwordField = new PasswordField();
        passwordField.setPromptText("Enter Admin Password");

        playtimeLimitDropdown = new ComboBox<>();
        playtimeLimitDropdown.getItems().addAll("1 Hour", "2 Hours", "3 Hours", "Unlimited");
        playtimeLimitDropdown.setValue("Unlimited");

        revivalToggleButton = new ToggleButton("Enable Bear Revival");
        revivalToggleButton.setSelected(true);

        userStatsDisplay = new Text("User Statistics: \nTotal Playtime: 0 hours\nTotal Sessions: 0");

        saveButton = new Button("Save");
        cancelButton = new Button("Cancel");

        // Define button actions
        saveButton.setOnAction(event -> saveSettings());
        cancelButton.setOnAction(event -> cancelSettings());

        // Layout arrangement
        VBox layout = new VBox(10);
        layout.getChildren().addAll(
                passwordField,
                playtimeLimitDropdown,
                revivalToggleButton,
                userStatsDisplay,
                saveButton,
                cancelButton
        );

        Scene scene = new Scene(layout, 300, 250);
        primaryStage.setTitle("Parental Controls");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Handles saving the parental control settings.
     * Validates the password and updates the settings for playtime limits,
     * character revival, and displays updated user statistics.
     */
    private void saveSettings() {
        String password = passwordField.getText();

        // Check for valid admin password
        if (password.equals("admin")) {
            String playtimeLimit = playtimeLimitDropdown.getValue();
            boolean isRevivalEnabled = revivalToggleButton.isSelected();

            // Save settings logic (for example, save to a file or apply game logic)
            userStatsDisplay.setText("Settings Saved: \nPlaytime Limit: " + playtimeLimit
                    + "\nRevival: " + (isRevivalEnabled ? "Enabled" : "Disabled"));

            // You can also add the logic for saving these settings to a database or config file
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
        userStatsDisplay.setText("User Statistics: \nTotal Playtime: 0 hours\nTotal Sessions: 0");
        primaryStage.close();  // Close the window if Cancel is clicked
    }
}

