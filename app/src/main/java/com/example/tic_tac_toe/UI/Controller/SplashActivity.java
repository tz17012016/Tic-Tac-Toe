package com.example.tic_tac_toe.UI.Controller;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tic_tac_toe.R;

@SuppressLint("CustomSplashScreen") // מבטלת את אזהרת היישום של מסך פתיחה מותאם אישית
public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // קריאה לשיטת onCreate של מחלקת האב (AppCompatActivity)
        setContentView(R.layout.activity_splash); // קובעת את תצוגת הפעילות ל-layout activity_splash

        // קובעת מאזין כדי להחיל חישוב של שוליים אוטומטיים בהתאם לשוליים של מערכת ההפעלה
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.splash), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom); // קובעת שוליים בהתאם לשוליים של מערכת ההפעלה
            return insets;
        });

        ImageView imageView = findViewById(R.id.tic_tac_toe_image); // מוצא את ImageView לפי ID ומאחסן אותו במשתנה
        Button startGameButton = findViewById(R.id.button_start_game); // מוצא את Button לפי ID ומאחסן אותו במשתנה

        // טוען את האנימציה מסוג סיבוב
        Animation rotateAnimation = AnimationUtils.loadAnimation(this, R.anim.rotate_animation);
        imageView.startAnimation(rotateAnimation); // מתחיל את האנימציה על ה-ImageView

        startGameButton.setOnClickListener(v -> {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class); // יוצר Intent כדי לפתוח את MainActivity
            startActivity(intent); // מתחיל את הפעילות החדשה
            finish(); // סוגר את SplashActivity כדי שהמשתמש לא יוכל לחזור אליו
        });
    }
}
