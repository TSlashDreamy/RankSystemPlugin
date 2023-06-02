package com.tslash.ranksystem;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RankCommand implements CommandExecutor {
    private Rank main;

    public RankCommand(Rank main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;

            if (player.isOp()) {
                if (args.length == 2) {
                    if (Bukkit.getOfflinePlayer(args[0]) != null) {
                        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);

                        for (RankList rank : RankList.values()) {
                            if (rank.name().equalsIgnoreCase(args[1])) {
                                main.getRankManager().setRank(target.getUniqueId(), rank, false);
                                player.sendMessage(ChatColor.GREEN + "You changed " + target.getName() + "'s rank to " + rank.getDisplay() + ChatColor.GREEN + ".");

                                if (target.isOnline()) {
                                    target.getPlayer().sendMessage(ChatColor.GREEN + player.getName() + " set your rank to " + rank.getDisplay() + ChatColor.GREEN + ".");
                                }
                                return false;
                            }
                        }

                        player.sendMessage(ChatColor.RED + "You did not specify a valid rank! Options: Guest, Member, Admin, Owner.");
                    } else {
                        player.sendMessage(ChatColor.RED + "This player has never played on the server!");
                    }
                } else {
                    player.sendMessage(ChatColor.RED + "Invalid usage! Try: /rank <player> <rank>.");
                }
            } else {
                player.sendMessage(ChatColor.RED + "This command is for OP!");
            }
        }


        return false;
    }
}
