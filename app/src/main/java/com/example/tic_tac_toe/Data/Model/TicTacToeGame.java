package com.example.tic_tac_toe.Data.Model;

import java.util.Arrays;

public class TicTacToeGame {
    private final char[] board; // מערך הלוח של המשחק
    private char currentPlayer; // משתנה לשחקן הנוכחי

    // קונסטרקטור אתחלתי
    public TicTacToeGame() {
        board = new char[9]; // אתחול מערך הלוח עם 9 תאים
        reset(); // קריאה לפונקציה reset() כדי לאתחל את המשחק
    }

    // מחזיר את הלוח
    public char[] getBoard() {
        return board;
    }

    // מחזיר את השחקן הנוכחי
    public char getCurrentPlayer() {
        return currentPlayer;
    }

    // מאתחל את הלוח והשחקן הנוכחי
    public void reset() {
        Arrays.fill(getBoard(), ' '); // ממלא את הלוח ברווחים (תאים ריקים)
        setCurrentPlayer('O'); // מגדיר את השחקן הנוכחי ל-'O'
    }

    // בודק אם המהלך תקף
    public boolean isMoveValid(int move) {
        return getBoard()[move] == ' '; // בודק אם התא פנוי
    }

    // מבצע מהלך
    public void makeMove(int move) {
        if (isMoveValid(move)) { // בודק אם המהלך תקף
            getBoard()[move] = getCurrentPlayer(); // ממלא את התא הנבחר בשחקן הנוכחי
            // מחליף את השחקן הנוכחי לאחר ביצוע מהלך
            setCurrentPlayer((getCurrentPlayer() == 'O') ? 'X' : 'O');
        }
    }

    // בודק אם המשחק הסתיים (ניצחון או תיקו)
    public boolean isGameOver() {
        return isWinning('O') || isWinning('X') || isDraw(); // אם אחד השחקנים ניצח או תיקו
    }

    // בודק אם יש תיקו
    public boolean isDraw() {
        for (char c : getBoard()) {
            if (c == ' ') return false; // אם יש תא ריק, המשחק לא תיקו
        }
        return !isWinning('O') && !isWinning('X'); // אם אין ניצחון לשני השחקנים, זה תיקו
    }

    // בודק אם שחקן מסוים ניצח
    public boolean isWinning(char player) {
        int[][] winCombinations = {
                {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, // שורות
                {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, // עמודות
                {0, 4, 8}, {2, 4, 6}             // אלכסונים
        };

        for (int[] combo : winCombinations) {
            // אם כל התאים באחת מהקומבינציות שייכים לשחקן, הוא ניצח
            if (getBoard()[combo[0]] == player && getBoard()[combo[1]] == player && getBoard()[combo[2]] == player) {
                return true;
            }
        }
        return false; // אם לא נמצא ניצחון
    }

    // מגדיר את השחקן הנוכחי
    public void setCurrentPlayer(char currentPlayer) {
        this.currentPlayer = currentPlayer;
    }
}
