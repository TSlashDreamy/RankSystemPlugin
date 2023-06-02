package com.tslash.ranksystem;

import com.tslash.ranksystem.manager.NametagManager;
import com.tslash.ranksystem.manager.RankManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Rank extends JavaPlugin {

    private RankManager rankManager;
    private NametagManager nametagManager;

    @Override
    public void onEnable() {
        getCommand("rank").setExecutor(new RankCommand(this));

        rankManager = new RankManager(this);
        nametagManager = new NametagManager(this);

        Bukkit.getPluginManager().registerEvents(new RankListener(this), this);
    }

    public RankManager getRankManager() { return rankManager; }
    public NametagManager getNametagManager() { return nametagManager; }
}
