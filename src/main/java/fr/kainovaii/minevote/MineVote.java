package fr.kainovaii.minevote;

import co.aikar.commands.PaperCommandManager;
import co.aikar.taskchain.TaskChainFactory;
import fr.kainovaii.minevote.command.MainCommand;
import fr.kainovaii.minevote.config.ConfigManager;
import fr.kainovaii.minevote.listeners.VotifierListener;
import fr.kainovaii.minevote.utils.MineVotePapiExpansion;
import fr.kainovaii.minevote.utils.gui.InventoryManager;
import fr.kainovaii.minevote.listeners.PlayerJoinListener;
import fr.kainovaii.minevote.utils.SQLite;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class MineVote extends JavaPlugin
{
    private static MineVote instance;
    private ConfigManager configManager;
    private PaperCommandManager commandManager;
    private SQLite sqLite;
    private TaskChainFactory taskChainFactory;

    @Override
    public void onEnable() {
        this.registerMotd();
        instance = this;
        this.registerConfig();
        this.connectDatabase();
        this.registerListener();
        this.registerCommand();
        InventoryManager.register(this);
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new MineVotePapiExpansion().register();
        }
    }

    private void registerMotd()
    {
        getLogger().info("▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬");
        getLogger().info("┳┳┓•    ┓┏       ┳┓    ┓┏┓  •    ┓┏  •• \u001B[0m");
        getLogger().info("┃┃┃┓┏┓┏┓┃┃┏┓╋┏┓  ┣┫┓┏  ┃┫ ┏┓┓┏┓┏┓┃┃┏┓┓┓\u001B[0m");
        getLogger().info("┛ ┗┗┛┗┗ ┗┛┗┛┗┗   ┻┛┗┫  ┛┗┛┗┻┗┛┗┗┛┗┛┗┻┗┗ \u001B[0m");
        getLogger().info("▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬");
    }

    public void registerConfig ()
    {
        configManager = new ConfigManager(this);
        configManager.loadConfigs();
    }

    private void registerListener()
    {
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
        getServer().getPluginManager().registerEvents(new VotifierListener(this), this);
    }

    public void registerCommand()
    {
        commandManager = new PaperCommandManager(this);
        commandManager.registerCommand(new MainCommand());
    }

    public void connectDatabase()
    {
        sqLite = new SQLite();
        sqLite.connectDatabase();
        sqLite.ensureTableExists();
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