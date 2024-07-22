package com.example.penolized;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.penolized.Patients.Patient;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private EditText patientNameEditText, BirthcertNoEditText, diseaseDiagnosedEditText, medicinesGivenEditText, sideeffectsEditText;
    private Button submitButton;
    private DatabaseReference PatientsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Firebase Database reference
        PatientsRef = FirebaseDatabase.getInstance().getReference().child("Patients");

        // Initialize EditText fields and Submit Button
        patientNameEditText = findViewById(R.id.PatientName);
        BirthcertNoEditText = findViewById(R.id.BirthcertNo);
        diseaseDiagnosedEditText = findViewById(R.id.DiseaseDiagnosed);
        medicinesGivenEditText = findViewById(R.id.Medicinesgiven);
        sideeffectsEditText = findViewById(R.id.sideeffects);
        submitButton = findViewById(R.id.Submit);

        // Set onClickListener for Submit Button
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get data from EditText fields
                String patientName = patientNameEditText.getText().toString().trim();
                String BirthcertNo = BirthcertNoEditText.getText().toString().trim();
                String diseaseDiagnosed = diseaseDiagnosedEditText.getText().toString().trim();
                String medicinesGiven = medicinesGivenEditText.getText().toString().trim();
                String sideeffects = sideeffectsEditText.getText().toString().trim();

                // Check if any field is empty
                if (patientName.isEmpty() || BirthcertNo.isEmpty() || medicinesGiven.isEmpty() || diseaseDiagnosed.isEmpty() || sideeffects.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Check if the birth certificate number already exists in the database
                PatientsRef.orderByChild("birthCertNo").equalTo(BirthcertNo).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        boolean birthCertExists = false;
                        boolean nameMatches = false;
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Patient existingPatient = snapshot.getValue(Patient.class);
                            if (existingPatient != null && existingPatient.getBirthcertNo().equals(BirthcertNo)) {
                                birthCertExists = true;
                                if (existingPatient.getPatientName().equals(patientName)) {
                                    nameMatches = true;
                                    break;
                                }
                            }
                        }

                        // Create a unique key for the patient
                        String patientId = PatientsRef.push().getKey();

                        // Create a Patient object
                        Patient patients = new Patient(patientName, BirthcertNo, diseaseDiagnosed, medicinesGiven, sideeffects);

                        // Write the patient object to Firebase Database
                        PatientsRef.child(patientId).setValue(patients)
                                .addOnCompleteListener(task -> {
                                    if (task.isSuccessful()) {
                                        // Data submitted successfully
                                        Toast.makeText(MainActivity.this, "Patient information submitted successfully", Toast.LENGTH_SHORT).show();
                                        // Clear EditText fields
                                        patientNameEditText.setText("");
                                        BirthcertNoEditText.setText("");
                                        diseaseDiagnosedEditText.setText("");
                                        medicinesGivenEditText.setText("");
                                        sideeffectsEditText.setText("");
                                    } else {
                                        // Error occurred while submitting data
                                        Toast.makeText(MainActivity.this, "Failed to submit patient information. Please try again.", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Handle database error
                        Toast.makeText(MainActivity.this, "Database error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
