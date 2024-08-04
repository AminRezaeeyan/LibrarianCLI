package com.librarian.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtil {
    private static final String DB_URL = "jdbc:sqlite:src/main/resources/libraryData.db";
    private static Connection connection = null;

    public static Connection connect() {
        try {
            if (connection == null) {
                connection = DriverManager.getConnection(DB_URL);
                registerShutdownHook();
            }
            return connection;
        }catch (SQLException e){
            throw new RuntimeException("Failed to connect to database", e);
        }
    }

    // Registers a shutdown hook to close the connection once before application exit
    // This ensures that connection is opened when needed and closed once at the end
    private static void registerShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(DatabaseUtil::closeConnection));
    }

    private static void closeConnection() {
        try {
            if (!connection.isClosed()) connection.close();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to close the connection of database", e);
        }
    }
}
