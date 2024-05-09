package tn.esprit.utils;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class MyDataBase {
    private final String URL = "jdbc:mysql://localhost:3306/travelwithme";

    private final String USER = "root";

    private final String PSW  = "";

    private static Connection connection ;
    private static MyDataBase instance  ;

    public MyDataBase()  {
        try {
            connection = DriverManager.getConnection(URL,USER,PSW);
            System.out.println("connected");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("not connected");
        }
    }
    public static MyDataBase getInstance(){
        if (instance == null){
            instance = new MyDataBase();
        }
        return instance ;
    }
    public Connection getConnection() {
        return connection;
    }

}