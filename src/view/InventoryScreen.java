package view;

import app.PoCoToApp;
import controller.BearController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Bear;
import model.Inventory;

/** Inventory screen: consume limited food and give gifts to the active bear. */
public class InventoryScreen {
    private final Stage stage;
    private final BearController controller;
    private final PoCoToApp app;
    private final Runnable returnToGameplay;
    private final Label feedback = new Label("Select an item to use it.");

    public InventoryScreen(Stage stage, BearController controller, PoCoToApp app) {
        this(stage, controller, app, () -> new GameplayScreen(controller, app).start(stage));
    }

    public InventoryScreen(Stage stage, BearController controller, PoCoToApp app,
                           Runnable returnToGameplay) {
        this.stage = stage;
        this.controller = controller;
        this.app = app;
        this.returnToGameplay = returnToGameplay;
        showInventoryScreen();
    }

    private void showInventoryScreen() {
        VBox layout = new VBox(20);
        layout.setPadding(new Insets(30));
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: #2c3e50;");

        Label title = new Label("Inventory");
        title.getStyleClass().add("section-title");
        Region underline = new Region();
        underline.getStyleClass().add("underline-bar");
        VBox titleBox = new VBox(5, title, underline);
        titleBox.setAlignment(Pos.CENTER);

        feedback.setStyle("-fx-text-fill: white; -fx-font-size: 14px;");
        Label explanation = new Label("Food restores Hunger. Gifts restore Happiness.");
        explanation.setStyle("-fx-text-fill: #d4e2ee; -fx-font-size: 14px;");

        HBox itemsRow = new HBox(20);
        itemsRow.setAlignment(Pos.CENTER);
        Inventory inventory = controller.getBear().getInventory();
        itemsRow.getChildren().addAll(
            itemCard("Apple", "assets/apple.png", 10, true, inventory),
            itemCard("Banana", "assets/banana.png", 15, true, inventory),
            itemCard("Teddy Bear", null, 20, false, inventory)
        );

        Button back = new Button("Back to Game");
        back.setOnAction(event -> returnToGameplay.run());
        layout.getChildren().addAll(titleBox, explanation, itemsRow, feedback, back);

        ScrollPane scrollPane = new ScrollPane(layout);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: #2c3e50; -fx-background-color: #2c3e50;");
        Scene scene = new Scene(scrollPane, 800, 700);
        var css = getClass().getResource("/styles.css");
        if (css != null) scene.getStylesheets().add(css.toExternalForm());
        app.switchScene(stage, scene, "PoCoTo - Inventory");
    }

    private VBox itemCard(String name, String imagePath, int points, boolean food,
                          Inventory inventory) {
        VBox card = new VBox(9);
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(15));
        card.setMinWidth(170);
        card.setStyle("-fx-background-color: #203347; -fx-background-radius: 8px; " +
                      "-fx-border-color: #47718b; -fx-border-radius: 8px;");

        if (imagePath != null) {
            ImageView icon = new ImageView(new Image("file:" + imagePath));
            icon.setFitWidth(72);
            icon.setFitHeight(72);
            icon.setPreserveRatio(true);
            card.getChildren().add(icon);
        } else {
            Label giftIcon = new Label("GIFT");
            giftIcon.setStyle("-fx-text-fill: #ffdc85; -fx-font-size: 26px; -fx-font-weight: bold;");
            giftIcon.setMinHeight(72);
            card.getChildren().add(giftIcon);
        }

        Label nameLabel = new Label(name);
        Label countLabel = new Label();
        nameLabel.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");
        countLabel.setStyle("-fx-text-fill: #d4e2ee;");
        Label effectLabel = new Label("+" + points + (food ? " Hunger" : " Happiness"));
        effectLabel.setStyle("-fx-text-fill: #d4e2ee;");
        Button useButton = new Button(food ? "Feed" : "Give Gift");
        Runnable refresh = () -> {
            int count = inventory.getItemCount(name);
            countLabel.setText("Quantity: " + count);
            useButton.setDisable(count == 0);
        };
        useButton.setOnAction(event -> {
            if (!inventory.useItem(name)) {
                feedback.setText("No " + name + " items remaining.");
                refresh.run();
                return;
            }
            Bear bear = controller.getBear();
            if (food) bear.feed(points);
            else bear.play(points);
            feedback.setText("Used " + name + "! +" + points +
                             (food ? " Hunger" : " Happiness") +
                             " (up to 100). Remaining: " + inventory.getItemCount(name));
            refresh.run();
        });
        refresh.run();
        card.getChildren().addAll(nameLabel, countLabel, effectLabel, useButton);
        return card;
    }
}
