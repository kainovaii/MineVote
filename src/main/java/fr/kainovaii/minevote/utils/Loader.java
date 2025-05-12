package fr.kainovaii.minevote.utils;

import co.aikar.commands.PaperCommandManager;
import fr.kainovaii.minevote.MineVote;
import fr.kainovaii.minevote.command.MainCommand;
import fr.kainovaii.minevote.config.ConfigManager;
import fr.kainovaii.minevote.listeners.PlayerJoinListener;
import fr.kainovaii.minevote.listeners.VotifierListener;
import fr.kainovaii.minevote.utils.gui.InventoryManager;
import org.bukkit.Bukkit;

public class Loader
{
    MineVote mineVote;
    public ConfigManager configManager;

    public Loader (MineVote plugin) {
        this.mineVote = plugin;
        InventoryManager.register(plugin);
    }

    public void registerMotd()
    {
        mineVote.getLogger().info("▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬");
        mineVote.getLogger().info("┳┳┓•    ┓┏       ┳┓    ┓┏┓  •    ┓┏  •• \u001B[0m");
        mineVote.getLogger().info("┃┃┃┓┏┓┏┓┃┃┏┓╋┏┓  ┣┫┓┏  ┃┫ ┏┓┓┏┓┏┓┃┃┏┓┓┓\u001B[0m");
        mineVote.getLogger().info("┛ ┗┗┛┗┗ ┗┛┗┛┗┗   ┻┛┗┫  ┛┗┛┗┻┗┛┗┗┛┗┛┗┻┗┗ \u001B[0m");
        mineVote.getLogger().info("▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬");
    }

    public void registerConfig ()
    {
        mineVote.getLogger().info("Loading configs");
        configManager = new ConfigManager(mineVote);
        configManager.loadConfigs();
    }

    public void registerListener()
    {
        mineVote.getLogger().info("Loading listeners");
        mineVote.getServer().getPluginManager().registerEvents(new PlayerJoinListener(mineVote), mineVote);
        mineVote.getServer().getPluginManager().registerEvents(new VotifierListener(mineVote), mineVote);
    }

    public void registerCommand()
    {
        mineVote.getLogger().info("Loading commands");
        PaperCommandManager commandManager = new PaperCommandManager(mineVote);
        commandManager.registerCommand(new MainCommand());
    }

    public void registerExpansion ()
    {
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {new MineVotePapiExpansion().register();}
    }

    public void connectDatabase()
    {
        mineVote.getLogger().info("Loading database");
        SQLite sqLite = new SQLite();
        sqLite.connectDatabase();
        sqLite.ensureTableExists();
    }
}
