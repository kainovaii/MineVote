package fr.kainovaii.minevote;

import co.aikar.commands.PaperCommandManager;
import fr.kainovaii.minevote.command.MainCommand;
import fr.kainovaii.minevote.config.ConfigManager;
import fr.kainovaii.minevote.listeners.VotifierListener;
import fr.kainovaii.minevote.utils.Loader;
import fr.kainovaii.minevote.utils.MineVotePapiExpansion;
import fr.kainovaii.minevote.utils.gui.InventoryManager;
import fr.kainovaii.minevote.listeners.PlayerJoinListener;
import fr.kainovaii.minevote.utils.SQLite;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class MineVote extends JavaPlugin
{
    private static MineVote instance;
    private Loader loader;
    public ConfigManager configManager;

    @Override
    public void onEnable()
    {
        instance = this;
        this.loader = new Loader(this);
        loader.registerMotd();
        loader.registerConfig();
        this.configManager = loader.configManager;
        loader.connectDatabase();
        loader.registerListener();
        loader.registerCommand();
        loader.registerExpansion();
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public static MineVote getInstance()
    {
        return instance;
    }

    @Override
    public void onDisable() {}
}