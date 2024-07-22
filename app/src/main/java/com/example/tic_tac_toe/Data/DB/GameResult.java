package com.example.tic_tac_toe.Data.DB;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

// מסמן שהמחלקה הזאת היא ישות של Room ותייצג טבלה בשם "game_result"
@Entity(tableName = "game_result")
public class GameResult {

    // מסמן את השדה הזה כמפתח ראשי בטבלה, והערך שלו ייווצר אוטומטית (autoGenerate = true)
    @PrimaryKey(autoGenerate = true)
    private int id;

    // שדות המייצגים עמודות בטבלה
    private String firstName;
    private String lastName;
    private String difficulty;
    private String date;
    private String result;

    // קונסטרקטור שמקבל את הערכים ההתחלתיים של השדות (ללא id כי הוא נוצר אוטומטית)
    public GameResult(String firstName, String lastName, String difficulty, String date, String result) {
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setDifficulty(difficulty);
        this.setDate(date);
        this.setResult(result);
    }

    // גטרים וסטרים עבור כל שדה במחלקה

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
