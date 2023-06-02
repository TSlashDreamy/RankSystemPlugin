package com.tslash.ranksystem.manager;

import com.tslash.ranksystem.Rank;
import com.tslash.ranksystem.RankList;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

public class RankManager {

    private Rank main;
    private File file;
    private YamlConfiguration config;

    private HashMap<UUID, PermissionAttachment> perms = new HashMap<>();


    public RankManager(Rank main) {
        this.main = main;

        if (!main.getDataFolder().exists()) {
            main.getDataFolder().mkdir();
        }

        file = new File(main.getDataFolder(), "ranks.yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        config = YamlConfiguration.loadConfiguration(file);
    }

    public void setRank(UUID uuid, RankList rank, boolean firstJoin) {
        if (Bukkit.getOfflinePlayer(uuid).isOnline() && !firstJoin) {
            Player player = Bukkit.getPlayer(uuid);
            if (player == null) return;

            PermissionAttachment attachment;
            if (perms.containsKey(uuid)) {
                attachment = perms.get(uuid);
            } else {
                attachment = player.addAttachment(main);
                perms.put(uuid, attachment);
            }

            for (String perm : getRank(uuid).getPermissions()) {
                if (player.hasPermission(perm)) {
                    attachment.unsetPermission(perm);
                }
            }

            for (String perm : rank.getPermissions()) {
                    attachment.setPermission(perm, true);
            }
        }

        config.set(uuid.toString(), rank.name());
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (Bukkit.getOfflinePlayer(uuid).isOnline()) {
            Player player = Bukkit.getPlayer(uuid);
            main.getNametagManager().removeTag(player);
            main.getNametagManager().newTag(player);
        }


    }

    public RankList getRank(UUID uuid) {
        return RankList.valueOf(config.getString(uuid.toString()));
    }
    public HashMap<UUID, PermissionAttachment> getPerms() { return perms; }
}
