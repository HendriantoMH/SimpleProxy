package me.rcti.simpleproxy.listener;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import me.rcti.simpleproxy.Main;
import me.rcti.simpleproxy.utils.CC;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class AntiSpamListener implements Listener {
    private ArrayList<ProxiedPlayer> cooldown = new ArrayList<>();

    public AntiSpamListener() {
    }

    @EventHandler
    @Deprecated
    public void onChat(ChatEvent event) {
        final ProxiedPlayer player = (ProxiedPlayer) event.getSender();
        if (!event.getMessage().startsWith("/") && !player.hasPermission("simpleproxy.bypass")) {
            if (this.cooldown.contains(player)) {
                event.setCancelled(true);
                player.sendMessage(Main.getInstance().data.prefix + CC.translate("&cPlease don't spam! You can send a message every 3 seconds."));
            } else {
                this.cooldown.add(player);
                ProxyServer.getInstance().getScheduler().schedule(Main.getInstance(), new Runnable() {
                    public void run() {
                        AntiSpamListener.this.cooldown.remove(player);
                    }
                }, 3L, TimeUnit.SECONDS);
            }
        }
    }
}