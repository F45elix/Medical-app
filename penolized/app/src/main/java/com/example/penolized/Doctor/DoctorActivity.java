package com.example.penolized.Doctor;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.penolized.Patients.Patient;
import com.example.penolized.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DoctorActivity extends AppCompatActivity {

    private Button searchButton;
    private Button send_button;
    private LinearLayout patientContainer;
    private EditText editText;
    private DatabaseReference PatientsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);

        // Initialize the views
        searchButton = findViewById(R.id.search_btn);
        patientContainer = findViewById(R.id.patientContainer);
        editText = findViewById(R.id.editText);
        send_button = findViewById(R.id.send_button);
        send_button.setOnClickListener(v -> startActivity(new Intent(DoctorActivity.this, prescription.class)));

        // Initialize the Firebase Database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        // Reference the path directly without using the patientId from Intent
        PatientsRef = database.getReference().child("Patients");

        // Set click listener for search button
        searchButton.setOnClickListener(v -> {
            String searchQuery = editText.getText().toString();
            searchPatient(searchQuery);
        });
    }

    private void searchPatient(String searchQuery) {
        PatientsRef.orderByChild("birthcertNo").equalTo(searchQuery).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Clear previous patient views
                patientContainer.removeAllViews();

                boolean found = false;

                if (snapshot.exists()) {
                    // Display patient data only if a matching patient is found
                    for (DataSnapshot patientSnapshot : snapshot.getChildren()) {
                        Patient patient = patientSnapshot.getValue(Patient.class);
                        if (patient != null) {
                            displayPatientInfo(patient);
                            found = true;
                        }
                    }
                }

                if (!found) {
                    // Display a message indicating no patient found
                    Toast.makeText(DoctorActivity.this, "No patient found with this birth certificate number", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle errors if any
            }
        });
    }

    private void displayPatientInfo(Patient patient) {
        // Inflate the layout for each patient
        LinearLayout patientLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.patient_info_layout, null);

        TextView viewPatientName = patientLayout.findViewById(R.id.viewPatientName);
        TextView viewBirthCertNo = patientLayout.findViewById(R.id.viewBirthcertNo);
        TextView viewDiseaseDiagnosed = patientLayout.findViewById(R.id.viewDiseaseDiagnosed);
        TextView viewMedicinesGiven = patientLayout.findViewById(R.id.viewMedicinesgiven);
        TextView viewSideEffects = patientLayout.findViewById(R.id.viewsideeffects);

        // Set the patient information to the views
        viewPatientName.setText("Patient Name: " + patient.getPatientName());
        viewBirthCertNo.setText("Birth Certificate No: " + patient.getBirthcertNo());
        viewDiseaseDiagnosed.setText("Disease Diagnosed: " + patient.getDiseaseDiagnosed());
        viewMedicinesGiven.setText("Medicines Given: " + patient.getMedicinesGiven());
        viewSideEffects.setText("Side Effects: " + patient.getSideeffects());

        CardView cardView = patientLayout.findViewById(R.id.card_view);
        cardView.setOnLongClickListener(v -> {
            // Do something when card is long pressed
            return true;
        });

        // Add the patient layout to the container
        patientContainer.addView(patientLayout);
    }
}