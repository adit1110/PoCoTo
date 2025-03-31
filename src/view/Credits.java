package com.yourteam.projectname.views;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Credits extends Stage {

    public Credits() {
        setTitle("Credits");

        // ---- Main Layout ----
        BorderPane mainPane = new BorderPane();
        mainPane.setPadding(new Insets(20));

        // ---- Top: Title Label ----
        Label titleLabel = new Label("Credits");
        titleLabel.getStyleClass().add("title");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;"); // Inline fallback styling
        BorderPane.setAlignment(titleLabel, Pos.CENTER);
        mainPane.setTop(titleLabel);

        // ---- Center: Developer Credits and Team Info ----
        VBox contentBox = new VBox(10);
        contentBox.setAlignment(Pos.TOP_LEFT);
        contentBox.setPadding(new Insets(20));

        Label teamInfo = new Label("Team 50 - Fall 2024 - CS2212\nUniversity of Western Ontario");
        teamInfo.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Label developer1 = new Label("Adit Bhimani - Lead Developer");
        Label developer2 = new Label("Bhavya Sharma - UI/UX Designer");
        Label developer3 = new Label("Krish Patel - Backend Developer");
        Label developer4 = new Label("Jayansh Bagga - Frontend Developer");
        Label developer5 = new Label("Jeremy Ro - Documentation & Testing");

        // "My Interests" Section
        Label interestsTitle = new Label("\nMy Interests:");
        interestsTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        Label interest1 = new Label("Adit: Passionate about game design and AI.");
        Label interest2 = new Label("Bhavya: Enjoys digital art and interactive design.");
        Label interest3 = new Label("Krish: Loves solving algorithm challenges.");
        Label interest4 = new Label("Jayansh: Enthusiastic about UI trends and creative coding.");
        Label interest5 = new Label("Jeremy: Focused on quality documentation and testing.");

        contentBox.getChildren().addAll(
                teamInfo, 
                developer1, 
                developer2, 
                developer3, 
                developer4, 
                developer5,
                interestsTitle,
                interest1, 
                interest2, 
                interest3, 
                interest4, 
                interest5
        );
        mainPane.setCenter(contentBox);

        // ---- Bottom: Back Button ----
        Button backButton = new Button("Back");
        backButton.setStyle("-fx-font-size: 14px; -fx-padding: 5 15;");
        backButton.setOnAction(e -> close()); // Close the Credits screen

        HBox bottomBox = new HBox(backButton);
        bottomBox.setAlignment(Pos.CENTER_RIGHT);
        bottomBox.setPadding(new Insets(10, 20, 20, 20));
        mainPane.setBottom(bottomBox);

        // ---- Scene Setup ----
        Scene scene = new Scene(mainPane, 600, 500);
        scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm()); // External CSS
        setScene(scene);
    }

    // ---- Small Credits Section for Main Game Page ----
    public static HBox createSmallCreditsSection() {
        Label smallCreditsLabel = new Label("© 2024 Team 50 - CS2212");
        smallCreditsLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: gray;");

        HBox smallCreditsBox = new HBox(smallCreditsLabel);
        smallCreditsBox.setAlignment(Pos.BOTTOM_RIGHT);
        smallCreditsBox.setPadding(new Insets(10));
        return smallCreditsBox;
    }

    // Standalone testing method
    public static void main(String[] args) {
        Application.launch(TestCreditsApp.class, args);
    }

    // Inner class for launching Credits as a standalone app
    public static class TestCreditsApp extends Application {
        @Override
        public void start(Stage primaryStage) {
            Credits creditsScreen = new Credits();
            creditsScreen.show();
        }
    }
}