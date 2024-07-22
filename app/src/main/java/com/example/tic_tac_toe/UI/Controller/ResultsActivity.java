package com.example.tic_tac_toe.UI.Controller;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.tic_tac_toe.Data.DB.AppDatabase;
import com.example.tic_tac_toe.Data.DB.GameResult;
import com.example.tic_tac_toe.R;

import java.util.List;

public class ResultsActivity extends AppCompatActivity {
    private RecyclerView recyclerView; // משתנה לשמירת ה-RecyclerView
    private ResultsAdapter adapter; // משתנה לשמירת ה-Adapter של התוצאות
    private AppDatabase db; // משתנה לשמירת האובייקט של מסד הנתונים

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this); // הפעלת מצב Edge-to-Edge
        setContentView(R.layout.activity_results); // קביעת תצוגת ה-Layout

        // החלת חלון אינסטים להתמודדות עם סרגלי המערכת
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.results), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initialization(); // קריאה לפונקציה שמבצעת אתחולים
    }

    // פונקציה שמבצעת אתחולים
    private void initialization() {
        // אתחול ה-RecyclerView
        setRecyclerView(findViewById(R.id.recyclerView));
        getRecyclerView().setLayoutManager(new LinearLayoutManager(this)); // קביעת LayoutManager ל-RecyclerView

        // אתחול מסד הנתונים
        setDb(Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "game_results").build());

        // שליפת תוצאות בביצוע רקע
        new Thread(() -> {
            List<GameResult> results = getDb().gameResultDao().getAllResults(); // שליפת כל התוצאות ממסד הנתונים
            runOnUiThread(() -> {
                setAdapter(new ResultsAdapter(results)); // אתחול ה-Adapter עם התוצאות
                getRecyclerView().setAdapter(getAdapter()); // קביעת ה-Adapter ל-RecyclerView
            });
        }).start();

        // טיפול בלחצן החזרה
        findViewById(R.id.button_back).setOnClickListener(v -> finish()); // סיום הפעילות עם לחצן חזרה

        // טיפול בלחצן איפוס
        findViewById(R.id.button_reset).setOnClickListener(v -> {
            new Thread(() -> {
                getDb().gameResultDao().deleteAllResults(); // מחיקת כל התוצאות ממסד הנתונים
                runOnUiThread(() -> {
                    getAdapter().notifyDataSetChanged(); // עדכון ה-Adapter על שינויים בנתונים
                    recreate(); // אתחול מחדש של הפעילות כדי לרענן את ה-RecyclerView
                });
            }).start();
        });
    }

    // פונקציות גטרים וסטרים ל-RecyclerView, Adapter ומסד הנתונים
    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    public ResultsAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(ResultsAdapter adapter) {
        this.adapter = adapter;
    }

    public AppDatabase getDb() {
        return db;
    }

    public void setDb(AppDatabase db) {
        this.db = db;
    }
}
