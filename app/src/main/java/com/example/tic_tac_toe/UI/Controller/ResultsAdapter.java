package com.example.tic_tac_toe.UI.Controller;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tic_tac_toe.Data.DB.GameResult;
import com.example.tic_tac_toe.R;

import java.util.List;

public class ResultsAdapter extends RecyclerView.Adapter<ResultsAdapter.ViewHolder> {
    private final List<GameResult> results;

    // Constructor: מקבל רשימה של תוצאות המשחק
    public ResultsAdapter(List<GameResult> results) {
        this.results = results;
    }

    @NonNull
    @Override
    // onCreateViewHolder: יוצר ViewHolder חדש כאשר אין מספיק ViewHolders ממוחזרים
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for an individual result item
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_result, parent, false);
        return new ViewHolder(view);
    }

    @Override
    // onBindViewHolder: קושר את הנתונים ל-ViewHolder מסוים במיקום נתון
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get the GameResult at the specified position
        GameResult result = getResults().get(position);

        // Set the text views with the data from the GameResult
        holder.firstNameTextView.setText(result.getFirstName());
        holder.lastNameTextView.setText(result.getLastName());
        holder.difficultyTextView.setText(result.getDifficulty());
        holder.dateTextView.setText(result.getDate());
        holder.resultTextView.setText(result.getResult());
    }

    @Override
    // getItemCount: מחזיר את מספר הפריטים במתאם
    public int getItemCount() {
        return getResults().size();
    }

    // getResults: מחזיר את רשימת תוצאות המשחק
    public List<GameResult> getResults() {
        return results;
    }

    // ViewHolder: מחזיק את התצוגות של פריט בודד ברשימה
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView firstNameTextView;
        public TextView lastNameTextView;
        public TextView difficultyTextView;
        public TextView dateTextView;
        public TextView resultTextView;

        // Constructor: מאתחל את כל התצוגות המרכיבות פריט בודד ברשימה
        public ViewHolder(View itemView) {
            super(itemView);
            firstNameTextView = itemView.findViewById(R.id.firstName);
            lastNameTextView = itemView.findViewById(R.id.lastName);
            difficultyTextView = itemView.findViewById(R.id.difficulty);
            dateTextView = itemView.findViewById(R.id.date);
            resultTextView = itemView.findViewById(R.id.result);
        }
    }
}
