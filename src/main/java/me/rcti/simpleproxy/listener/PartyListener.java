package me.rcti.simpleproxy.listener;

import java.util.Iterator;

import me.rcti.simpleproxy.Main;
import me.rcti.simpleproxy.manager.PartyManager;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PartyListener implements Listener {
    public PartyListener() {
    }

    @EventHandler
    @Deprecated
    public void onSwitch(ServerSwitchEvent event) {
        ProxiedPlayer player = event.getPlayer();
        if (PartyManager.partyleader.contains(player.getName())) {
            ServerInfo serverInfo = player.getServer().getInfo();
            Iterator iterator = ProxyServer.getInstance().getPlayers().iterator();

            while(iterator.hasNext()) {
                ProxiedPlayer target = (ProxiedPlayer) iterator.next();
                if (PartyManager.inparty.containsKey(target.getName()) && PartyManager.inparty.get(target.getName()) == player.getName()) {
                    target.sendMessage(Main.getInstance().data.partyprefix + "§7Party Server: §f" + serverInfo.getName() + "§7.");
                    target.connect(serverInfo);
                }
            }
        }

    }

    @EventHandler
    @Deprecated
    public void leave(PlayerDisconnectEvent e) {
        ProxiedPlayer p = e.getPlayer();
        if (PartyManager.partyleader.contains(p.getName())) {
            PartyManager.leaveParty(p);
        }

    }
}

