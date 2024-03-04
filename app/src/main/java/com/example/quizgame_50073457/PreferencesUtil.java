package com.example.quizgame_50073457;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PreferencesUtil {
    private static final String PREFS_NAME = "QuizAppPrefs";
    private static final String USER_EMAIL = "userEmail";
    private static final String USER_PASSWORD = "userPassword";
    private static final String USER_SCORE = "userScore";
    private static final String USER_ATTEMPTS = "userAttempts";
    private static final String SCORES_KEY = "scores";
    private static final String CURRENT_USER_KEY = "currentUser";

    private static final String QUESTIONS_KEY = "questions";

    public static void saveUser(Context context, String email, String password) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(USER_EMAIL, email);
        editor.putString(USER_PASSWORD, password);
        editor.apply();
    }

    public static boolean validateUser(Context context, String email, String password) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String savedEmail = prefs.getString(USER_EMAIL, "");
        String savedPassword = prefs.getString(USER_PASSWORD, "");
        return savedEmail.equals(email) && savedPassword.equals(password);
    }

    public static void saveScore(Context context, int score) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(USER_SCORE, score);
        editor.apply();
    }

    public static int getScore(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getInt(USER_SCORE, 0);
    }
    // Method to save the JSON string of scores into SharedPreferences
    public static void saveScoresJson(Context context, String jsonScores) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(SCORES_KEY, jsonScores); // SCORES_KEY is the key for storing scores JSON
        editor.apply(); // or editor.commit() if you need synchronous save
    }


    public static String getScoresJson(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String scoresJson = prefs.getString(SCORES_KEY, "");
        Log.d("QuizStatActivity", "Loaded scores JSON: " + scoresJson);
        return scoresJson;
    }


    // Method to get the current username
    public static String getCurrentUsername(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        // Assuming USER_EMAIL is the key where the user's email is stored.
        return prefs.getString(USER_EMAIL, "Unknown User");
    }


    public static void saveQuestionsJson(Context context, String json) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(QUESTIONS_KEY, json);
        editor.apply();
    }

    public static String getQuestionsJson(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getString(QUESTIONS_KEY, ""); // Return an empty string if nothing is found
    }


    public static void addScore(Context context, Score newScore) {
        List<Score> scores = loadScores(context);
        scores.add(newScore);
        saveScores(context, scores);
    }

    public static List<Score> loadScores(Context context) {
        String jsonScores = getScoresJson(context);
        if (jsonScores.isEmpty()) return new ArrayList<>();
        return new Gson().fromJson(jsonScores, new TypeToken<ArrayList<Score>>() {}.getType());
    }

    public static void saveScores(Context context, List<Score> scores) {
        String jsonScores = new Gson().toJson(scores);
        saveScoresJson(context, jsonScores);
    }
}





