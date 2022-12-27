package me.rcti.simpleproxy;

import me.rcti.simpleproxy.commands.PartyCommand;
import me.rcti.simpleproxy.commands.PingCommand;
import me.rcti.simpleproxy.database.MySQL;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import me.rcti.simpleproxy.listener.PartyInfoListener;
import me.rcti.simpleproxy.listener.PartyListener;
import me.rcti.simpleproxy.listener.TabCompleteListener;
import me.rcti.simpleproxy.manager.DataManager;
import me.rcti.simpleproxy.utils.Stats;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class Main extends Plugin {
    public static Main instance;
    public DataManager data;
    public PluginManager pluginManager;
    public Stats mysql;
    public ArrayList<ProxiedPlayer> teamban = new ArrayList();
    public ExecutorService executorService = Executors.newCachedThreadPool();

    public Main() {
    }

    public void onEnable() {
        this.pluginManager = this.getProxy().getPluginManager();
        instance = this;
        MySQL.createTable();

        try {
            MySQL.connect();
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        try {
            this.connectMySQL();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        this.register();
    }

    private void connectMySQL() throws IOException {
        if (!this.getDataFolder().exists()) {
            this.getDataFolder().mkdir();
        }

        File file = new File(this.getDataFolder(), "config.yml");
        if (!file.exists()) {
            try {
                Throwable throwable = null;
                Object object = null;

                try {
                    InputStream in = this.getResourceAsStream("config.yml");

                    try {
                        Files.copy(in, file.toPath());
                    } finally {
                        if (in != null) {
                            in.close();
                        }

                    }
                } catch (Throwable throwable2) {
                    if (throwable == null) {
                        throwable = throwable2;
                    } else if (throwable != throwable2) {
                        throwable.addSuppressed(throwable2);
                    }

                    throw throwable;
                }
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }

        Configuration configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(this.getDataFolder(), "config.yml"));
        this.mysql = new Stats(configuration.getString("Host"), configuration.getString("Database"), configuration.getString("User"), configuration.getString("Password"));
        ConfigurationProvider.getProvider(YamlConfiguration.class).save(configuration, new File(this.getDataFolder(), "config.yml"));
        this.mysql.update("create table if not exists uuids (name varchar(64), uniqueid varchar(100), playername varchar(100), UNIQUE KEY(name));");
    }

    private void register() {
        pluginManager.registerCommand(this, new PingCommand());
        pluginManager.registerCommand(this, new PartyCommand());
        ProxyServer.getInstance().getPluginManager().registerListener(this, new TabCompleteListener());
        ProxyServer.getInstance().getPluginManager().registerListener(this, new PartyInfoListener());
        ProxyServer.getInstance().getPluginManager().registerListener(this, new PartyListener());
    }

    public void onDisable() {
    }

    public static Main getInstance() {
        return instance;
    }
}