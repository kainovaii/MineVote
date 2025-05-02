package fr.kainovaii.minevote;

import co.aikar.commands.PaperCommandManager;
import fr.kainovaii.minevote.commands.MainCommand;
import fr.kainovaii.minevote.utils.gui.InventoryManager;
import fr.kainovaii.minevote.listeners.PlayerJoinListener;
import fr.kainovaii.minevote.utils.SQLite;
import org.bukkit.plugin.java.JavaPlugin;

public final class MineVote extends JavaPlugin {
    private static MineVote instance;
    private PaperCommandManager commandManager;
    private SQLite sqLite;

    @Override
    public void onEnable() {
        instance = this;
        this.registerMotd();
        this.connectDatabase();
        this.registerListener();
        this.registerCommand();
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

    private void registerListener()
    {
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
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