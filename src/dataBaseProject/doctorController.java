package dataBaseProject;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import org.jcp.xml.dsig.internal.dom.Utils;

import java.net.URL;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class doctorController implements Initializable {

    public loginModel loginModel;
    private Connection conn = null;
    private Statement statement = null;
    private int doctorID;

    private Date dateVisit;

    private visit tmpVisit;

    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private List<visit> visitList =  new ArrayList<>();
    private ObservableList<visit> visitListObservable =FXCollections.observableArrayList();

    @FXML
    private Button insertInfoButton;

    @FXML
    private TableColumn<visit, String> resumeColumn;

    @FXML
    private Button showAppointmentButton;

    @FXML
    private TableColumn<visit, Time> timeColumn;

    @FXML
    private TableColumn<visit, Integer> patientIDColumn;

    @FXML
    private TableColumn<visit, String> statusColumn;

    @FXML
    private Button lastVisitButton;

    @FXML
    private Label doctorIDLabel;

    @FXML
    private TextField dateTextField;

    @FXML
    private TableView<visit> infoTable;

    @FXML
    private TableColumn<visit, String> surnameColumn;

    @FXML
    private TableColumn<visit, String> nameColumn;

    @FXML
    private Button statusButton;

    @FXML
    private TextField statusTextField;

    @FXML
    private TextField resumeTextField;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        visitListObservable =FXCollections.observableArrayList(new visit());

        resumeColumn.setCellValueFactory(new PropertyValueFactory<visit,String>("result"));

        timeColumn.setCellValueFactory(new PropertyValueFactory<visit,Time>("time"));

        patientIDColumn.setCellValueFactory(new PropertyValueFactory<visit,Integer>("patientID"));

        statusColumn.setCellValueFactory(new PropertyValueFactory<visit,String>("status"));

        nameColumn.setCellValueFactory(new PropertyValueFactory<visit,String>("name"));

        surnameColumn.setCellValueFactory(new PropertyValueFactory<visit,String>("surname"));

        infoTable.setEditable(true);
        infoTable.getSelectionModel().cellSelectionEnabledProperty().set(true);
    }


    public void showTodayAppointment() throws SQLException, ParseException {

        //Zastanów sie jak czyścić
        visitListObservable.clear();
        visitListObservable =FXCollections.observableArrayList(new visit());

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
            String sql = "SELECT visit.patientID, visit.visitID, visit.visitTime, visit.status, visit.date, visit.doctorID, visit.result, patient.name, patient.surname FROM visit , patient WHERE (patient.patientID = visit.patientID AND visit.doctorID = '"+doctorID+"' AND visit.date >= '"+tmpDate+"');";

            ResultSet rs = statement.executeQuery(sql);

            while(rs.next()){

                visit tmpVisit = new visit();

                Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(rs.getString("date"));
                tmpVisit.setDateVisit(date1);

                System.out.println("IN");
                tmpVisit.setDoctorID(rs.getInt("doctorID"));
                tmpVisit.setPatientID(rs.getInt("patientID"));

                tmpVisit.setStatusOfVisit(rs.getString("status"));

                tmpVisit.setResult(rs.getString("result"));

                tmpVisit.setVisitID(rs.getInt("visitID"));

                tmpVisit.setName(rs.getString("name"));
                tmpVisit.setSurname(rs.getString("surname"));

                LocalTime tmpTime = LocalTime.parse(rs.getString("visitTime"));
                tmpVisit.setTime(tmpTime);


                visitList.add(tmpVisit);
                visitListObservable.add(tmpVisit);
            }

            infoTable.getItems().clear();
            infoTable.getItems().addAll(visitListObservable);

            infoTable.refresh();
            rs.close();

        }


    }

    public void changeStatus() throws SQLException {

        visit tmpVisit = infoTable.getSelectionModel().getSelectedItem();

        String tmpStatus = statusTextField.getText();
        int visitID = tmpVisit.getVisitID();
        tmpVisit.setStatusOfVisit(tmpStatus);
        int index =findInList(visitID);
        visitListObservable.get(index).setStatusOfVisit(tmpStatus);

        if (conn != null) {
        statement = conn.createStatement();
        String sql = "UPDATE visit SET status = '"+tmpStatus+"' WHERE visitID = "+visitID+"; ";

        System.out.println("Status changed");
        statement.executeUpdate(sql);
        statement.close();

        infoTable.getItems().clear();
        infoTable.getItems().addAll(visitListObservable);
        infoTable.refresh();


    }
}

    public void lastResume() throws SQLException {
        visit tmpVisit = infoTable.getSelectionModel().getSelectedItem();
        int tmpPatientID = tmpVisit.getVisitID();
        if (conn != null) {
            statement = conn.createStatement();
            String sql = "SELECT resume FROM visit WHERE visitID = '"+tmpPatientID+"'; ";

            System.out.println("Status changed");
            statement.executeUpdate(sql);
            statement.close();

            infoTable.getItems().clear();
            infoTable.getItems().addAll(visitListObservable);
            infoTable.refresh();


        }
    }

    int findInList(int visitID){
        for(int i = 0 ;i < visitListObservable.size();i++){
            if(visitListObservable.get(i).getVisitID()==visitID)
                return i;
        }
       return -1;
    }
    public void addResume() throws SQLException {
        visit tmpVisit = infoTable.getSelectionModel().getSelectedItem();

        String result = resumeTextField.getText();
        tmpVisit.setResult(result);

        int visitID = tmpVisit.getVisitID();
        int index =findInList(visitID);
        visitListObservable.get(index).setResult(result);

        if (conn != null) {

            statement = conn.createStatement();
            String sql = "UPDATE visit SET result = '"+result+"' WHERE visitID = "+visitID+"; ";

            statement.executeUpdate(sql);

            statement.close();

            infoTable.getItems().clear();
            infoTable.getItems().addAll(visitListObservable);
            infoTable.refresh();

        }

    }

    public void getConnection(loginModel tmpLoginModel,int id){
        this.loginModel = tmpLoginModel;
        conn = loginModel.connection;
        this.doctorID = id;
        this.doctorIDLabel.setText( "Doctor ID: " + Integer.toString(doctorID) + "");
    }


}
