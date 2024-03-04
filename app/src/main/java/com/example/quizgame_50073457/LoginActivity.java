package com.example.quizgame_50073457;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Toast;


public class LoginActivity extends AppCompatActivity {

    private EditText email, password;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.editTextEmailLogin);
        password = findViewById(R.id.editTextPasswordLogin);
        btnLogin = findViewById(R.id.buttonLogin);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInputs()){
                    if (PreferencesUtil.validateUser(LoginActivity.this, email.getText().toString(), password.getText().toString())) {
                        // Navigate to Quiz or Rules Activity
                        startActivity(new Intent(LoginActivity.this, RulesActivity.class));
                        //startActivity(new Intent(LoginActivity.this, QuizActivity.class));
                        finish();
                    } else {
                        // Show error
                        Toast.makeText(LoginActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }


    private boolean validateInputs() {
        // Validate that the email and password fields are not empty
        if (TextUtils.isEmpty(email.getText()) || TextUtils.isEmpty(password.getText())) {
            Toast.makeText(LoginActivity.this, "Please enter both email and password", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validate email format
        if (!Patterns.EMAIL_ADDRESS.matcher(email.getText()).matches()) {
            Toast.makeText(LoginActivity.this, "Please enter a valid email address", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    // Assume initialization and setContentView are done
    private void navigateToRules() {
        Intent intent = new Intent(LoginActivity.this, RulesActivity.class);
        startActivity(intent);
        finish();
    }
}
