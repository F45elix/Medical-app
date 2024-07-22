package com.example.penolized.Doctor;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.penolized.R;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivitydoc extends AppCompatActivity {
    private EditText email, password;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.regdoc_layout);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        Button btn_login = findViewById(R.id.btn_login);
        mAuth = FirebaseAuth.getInstance();

        btn_login.setOnClickListener(v -> {
            String emailInput = email.getText().toString().trim();
            String passwordInput = password.getText().toString().trim();

            if (emailInput.isEmpty()) {
                email.setError("Email is empty");
                email.requestFocus();
                return;
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
                email.setError("Enter a valid email");
                email.requestFocus();
                return;
            }
            if (passwordInput.isEmpty()) {
                password.setError("Password is empty");
                password.requestFocus();
                return;
            }
            if (passwordInput.length() < 6) {
                password.setError("Password length should be at least 6 characters");
                password.requestFocus();
                return;
            }

            mAuth.signInWithEmailAndPassword(emailInput, passwordInput).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    startActivity(new Intent(LoginActivitydoc.this, DoctorActivity.class));
                    finish(); // Finish this activity after successful login
                } else {
                    Toast.makeText(LoginActivitydoc.this,
                            "Please check your login credentials",
                            Toast.LENGTH_SHORT).show();
                }
            });
        });

        //btn_sign.setOnClickListener(v -> startActivity(new Intent(LoginActivitydoc.this, RegistrationActivity.class)));
    }
}
