package me.rcti.simpleproxy.commands;

import java.util.Iterator;

import me.rcti.simpleproxy.Main;
import me.rcti.simpleproxy.utils.CC;
import me.rcti.simpleproxy.manager.PartyManager;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.connection.Server;
import net.md_5.bungee.api.plugin.Command;

public class PartyCommand extends Command {

    public PartyCommand() {
        super("party");
    }

    @Deprecated
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof ProxiedPlayer) {
            ProxiedPlayer player = (ProxiedPlayer) sender;
            if (args.length < 1) {
                player.sendMessage(CC.translate("&8&m--------------------------------"));
                player.sendMessage("");
                player.sendMessage(CC.translate("&8» &c/party help &8● &7cPage"));
                player.sendMessage(CC.translate("&8» &c/p &8● &7Write in party chat"));
                player.sendMessage("");
                player.sendMessage(CC.translate("&8» &c/party invite <player> &8● &7Invite a player to your party"));
                player.sendMessage(CC.translate("&8» &c/party list &8● &7List of players in your party"));
                player.sendMessage(CC.translate("&8» &c/party leave &8● &7Leave your party"));
                player.sendMessage(CC.translate("&8» &c/party accept &8● &7Accept a party invitation"));
                player.sendMessage(CC.translate("&8» &c/party kick <player> &8● &7Kick a player from your party"));
                player.sendMessage("");
                player.sendMessage(CC.translate("&8&m--------------------------------"));
            } else if (args[0].equalsIgnoreCase("create")) {
                PartyManager.newParty(player);
            } else if (args[0].equalsIgnoreCase("list")) {
                PartyManager.listParty(player);
            } else if (args[0].equalsIgnoreCase("leave")) {
                PartyManager.leaveParty(player);
            } else {
                ProxiedPlayer target;
                Iterator iterator;
                if (args[0].equalsIgnoreCase("join")) {
                    if (args.length > 1) {
                        iterator = ProxyServer.getInstance().getPlayers().iterator();

                        while(iterator.hasNext()) {
                            player = (ProxiedPlayer) iterator.next();
                            if (player.getName().equalsIgnoreCase(args[1])) {
                                if (PartyManager.inparty.containsKey(player)) {
                                    target = ProxyServer.getInstance().getPlayer(args[1]);
                                    Server server = target.getServer();
                                    player.connect(server.getInfo());
                                } else {
                                    player.sendMessage(Main.getInstance().data.partyprefix + CC.translate("&cThis player is not in a party!"));
                                }
                            } else {
                                player.sendMessage(Main.getInstance().data.partyprefix + CC.translate("&cThis player is not online!"));
                            }
                        }
                    }
                } else if (args[0].equalsIgnoreCase("invite")) {
                    if (args.length > 1) {
                        iterator = ProxyServer.getInstance().getPlayers().iterator();

                        while(iterator.hasNext()) {
                            player = (ProxiedPlayer) iterator.next();
                            if (player.getName().equalsIgnoreCase(args[1])) {
                                target = ProxyServer.getInstance().getPlayer(args[1]);
                                PartyManager.onPartyInvite(player, target);
                                return;
                            }
                        }

                        player.sendMessage(Main.getInstance().data.partyprefix + CC.translate("&cThis player is not online!"));
                    } else {
                        player.sendMessage(Main.getInstance().data.partyprefix + CC.translate("&c/party invite <player>"));
                    }
                } else if (args[0].equalsIgnoreCase("accept")) {
                    PartyManager.onPartyAccept(player);
                } else if (args[0].equalsIgnoreCase("kick")) {
                    if (args.length > 1) {
                        iterator = ProxyServer.getInstance().getPlayers().iterator();

                        while(iterator.hasNext()) {
                            player = (ProxiedPlayer) iterator.next();
                            if (player.getName().equalsIgnoreCase(args[1])) {
                                PartyManager.onPartyKick(player, player);
                                return;
                            }
                        }

                        player.sendMessage(Main.getInstance().data.partyprefix + CC.translate("&cThis player is not online!"));
                    } else {
                        player.sendMessage(Main.getInstance().data.partyprefix + CC.translate("&c/party kick <player>"));
                    }
                } else if (args[0].equalsIgnoreCase("help")) {
                    player.sendMessage(CC.translate("&8&m--------------------------------"));
                    player.sendMessage("");
                    player.sendMessage(CC.translate("&8» &c/party help &8● &7cPage"));
                    player.sendMessage(CC.translate("&8» &c/p &8● &7Write in party chat"));
                    player.sendMessage("");
                    player.sendMessage(CC.translate("&8» &c/party invite <player> &8● &7Invite a player to your party"));
                    player.sendMessage(CC.translate("&8» &c/party list &8● &7List of players in your party"));
                    player.sendMessage(CC.translate("&8» &c/party leave &8● &7Leave your party"));
                    player.sendMessage(CC.translate("&8» &c/party accept &8● &7Accept a party invitation"));
                    player.sendMessage(CC.translate("&8» &c/party kick <player> &8● &7Kick a player from your party"));
                    player.sendMessage("");
                    player.sendMessage(CC.translate("&8&m--------------------------------"));
                } else {
                    player.sendMessage(CC.translate("&8&m--------------------------------"));
                    player.sendMessage("");
                    player.sendMessage(CC.translate("&8» &c/party help &8● &7cPage"));
                    player.sendMessage(CC.translate("&8» &c/p &8● &7Write in party chat"));
                    player.sendMessage("");
                    player.sendMessage(CC.translate("&8» &c/party invite <player> &8● &7Invite a player to your party"));
                    player.sendMessage(CC.translate("&8» &c/party list &8● &7List of players in your party"));
                    player.sendMessage(CC.translate("&8» &c/party leave &8● &7Leave your party"));
                    player.sendMessage(CC.translate("&8» &c/party accept &8● &7Accept a party invitation"));
                    player.sendMessage(CC.translate("&8» &c/party kick <player> &8● &7Kick a player from your party"));
                    player.sendMessage("");
                    player.sendMessage(CC.translate("&8&m--------------------------------"));
                }
            }
        }

    }
}
