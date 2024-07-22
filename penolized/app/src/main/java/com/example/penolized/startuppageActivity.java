package com.example.penolized;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.penolized.Doctor.LoginActivitydoc;
import com.example.penolized.Patients.LoginActivity;

public class startuppageActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startuppage_layout2);

        Button btndoctor = findViewById(R.id.btndoctor);
        Button btnpatient = findViewById(R.id.btnpatient);


        btndoctor.setOnClickListener(v -> startActivity(new Intent(startuppageActivity.this, LoginActivitydoc.class)));

        btnpatient.setOnClickListener(v -> startActivity(new Intent(startuppageActivity.this, LoginActivity.class)));
    }
}
