package view;

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

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox; // added by Adit
import javafx.geometry.Insets; // added by Adit
import javafx.geometry.Pos; // added by Adit
import javafx.scene.control.Label; // added by Adit
import javafx.scene.control.Button; // added by Adit
import javafx.scene.control.ComboBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.Parent; // added by Adit
import app.PoCoToApp; // added by Adit
/**
 * ParentalControlScreen has all the components for the admin options with dropdown with a password field.
 */
public class ParentalControlScreen {

    private PasswordField passwordField;
    private ComboBox<String> playtimeLimitDropdown;
    private ToggleButton revivalToggleButton;
    private Text userStatsDisplay;
    private Button saveButton;
    private Button cancelButton;
    private Button mainMenuButton; // added by Adit
    private PoCoToApp app; // Added by Adit

    private Stage primaryStage;

    private VBox root; // added by Adit


    /**
     * Constructs the ParentalControlScreen UI components, including the password field,
     * playtime limit dropdown, revival toggle, user statistics display, and action buttons.
     * 
     * @param primaryStage The primary stage to display the UI.
     */
    public ParentalControlScreen(Stage primaryStage, PoCoToApp app ) { // slightly modified by Adit
        this.primaryStage = primaryStage;
        this.app = app; // added by Adit

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
        mainMenuButton = new Button("Back to Main Menu");

        // Define button actions
        saveButton.setOnAction(event -> saveSettings());
        cancelButton.setOnAction(event -> cancelSettings());
        mainMenuButton.setOnAction(e -> app.showMainMenu(primaryStage)); // added by Adit

        // Layout arrangement
        VBox layout = new VBox(15);  // Added custom spacing
        layout.getChildren().addAll(
                passwordField,
                playtimeLimitDropdown,
                revivalToggleButton,
                userStatsDisplay,
                saveButton,
                cancelButton,
                mainMenuButton // added by Adit
        );

        this.root = layout; // added by Adit

        // Create the scene with the layout and set dimensions
        Scene scene = new Scene(layout, 800, 600);

        // Link the stylesheet to the scene
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

        // Set the title and scene for the stage
        primaryStage.setTitle("Parental Controls");
        primaryStage.setScene(scene);

        // Show the stage
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
        // Password is: admin
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

    public Parent getRoot() { // added by Adit to make ParentalControlScreen's UI into a scene manually
        return root;
    }

    public VBox createFreshRoot() { // added by Adit
        VBox root = new VBox(20);
        root.setPadding(new Insets(20));

        root.setAlignment(Pos.CENTER);

        Label title = new Label("Parental Controls");
        title.getStyleClass().add("title");

        Label warningLabel = new Label("This section is restricted.");
        warningLabel.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");

        Button mainMenuButton = new Button("Back to Main Menu");
        mainMenuButton.setOnAction(e -> app.showMainMenu(primaryStage));

        root.getChildren().addAll(title, warningLabel, mainMenuButton);
        return root;
    }
}
