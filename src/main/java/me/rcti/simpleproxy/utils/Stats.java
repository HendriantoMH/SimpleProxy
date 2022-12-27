package me.rcti.simpleproxy.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Stats {
    private final String HOST;
    private final String DATABASE;
    private final String USER;
    private final String PASSWORD;
    private Connection connection;

    public Stats(String host, String database, String user, String password) {
        this.HOST = host;
        this.DATABASE = database;
        this.USER = user;
        this.PASSWORD = password;
        this.connect();
    }

    public void connect() {
        try {
            this.connection = DriverManager.getConnection("jdbc:mysql://" + this.HOST + ":3306/" + this.DATABASE + "?autoReconnect=true", this.USER, this.PASSWORD);
            System.out.println("[MySQL] MySQL has connected!");
        } catch (SQLException exception) {
            System.out.println("[MySQL] MySQL failed to connect! ERROR: " + exception.getMessage());
        }

    }

    public void close() {
        try {
            if (this.connection != null) {
                this.connection.close();
                System.out.println("[MySQL] MySQL connection was terminated successfully!");
            }
        } catch (SQLException exception) {
            System.out.println("[MySQL] Failed to stopping MySQL connection! ERROR: " + exception.getMessage());
        }

    }

    public void update(String qry) {
        Statement statement = null;

        try {
            statement = this.connection.createStatement();
            statement.executeUpdate(qry);
        } catch (Exception exception) {
            this.connect();
            System.err.println(exception);
        }

        closeStatement(statement);
    }

    public ResultSet query(String qry) {
        ResultSet resultSet = null;

        try {
            Statement statement = this.connection.createStatement();
            resultSet = statement.executeQuery(qry);
        } catch (SQLException exception) {
            this.connect();
            System.err.println(exception);
        }

        return resultSet;
    }

    public static void closeStatement(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }

    }

    public static void closeResultset(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }

    }
}
