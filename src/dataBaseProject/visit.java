package dataBaseProject;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

import java.sql.Time;
import java.time.LocalTime;
import java.util.Date;

public class visit {
    private Date dateVisit = null;
    private LocalTime time =null;
    private String statusOfVisit ="zarezerwowana";
    private String result = null;
    private int patientID;
    private int doctorID;
    private int visitID;
    private patient patient;

    public visit( Date tmpDateVisit, LocalTime tmpTime, String tmpStatusOfVisit, String tmpResult, int tmpPatientID,int tmpDoctorID, int tmpVisitID, String name, String surname) {
        this.dateVisit = tmpDateVisit;
        this.statusOfVisit = tmpStatusOfVisit;
        this.result = tmpResult;
        this.patientID = tmpPatientID;
        this.doctorID = tmpDoctorID;
        this.visitID = tmpVisitID;
        this.patient = new patient(name,surname,patientID);
    }

    public visit(){

       this.dateVisit =null;
       this.time = null;
       this.result = null;
       this.statusOfVisit = "zarezerwowana";
       this.patientID = 0;
       this.doctorID = 0;
       this.visitID = 0;
       this.patient = new patient();
    }
    public int getVisitID() {
        return visitID;
    }

    public void setVisitID(int visitID) {
        this.visitID = visitID;
    }

    public int getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(int doctorID) {
        this.doctorID = doctorID;
    }

    public String getName () {
        return patient.getName();
    }

    public void setName(String name) {
        this.patient.setName(name);
    }

    public String getSurname () {
        return patient.getSurname();
    }

    public void setSurname(String name) {
        this.patient.setSurname(name);
    }


    public int getPatientID() {
        return patientID;
    }

    public void setPatientID(int patientID) {
        this.patientID = patientID;
    }

    public Date getDateVisit() {
        return dateVisit;
    }

    public void setDateVisit( Date dateVisit) {
        this.dateVisit = dateVisit;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public String getStatusOfVisit() {
        return statusOfVisit;
    }

    public void setStatusOfVisit(String statusOfVisit) {
        this.statusOfVisit = statusOfVisit;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }


}
