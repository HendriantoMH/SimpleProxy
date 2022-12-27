package me.rcti.simpleproxy.listener;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.TabCompleteEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class TabCompleteListener implements Listener {
    public TabCompleteListener() {
    }

    @EventHandler
    public void onTab(TabCompleteEvent e) {
        ProxiedPlayer p = (ProxiedPlayer)e.getSender();
        if (!p.hasPermission("simpleproxy.admin") && e.getCursor().startsWith("/")) {
            e.setCancelled(true);
        }

    }
}
