package com.example.tic_tac_toe.Data.DB;

import androidx.room.Database;
import androidx.room.RoomDatabase;

// מסמן את המחלקה הזו כמסד נתונים של Room עם הישות GameResult וגרסת מסד הנתונים היא 1
@Database(entities = {GameResult.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    // מחזיר מופע של GameResultDao, הממשק המוגדר עבור פעולות מסד הנתונים על הטבלה
    public abstract GameResultDao gameResultDao();
}
