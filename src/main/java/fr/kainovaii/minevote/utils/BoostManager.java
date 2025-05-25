package fr.kainovaii.minevote.utils;

import co.aikar.taskchain.BukkitTaskChainFactory;
import co.aikar.taskchain.TaskChainFactory;
import fr.kainovaii.minevote.MineVote;
import fr.kainovaii.minevote.config.ConfigManager;
import org.bukkit.Bukkit;

public class BoostManager
{
    private final MineVote mineVote;
    private final ConfigManager configManager;
    private final TaskChainFactory taskChainFactory;

    public BoostManager()
    {
        this.mineVote = MineVote.getInstance();
        this.configManager = MineVote.getInstance().getConfigManager();
        this.taskChainFactory = BukkitTaskChainFactory.create(this.mineVote);
    }

    public void start()
    {
        configManager.setConfig("boost-settings.status", true);

        for (Object command : configManager.getConfigList("boost-event.start")) {
            mineVote.getServer().dispatchCommand(Bukkit.getConsoleSender(), command.toString());
        }

        taskChainFactory.newChain()
        .delay(configManager.getInt("boost-settings.time"))
        .sync(() -> {
            for (Object command : configManager.getConfigList("boost-event.end")) {
                mineVote.getServer().dispatchCommand(Bukkit.getConsoleSender(), command.toString());
            }
            configManager.setConfig("boost-settings.status", false);
        })
        .execute();
    }

}
