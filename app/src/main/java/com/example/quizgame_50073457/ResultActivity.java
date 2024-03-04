package com.example.quizgame_50073457;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;

import java.util.List;


public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);


        int score = getIntent().getIntExtra("score", 0);
        // Assuming score is passed as an extra in the intent

        updateScoreInSharedPreferences(score);
        TextView tvScore = findViewById(R.id.tvScore);
        tvScore.setText("Score: " + score);

        Button btnRetakeQuiz = findViewById(R.id.btnRetakeQuiz);
        btnRetakeQuiz.setOnClickListener(v -> {
            startActivity(new Intent(this, QuizActivity.class));
            finish();
        });

        Button btnExit = findViewById(R.id.btnExit);
        btnExit.setOnClickListener(v -> finish());

        Button btnViewQuizStats = findViewById(R.id.btnViewStat);
        btnViewQuizStats.setOnClickListener(v -> {
            startActivity(new Intent(this, QuizStatActivity.class));
        });
    }

    private void updateScoreInSharedPreferences(int score) {
        // Fetch current scores
        String scoresJson = PreferencesUtil.getScoresJson(this);
        Gson gson = new Gson();
        Type scoreListType = new TypeToken<ArrayList<Score>>(){}.getType();
        List<Score> scores = gson.fromJson(scoresJson, scoreListType);
        if (scores == null) scores = new ArrayList<>();

        // Add the new score
        String currentUsername = PreferencesUtil.getCurrentUsername(this);
        String timestamp = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date(System.currentTimeMillis()));
        scores.add(new Score(currentUsername, score, timestamp));


        // Save updated scores
        PreferencesUtil.saveScoresJson(this, gson.toJson(scores));
    }

}
