package view;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class InventoryScreen {
    private Stage primaryStage;

    public InventoryScreen(Stage primaryStage) {
        this.primaryStage = primaryStage;
        showInventoryScreen();
    }

    private void showInventoryScreen() {
        VBox layout = new VBox(20);
        
        // Load images from assets
        Image appleImage = new Image(getClass().getResourceAsStream("/assets/apple.png"));
        Image bananaImage = new Image(getClass().getResourceAsStream("/assets/banana.png"));

        ImageView appleView = new ImageView(appleImage);
        appleView.setFitWidth(100);
        appleView.setFitHeight(100);

        ImageView bananaView = new ImageView(bananaImage);
        bananaView.setFitWidth(100);
        bananaView.setFitHeight(100);

        // Back button to return to GameplayScreen
        Button backButton = new Button("Back to Game");
        backButton.setOnAction(e -> new GameplayScreen(primaryStage));

        layout.getChildren().addAll(appleView, bananaView, backButton);
        
        Scene scene = new Scene(layout, 800, 600);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
