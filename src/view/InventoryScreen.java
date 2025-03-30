package view;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import controller.BearController; // added by Adit
import app.PoCoToApp; // added by Adit

public class InventoryScreen {
    private Stage primaryStage;
    private BearController controller; // added by Adit
    private PoCoToApp app; // added by Adit

    public InventoryScreen(Stage primaryStage, BearController controller, PoCoToApp app) {
        this.primaryStage = primaryStage;
        this.controller = controller; // added by Adit
        this.app = app; // added by Adit
        showInventoryScreen();
    }

    private void showInventoryScreen() {
        VBox layout = new VBox(20);
        
        // Load images from assets
        Image appleImage = new Image("file:assets/apple.png");
        Image bananaImage = new Image("file:assets/banana.png");

        ImageView appleView = new ImageView(appleImage);
        appleView.setFitWidth(100);
        appleView.setFitHeight(100);

        ImageView bananaView = new ImageView(bananaImage);
        bananaView.setFitWidth(100);
        bananaView.setFitHeight(100);

        // Back button to return to GameplayScreen
        Button backButton = new Button("Back to Game");
        backButton.setOnAction(e -> { // modified by Adit to cleanly return to the gameplay screen
            GameplayScreen gameplay = new GameplayScreen(controller, app);
            gameplay.start(primaryStage);
        });
        

        layout.getChildren().addAll(appleView, bananaView, backButton);
        
        Scene scene = new Scene(layout, 800, 600);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm()); // modified by Adit to look for styles.css from the root of the classpath

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
