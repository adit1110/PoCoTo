package view; // added by Adit

/**
 * The InventoryScreen class represents the inventory UI in the game.
 * It displays available items (apple and banana) and allows the player
 * to return to the GameplayScreen.
 * 
 * This class loads item images from the assets folder and applies styles
 * from styles.css for a consistent UI.
 * 
 * @author Krish Patel
 */

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets; // added by Adit
import javafx.geometry.Pos; // added by Adit
import javafx.scene.layout.HBox; // added by Adit
import javafx.stage.Stage;
import controller.BearController; // added by Adit
import app.PoCoToApp; // added by Adit
import javafx.scene.text.Text; // added by Adit
import javafx.scene.layout.Region;



/**
 * The InventoryScreen class represents the UI and all the items placed in the inventory section
 */
public class InventoryScreen {
    private Stage primaryStage;
    private BearController controller; // added by Adit
    private PoCoToApp app; // added by Adit
    private Text feedbackText; // added by Adit

    public InventoryScreen(Stage primaryStage, BearController controller, PoCoToApp app) {
        this.primaryStage = primaryStage;
        this.controller = controller; // added by Adit
        this.app = app; // added by Adit
        showInventoryScreen();
    }

    private void showInventoryScreen() {
        VBox layout = new VBox(20);
        layout.setPadding(new Insets(20)); // added by Adit to style the inventory screen
        layout.setAlignment(Pos.CENTER); // added by Adit to style the inventory screen

        VBox titleBox = new VBox(5); // spacing between text and underline
        titleBox.setAlignment(Pos.CENTER);
        
        // added by Adit to implement a physical title and implement styling similar to other pages
        Text titleText = new Text("Inventory");
        titleText.getStyleClass().add("section-title");

        Region underline = new Region();
        underline.getStyleClass().add("underline-bar");

        titleBox.getChildren().addAll(titleText, underline);


        layout.setStyle("-fx-background-color: #2c3e50;"); // added by Adit to style the inventory screen


        feedbackText = new Text(""); // added by Adit to have a visual confirmation for feeding the bear show up
        feedbackText.setStyle("-fx-fill: white; -fx-font-size: 14px;"); // added by Adit to have a visual confirmation for feeding the bear show up
        
        // Load images from assets
        Image appleImage = new Image("file:assets/apple.png");
        Image bananaImage = new Image("file:assets/banana.png");

        // feed bear with fruit logic added by Adit
        ImageView appleView = new ImageView(appleImage);
        appleView.setFitWidth(80);
        appleView.setFitHeight(80);
        appleView.setOnMouseClicked(e -> {
            controller.feedBear();
            feedbackText.setText("Fed the bear with a apple!"); // added by Adit to have a visual confirmation for feeding the bear show up
        });

        // feed bear with fruit logic added by Adit
        ImageView bananaView = new ImageView(new Image("file:assets/banana.png"));
        bananaView.setFitWidth(80);
        bananaView.setFitHeight(80);
        bananaView.setOnMouseClicked(e -> {
            controller.feedBear();
            feedbackText.setText("Fed the bear with a banana!"); // added by Adit to have a visual confirmation for feeding the bear show up
        });

        HBox imageBox = new HBox(40, appleView, bananaView); // modified by Adit to style the inventory screen
        imageBox.setAlignment(Pos.CENTER); // added by Adit to style the inventory screen

        // Back button to return to GameplayScreen
        Button backButton = new Button("Back to Game");
        backButton.setOnAction(e -> { // modified by Adit to cleanly return to the gameplay screen
            GameplayScreen gameplay = new GameplayScreen(controller, app);
            gameplay.start(primaryStage);
        });

        layout.getChildren().addAll(titleBox, imageBox, feedbackText, backButton); // added by Adit
                
        Scene scene = new Scene(layout, 800, 600);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm()); // modified by Adit to look for styles.css from the root of the classpath

        primaryStage.setScene(scene);
        primaryStage.setTitle("PoCoTo - Inventory"); // added by Adit to title the screen
        primaryStage.show();
    }
}
