package fr.kainovaii.minevote;

import co.aikar.commands.PaperCommandManager;
import fr.kainovaii.minevote.command.MainCommand;
import fr.kainovaii.minevote.config.ConfigManager;
import fr.kainovaii.minevote.listeners.VotifierListener;
import fr.kainovaii.minevote.utils.Loader;
import fr.kainovaii.minevote.utils.VotesManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class MineVote extends JavaPlugin
{
    private static MineVote instance;
    public ConfigManager configManager;
    private VotesManager votesManager;

    @Override
    public void onEnable()
    {
        instance = this;
        Loader loader = new Loader(this);
        loader.registerMotd();
        loader.registerConfig();
        this.configManager = loader.configManager;
        this.votesManager = new VotesManager(this);
        loader.connectDatabase();
        loader.registerListener();
        loader.registerCommand();
        loader.registerExpansion();
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public VotesManager getVotesManager() { return votesManager; }

    public static MineVote getInstance()
    {
        return instance;
    }

    @Override
    public void onDisable() {}
}