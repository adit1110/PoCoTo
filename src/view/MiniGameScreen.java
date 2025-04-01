/**
 * MiniGameScreen.java
 * 
 * This class implements a mini-game screen for the virtual pet game "PoCoTo".
 * The mini-game is a Tic Tac Toe variant using bear-themed symbols: "Pic" (player) and "Pac" (computer).
 * It handles player moves, makes computer moves, checks for win conditions or draw,
 * and communicates the final result via a result handler callback.
 * 
 * @author Jeremy Ro
 */

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
import javafx.scene.control.Alert; // added by Adit
import javafx.application.Platform; // added by Adit

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

/**
 * MiniGameScreen class represents the mini-game screen for the Tic Tac Toe variant.
 * It extends the Stage class to create a new window for the game.
 * The game allows a player to play against a computer in a 3x3 grid.
 */

public class MiniGameScreen extends Stage {

    private static final int BOARD_SIZE = 3;
    private Button[][] boardButtons = new Button[BOARD_SIZE][BOARD_SIZE];
    private static final String PLAYER_SYMBOL = "Pic";
    private static final String COMPUTER_SYMBOL = "Pac";
    private Consumer<String> resultHandler;
    private boolean gameOver = false;
    private String winner; // added by Adit

    private Label statusLabel; // Displays game status (e.g., "Player's Turn", "Computer Wins")

    /**
     * Constructs a new MiniGameScreen, initializing the UI elements and event handling.
     */
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

        // Initalize board buttons
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
        Scene scene = new Scene(mainPane, 800, 640);
        var css = getClass().getResource("/styles.css"); // added by Adit to add put the styles in
        if (css != null) {
            scene.getStylesheets().add(css.toExternalForm());
        } else {
            System.err.println("Minigame Screen: styles.css not found.");
        }
        setScene(scene);
    }

    /**
     * Sets the result handler callback.
     * When the mini-game ends, the handler is called with a result string:
     * "Player Win", "Computer Wins", or "Draw".
     * 
     * @param handler Consumer to process the result.
     */

    public void setResultHandler(Consumer<String> handler) {
        this.resultHandler = handler;
    }

    /**
     * Handles the player's move when a cell is clicked.
     * Checks if the move is valid, marks the cell, and evaluates game state.
     *
     * @param row the row index of the clicked cell.
     * @param col the column index of the clicked cell.
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
     * 
     * @return the symbol ("Pic" or "Pac") of the winner, "Draw" if board is full with no winner, or null if the game should continue.
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
     * 
     * @return true if the board is full; false otherwise.
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
     * Ends the game by setting the game over flag, printing the result, and calling the result handler callback.
     *
     * @param winner the symbol of the winner ("Pic", "Pac", or "Draw").
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

        // Show alert popup on game screen (added by Adit)

        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Game Over");
            alert.setHeaderText(null);
            alert.setContentText(result);
            alert.showAndWait();
        });
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

    /**
     * Main method for standalong testing.
     * 
     * @param args command line arguments.
     */
    public static void main(String[] args) {
        Application.launch(TestMiniGameApp.class, args);
    }

    /**
     * Inner class for launching MiniGameScreen
     */
    public static class TestMiniGameApp extends Application {
        @Override
        public void start(Stage primaryStage) {
            MiniGameScreen miniGameScreen = new MiniGameScreen();
            miniGameScreen.setResultHandler(result -> System.out.println("Mini-game result: " + result));
            miniGameScreen.show();
        }
    }
}