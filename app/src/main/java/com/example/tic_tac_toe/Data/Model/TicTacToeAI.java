package com.example.tic_tac_toe.Data.Model;

import java.util.Random;

public class TicTacToeAI {
    private final Random random = new Random();

    // מחזיר מהלך לפי רמת הקושי שנבחרה
    public int getMove(char[] board, int level) {
        switch (level) {
            case 0: // רמה קלה: מהלך אקראי
                return getRandomMove(board);
            case 1: // רמה בינונית: מהלך בינוני
                return getMediumMove(board);
            case 2: // רמה קשה: מהלך חכם (אלגוריתם מינימקס)
                return getHardMove(board);
        }
        return -1; // במקרה של רמה לא מוכרת
    }

    // מחזיר מהלך אקראי בלוח המשחק
    private int getRandomMove(char[] board) {
        int move;
        do {
            move = getRandom().nextInt(9); // בוחר מקום אקראי בין 0 ל-8
        } while (board[move] != ' '); // מבטיח שהמקום פנוי
        return move;
    }

    // מחזיר מהלך בינוני בלוח המשחק
    private int getMediumMove(char[] board) {
        // קודם כל, נסה לנצח אם אפשר
        int move = findWinningMove(board, 'O');
        if (move != -1) return move;

        // לאחר מכן, חסום את היריב אם הוא עומד לנצח
        move = findWinningMove(board, 'X');
        if (move != -1) return move;

        // לאחר מכן, נסה לשחק במרכז אם הוא פנוי
        if (board[4] == ' ') return 4;

        // לאחר מכן, נסה לשחק באחת הפינות אם הן פנויות
        int[] corners = {0, 2, 6, 8};
        move = getRandomMoveFromList(board, corners);
        if (move != -1) return move;

        // אם כל השאר לא אפשריים, בחר מהלך אקראי
        return getRandomMove(board);
    }

    // מחזיר מהלך חכם בלוח המשחק
    private int getHardMove(char[] board) {
        return minimax(board, 'O', true).move;
    }

    // מחפש מהלך מנצח עבור השחקן הנתון
    private int findWinningMove(char[] board, char player) {
        for (int i = 0; i < board.length; i++) {
            if (board[i] == ' ') {
                board[i] = player;
                if (isWinning(board, player)) {
                    board[i] = ' ';
                    return i;
                }
                board[i] = ' ';
            }
        }
        return -1;
    }

    // בודק אם השחקן הנתון ניצח
    private boolean isWinning(char[] board, char player) {
        int[][] winCombinations = {
                {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, // שורות
                {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, // עמודות
                {0, 4, 8}, {2, 4, 6}             // אלכסונים
        };

        for (int[] combo : winCombinations) {
            if (board[combo[0]] == player && board[combo[1]] == player && board[combo[2]] == player) {
                return true;
            }
        }
        return false;
    }

    // בודק אם הלוח מלא
    private boolean isBoardFull(char[] board) {
        for (char c : board) {
            if (c == ' ') return false;
        }
        return true;
    }

    // מחזיר מהלך אקראי מרשימת מהלכים נתונה
    private int getRandomMoveFromList(char[] board, int[] moves) {
        int[] validMoves = new int[moves.length];
        int count = 0;

        for (int move : moves) {
            if (board[move] == ' ') {
                validMoves[count++] = move;
            }
        }

        if (count == 0) return -1;

        return validMoves[getRandom().nextInt(count)];
    }

    // אלגוריתם מינימקס: מחזיר מהלך עם הציון הטוב ביותר
    private MoveScore minimax(char[] board, char player, boolean isMaximizing) {
        char opponent = (player == 'O') ? 'X' : 'O';

        // בדיקת תנאי ניצחון או תיקו
        if (isWinning(board, 'O')) return new MoveScore(1);
        if (isWinning(board, 'X')) return new MoveScore(-1);
        if (isBoardFull(board)) return new MoveScore(0);

        MoveScore bestMove = new MoveScore();
        bestMove.score = isMaximizing ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        // חיפוש מהלך מיטבי לכל תא פנוי
        for (int i = 0; i < board.length; i++) {
            if (board[i] == ' ') {
                board[i] = player;
                MoveScore currentMove = minimax(board, opponent, !isMaximizing);
                board[i] = ' ';
                currentMove.move = i;

                if (isMaximizing) {
                    if (currentMove.score > bestMove.score) {
                        bestMove = currentMove;
                    }
                } else {
                    if (currentMove.score < bestMove.score) {
                        bestMove = currentMove;
                    }
                }
            }
        }
        return bestMove;
    }

    // מחזיר את אובייקט ה-Random
    public Random getRandom() {
        return random;
    }

    // מחלקה פנימית עבור מהלך וציון של המהלך
    private static class MoveScore {
        int move;
        int score;

        MoveScore() {
        }

        MoveScore(int score) {
            this.score = score;
        }
    }
}
