package fr.kainovaii.minevote;

import co.aikar.commands.PaperCommandManager;
import co.aikar.taskchain.BukkitTaskChainFactory;
import co.aikar.taskchain.TaskChainFactory;
import fr.kainovaii.minevote.commands.MainCommand;
import fr.kainovaii.minevote.listeners.VotifierListener;
import fr.kainovaii.minevote.tasks.VoterTask;
import fr.kainovaii.minevote.utils.ApiClient;
import fr.kainovaii.minevote.utils.gui.InventoryManager;
import fr.kainovaii.minevote.listeners.PlayerJoinListener;
import fr.kainovaii.minevote.utils.SQLite;
import org.bukkit.plugin.java.JavaPlugin;

public final class MineVote extends JavaPlugin
{
    private static MineVote instance;
    private PaperCommandManager commandManager;
    private SQLite sqLite;
    private TaskChainFactory taskChainFactory;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        this.registerMotd();
        this.connectDatabase();
        this.registerListener();
        this.registerCommand();
        this.registerTask();
        ApiClient.init();
        InventoryManager.register(this);
    }

    private void registerMotd()
    {
        getLogger().info("▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬");
        getLogger().info("┳┳┓•    ┓┏       ┳┓    ┓┏┓  •    ┓┏  •• \u001B[0m");
        getLogger().info("┃┃┃┓┏┓┏┓┃┃┏┓╋┏┓  ┣┫┓┏  ┃┫ ┏┓┓┏┓┏┓┃┃┏┓┓┓\u001B[0m");
        getLogger().info("┛ ┗┗┛┗┗ ┗┛┗┛┗┗   ┻┛┗┫  ┛┗┛┗┻┗┛┗┗┛┗┛┗┻┗┗ \u001B[0m");
        getLogger().info("▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬");
    }

    public void registerTask()
    {
        this.taskChainFactory = BukkitTaskChainFactory.create(this);
        new VoterTask(this, taskChainFactory).startRepeatingTask();
    }

    private void registerListener()
    {
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
        getServer().getPluginManager().registerEvents(new VotifierListener(), this);
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

    public static MineVote getInstance()
    {
        return instance;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}