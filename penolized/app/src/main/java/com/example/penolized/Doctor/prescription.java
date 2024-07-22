package com.example.penolized.Doctor;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.penolized.R;

public class prescription extends AppCompatActivity {

    private EditText prescriptionEditText;
    private EditText patient_mobile_edit_text;
    private Button send_button;
    private EditText medicineNameEditText;
    private Button search_Button;
    private Button find_pharmacy_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prescription);

        prescriptionEditText = findViewById(R.id.prescription_edit_text);
        patient_mobile_edit_text = findViewById(R.id.patient_mobile_edit_text);
        send_button = findViewById(R.id.send_button);
        medicineNameEditText = findViewById(R.id.medicine_name_edit_text);
        search_Button = findViewById(R.id.search_button);
        find_pharmacy_button = findViewById(R.id.find_pharmacy_button);

        send_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String prescriptionText = prescriptionEditText.getText().toString();
                String patientPhoneNumber = patient_mobile_edit_text.getText().toString();

                if (!patientPhoneNumber.isEmpty()) {
                    SmsManager smsManager = SmsManager.getDefault();
                    try {
                        smsManager.sendTextMessage(patientPhoneNumber, null, prescriptionText, null, null);
                        Toast.makeText(prescription.this, "Prescription sent via SMS", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Log.e("SendSMS", "Error sending SMS: " + e.getMessage());
                        Toast.makeText(prescription.this, "Error sending SMS. Please try again later.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(prescription.this, "Please enter patient's mobile number", Toast.LENGTH_SHORT).show();
                }
            }
        });

        search_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String medicineName = medicineNameEditText.getText().toString().trim();
                if (!medicineName.isEmpty()) {
                    performWebSearch(medicineName);
                } else {
                    Toast.makeText(prescription.this, "Please enter a medicine name", Toast.LENGTH_SHORT).show();
                }
            }
        });

        find_pharmacy_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the default device location
                Location userLocation = getDefaultLocation();
                if (userLocation != null) {
                    double latitude = userLocation.getLatitude();
                    double longitude = userLocation.getLongitude();
                    // Call the method to find the nearest pharmacy
                    findNearestPharmacy(latitude, longitude);
                } else {
                    // Inform the user that location services are unavailable
                    Toast.makeText(prescription.this, "Unable to retrieve device location", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void performWebSearch(String medicineName) {
        String searchQuery = "https://www.google.com/search?q=" + Uri.encode(medicineName);
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(searchQuery));
        startActivity(browserIntent);
    }

    private Location getDefaultLocation() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (locationManager != null) {
            try {
                return locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            } catch (SecurityException e) {
                Log.e("Location", "Security Exception: " + e.getMessage());
            }
        }
        return null;
    }

    private void findNearestPharmacy(double latitude, double longitude) {
        try {
            // Construct a URI to search for pharmacies near the user's location in Google Maps
            Uri gmmIntentUri = Uri.parse("geo:" + latitude + "," + longitude + "?q=pharmacy");

            // Create an Intent to open Google Maps with the search results
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");

            // Open Google Maps to display the nearest pharmacies
            startActivity(mapIntent);
        } catch (Exception e) {
            // Log any exceptions that occur
            Log.e("FindNearestPharmacy", "Error finding nearest pharmacy: " + e.getMessage());
            // Inform the user about the error
            Toast.makeText(this, "Error finding nearest pharmacy. Please try again later.", Toast.LENGTH_SHORT).show();
        }
    }
}
