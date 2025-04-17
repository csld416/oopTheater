package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author csld
 */
public class DatabaseConnection {

    private static final String DB_NAME = "oopMovieDB";
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/" + DB_NAME;
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "";

    private Connection connection;

    public DatabaseConnection() {
        try {

            //load the JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            //create connection to the database
            connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);

            if (connection != null) {
                System.out.println("Connected!");
            }

        } catch (ClassNotFoundException ex) {
            System.out.println("JDBC Driver not found!");
        } catch (SQLException ex) {
            System.out.println("Failed to connect - " + ex.getMessage());
        }
    }

    //method to the connection
    public Connection getConnection() {
        return connection;
    }

}
