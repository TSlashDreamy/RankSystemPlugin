package com.tslash.ranksystem;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.permissions.PermissionAttachment;

import java.util.HashMap;
import java.util.UUID;

public class RankListener implements Listener {
    private Rank main;
    private HashMap<UUID, PermissionAttachment> perms;

    public RankListener(Rank main) {
        this.main = main;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();

        if (!player.hasPlayedBefore()) {
            main.getRankManager().setRank(player.getUniqueId(), RankList.GUEST, true);
        }

        main.getNametagManager().setNametags(player);
        main.getNametagManager().newTag(player);

        PermissionAttachment attachment;
        perms = main.getRankManager().getPerms();

        if (perms.containsKey(player.getUniqueId())) {
            attachment = perms.get(player.getUniqueId());
        } else {
            attachment = player.addAttachment(main);
            perms.put(player.getUniqueId(), attachment);
        }

        for (String perm : main.getRankManager().getRank(player.getUniqueId()).getPermissions()) {
            attachment.setPermission(perm, true);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        main.getNametagManager().removeTag(e.getPlayer());
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        e.setCancelled(true);

        Player player = e.getPlayer();

        Bukkit.broadcastMessage(main.getRankManager().getRank(player.getUniqueId()).getDisplay() + " " + player.getName() + ": " + ChatColor.GRAY + e.getMessage());
    }

}
