package dataBaseProject;

import java.sql.Connection;
import java.sql.*;

public class loginModel {
    Connection connection;

    public loginModel(){
        connection = sqlConnections.Connector();
        if(connection == null) System.exit(1);
    }

    public boolean isDbConnected(){

        try {
            return !connection.isClosed();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }
}
