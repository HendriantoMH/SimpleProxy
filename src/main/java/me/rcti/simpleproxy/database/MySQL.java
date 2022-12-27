package me.rcti.simpleproxy.database;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import me.rcti.simpleproxy.Main;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class MySQL {
    public static Connection connection;

    public MySQL() {
    }

    public static void connect() throws IOException {
        try {
            if (!Main.getInstance().getDataFolder().exists()) {
                Main.getInstance().getDataFolder().mkdir();
            }

            File file = new File(Main.getInstance().getDataFolder(), "config.yml");
            if (!file.exists()) {
                try {
                    Throwable throwable = null;
                    Object object = null;


                    try {
                        InputStream in = Main.getInstance().getResourceAsStream("config.yml");

                        try {
                            Files.copy(in, file.toPath());
                        } finally {
                            if (in != null) {
                                in.close();
                            }

                        }
                    } catch (Throwable throwable1) {
                        if (throwable == null) {
                            throwable = throwable1;
                        } else if (throwable != throwable1) {
                            throwable.addSuppressed(throwable1);
                        }

                        throw throwable1;
                    }
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }

            Configuration configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(Main.getInstance().getDataFolder(), "config.yml"));
            connection = DriverManager.getConnection("jdbc:mysql://" + configuration.getString("Host") + ":3306/" + configuration.getString("Database") + "?autoReconnect=true", configuration.getString("User"), configuration.getString("Password"));
            System.out.println("[ProxySystem] MySQL connected!");
        } catch (SQLException exception) {
            System.out.println("[ProxySystem] MySQL connection failed!");
        }
    }

    public static void close() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public static void update(String qry) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(qry);
            statement.close();
        } catch (SQLException exception) {
            try {
                connect();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

            System.err.println(exception);
        }

    }

    public static boolean isConnected() {
        return connection != null;
    }

    public static void createTable() {
        if (isConnected()) {
            try {
                connection.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS TeamLogin (Name VARCHAR(100), active int);");
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }

    }

    public static ResultSet getResult(String qry) {
        if (isConnected()) {
            try {
                return connection.createStatement().executeQuery(qry);
            } catch (SQLException connection) {
                connection.printStackTrace();
            }
        }
        return null;
    }

    public ResultSet query(String qry) {
        ResultSet resultSet = null;

        try {
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(qry);
        } catch (SQLException exception) {
            try {
                connect();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

            System.err.println(exception);
        }

        return resultSet;
    }
}
