package com.example.penolized;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.penolized.Patients.Patient;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeActivity extends AppCompatActivity {
    private Button btn_add, queryButton;
    private LinearLayout patientContainer;
    private EditText queryEditText;
    private DatabaseReference PatientsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Initialize the views
        patientContainer = findViewById(R.id.patientContainer);
        btn_add = findViewById(R.id.btn_add);
        queryButton = findViewById(R.id.btn_query);
        queryEditText = findViewById(R.id.edit_query);

        // Initialize the Firebase Database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        // Reference the path directly without using the patientId from Intent
        PatientsRef = database.getReference().child("Patients");

        // Set onClickListener for Add Button
        btn_add.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this, MainActivity.class)));

        // Set OnClickListener for the Query Button
        queryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve input from the EditText
                String birthCertNo = queryEditText.getText().toString().trim();

                // Check if input is empty
                if (birthCertNo.isEmpty()) {
                    Toast.makeText(HomeActivity.this, "Please enter a birth certificate number", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Query the database to check if a patient with this birth certificate number exists
                PatientsRef.orderByChild("birthcertNo").equalTo(birthCertNo).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            // If a patient with the entered birth certificate number exists, display their name
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                Patient patient = snapshot.getValue(Patient.class);
                                if (patient != null) {
                                    String patientName = patient.getPatientName();
                                    Toast.makeText(HomeActivity.this, "Patient name: " + patientName, Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }
                        } else {
                            // If no patient with the entered birth certificate number exists, display a message
                            Toast.makeText(HomeActivity.this, "No patient found with this birth certificate number", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Handle database error
                        Toast.makeText(HomeActivity.this, "Database error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        // Set up a listener for patient data
        PatientsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Check if the snapshot has data
                if (snapshot.exists()) {
                    // Clear the previous patient views
                    patientContainer.removeAllViews();

                    for (DataSnapshot patientSnapshot : snapshot.getChildren()) {
                        Patient patient = patientSnapshot.getValue(Patient.class);
                        displayPatientInfo(patient);
                    }
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

        // Initialize the views for each patient
        TextView viewPatientName = patientLayout.findViewById(R.id.viewPatientName);
        TextView viewBirthcertNo = patientLayout.findViewById(R.id.viewBirthcertNo);
        TextView viewDiseaseDiagnosed = patientLayout.findViewById(R.id.viewDiseaseDiagnosed);
        TextView viewMedicinesGiven = patientLayout.findViewById(R.id.viewMedicinesgiven);
        TextView viewsideeffects = patientLayout.findViewById(R.id.viewsideeffects);

        // Set the patient information to the views
        viewPatientName.setText("Patient Name: " + patient.getPatientName());
        viewBirthcertNo.setText("Birth Certificate: " + patient.getBirthcertNo());
        viewDiseaseDiagnosed.setText("Disease Diagnosed: " + patient.getDiseaseDiagnosed());
        viewMedicinesGiven.setText("Medicines Given: " + patient.getMedicinesGiven());
        viewsideeffects.setText("Side Effects: " + patient.getSideeffects());

        CardView cardView = patientLayout.findViewById(R.id.card_view);
        cardView.setOnLongClickListener(v -> {
            // Trigger the share information method
            shareInformation(patient);
            return true;  // Consume the long-press event
        });
        // Add the patient layout to the container
        patientContainer.addView(patientLayout);
    }

    private void shareInformation(Patient patient) {
        String shareText = "Patient Information: \n" +
                "Name: " + patient.getPatientName() + "\n" +
                "Birth Certificate: " + patient.getBirthcertNo() + "\n" +
                "Medicines: " + patient.getMedicinesGiven() + "\n" +
                "Diagnosis: " + patient.getDiseaseDiagnosed() + "\n" +
                "Side Effects: " + patient.getSideeffects();

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, shareText);
        sendIntent.setType("text/plain");

        // Choose the title for the share menu
        Intent shareIntent = Intent.createChooser(sendIntent, "Share Patient Information");
        startActivity(shareIntent);
    }
}
