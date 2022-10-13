package com.revature.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionManager {
    private static Connection conn;
    private static Properties props;

    public ConnectionManager() {}
    public static Connection getConnection() throws SQLException {
        if (props == null) {
            props = loadProperties();
        }
        if (conn == null || conn.isClosed()) {
            conn = DriverManager.getConnection(
                    props.getProperty("url"),
                    props.getProperty("username"),
                    props.getProperty("password")
            );
        }
        return conn;
    }

    private static Properties loadProperties() {
        Properties props = new Properties();

        try {
            FileInputStream in = new FileInputStream("src/main/resources/project1.properties");
            props.load(in);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return props;
    }
}
