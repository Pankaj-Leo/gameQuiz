package com.example.quizgame_50073457;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Patterns;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class RegistrationActivity extends AppCompatActivity {

    private EditText firstName, lastName, email, password, dob;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        firstName = findViewById(R.id.editTextFirstName);
        lastName = findViewById(R.id.editTextLastName);
        email = findViewById(R.id.editTextEmail);
        password = findViewById(R.id.editTextPassword);
        dob = findViewById(R.id.editTextDob);
        btnRegister = findViewById(R.id.buttonRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInputs()) {
                    Toast.makeText(RegistrationActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                    PreferencesUtil.saveUser(RegistrationActivity.this, email.getText().toString(), password.getText().toString());
                    // Return to previous screen or login screen
                    finish(); // Go back to the previous activity page
                }
            }
        });
    }

    private boolean validateInputs() {
        // Validate that the entered data is not empty
        if (TextUtils.isEmpty(firstName.getText()) ||
                TextUtils.isEmpty(lastName.getText()) ||
                TextUtils.isEmpty(email.getText()) ||
                TextUtils.isEmpty(password.getText()) ||
                TextUtils.isEmpty(dob.getText())) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validate email format
        if (!Patterns.EMAIL_ADDRESS.matcher(email.getText()).matches()) {
            Toast.makeText(this, "Enter a valid email", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validate first name length
        if (firstName.getText().length() < 3 || firstName.getText().length() > 30) {
            Toast.makeText(this, "First name must be between 3 and 30 characters", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validate date format MM/DD/YYYY
        if (!isValidDate(dob.getText().toString())) {
            Toast.makeText(this, "Date must be in MM/DD/YYYY format", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private boolean isValidDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        sdf.setLenient(false); // Don't automatically convert invalid date
        try {
            sdf.parse(date); // If the date is valid, this line will succeed
            return true;
        } catch (ParseException e) {
            return false; // If the date is invalid, parsing will fail
        }
    }
}


