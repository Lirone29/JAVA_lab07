package dataBaseProject;

public class doctor {
    String name;
    String surname;
    int roomNumber;
    int doctorID;

    doctor(String tmpName, String tmpSurname,int tmpRoomNr, int tmpDotorID){
        this.name = tmpName;
        this.surname = tmpSurname;
        this.roomNumber = tmpRoomNr;
        this.doctorID = tmpDotorID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public int getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(int doctorID) {
        this.doctorID = doctorID;
    }
}
