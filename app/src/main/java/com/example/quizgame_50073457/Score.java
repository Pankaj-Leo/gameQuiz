package com.example.quizgame_50073457;

public class Score {
    private String username;
    private int score;
    private String timestamp; // ISO 8601 format, e.g., "2023-03-22T14:00:00Z"

    public Score(String username, int score, String timestamp) {
        this.username = username;
        this.score = score;
        this.timestamp = timestamp;
    }

    // Getters
    public String getUsername() {
        return username;
    }

    public int getScore() {
        return score;
    }

    public String getTimestamp() {
        return timestamp;
    }

    // Setters,
}
