package me.rcti.simpleproxy.commands;

import me.rcti.simpleproxy.Main;
import me.rcti.simpleproxy.utils.CC;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class PingCommand extends Command {
    public PingCommand() {
        super("ping");
    }

    public void execute(CommandSender sender, String[] args) {
        if (args.length != 0) {
            sender.sendMessage(Main.getInstance().data.prefix + CC.translate("&cUsage: /ping"));
        } else {
            ProxiedPlayer p = (ProxiedPlayer)sender;
            sender.sendMessage(Main.getInstance().data.prefix + CC.translate("&7Your ping is &r" + p.getPing() + "ms"));
        }
    }

    private String getPing(ProxiedPlayer p) {
        if (p.getPing() <= 31) {
            return "§a" + p.getPing();
        } else if (p.getPing() >= 30) {
            return "§e" + p.getPing();
        } else if (p.getPing() >= 80) {
            return "§c" + p.getPing();
        } else {
            return p.getPing() >= 150 ? "§4" + p.getPing() : "§f" + p.getPing();
        }
    }
}
