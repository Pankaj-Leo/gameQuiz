package com.example.quizgame_50073457;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class QuizStatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_stat);

        List<Score> scores = loadScores();
        displayTopScores(scores);
        displayRecentScores(scores);
    }

    private List<Score> loadScores() {
        String scoresJson = PreferencesUtil.getScoresJson(this);
        Type scoreListType = new TypeToken<List<Score>>(){}.getType();
        return new Gson().fromJson(scoresJson, scoreListType);
    }

    private void displayTopScores(List<Score> scores) {
        List<String> topScores = scores.stream()
                .sorted(Collections.reverseOrder(Comparator.comparing(Score::getScore)))
                .limit(5)
                .map(score -> String.format("%s - %d", score.getUsername(), score.getScore()))
                .collect(Collectors.toList());

        ListView topScoresListView = findViewById(R.id.topScoresListView);
        topScoresListView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, topScores));
    }

    private void displayRecentScores(List<Score> scores) {
        String currentUsername = PreferencesUtil.getCurrentUsername(this);
        List<String> recentScores = scores.stream()
                .filter(score -> score.getUsername().equals(currentUsername))
                .sorted(Collections.reverseOrder(Comparator.comparing(Score::getTimestamp)))
                .limit(5)
                .map(score -> String.format("%s - %d", score.getTimestamp(), score.getScore()))
                .collect(Collectors.toList());

        ListView recentScoresListView = findViewById(R.id.recentScoresListView);
        recentScoresListView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, recentScores));
    }
}
