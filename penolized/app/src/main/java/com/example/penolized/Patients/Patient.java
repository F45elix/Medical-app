package com.example.penolized.Patients;
public class Patient {

    private String patientName;

    private String BirthcertNo;
    private String diseaseDiagnosed;
    private String medicinesGiven;

    private String sideeffects;


    public Patient() {
        // Required empty constructor for Firebase
    }
    public Patient(String patientName, String BirthcertNo, String diseaseDiagnosed,String medicinesGiven,  String sideeffects) {
        this.patientName = patientName;
        this.BirthcertNo = BirthcertNo;
        this.medicinesGiven = medicinesGiven;
        this.diseaseDiagnosed = diseaseDiagnosed;
        this.sideeffects = sideeffects;
    }


    // Add getters and setters
    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getBirthcertNo() {
        return BirthcertNo;
    }

    public void setBirthcertNo(String BirthcertNo) {
        this.BirthcertNo = BirthcertNo;
    }
    public String getDiseaseDiagnosed() {
        return diseaseDiagnosed;
    }

    public void setDiseaseDiagnosed(String diseaseDiagnosed) {
        this.diseaseDiagnosed = diseaseDiagnosed;
    }


    public String getMedicinesGiven() {
        return medicinesGiven;
    }

    public void setMedicinesGiven(String medicinesGiven) {
        this.medicinesGiven = medicinesGiven;
    }


    public String getSideeffects() {
        return sideeffects;
    }

    public void setSideeffects(String sideeffects) {
        this.sideeffects = sideeffects;
    }
}
