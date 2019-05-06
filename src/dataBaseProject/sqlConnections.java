package dataBaseProject;
import java.sql.*;
import java.sql.Connection;

public class sqlConnections {
    public static Connection Connector(){
        try{
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\Eliza\\Desktop\\PWR\\Sem\\JAVA\\lab07\\dataBase\\lab07_DataBase.db");
            return conn;
        }catch (Exception e){
            System.out.println(e);
            return null;
        }

    };
}

