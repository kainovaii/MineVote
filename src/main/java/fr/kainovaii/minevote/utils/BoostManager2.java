package fr.kainovaii.minevote.utils;

import co.aikar.taskchain.BukkitTaskChainFactory;
import co.aikar.taskchain.TaskChain;
import co.aikar.taskchain.TaskChainFactory;
import fr.kainovaii.minevote.MineVote;
import fr.kainovaii.minevote.config.ConfigManager;
import org.bukkit.Bukkit;

public class BoostManager2
{
    private final MineVote mineVote;
    private final ConfigManager configManager;
    private final TaskChainFactory taskChainFactory;

    public BoostManager2()
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

        TaskChain<?> chain = taskChainFactory.newChain();
        for (int i = configManager.getInt("boost-settings.time"); i > 0; i--) {
            final int secondsLeft = i;
            chain = chain.delay(20) // 1 seconde = 20 ticks
            .sync(() -> {
                MineVote.setGlobalTimerSeconds(secondsLeft);
                System.out.println("Time left: " + secondsLeft);
            });
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
