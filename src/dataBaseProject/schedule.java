package dataBaseProject;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;

public class schedule {
    Date date = null;
    private LocalTime beginTime;
    private LocalTime endTime;
    private int doctorID;
    private int numberOfVisit = 0;
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private String name;
    private int numbetOfVisit =0;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumbetOfVisit() {
        return numbetOfVisit;
    }

    public void setNumbetOfVisit(int numbetOfVisit) {
        this.numbetOfVisit = numbetOfVisit;
    }

    public schedule(Date date, LocalTime beginTime, LocalTime endTime, int doctorID) {
        this.date = date;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.doctorID = doctorID;
        this.numberOfVisit =0;
    }

   public schedule(){
        this.date = null;
        this.beginTime = null;
        this.endTime = null;
        this.doctorID = -1;
        this.numberOfVisit = 0;
        this.name = null;
        this.numberOfVisit = 0;
   }
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = (date);
    }

    public LocalTime getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(LocalTime beginTime) {
        this.beginTime = beginTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public int getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(int doctorID) {
        this.doctorID = doctorID;
    }

    public int getNumberOfVisit() {
        return numberOfVisit;
    }

    public void setNumberOfVisit(int numberOfVisit) {
        this.numberOfVisit = numberOfVisit;
    }
}
