package view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HelpScreen extends Stage {

    public HelpScreen() {
        setTitle("Help & Tutorials");

        // ---- Top: Title ----
        Label titleLabel = new Label("Help & Tutorials");
        titleLabel.getStyleClass().add("title"); // Add CSS class for styling
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;"); // Inline styling as fallback
        VBox topBox = new VBox(titleLabel);
        topBox.setAlignment(Pos.CENTER);
        topBox.setPadding(new Insets(20, 0, 10, 0));

        // ---- Center: Help Content ----
        TextArea helpContent = new TextArea();
        helpContent.setEditable(false);
        helpContent.setWrapText(true);
        helpContent.setText(getHelpText());
        helpContent.setPrefHeight(400);
        helpContent.setStyle("-fx-font-size: 14px; -fx-control-inner-background: #f9f9f9;"); // Better readability
        BorderPane.setMargin(helpContent, new Insets(10, 20, 10, 20));

        // ---- Bottom: Navigation Buttons ----
        Button backButton = new Button("Back to Main Menu");
        backButton.setStyle("-fx-font-size: 14px; -fx-padding: 5 15;"); // Style the button
        backButton.setOnAction(e -> close()); // Close the HelpScreen

        HBox bottomBox = new HBox(10, backButton);
        bottomBox.setAlignment(Pos.CENTER_RIGHT);
        bottomBox.setPadding(new Insets(10, 20, 20, 20));

        // ---- Main Layout ----
        BorderPane mainPane = new BorderPane();
        mainPane.setTop(topBox);
        mainPane.setCenter(helpContent);
        mainPane.setBottom(bottomBox);

        // ---- Scene Setup ----
        Scene scene = new Scene(mainPane, 800, 600);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm()); // External CSS
        setScene(scene);
    }

    /**
     * Provides help and tutorial information for the game.
     *
     * @return A String containing instructions on how to play the game.
     */
    private String getHelpText() {
        return "Welcome to PoCoTo, your virtual pet simulation game!\n\n" +
               "Getting Started:\n" +
               "- Create a new game from the main menu and select your bear companion.\n" +
               "- Follow the on-screen prompts to name your pet and begin your adventure.\n\n" +
               "Gameplay Instructions:\n" +
               "- Feed your pet by selecting food items from your inventory to increase fullness.\n" +
               "- Play with your pet to boost its happiness; you can also engage in mini-games for bonus points.\n" +
               "- When your pet becomes sleepy, use the 'Sleep' command to let it rest and regain energy.\n" +
               "- Monitor vital statistics (health, fullness, happiness, sleep) on the gameplay screen.\n\n" +
               "Saving and Loading:\n" +
               "- The game automatically saves progress at checkpoints, or you can manually save from the menu.\n" +
               "- To load a saved game, select the 'Load Game' option from the main menu.\n\n" +
               "Parental Controls:\n" +
               "- Parents can restrict gameplay by enabling parental controls in the settings screen.\n" +
               "- Options include setting playtime limits and resetting play statistics.\n\n" +
               "Additional Help:\n" +
               "- For further assistance, refer to the official documentation or contact support.\n\n" +
               "Enjoy your journey with PoCoTo!";
    }


    // Main method for standalone testing
    public static void main(String[] args) {
        Application.launch(TestHelpApp.class, args);
    }

    // Inner class to run the HelpScreen as a standalone application for testing.
    public static class TestHelpApp extends Application {
        @Override
        public void start(Stage primaryStage) {
            HelpScreen helpScreen = new HelpScreen();
            helpScreen.show();
        }
    }
}