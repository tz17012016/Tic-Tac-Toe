//package com.example.tic_tac_toe.UI.Controller;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.os.Handler;
//import android.text.Editable;
//import android.text.InputFilter;
//import android.text.TextUtils;
//import android.text.TextWatcher;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.LinearLayout;
//import android.widget.RadioGroup;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AlertDialog;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.graphics.Insets;
//import androidx.core.view.ViewCompat;
//import androidx.core.view.WindowInsetsCompat;
//import androidx.gridlayout.widget.GridLayout;
//import androidx.room.Room;
//
//import com.example.tic_tac_toe.Data.DB.AppDatabase;
//import com.example.tic_tac_toe.Data.DB.GameResult;
//import com.example.tic_tac_toe.Data.Model.TicTacToeAI;
//import com.example.tic_tac_toe.Data.Model.TicTacToeGame;
//import com.example.tic_tac_toe.R;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.Locale;
//
//public class MainActivity extends AppCompatActivity {
//    private TicTacToeGame game;
//    private TicTacToeAI ai;
//    private int difficultyLevel;
//    private char currentPlayer;
//    private Button[] buttons;
//    private AppDatabase db;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
//        initialization();
//    }
//
//    private void initialization() {
//        setDb(Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "game_results")
//                .allowMainThreadQueries()
//                .build());
//
//        setAi(new TicTacToeAI());
//        setGame(new TicTacToeGame());
//        setButtons(new Button[9]);
//
//        GridLayout gameBoard = findViewById(R.id.game_board);
//        for (int i = 0; i < 9; i++) {
//            Button button = (Button) gameBoard.getChildAt(i);
//            getButtons()[i] = button;
//            final int finalI = i;
//            button.setOnClickListener(v -> handlePlayerMove(finalI));
//        }
//
//        RadioGroup difficultyRadioGroup = findViewById(R.id.difficulty_radio_group);
//        difficultyRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
//            if (checkedId == R.id.radio_easy) {
//                setDifficultyLevel(0);
//            } else if (checkedId == R.id.radio_medium) {
//                setDifficultyLevel(1);
//            } else if (checkedId == R.id.radio_hard) {
//                setDifficultyLevel(2);
//            }
//        });
//
//        Button startGameButton = findViewById(R.id.button_start_game);
//        startGameButton.setOnClickListener(v -> resetGame());
//
//        Button viewResultsButton = findViewById(R.id.button_view_results);
//        viewResultsButton.setOnClickListener(v -> {
//            Intent intent = new Intent(MainActivity.this, ResultsActivity.class);
//            startActivity(intent);
//        });
//
//        resetGame();
//    }
//
//    private void handlePlayerMove(int move) {
//        if (getGame().isGameOver() || !getGame().isMoveValid(move)) return;
//
//        getGame().makeMove(move);
//        updateUI();
//
//        if (getGame().isGameOver()) {
//            handleGameOver();
//            return;
//        }
//
//        setCurrentPlayer('X');
//
//        new Handler().postDelayed(() -> {
//            int aiMove = getAi().getMove(getGame().getBoard(), getDifficultyLevel());
//            getGame().makeMove(aiMove);
//            updateUI();
//
//            if (getGame().isGameOver()) {
//                handleGameOver();
//            }
//
//            setCurrentPlayer('O');
//        }, 500);
//    }
//
//    private void updateUI() {
//        char[] board = getGame().getBoard();
//        for (int i = 0; i < board.length; i++) {
//            getButtons()[i].setText(String.valueOf(board[i]));
//            getButtons()[i].setEnabled(board[i] == ' ');
//        }
//    }
//
//    private void handleGameOver() {
//        String message;
//        if (getGame().isDraw()) {
//            message = "תיקו!";
//        } else {
//            message = (getCurrentPlayer() == 'O' ? "שחקן" : "מחשב") + " ניצח!";
//        }
//
//        new AlertDialog.Builder(this)
//                .setTitle("סיום משחק")
//                .setMessage(message)
//                .setPositiveButton("אישור", (dialog, which) -> {
//
//                    if (message.equals("שחקן ניצח!")) {
//
//                        showSaveDialog(message);
//                    }
//
//                })
//                .show();
//    }
//
//
//    private void showSaveDialog(String result) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("הזן את פרטיך");
//
//        // Create a LinearLayout to hold the EditText fields
//        LinearLayout layout = new LinearLayout(this);
//        layout.setOrientation(LinearLayout.VERTICAL);
//
//        // Create EditText fields dynamically
//        final EditText firstNameInput = new EditText(this);
//        firstNameInput.setHint("שם פרטי");
//        firstNameInput.setFilters(new InputFilter[]{new InputFilter.LengthFilter(8)});
//
//        final EditText lastNameInput = new EditText(this);
//        lastNameInput.setHint("שם משפחה");
//        lastNameInput.setFilters(new InputFilter[]{new InputFilter.LengthFilter(8)});
//
//        // Add EditText fields to the layout
//        layout.addView(firstNameInput);
//        layout.addView(lastNameInput);
//        builder.setView(layout);
//
//        // Set the buttons for the dialog
//        builder.setPositiveButton("אישור", null); // Set to null to disable initially
//        builder.setNegativeButton("ביטול", (dialog, which) -> dialog.dismiss());
//
//        // Create and show the dialog
//        AlertDialog dialog = builder.create();
//        dialog.setOnShowListener(d -> {
//            Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
//
//            TextWatcher textWatcher = new TextWatcher() {
//                @Override
//                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                }
//
//                @Override
//                public void onTextChanged(CharSequence s, int start, int before, int count) {
//                    validateInputs(firstNameInput, lastNameInput, positiveButton);
//                }
//
//                @Override
//                public void afterTextChanged(Editable s) {
//                }
//            };
//
//            firstNameInput.addTextChangedListener(textWatcher);
//            lastNameInput.addTextChangedListener(textWatcher);
//
//            if (positiveButton != null) {
//                positiveButton.setOnClickListener(v -> {
//                    if (validateInputs(firstNameInput, lastNameInput, positiveButton)) {
//                        String firstName = firstNameInput.getText().toString();
//                        String lastName = lastNameInput.getText().toString();
//                        String currentDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
//                        String difficulty = getDifficultyLevelString(getDifficultyLevel());
//                        GameResult gameResult = new GameResult(firstName, lastName, difficulty, currentDate, result);
//                        saveGameResult(gameResult);
//                        dialog.dismiss(); // Close dialog after saving
//                    } else {
//                        Toast.makeText(MainActivity.this, "שדה זה לא יכול להיות ריק", Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//
//            // Initial validation to enable/disable the button
//            assert positiveButton != null;
//            validateInputs(firstNameInput, lastNameInput, positiveButton);
//        });
//
//        dialog.show();
//    }
//
//    private boolean validateInputs(EditText firstNameInput, EditText lastNameInput, Button positiveButton) {
//        boolean isFirstNameValid = firstNameInput.getText().length() >= 2 && firstNameInput.getText().length() <= 8;
//        boolean isLastNameValid = lastNameInput.getText().length() >= 2 && lastNameInput.getText().length() <= 8;
//
//        positiveButton.setEnabled(isFirstNameValid && isLastNameValid);
//
//        return isFirstNameValid && isLastNameValid;
//    }
//
//
//    private void validateInputs(EditText firstNameInput, EditText lastNameInput, AlertDialog dialog) {
//        // Check if inputs are empty or not
//        boolean isFirstNameValid = !TextUtils.isEmpty(firstNameInput.getText());
//        boolean isLastNameValid = !TextUtils.isEmpty(lastNameInput.getText());
//
//        // Enable/Disable the "אישור" button based on the input validity
//        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
//        if (positiveButton != null) {
//            positiveButton.setEnabled(isFirstNameValid && isLastNameValid);
//        }
//    }
//
//    private void saveGameResult(GameResult gameResult) {
//        new Thread(() -> getDb().gameResultDao().insert(gameResult)).start();
//    }
//
//    private String getDifficultyLevelString(int level) {
//        switch (level) {
//            case 0:
//                return "קל";
//            case 1:
//                return "בינוני";
//            case 2:
//                return "קשה";
//            default:
//                return "לא ידוע";
//        }
//    }
//
//    private void resetGame() {
//        getGame().reset();
//        for (Button button : getButtons()) {
//            button.setText("");
//            button.setEnabled(true);
//        }
//        setCurrentPlayer('O');
//    }
//
//    public TicTacToeGame getGame() {
//        return game;
//    }
//
//    public void setGame(TicTacToeGame game) {
//        this.game = game;
//    }
//
//    public TicTacToeAI getAi() {
//        return ai;
//    }
//
//    public void setAi(TicTacToeAI ai) {
//        this.ai = ai;
//    }
//
//    public int getDifficultyLevel() {
//        return difficultyLevel;
//    }
//
//    public void setDifficultyLevel(int difficultyLevel) {
//        this.difficultyLevel = difficultyLevel;
//    }
//
//    public char getCurrentPlayer() {
//        return currentPlayer;
//    }
//
//    public void setCurrentPlayer(char currentPlayer) {
//        this.currentPlayer = currentPlayer;
//    }
//
//    public Button[] getButtons() {
//        return buttons;
//    }
//
//    public void setButtons(Button[] buttons) {
//        this.buttons = buttons;
//    }
//
//    public AppDatabase getDb() {
//        return db;
//    }
//
//    public void setDb(AppDatabase db) {
//        this.db = db;
//    }
//}
package com.example.tic_tac_toe.UI.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;
import androidx.room.Room;

import com.example.tic_tac_toe.Data.DB.AppDatabase;
import com.example.tic_tac_toe.Data.DB.GameResult;
import com.example.tic_tac_toe.Data.Model.TicTacToeAI;
import com.example.tic_tac_toe.Data.Model.TicTacToeGame;
import com.example.tic_tac_toe.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private TicTacToeGame game;
    private TicTacToeAI ai;
    private int difficultyLevel;
    private char currentPlayer;
    private Button[] buttons;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialization();
    }

    private void initialization() {
        // Initialize the database
        setDb(Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "game_results")
                .allowMainThreadQueries()
                .build());

        // Initialize the AI and game objects
        setAi(new TicTacToeAI());
        setGame(new TicTacToeGame());
        setButtons(new Button[9]);

        // Set up the game board
        GridLayout gameBoard = findViewById(R.id.game_board);
        for (int i = 0; i < 9; i++) {
            Button button = (Button) gameBoard.getChildAt(i);
            getButtons()[i] = button;
            final int finalI = i;
            button.setOnClickListener(v -> handlePlayerMove(finalI));
        }

        // Set up the difficulty radio group
        RadioGroup difficultyRadioGroup = findViewById(R.id.difficulty_radio_group);
        difficultyRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.radio_easy) {
                setDifficultyLevel(0);
            } else if (checkedId == R.id.radio_medium) {
                setDifficultyLevel(1);
            } else if (checkedId == R.id.radio_hard) {
                setDifficultyLevel(2);
            }
        });

        // Set up the start game button
        Button startGameButton = findViewById(R.id.button_start_game);
        startGameButton.setOnClickListener(v -> resetGame());

        // Set up the view results button
        Button viewResultsButton = findViewById(R.id.button_view_results);
        viewResultsButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ResultsActivity.class);
            startActivity(intent);
        });

        // Start a new game
        resetGame();
    }

    private void handlePlayerMove(int move) {
        // Check if the game is over or if the move is invalid
        if (getGame().isGameOver() || !getGame().isMoveValid(move)) return;

        // Make the player's move
        getGame().makeMove(move);
        updateUI();

        // Check if the game is over after the player's move
        if (getGame().isGameOver()) {
            handleGameOver();
            return;
        }

        // Switch to the AI player
        setCurrentPlayer('X');

        // Make the AI's move after a short delay
        new Handler().postDelayed(() -> {
            int aiMove = getAi().getMove(getGame().getBoard(), getDifficultyLevel());
            getGame().makeMove(aiMove);
            updateUI();

            // Check if the game is over after the AI's move
            if (getGame().isGameOver()) {
                handleGameOver();
            }

            // Switch back to the human player
            setCurrentPlayer('O');
        }, 500);
    }

    private void updateUI() {
        // Update the UI to reflect the current state of the board
        char[] board = getGame().getBoard();
        for (int i = 0; i < board.length; i++) {
            getButtons()[i].setText(String.valueOf(board[i]));
            getButtons()[i].setEnabled(board[i] == ' ');
        }
    }

    private void handleGameOver() {
        // Determine the game over message
        String message;
        if (getGame().isDraw()) {
            message = "תיקו!";
        } else {
            message = (getCurrentPlayer() == 'O' ? "שחקן" : "מחשב") + " ניצח!";
        }

        // Show an alert dialog with the game over message
        new AlertDialog.Builder(this)
                .setTitle("סיום משחק")
                .setMessage(message)
                .setPositiveButton("אישור", (dialog, which) -> {
                    if (message.equals("שחקן ניצח!")) {
                        showSaveDialog(message);
                    }
                })
                .show();
    }

    private void showSaveDialog(String result) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("הזן את פרטיך");

        // Create a LinearLayout to hold the EditText fields
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        // Create EditText fields dynamically
        final EditText firstNameInput = new EditText(this);
        firstNameInput.setHint("שם פרטי");
        firstNameInput.setFilters(new InputFilter[]{new InputFilter.LengthFilter(8)});

        final EditText lastNameInput = new EditText(this);
        lastNameInput.setHint("שם משפחה");
        lastNameInput.setFilters(new InputFilter[]{new InputFilter.LengthFilter(8)});

        // Add EditText fields to the layout
        layout.addView(firstNameInput);
        layout.addView(lastNameInput);
        builder.setView(layout);

        // Set the buttons for the dialog
        builder.setPositiveButton("אישור", null); // Set to null to disable initially
        builder.setNegativeButton("ביטול", (dialog, which) -> dialog.dismiss());

        // Create and show the dialog
        AlertDialog dialog = builder.create();
        dialog.setOnShowListener(d -> {
            Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);

            // Add TextWatchers to validate input fields
            TextWatcher textWatcher = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    validateInputs(firstNameInput, lastNameInput, positiveButton);
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            };

            firstNameInput.addTextChangedListener(textWatcher);
            lastNameInput.addTextChangedListener(textWatcher);

            // Handle the positive button click
            if (positiveButton != null) {
                positiveButton.setOnClickListener(v -> {
                    if (validateInputs(firstNameInput, lastNameInput, positiveButton)) {
                        String firstName = firstNameInput.getText().toString();
                        String lastName = lastNameInput.getText().toString();
                        String currentDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
                        String difficulty = getDifficultyLevelString(getDifficultyLevel());
                        GameResult gameResult = new GameResult(firstName, lastName, difficulty, currentDate, result);
                        saveGameResult(gameResult);
                        dialog.dismiss(); // Close dialog after saving
                    } else {
                        Toast.makeText(MainActivity.this, "שדה זה לא יכול להיות ריק", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            // Initial validation to enable/disable the button
            assert positiveButton != null;
            validateInputs(firstNameInput, lastNameInput, positiveButton);
        });

        dialog.show();
    }

    private boolean validateInputs(EditText firstNameInput, EditText lastNameInput, Button positiveButton) {
        // Validate that both inputs are between 2 and 8 characters
        boolean isFirstNameValid = firstNameInput.getText().length() >= 2 && firstNameInput.getText().length() <= 8;
        boolean isLastNameValid = lastNameInput.getText().length() >= 2 && lastNameInput.getText().length() <= 8;

        // Enable the positive button if both inputs are valid
        positiveButton.setEnabled(isFirstNameValid && isLastNameValid);

        return isFirstNameValid && isLastNameValid;
    }

    private void validateInputs(EditText firstNameInput, EditText lastNameInput, AlertDialog dialog) {
        // Check if inputs are empty or not
        boolean isFirstNameValid = !TextUtils.isEmpty(firstNameInput.getText());
        boolean isLastNameValid = !TextUtils.isEmpty(lastNameInput.getText());

        // Enable/Disable the "אישור" button based on the input validity
        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        if (positiveButton != null) {
            positiveButton.setEnabled(isFirstNameValid && isLastNameValid);
        }
    }

    private void saveGameResult(GameResult gameResult) {
        // Save the game result in a background thread
        new Thread(() -> getDb().gameResultDao().insert(gameResult)).start();
    }

    private String getDifficultyLevelString(int level) {
        // Convert the difficulty level to a string
        switch (level) {
            case 0:
                return "קל";
            case 1:
                return "בינוני";
            case 2:
                return "קשה";
            default:
                return "לא ידוע";
        }
    }

    private void resetGame() {
        // Reset the game and update the UI
        getGame().reset();
        for (Button button : getButtons()) {
            button.setText("");
            button.setEnabled(true);
        }
        setCurrentPlayer('O');
    }

    // Getters and setters for the game, AI, difficulty level, current player, buttons, and database

    public TicTacToeGame getGame() {
        return game;
    }

    public void setGame(TicTacToeGame game) {
        this.game = game;
    }

    public TicTacToeAI getAi() {
        return ai;
    }

    public void setAi(TicTacToeAI ai) {
        this.ai = ai;
    }

    public int getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(int difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public char getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(char currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public Button[] getButtons() {
        return buttons;
    }

    public void setButtons(Button[] buttons) {
        this.buttons = buttons;
    }

    public AppDatabase getDb() {
        return db;
    }

    public void setDb(AppDatabase db) {
        this.db = db;
    }
}
