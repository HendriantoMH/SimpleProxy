package me.rcti.simpleproxy.utils;

import me.rcti.simpleproxy.Main;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UUID {
    public UUID() {
    }

    public static boolean playerExists(String name) {
        ResultSet resultSet = Main.getInstance().mysql.query("select * from uuids where name= '" + name.toLowerCase() + "'");

        try {
            if (resultSet.next() && resultSet.getString("name") != null) {
                Stats.closeResultset(resultSet);
                return true;
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        Stats.closeResultset(resultSet);
        return false;
    }

    public static void createPlayerData(String name, String uuid) {
        if (!playerExists(name)) {
            Main.getInstance().mysql.update("insert into uuids (name, uniqueid, playername) values ('" + name.toLowerCase() + "','" + uuid + "', '" + name + "');");
        }

        setName(name, uuid);
    }

    public static String getUUID(String name) {
        ResultSet resultSet = Main.getInstance().mysql.query("select * from uuids where name= '" + name.toLowerCase() + "'");

        try {
            if (resultSet.next()) {
                String uuid = resultSet.getString("uniqueid");
                Stats.closeResultset(resultSet);
                return uuid;
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        Stats.closeResultset(resultSet);
        return null;
    }

    public static String getName(String name) {
        ResultSet resultSet = Main.getInstance().mysql.query("select * from uuids where name= '" + name.toLowerCase() + "'");

        try {
            if (resultSet.next()) {
                String uuid = resultSet.getString("playername");
                Stats.closeResultset(resultSet);
                return uuid;
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        Stats.closeResultset(resultSet);
        return null;
    }

    public static void setName(String name, String uuid) {
        Main.getInstance().mysql.update("update uuids set name= '" + name.toLowerCase() + "' where uniqueid= '" + uuid + "';");
    }
}
