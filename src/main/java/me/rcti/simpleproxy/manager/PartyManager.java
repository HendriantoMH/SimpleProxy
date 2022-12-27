package me.rcti.simpleproxy.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Objects;

import me.rcti.simpleproxy.Main;
import me.rcti.simpleproxy.utils.CC;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import net.md_5.bungee.api.connection.ProxiedPlayer;

@Deprecated
public class PartyManager {
    public static ArrayList<String> partyleader = new ArrayList();
    public static HashMap<String, String> inparty = new HashMap();
    public static HashMap<String, Long> invitetime = new HashMap();
    public static HashMap<String, String> invite = new HashMap();
    private static Integer maxparty = 4;

    public PartyManager() {
    }

    public static void newParty(ProxiedPlayer player) {
        if (inparty.containsKey(player.getName())) {
            player.sendMessage(Main.getInstance().data.partyprefix + CC.translate("&cYou're not at the party!"));
        } else if (partyleader.contains(player.getName())) {
            player.sendMessage(Main.getInstance().data.partyprefix + CC.translate("&cYou're already in a party!"));
        } else {
            partyleader.add(player.getName());
        }
    }

    public static void listParty(ProxiedPlayer player) {
        ProxiedPlayer target;
        Iterator iterator;

        if (partyleader.contains(player.getName())) {
            player.sendMessage(Main.getInstance().data.partyprefix + CC.translate("&7Leader: &f" + player.getServer().getInfo().getName()));
            player.sendMessage(Main.getInstance().data.partyprefix + CC.translate("&7Members:"));
            iterator = ProxyServer.getInstance().getPlayers().iterator();

            while(iterator.hasNext()) {
                target = (ProxiedPlayer) iterator.next();
                if (inparty.containsKey(target.getName()) && inparty.get(target.getName()) == player.getName()) {
                    player.sendMessage(Main.getInstance().data.partyprefix + "§c" + " §8● §f" + target.getServer().getInfo().getName());
                }
            }
        }

        if (inparty.containsKey(player.getName())) {
            try {
                player.sendMessage(Main.getInstance().data.partyprefix + "§7Leader: §f" + (String)inparty.get(player.getName()) + " §8● §f" + ProxyServer.getInstance().getPlayer((String)inparty.get(player.getName())).getServer().getInfo().getName());
            } catch (NullPointerException ignored) {
            }

            iterator = ProxyServer.getInstance().getPlayers().iterator();

            while(iterator.hasNext()) {
                target = (ProxiedPlayer)iterator.next();
                if (inparty.containsKey(target.getName()) && inparty.get(target.getName()) == inparty.get(player.getName())) {
                    player.sendMessage(Main.getInstance().data.partyprefix + "§f" + target.getName() + " §8● §f" + target.getServer().getInfo().getName());
                }
            }
        }

        if (!inparty.containsKey(player.getName()) && !partyleader.contains(player.getName())) {
            player.sendMessage(Main.getInstance().data.partyprefix + CC.translate("&cYou're not in a party!"));
        }
    }

    public static void leaveParty(ProxiedPlayer player) {
        ProxiedPlayer target;
        Iterator iterator;
        if (inparty.containsKey(player.getName())) {
            player.sendMessage(Main.getInstance().data.partyprefix + CC.translate("&cYou left the party!"));
            iterator = ProxyServer.getInstance().getPlayers().iterator();

            while(iterator.hasNext()) {
                target = (ProxiedPlayer) iterator.next();
                if (Objects.equals(inparty.get(target.getName()), inparty.get(player.getName()))) {
                    target.sendMessage(Main.getInstance().data.partyprefix + CC.translate("&f" + player.getName() + " &fcleft the party!"));
                }
            }
            inparty.remove(player.getName());
        } else if (partyleader.contains(player.getName())) {
            player.sendMessage(Main.getInstance().data.partyprefix + CC.translate("&cParty was disbanded due to missing players!"));
            partyleader.remove(player.getName());
            iterator = ProxyServer.getInstance().getPlayers().iterator();

            while(iterator.hasNext()) {
                target = (ProxiedPlayer) iterator.next();
                if (inparty.containsKey(target.getName()) && Objects.equals(inparty.get(target.getName()), player.getName())) {
                    player.sendMessage(Main.getInstance().data.partyprefix + CC.translate("&cParty was disbanded due to missing players!"));
                    inparty.remove(target.getName());
                }
            }
        } else {
            if (!inparty.containsKey(player.getName()) && !partyleader.contains(player.getName())) {
                player.sendMessage(Main.getInstance().data.partyprefix + CC.translate("&cYou're not in a party!"));
            }
        }
    }

    public static void onPartyChat(ProxiedPlayer player, String text) {
        text = ChatColor.translateAlternateColorCodes('&', text);
        ProxiedPlayer target;
        Iterator iterator;
        if (partyleader.contains(player.getName())) {
            player.sendMessage(Main.getInstance().data.partychat + CC.translate("&r" + player.getName() + " &8» &7" + text));
            iterator = ProxyServer.getInstance().getPlayers().iterator();

            while(iterator.hasNext()) {
                target = (ProxiedPlayer) iterator.next();
                if (inparty.containsKey(target.getName()) && Objects.equals(inparty.get(target.getName()), player.getName())) {
                    target.sendMessage(Main.getInstance().data.partychat + CC.translate("&r" + player.getName() + " &8» &7" + text));
                }
            }
        }

        if (inparty.containsKey(player.getName())) {
            ProxyServer.getInstance().getPlayer((String)inparty.get(player.getName())).sendMessage(Main.getInstance().data.partychat + CC.translate("&r" + player.getName() + " &8» &7" + text));
            iterator = ProxyServer.getInstance().getPlayers().iterator();

            while(iterator.hasNext()) {
                target = (ProxiedPlayer) iterator.next();
                if (inparty.containsKey(target.getName()) && inparty.get(target.getName()) == inparty.get(player.getName())) {
                    target.sendMessage(Main.getInstance().data.partychat + CC.translate("&r" + player.getName() + " &8» &7" + text));
                }
            }
        }

        if (!inparty.containsKey(player.getName()) && !partyleader.contains(player.getName())) {
            player.sendMessage(Main.getInstance().data.partyprefix + CC.translate("&cYou're not in a party!"));
        }
    }

    public static void onPartyInvite(ProxiedPlayer player, ProxiedPlayer target) {
        long time = System.currentTimeMillis();
        if (partyleader.contains(target.getName())) {
            Integer iparty = 0;
            Iterator iterator = ProxyServer.getInstance().getPlayers().iterator();

            while(iterator.hasNext()) {
                ProxiedPlayer i = (ProxiedPlayer) iterator.next();
                if (inparty.containsKey(i.getName()) && inparty.get(i.getName()) == player.getName()) {
                    iparty = iparty + 1;
                }
            }

            if (iparty >= maxparty) {
                player.sendMessage(Main.getInstance().data.partyprefix + CC.translate("&cThe party is full!"));
                return;
            }

            if (inparty.containsKey(target.getName())) {
                player.sendMessage(Main.getInstance().data.partyprefix + CC.translate("&cThis player is already in a party!"));
                return;
            }

            if (partyleader.contains(target.getName())) {
                player.sendMessage(Main.getInstance().data.partyprefix + CC.translate("&cThis player is already in a party!"));
                return;
            }

            if (!inparty.containsKey(target.getName()) && !partyleader.contains(target.getName())) {
                invite.put(target.getName(), player.getName());
                invitetime.put(target.getName(), time);
                player.sendMessage(Main.getInstance().data.partyprefix + CC.translate("&f" + target.getName() + " &7has been invited to your party!"));
                target.sendMessage(Main.getInstance().data.partyprefix + CC.translate("&f" + player.getName() + " &7has invited you to a party!"));

                TextComponent textComponent = new TextComponent(Main.getInstance().data.partyprefix + CC.translate("&7Click &8[&a&lHERE&8] &7to accept the invite!"));
                textComponent.setClickEvent(new ClickEvent(Action.RUN_COMMAND, "/party accept " + player.getName()));
                textComponent.setHoverEvent(new HoverEvent(net.md_5.bungee.api.chat.HoverEvent.Action.SHOW_TEXT, (new ComponentBuilder(CC.translate("&7Click to accept the invite!")).create())));
                target.sendMessage(textComponent);
                return;
            }
        } else if (inparty.containsKey(player.getName())) {
            player.sendMessage(Main.getInstance().data.partyprefix + CC.translate("&cYou're not the party leader!"));
        } else if (!inparty.containsKey(player.getName()) && !partyleader.contains(player.getName())) {
            newParty(player);
            onPartyInvite(player, target);
        }

    }

    public static void onPartyAccept(ProxiedPlayer player) {
        if (partyleader.contains(player.getName()) | inparty.containsKey(player.getName())) {
            player.sendMessage(Main.getInstance().data.partyprefix + CC.translate("&cYou're already in a party!"));
        } else if (invite.containsKey(player.getName())) {
            Long time = System.currentTimeMillis();
            Long diff = time / 1000L - invitetime.get(player.getName()) / 1000L;
            if (diff > 60L) {
                player.sendMessage(Main.getInstance().data.partyprefix + CC.translate("&cThe invite has expired!"));
            } else {
                ProxiedPlayer target = ProxyServer.getInstance().getPlayer(invite.get(player.getName()));
                target.sendMessage(Main.getInstance().data.partyprefix + CC.translate("&f" + player.getName() + " &7has joined your party!"));
                invite.remove(player.getName());
                invitetime.remove(player.getName());
                inparty.put(player.getName(), target.getName());
                player.sendMessage(Main.getInstance().data.partyprefix + CC.translate("&7You have joined &f" + target.getName() + "'s &7party!"));
            }
        } else {
            player.sendMessage(Main.getInstance().data.partyprefix + CC.translate("&cYou don't have any invites!"));
        }
    }

    public static void onPartyKick(ProxiedPlayer player, ProxiedPlayer target) {
        if (partyleader.contains(player.getName())) {
            if (inparty.containsKey(target.getName()) && inparty.get(target.getName()) == player.getName()) {
                inparty.remove(target.getName());
                target.sendMessage(Main.getInstance().data.partyprefix + CC.translate("&cYou have been kicked from the party!"));
                player.sendMessage(Main.getInstance().data.partyprefix + CC.translate("&f" + target.getName() + " &7has been kicked from the party!"));
                Iterator iterator = ProxyServer.getInstance().getPlayers().iterator();

                while(iterator.hasNext()) {
                    ProxiedPlayer ip = (ProxiedPlayer) iterator.next();
                    if (inparty.containsKey(ip.getName()) && inparty.get(ip.getName()) == player.getName()) {
                        ip.sendMessage(Main.getInstance().data.partyprefix + CC.translate("&f" + target.getName() + " &7has been kicked from the party!"));
                    }
                }
            } else {
                player.sendMessage(Main.getInstance().data.partyprefix + CC.translate("&cThis player is not in your party!"));
            }
        } else {
            player.sendMessage(Main.getInstance().data.partyprefix + CC.translate("&cYou're not the party leader!"));
        }
    }
}
