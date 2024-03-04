package com.example.quizgame_50073457;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
public class RulesActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);
        // Layout contains TextView with rules

        Button takeQuiz = findViewById(R.id.takeQuiz);
        takeQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RulesActivity.this, QuizActivity.class));
            }
        });

        Button btnViewResults = findViewById(R.id.btnViewResults);
        btnViewResults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RulesActivity.this, ResultActivity.class));
            }
        });

        Button btnViewStat = findViewById(R.id.btnViewStat);
        btnViewResults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RulesActivity.this, QuizStatActivity.class));
            }
        });

    }
}
