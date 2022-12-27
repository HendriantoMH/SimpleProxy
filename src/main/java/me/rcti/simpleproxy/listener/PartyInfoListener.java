package me.rcti.simpleproxy.listener;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PartyInfoListener implements Listener {
    public PartyInfoListener() {
    }

    @EventHandler
    public void onChat(ChatEvent event) {
        ProxiedPlayer player = (ProxiedPlayer) event.getSender();
        String message = event.getMessage();

        if (message.equals("/help")) {
            event.setCancelled(true);
            player.sendMessage("§8§m--------------------------------");
            player.sendMessage("");
            player.sendMessage("§8» §c" + message + " §8● §7Page");
            player.sendMessage("");
            player.sendMessage("§8» §c/hub §8● §7Connect to lobby server");
            player.sendMessage("§8» §c/link §8● §7Verifiziere dich auf dem Ts");
            player.sendMessage("§8» §c/ping §8● §7Check your ping");
            player.sendMessage("§8» §c/stats §8● §7View your statistics");
            player.sendMessage("§8» §c/coins §8● §7See the coins you have");
            player.sendMessage("§8» §c/party §8● §7Manage your party");
            player.sendMessage("");
            player.sendMessage("§8§m--------------------------------");
        }
    }
}
