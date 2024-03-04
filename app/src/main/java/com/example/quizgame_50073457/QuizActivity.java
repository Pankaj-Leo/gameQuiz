package com.example.quizgame_50073457;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuizActivity extends AppCompatActivity {
    private List<Question> questions = new ArrayList<>();
    private int currentQuestionIndex = 0;
    private int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        List<Question> questions = new ArrayList<>();
        questions.add(new Question("What hand beats a full house in texas holdem poker?", Arrays.asList("Flush", "Three of a Kind", "Straight", "Four of a Kind"), "Four of a Kind"));
        questions.add(new Question("What is probability of getting one King out of a random draw?", Arrays.asList("1/52", "4/52", "1/13", "1"), Arrays.asList( "4/52", "1/13")));
        questions.add(new Question("What do you mean by work check in poker?", Arrays.asList("Check your Phone for text", "See other peoples card", "Be in the game when bets are matched, without placing any new bet", "Bet all your chips on the hand"), "Be in the game when bets are matched, without placing any new bet"));
        questions.add(new Question("What is a flush in poker?", Arrays.asList("Flush all cards in table", "consecutive cards", "cards of same suit", "cards of same number"), "cards of same suit"));
        questions.add(new Question("What is a river in poker?", Arrays.asList("Hudson river", "Last card to be drawn in the community cards", "Set of cards with same suit", "Friend of Big Blind"), "Last card to be drawn in the community cards"));


        String jsonQuestions = Question.questionsToJson(questions);
        PreferencesUtil.saveQuestionsJson(getApplicationContext(), jsonQuestions);

        loadQuestions();

        if (!questions.isEmpty()) {
            displayQuestion(currentQuestionIndex);
        } else {
            showError("No questions available.");
        }

        Button btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(v -> submitAnswer());
    }

    private void loadQuestions() {
        String jsonQuestions = PreferencesUtil.getQuestionsJson(this);
        if (!jsonQuestions.isEmpty()) {
            questions = Question.jsonToQuestions(jsonQuestions);
            if (questions == null || questions.isEmpty()) {
                showError("Failed to load questions.");
            } else {
                displayQuestion(currentQuestionIndex);
            }
        } else {
            showError("No questions available.");
        }
    }

    private void displayQuestion(int questionIndex) {
        Question question = questions.get(questionIndex);
        TextView tvQuestion = findViewById(R.id.tvQuestion);
        RadioGroup rgSingleChoice = findViewById(R.id.rgSingleChoice);
        LinearLayout llMultipleChoice = findViewById(R.id.llMultipleChoice);

        tvQuestion.setText(question.getQuestionText());
        rgSingleChoice.removeAllViews();
        llMultipleChoice.removeAllViews();

        if (question.isSingleChoice()) {
            displaySingleChoiceQuestion(question, rgSingleChoice);
        } else {
            displayMultipleChoiceQuestion(question, llMultipleChoice);
        }
    }

    private void displaySingleChoiceQuestion(Question question, RadioGroup rgSingleChoice) {
        rgSingleChoice.setVisibility(View.VISIBLE);
        for (String option : question.getOptions()) {
            RadioButton rb = new RadioButton(this);
            rb.setText(option);
            rgSingleChoice.addView(rb);
        }
    }

    private void displayMultipleChoiceQuestion(Question question, LinearLayout llMultipleChoice) {
        llMultipleChoice.setVisibility(View.VISIBLE);
        for (String option : question.getOptions()) {
            CheckBox cb = new CheckBox(this);
            cb.setText(option);
            llMultipleChoice.addView(cb);
        }
    }

    private void submitAnswer() {
        Question currentQuestion = questions.get(currentQuestionIndex);

        // Show confirmation dialog
        new AlertDialog.Builder(this)
                .setTitle("Confirm Answer")
                .setMessage("Are you sure you want to submit this answer?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    if (checkAnswer(currentQuestion)) {
                        score++;
                    }
                    prepareNextQuestionOrFinish();
                })
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                .show();
    }

    private boolean checkAnswer(Question question) {
        if (question.isSingleChoice()) {
            return checkSingleChoiceAnswer(question);
        } else {
            return checkMultipleChoiceAnswer(question);
        }
    }

    private boolean checkSingleChoiceAnswer(Question question) {
        RadioGroup rgSingleChoice = findViewById(R.id.rgSingleChoice);
        int selectedId = rgSingleChoice.getCheckedRadioButtonId();
        if (selectedId != -1) {
            RadioButton selectedOption = findViewById(selectedId);
            return selectedOption.getText().toString().equals(question.getCorrectAnswer());
        }
        return false;
    }

    private boolean checkMultipleChoiceAnswer(Question question) {
        LinearLayout llMultipleChoice = findViewById(R.id.llMultipleChoice);
        List<String> selectedAnswers = new ArrayList<>();
        for (int i = 0; i < llMultipleChoice.getChildCount(); i++) {
            CheckBox cb = (CheckBox) llMultipleChoice.getChildAt(i);
            if (cb.isChecked()) {
                selectedAnswers.add(cb.getText().toString());
            }
        }
        return selectedAnswers.equals(question.getCorrectAnswers());
    }

    private void prepareNextQuestionOrFinish() {
        currentQuestionIndex++;
        if (currentQuestionIndex < questions.size()) {
            displayQuestion(currentQuestionIndex);
        } else {
            finishQuiz();
        }
    }

    private void finishQuiz() {
        PreferencesUtil.saveScore(this, score);
        startActivity(new Intent(this, ResultActivity.class));
        finish();
    }

    private void showError(String message) {
        // Assuming you have a TextView with the ID tvError in your layout
        TextView tvError = findViewById(R.id.tvError);
        if (tvError != null) {
            tvError.setText(message);
            tvError.setVisibility(View.VISIBLE);
        } else {
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        }
    }

}