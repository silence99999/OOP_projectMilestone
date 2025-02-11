package org.example.util.db;

import java.sql.*;

public class DBConnect {
    private static final String URL = "jdbc:mysql://localhost:3306/cities_db";
    private static final String USER = "user";
    private static final String PASSWORD = "password";

    public static Connection GetConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");

        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
