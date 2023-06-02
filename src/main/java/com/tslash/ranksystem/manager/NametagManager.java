package com.tslash.ranksystem.manager;

import com.tslash.ranksystem.Rank;
import com.tslash.ranksystem.RankList;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

public class NametagManager {

    private Rank main;

    public NametagManager(Rank main) { this.main = main; }

    public void setNametags(Player player) {
        player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
        for (RankList rank : RankList.values()) {
            Team team = player.getScoreboard().registerNewTeam(rank.name());
            team.setPrefix(rank.getDisplay() + " ");
        }

        for (Player target : Bukkit.getOnlinePlayers()) {
            if (player.getUniqueId() != target.getUniqueId()) {
                player.getScoreboard().getTeam(main.getRankManager().getRank(target.getUniqueId()).name()).addEntry(target.getName());
            }
        }
    }

    public void newTag(Player player) {
        RankList rank = main.getRankManager().getRank(player.getUniqueId());
        for (Player target : Bukkit.getOnlinePlayers()) {
            target.getScoreboard().getTeam(rank.name()).addEntry(player.getName());
        }
    }

    public void removeTag(Player player) {
        for (Player target : Bukkit.getOnlinePlayers()) {
            target.getScoreboard().getEntryTeam(player.getName()).removeEntry(player.getName());
        }
    }

}
