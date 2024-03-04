package com.example.quizgame_50073457;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnLogin = findViewById(R.id.btnLogin);
        Button btnRegister = findViewById(R.id.btnRegister);

        btnLogin.setOnClickListener(v -> {
            // Navigate to LoginActivity
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        });

        btnRegister.setOnClickListener(v -> {
            // Navigate to RegistrationActivity
            startActivity(new Intent(MainActivity.this, RegistrationActivity.class));
        });
    }
}
