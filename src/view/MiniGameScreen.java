package view;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox; // added by Adit
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

public class MiniGameScreen extends Stage {

    private static final int BOARD_SIZE = 3;
    private Button[][] boardButtons = new Button[BOARD_SIZE][BOARD_SIZE];
    private static final String PLAYER_SYMBOL = "Pic";
    private static final String COMPUTER_SYMBOL = "Pac";
    private Consumer<String> resultHandler;
    private boolean gameOver = false;
    private String winner; // added by Adit

    private Label statusLabel; // Displays game status (e.g., "Player's Turn", "Computer Wins")

    public MiniGameScreen() {
        setTitle("MiniGame: Pic, Pac, Toe");

        // ---- Main Layout ----
        BorderPane mainPane = new BorderPane();
        mainPane.setPadding(new Insets(20));

        // ---- Top: Title and Status ----
        Label titleLabel = new Label("MiniGame: Pic, Pac, Toe");
        titleLabel.getStyleClass().add("title");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        statusLabel = new Label("Player's Turn");
        statusLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: gray;");

        VBox topBox = new VBox(10, titleLabel, statusLabel);
        topBox.setAlignment(Pos.CENTER);
        mainPane.setTop(topBox);

        // ---- Center: Board Grid ----
        GridPane boardGrid = new GridPane();
        boardGrid.setHgap(10);
        boardGrid.setVgap(10);
        boardGrid.setAlignment(Pos.CENTER);

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                Button cellButton = new Button("");
                cellButton.setPrefSize(100, 100);
                cellButton.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
                final int r = row;
                final int c = col;
                cellButton.setOnAction(e -> handlePlayerMove(r, c));
                boardButtons[row][col] = cellButton;
                boardGrid.add(cellButton, col, row);
            }
        }
        mainPane.setCenter(boardGrid);

        // ---- Bottom: Control Buttons ----
        Button resetButton = new Button("Reset");
        resetButton.setStyle("-fx-font-size: 14px; -fx-padding: 5 15;");
        resetButton.setOnAction(e -> resetGame());

        Button backButton = new Button("Back");
        backButton.setStyle("-fx-font-size: 14px; -fx-padding: 5 15;");
        backButton.setOnAction(e -> close());

        HBox bottomBox = new HBox(10, resetButton, backButton);
        bottomBox.setAlignment(Pos.CENTER_RIGHT);
        bottomBox.setPadding(new Insets(20));
        mainPane.setBottom(bottomBox);

        // ---- Scene Setup ----
        Scene scene = new Scene(mainPane, 800, 600);
        scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
        setScene(scene);
    }

    /**
     * Sets the result handler callback.
     */
    public void setResultHandler(Consumer<String> handler) {
        this.resultHandler = handler;
    }

    /**
     * Handles the player's move.
     */
    private void handlePlayerMove(int row, int col) {
        if (gameOver) return;
        Button cell = boardButtons[row][col];
        if (!cell.getText().isEmpty()) return; // Cell already occupied

        cell.setText(PLAYER_SYMBOL);
        statusLabel.setText("Computer's Turn");

        // Check for a winner after player's move
        winner = checkWinner(); // modified by Adit to help fix bugs
        if (winner != null) {
            endGame(winner);
            return;
        }

        // If board is not full, let the computer move
        if (!isBoardFull()) {
            Platform.runLater(() -> {
                try {
                    Thread.sleep(500); // Simulate computer thinking time
                } catch (InterruptedException ignored) {}
                computerMove();
                winner = checkWinner();
                if (winner != null) {
                    endGame(winner);
                } else {
                    statusLabel.setText("Player's Turn");
                }
            });
        } else {
            endGame("Draw");
        }
    }

    /**
     * Computer makes a move by choosing a random empty cell.
     */
    private void computerMove() {
        List<int[]> emptyCells = new ArrayList<>();
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                if (boardButtons[row][col].getText().isEmpty()) {
                    emptyCells.add(new int[]{row, col});
                }
            }
        }
        if (emptyCells.isEmpty()) return;
        Random rand = new Random();
        int[] move = emptyCells.get(rand.nextInt(emptyCells.size()));
        boardButtons[move[0]][move[1]].setText(COMPUTER_SYMBOL);
    }

    /**
     * Checks the board for a winner.
     */
    private String checkWinner() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            // Check rows
            if (!boardButtons[i][0].getText().isEmpty() &&
                boardButtons[i][0].getText().equals(boardButtons[i][1].getText()) &&
                boardButtons[i][1].getText().equals(boardButtons[i][2].getText())) {
                return boardButtons[i][0].getText();
            }
            // Check columns
            if (!boardButtons[0][i].getText().isEmpty() &&
                boardButtons[0][i].getText().equals(boardButtons[1][i].getText()) &&
                boardButtons[1][i].getText().equals(boardButtons[2][i].getText())) {
                return boardButtons[0][i].getText();
            }
        }
        // Check diagonals
        if (!boardButtons[0][0].getText().isEmpty() &&
            boardButtons[0][0].getText().equals(boardButtons[1][1].getText()) &&
            boardButtons[1][1].getText().equals(boardButtons[2][2].getText())) {
            return boardButtons[0][0].getText();
        }
        if (!boardButtons[0][2].getText().isEmpty() &&
            boardButtons[0][2].getText().equals(boardButtons[1][1].getText()) &&
            boardButtons[1][1].getText().equals(boardButtons[2][0].getText())) {
            return boardButtons[0][2].getText();
        }
        return null;
    }

    /**
     * Checks if the board is full.
     */
    private boolean isBoardFull() {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                if (boardButtons[row][col].getText().isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Ends the game and displays the result.
     */
    private void endGame(String winner) {
        gameOver = true;
        String result;
        if (winner.equals(PLAYER_SYMBOL)) {
            result = "Player Wins!";
        } else if (winner.equals(COMPUTER_SYMBOL)) {
            result = "Computer Wins!";
        } else {
            result = "It's a Draw!";
        }
        statusLabel.setText(result);
        if (resultHandler != null) {
            resultHandler.accept(result);
        }
    }

    /**
     * Resets the game board.
     */
    private void resetGame() {
        gameOver = false;
        statusLabel.setText("Player's Turn");
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                boardButtons[row][col].setText("");
            }
        }
    }

    // ---- Main Method for Testing ----
    public static void main(String[] args) {
        Application.launch(TestMiniGameApp.class, args);
    }

    public static class TestMiniGameApp extends Application {
        @Override
        public void start(Stage primaryStage) {
            MiniGameScreen miniGameScreen = new MiniGameScreen();
            miniGameScreen.setResultHandler(result -> System.out.println("Mini-game result: " + result));
            miniGameScreen.show();
        }
    }
}