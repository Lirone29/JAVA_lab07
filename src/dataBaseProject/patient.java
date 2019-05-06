package dataBaseProject;

public class patient {
    String name;
    String surname;
    int patientID;

    patient(String tmpName, String tmpSurname,int tmpPatientID){
        this.name = tmpName;
        this.surname = tmpSurname;
        this.patientID = tmpPatientID;

    }

    patient(){
        this.name = null;
        this.surname = null;
        this.patientID = 0;
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

    public int getPatientID() {
        return patientID;
    }

    public void setPatientID(int patientID) {
        this.patientID = patientID;
    }
}
