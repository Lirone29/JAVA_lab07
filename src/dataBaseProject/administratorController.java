package dataBaseProject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class administratorController implements Initializable {

    String name;
    String surname;
    int patientID;

    private Date dateVisit;
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    int administratorID;
    public loginModel loginModel;

    private Connection conn = null;
    private Statement statement = null;
    private List<schedule> scheduleList = null;

    private ObservableList<schedule> scheduleListObservable = FXCollections.observableArrayList();
    private ObservableList<visit> visitObservableList = FXCollections.observableArrayList();

    private schedule tmpSchedule =null;
    private visit tmpVisit = null;
    private patient tmpPatient = null;

    @FXML
    private TextField endTimeTextField;

    @FXML
    private TextField beginTextField;

    @FXML
    private TableColumn<schedule, Integer> patientIDColumn;

    @FXML
    private TextField nameTextField;

    @FXML
    private TableView<schedule> tableView;

    @FXML
    private TableColumn<schedule, Integer> doctorIDColumn;

    @FXML
    private Button cancelAppointmentButton;

    @FXML
    private Button addPatientButton;

    @FXML
    private TextField surnameTextField;

    @FXML
    private TableColumn<schedule, LocalTime> beginTimeColumn;

    @FXML
    private Button showScheduleButton;

    @FXML
    private TextField dateTextField;

    @FXML
    private TableColumn<schedule, String> nameColumn;

    @FXML
    private Button addAppointmentButton;

    @FXML
    private TableColumn<schedule, Date > dateColumn;

    @FXML
    private TableColumn<schedule, LocalTime> endTimeColumn;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

       scheduleListObservable =FXCollections.observableArrayList(new schedule());

        dateColumn.setCellValueFactory(new PropertyValueFactory<schedule,Date>("date"));

        endTimeColumn.setCellValueFactory(new PropertyValueFactory<schedule,LocalTime>("EndTime"));
        beginTimeColumn.setCellValueFactory(new PropertyValueFactory<schedule,LocalTime>("BeginTime"));

        nameColumn.setCellValueFactory(new PropertyValueFactory<schedule,String>("Name"));

        patientIDColumn.setCellValueFactory(new PropertyValueFactory<schedule,Integer>("PatientID"));

        patientIDColumn.setVisible(false);

        doctorIDColumn.setCellValueFactory(new PropertyValueFactory<schedule,Integer>("DoctorID"));

        tableView.setEditable(true);
        tableView.getSelectionModel().cellSelectionEnabledProperty().set(true);

    }

    public void addDoctor(){

    }

    //done
    public void insertPatient() throws SQLException {

    this.name = nameTextField.getText();
    this.surname = surnameTextField.getText();

            String sql = "INSERT INTO patient(name, surname) VALUES(?,?)";

            try (
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, name);
                pstmt.setString(2, surname);
                pstmt.executeUpdate();
                pstmt.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
    }

    public void searchPatient() throws SQLException {

        this.name = nameTextField.getText();
        this.surname = surnameTextField.getText();

        if(conn!=null) {
            statement = conn.createStatement();

            String sql = "SELECT patientID FROM patient WHERE (patient.name = '\"+name+\"' AND patient.surname = '\"+surname+\"');";

            ResultSet rs = statement.executeQuery(sql);

            this.patientID = rs.getInt("patient.patientID");
            System.out.println(this.patientID);

            statement.close();
        }
    }

    //done
    public void showSchedule() throws SQLException, ParseException {

        scheduleListObservable.clear();
        scheduleListObservable =FXCollections.observableArrayList();

        dateVisit = new Date();
        String tmpDate;

        if(dateTextField.getText().trim().equals("")==true){
            tmpDate =  dateFormat.format(dateVisit).toString();
        }else {
            tmpDate = dateTextField.getText();
        }

        if(conn!=null) {
            System.out.println("BEGIN");
            statement = conn.createStatement();

            String sql = "SELECT schedule.doctorID, schedule.date, schedule.beginTime, schedule.endTime FROM schedule WHERE date = '"+tmpDate+"';";

            ResultSet rs = statement.executeQuery(sql);

            while(rs.next()) {

                System.out.println("IN");
                tmpSchedule = new schedule();
                tmpSchedule.setDoctorID(rs.getInt("doctorID"));

                Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(rs.getString("date"));
                tmpSchedule.setDate(date1);

                String tmpString = rs.getString("beginTime");
                LocalTime tmpTime1 = LocalTime.parse(tmpString);
                System.out.println("" +tmpTime1);
                tmpSchedule.setBeginTime(tmpTime1);

                LocalTime tmpTime2 = LocalTime.parse(rs.getString("endTime"));
                tmpSchedule.setEndTime(tmpTime2);

                String name = findName(tmpSchedule.getDoctorID());
                tmpSchedule.setName(name);

                scheduleListObservable.add(tmpSchedule);

            }

            tableView.getItems().clear();
            tableView.getItems().addAll(scheduleListObservable);
            tableView.refresh();

            rs.close();

        }
    }

    public int countAppointments(int doctorID) throws SQLException {

        int counter =0;
        dateVisit = new Date();
        String tmpDate;

        if(dateTextField.getText().trim().equals("")==true){
            tmpDate =  dateFormat.format(dateVisit).toString();
        }else {
            tmpDate = dateTextField.getText();
        }

        if(conn!=null) {
            System.out.println("BEGIN");
            statement = conn.createStatement();

            String sql = "SELECT visit.visitID FROM visit WHERE (doctorID = '" + doctorID + "' AND date = '" + tmpDate + "');";

            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                counter++;
            }

            rs.close();

        }
        return counter;
    }

    //done ale nie co 20 min
    public void addAppoinment() throws ParseException, SQLException {

       schedule tmpSchedule = tableView.getSelectionModel().getSelectedItem();

        visit appointment = new visit();
        appointment.setName( this.nameTextField.getText());
        appointment.setSurname(this.nameTextField.getText());
        appointment.setPatientID(this.patientID);

        String getTime = beginTextField.getText();
        System.out.println(getTime);
       // LocalTime tmpTime = LocalTime.parse(getTime);

        String tmpTime3 = getLastAppoinmetTime(appointment.getDoctorID());
        LocalTime tmpTimeLocal = LocalTime.parse(tmpTime3);
        tmpTimeLocal = tmpTimeLocal.plusMinutes(20);
        appointment.setTime(tmpTimeLocal);

       // appointment.setTime(tmpTime);

        Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(dateTextField.getText());
        tmpVisit.setDateVisit(date1);

        tmpVisit.setStatusOfVisit("oczekiwanie");
        tmpVisit.setResult("");

        appointment.setDoctorID(tmpSchedule.getDoctorID());

        int count =  countVisit(appointment.getDoctorID(),appointment.getDateVisit());
      //  int count = countAppointments(appointment.getDoctorID());

        if(count<20){
                String sql = "INSERT INTO visit(patientID, date,status,doctorID,visitTime) VALUES(?,?,?,?,?)";

                try (Connection conn = loginModel.connection;
                     PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setInt(1, appointment.getPatientID());
                    pstmt.setString(2, appointment.getDateVisit().toString());
                    pstmt.setString(3,appointment.getStatusOfVisit());
                    pstmt.setInt(4,appointment.getDoctorID());
                    pstmt.setString(5, appointment.getTime().toString());
                    pstmt.executeUpdate();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }

        }

    }


    //get last appointment

    public void getLastAppoinmentTime(){

    }
    public void showEmptyTimeSlot() throws SQLException, ParseException {

        dateVisit = new Date();
        String tmpDate;

        if(dateTextField.getText().trim().equals("")==true){
            tmpDate =  dateFormat.format(dateVisit).toString();
        }else {
            tmpDate = dateTextField.getText();
        }

        if(conn!=null) {
            System.out.println("BEGIN");
            statement = conn.createStatement();

            String sql = "SELECT schedule.doctorID, schedule.date, schedule.beginTime, schedule.endTime FROM schedule WHERE date = '"+tmpDate+"';";

            ResultSet rs = statement.executeQuery(sql);

            while(rs.next()) {

                System.out.println("IN");
                tmpSchedule = new schedule();
                tmpSchedule.setDoctorID(rs.getInt("doctorID"));

                Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(rs.getString("date"));
                tmpSchedule.setDate(date1);

                String tmpString = rs.getString("beginTime");
                LocalTime tmpTime1 = LocalTime.parse(tmpString);
                System.out.println("" +tmpTime1);
                tmpSchedule.setBeginTime(tmpTime1);

                LocalTime tmpTime2 = LocalTime.parse(rs.getString("endTime"));
                tmpSchedule.setEndTime(tmpTime2);

                String name = findName(tmpSchedule.getDoctorID());
                tmpSchedule.setName(name);

                scheduleListObservable.add(tmpSchedule);

            }

            tableView.getItems().clear();
            tableView.getItems().addAll(scheduleListObservable);
            tableView.refresh();

            rs.close();

        }

    }

    public String findName(int doctorID) throws SQLException {

        statement = conn.createStatement();

        String sql = "SELECT doctor.name, doctor.surname FROM doctor WHERE doctorID = '"+doctorID+"';";

        ResultSet rs = statement.executeQuery(sql);

        String tmpString = rs.getString("name");
        statement.close();
        return  tmpString;


    }

    public String getLastAppoinmetTime(int doctorID) throws SQLException {

        String result =null;
        if (conn != null) {

            statement = conn.createStatement();
            String sql = "SELECT visitTime FROM visit WHERE doctorID= '\"+doctorID+\"' ORDER BY visitTime DSC;";
            ResultSet rs = statement.executeQuery(sql);
            result = rs.getString("visitTime");
            statement.close();
        }

        return result;
    }

    public int countVisit(int doctorID, Date date) throws SQLException {

        int tmpNumberOfVisit = 0;
        if (conn != null) {
            if (statement.isClosed() == true) {
                statement = conn.createStatement();

                String sql = "SELECT visitID FROM visit WHERE date = '\"+date+\"' AND doctorID = '\"+doctorID+\"';";
                ResultSet rs = statement.executeQuery(sql);

                while (rs.next()){
                    tmpNumberOfVisit++;
                }

                statement.close();
                return  tmpNumberOfVisit;

            } else return  tmpNumberOfVisit;
        }
        return 0;
    }

    public void removePatient(){

        this.name = nameTextField.getText();
        this.surname = surnameTextField.getText();

        String sql = "INSERT INTO patient(name, surname) VALUES(?,?)";

        try (Connection conn =  loginModel.connection;
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, surname);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public void getConnection(loginModel tmpLoginModel,int id){
        this.loginModel = tmpLoginModel;
        conn = loginModel.connection;
        this.administratorID = id;
    }

}
