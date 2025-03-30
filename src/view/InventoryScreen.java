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

 package view;

 import javafx.scene.Scene;
 import javafx.scene.control.Button;
 import javafx.scene.image.Image;
 import javafx.scene.image.ImageView;
 import javafx.scene.layout.VBox;
 import javafx.stage.Stage;
 
 /**
  * The InventoryScreen class creates and displays the inventory screen.
  * It contains images of the available items and a button to return to the game.
  */
 public class InventoryScreen {
     private Stage primaryStage;
 
     /**
      * Constructs an InventoryScreen object and initializes the inventory UI.
      * 
      * @param primaryStage The primary stage used to display the inventory.
      */
     public InventoryScreen(Stage primaryStage) {
         this.primaryStage = primaryStage;
         showInventoryScreen();
     }
 
     /**
      * Creates and displays the inventory screen layout, including item images
      * and a back button to return to the GameplayScreen.
      */
     private void showInventoryScreen() {
         VBox layout = new VBox(20);
 
         // Load images from the assets folder
         Image appleImage = new Image(getClass().getResourceAsStream("/assets/apple.png"));
         Image bananaImage = new Image(getClass().getResourceAsStream("/assets/banana.png"));
 
         // Create ImageView components for items
         ImageView appleView = new ImageView(appleImage);
         appleView.setFitWidth(100);
         appleView.setFitHeight(100);
 
         ImageView bananaView = new ImageView(bananaImage);
         bananaView.setFitWidth(100);
         bananaView.setFitHeight(100);
 
         // Back button to return to the GameplayScreen
         Button backButton = new Button("Back to Game");
         backButton.setOnAction(e -> new GameplayScreen(primaryStage));
 
         // Add UI components to the layout
         layout.getChildren().addAll(appleView, bananaView, backButton);
 
         // Create and apply the scene
         Scene scene = new Scene(layout, 800, 600);
         scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
 
         // Set and display the scene
         primaryStage.setScene(scene);
         primaryStage.show();
     }
 }
 