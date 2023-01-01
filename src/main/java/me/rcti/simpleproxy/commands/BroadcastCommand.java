package me.rcti.simpleproxy.commands;

import me.rcti.simpleproxy.Main;
import me.rcti.simpleproxy.utils.CC;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class BroadcastCommand extends Command {
    public BroadcastCommand() {
        super("broadcast", null, "bc");
    }

    @Deprecated
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof ProxiedPlayer) {
            ProxiedPlayer player = (ProxiedPlayer)sender;
            StringBuilder message = new StringBuilder();

            for (String arg : args) {
                message.append(arg).append(" ");
            }

            if (player.hasPermission("simpleproxy.admin")) {
                if (args.length > 0) {
                    if (!message.toString().equals(" ") && !message.toString().equals("")) {
                        ProxyServer.getInstance().broadcast(CC.translate("&f&l[BROADCAST] &7" + message.toString().replace("&", "§")));
                    } else {
                        player.sendMessage(Main.getInstance().data.prefix + CC.translate("&cUsage: /bc <message>"));
                    }
                } else {
                    player.sendMessage(Main.getInstance().data.prefix + CC.translate("&cUsage: /bc <message>"));
                }
            } else {
                player.sendMessage(Main.getInstance().data.unknown("/broadcast"));
            }
        }
    }
}
