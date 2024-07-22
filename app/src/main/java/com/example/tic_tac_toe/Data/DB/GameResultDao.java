package com.example.tic_tac_toe.Data.DB;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

// מסמן שהאינטרפייס הזה הוא DAO (Data Access Object) עבור Room
@Dao
public interface GameResultDao {

    // שאילתת SQL שמחזירה את כל הרשומות מטבלת game_result
    @Query("SELECT * FROM game_result")
    List<GameResult> getAllResults(); // מחזירה רשימה של כל תוצאות המשחק

    // מגדיר פעולה של הכנסת רשומה חדשה לטבלת game_result
    @Insert
    void insert(GameResult gameResult); // מכניס אובייקט חדש של GameResult לטבלה

    // שאילתת SQL שמוחקת את כל הרשומות מטבלת game_result
    @Query("DELETE FROM game_result")
    void deleteAllResults(); // מוחק את כל התוצאות מהטבלה
}
