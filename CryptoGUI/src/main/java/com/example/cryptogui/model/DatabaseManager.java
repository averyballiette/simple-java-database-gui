package com.example.cryptogui.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {

    private static final String URL = "REQUIRED DB URL";
    private static final String USER = "REQUIRED DB USER";
    private static final String PASSWORD = "REQUIRED DB PASSWORD";

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
