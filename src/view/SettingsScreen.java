package view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class SettingsScreen {

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

        // Back Button
        Button backButton = new Button("Back");
        GridPane.setConstraints(backButton, 1, 3);
        backButton.setOnAction(e -> {
            // Handle back navigation here
            primaryStage.setScene(new Scene(new Label("Main Screen Placeholder"), 400, 300));
        });

        // Add all elements to the grid
        grid.getChildren().addAll(titleLabel, volumeLabel, volumeSlider, darkModeLabel, darkModeCheckBox, saveButton, backButton);

        // Create and set the scene
        Scene settingsScene = new Scene(grid, 400, 300);
        primaryStage.setScene(settingsScene);
        primaryStage.setTitle("Settings");
        primaryStage.show();
    }
}public class HelpScreen extends Stage {    public HelpScreen() {        setTitle("Help & Tutorials");        // ---- Top: Title ----        Label titleLabel = new Label("Help & Tutorials");        titleLabel.getStyleClass().add("title");        VBox topBox = new VBox(titleLabel);        topBox.setAlignment(Pos.CENTER);        topBox.setPadding(new Insets(20, 0, 10, 0));        // ---- Center: Help Content ----        TextArea helpContent = new TextArea();        helpContent.setEditable(false);        helpContent.setWrapText(true);        helpContent.setText(getHelpText());        helpContent.setPrefHeight(400);        BorderPane.setMargin(helpContent, new Insets(10, 20, 10, 20));        // ---- Bottom: Back Button ----        Button backButton = new Button("Back");        backButton.setOnAction(e -> close());        HBox bottomBox = new HBox(backButton);        bottomBox.setAlignment(Pos.CENTER_RIGHT);        bottomBox.setPadding(new Insets(10, 20, 20, 20));        // ---- Main Layout ----        BorderPane mainPane = new BorderPane();        mainPane.setTop(topBox);        mainPane.setCenter(helpContent);        mainPane.setBottom(bottomBox);        Scene scene = new Scene(mainPane, 600, 500);        scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());        setScene(scene);    }    /**     * Provides help and tutorial information for the game.     *     * @return A String containing instructions on how to play the game.     */    private String getHelpText() {        return "Welcome to PoCoTo, your virtual pet simulation game!\n\n" +               "Getting Started:\n" +               "- Create a new game from the main menu and select your bear companion.\n" +               "- Follow the on-screen prompts to name your pet and begin your adventure.\n\n" +               "Gameplay Instructions:\n" +               "- Feed your pet by selecting food items from your inventory to increase fullness.\n" +               "- Play with your pet to boost its happiness; you can also engage in mini-games for bonus points.\n" +               "- When your pet becomes sleepy, use the 'Sleep' command to let it rest and regain energy.\n" +               "- Monitor vital statistics (health, fullness, happiness, sleep) on the gameplay screen.\n\n" +               "Saving and Loading:\n" +               "- The game automatically saves progress at checkpoints, or you can manually save from the menu.\n" +               "- To load a saved game, select the 'Load Game' option from the main menu.\n\n" +               "Parental Controls:\n" +               "- Parents can restrict gameplay by enabling parental controls in the settings screen.\n" +               "- Options include setting playtime limits and resetting play statistics.\n\n" +               "Additional Help:\n" +               "- For further assistance, refer to the official documentation or contact support.\n\n" +               "Enjoy your journey with PoCoTo!";    }    // Main method for standalone testing    public static void main(String[] args) {        Application.launch(TestHelpApp.class, args);    }    // Inner class to run the HelpScreen as a standalone application for testing.    public static class TestHelpApp extends Application {        @Override        public void start(Stage primaryStage) {            HelpScreen helpScreen = new HelpScreen();            helpScreen.show();        }    }}