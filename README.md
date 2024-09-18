Patient Data Mobile App README

Welcome to the Patient Data Mobile App! This application is built with Java and Kotlin using Android Studio. It allows users to input patient data, store it securely in Firebase, and provides various features to enhance healthcare management.


Table of Contents
Overview
Features
Installation
Usage
Customization
Credits
License
Overview
The Patient Data Mobile App is designed to help healthcare professionals manage patient information efficiently. Users can input and retrieve patient data using a birth certificate number, find nearby chemists using Google Maps, research medicine and symptoms through a Google Search API, and share prescriptions via sms.

Features
Patient Data Input: Securely input and store patient information in Firebase.
Data Retrieval: Search for patient info using the birth certificate number.
Google Maps Integration: Identify the nearest chemists and display their locations on a map.
Google Search API: Research medicines and symptoms directly within the app.
Prescription Sharing: Edit prescriptions and share them with patients via email.
Installation
To set up the project locally, follow these steps:

Clone the Repository:

bash
Copy code
git clone https://github.com/yourusername/patient-data-app.git
cd patient-data-app
Open in Android Studio:

Open Android Studio.
Select "Open an Existing Project" and navigate to the cloned directory.
Set Up Firebase:

Follow the Firebase documentation to set up Firebase for your project.
Add your google-services.json file to the app/ directory.
Add Dependencies:

Ensure all necessary dependencies for Firebase, Google Maps, and any other required libraries are included in your build.gradle files.
Sync the Project:

Click "Sync Now" in Android Studio to install all dependencies.
Usage
Input Patient Data:

Use the input form to enter patient details and save them to Firebase.
Search for Patient Info:

Enter the birth certificate number in the search field to retrieve and display patient information.
Find Nearest Chemists:

Access the Google Maps feature to view locations of nearby chemists.
Research Medicines and Symptoms:

Utilize the Google Search API to look up information on medications and symptoms.
Share Prescriptions:

Edit prescriptions in the designated field and share them via email directly from the app.
Customization
You can customize the app by modifying the following files:

Java/Kotlin Files: Update the logic in MainActivity.kt or relevant activity files to add new features or modify existing ones.
XML Layouts: Change the UI in the res/layout/ directory to fit your design preferences.
Firebase Rules: Adjust Firebase security rules as needed in the Firebase Console.
Credits
Firebase: Used for backend storage and authentication.
Google Maps API: Integrated to display chemist locations.
Google Search API: Used for fetching medicine and symptom information.
