package com.example.quizgame_50073457;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Question {
    private String questionText;
    private List<String> options;
    private String correctAnswer; // For single-choice questions
    private List<String> correctAnswers; // For multiple-choice questions
    private boolean isSingleChoice;

    // Constructor for single-choice questions
    public Question(String questionText, List<String> options, String correctAnswer) {
        this.questionText = questionText;
        this.options = options;
        this.correctAnswer = correctAnswer;
        this.isSingleChoice = true;
    }

    // Constructor for multiple-choice questions
    public Question(String questionText, List<String> options, List<String> correctAnswers) {
        this.questionText = questionText;
        this.options = options;
        this.correctAnswers = correctAnswers;
        this.isSingleChoice = false;
    }

    // Getters
    public String getQuestionText() {
        return questionText;
    }

    public List<String> getOptions() {
        return options;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public List<String> getCorrectAnswers() {
        return correctAnswers;
    }

    public boolean isSingleChoice() {
        return isSingleChoice;
    }

    // Serialization methods
    public static String questionsToJson(List<Question> questions) {
        Gson gson = new Gson();
        return gson.toJson(questions);
    }

    public static List<Question> jsonToQuestions(String json) {
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<Question>>(){}.getType();
        return gson.fromJson(json, listType);
    }

    // Example usage
    public static void main(String[] args) {
        List<Question> questions = new ArrayList<>();
        questions.add(new Question("What hand beats a full house in texas holdem poker?", Arrays.asList("Flush", "Three of a Kind", "Straight", "Four of a Kind"), "Four of a Kind"));
        questions.add(new Question("What is probability of getting one King out of a random draw?", Arrays.asList("1/52", "4/52", "1/13", "1"), Arrays.asList( "4/52", "1/13")));
        questions.add(new Question("What do you mean by work check in poker?", Arrays.asList("Check your Phone for text", "See other peoples card", "Be in the game when bets are matched, without placing any new bet", "Bet all your chips on the hand"), "Be in the game when bets are matched, without placing any new bet"));
        questions.add(new Question("What is a flush in poker?", Arrays.asList("Flush all cards in table", "consecutive cards", "cards of same suit", "cards of same number"), "cards of same suit"));
        questions.add(new Question("What is a river in poker?", Arrays.asList("Hudson river", "Last card to be drawn in the community cards", "Set of cards with same suit", "Friend of Big Blind"), "Last card to be drawn in the community cards"));


        String json = Question.questionsToJson(questions);
        System.out.println();

//         Example of converting JSON back to questions
        List<Question> questionsFromJson = Question.jsonToQuestions(json);
        for (Question question : questionsFromJson) {
            System.out.println(question.getQuestionText());
        }

    }
}
