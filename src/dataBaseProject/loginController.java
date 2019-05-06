package dataBaseProject;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.security.auth.spi.LoginModule;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class loginController implements Initializable {

    public loginModel loginModel = new loginModel();
    private String login;
    private String password;

    private Connection conn = null;
    private Statement statement = null;

    @FXML
    private Label logAsLabel;

    @FXML
    private Button administratorButton;

    @FXML
    private Button doctorButton;

    @FXML
    private Label passLabel;

    @FXML
    private Label loginLabel;

    @FXML
    private TextField loginTextField;

    @FXML
    void bf3939(ActionEvent event) {

    }

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private Label connectionButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        if(loginModel.isDbConnected()){
            connectionButton.setText("CONNECTED!");
        }else {
            connectionButton.setText("DISCONNECTED!!");
        }
    }

    public void loginAsDoctor() throws IOException, SQLException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("doctor.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();

        conn = loginModel.connection;

        int doctorID = getDoctorID();

        doctorController doctorController = fxmlLoader.getController();
        doctorController.getConnection(this.loginModel,doctorID);

        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        stage.show();
    }

    public void loginAsAdministrator() throws IOException, SQLException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("admin.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();

        conn = loginModel.connection;

        int adminID = getAdmninID();
        administratorController administratorController = fxmlLoader.getController();
        administratorController.getConnection(this.loginModel,adminID);

        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        stage.show();
    }

    public int getAdmninID() throws SQLException {

        int tmpID = 0;
        this.login = loginTextField.getText();
        this.password = passwordTextField.getText();
       // this.password = passTextField.getText();

        if(conn!=null) {

            statement = conn.createStatement();
            String sql = "SELECT admin.adminID FROM admin WHERE (admin.username = '"+login+"' AND admin.password = '"+password+"');";

            ResultSet rs = statement.executeQuery(sql);

            tmpID = rs.getInt("adminID");

            System.out.println(tmpID);
            rs.close();
        }
        return tmpID;
    }

    public int getDoctorID() throws SQLException
    {
        int tmpID = 0;
    this.login = loginTextField.getText();
    this.password = passwordTextField.getText();

    if(conn!=null) {

        statement = conn.createStatement();
        String sql = "SELECT doctor.doctorID FROM doctor WHERE (doctor.username = '"+login+"' AND doctor.password = '"+password+"');";

        ResultSet rs = statement.executeQuery(sql);

        tmpID = rs.getInt("doctorID");

        System.out.println(tmpID);
        rs.close();
    }
        return tmpID;
    }

}
