package fr.kainovaii.minevote.utils;

import co.aikar.taskchain.BukkitTaskChainFactory;
import co.aikar.taskchain.TaskChain;
import co.aikar.taskchain.TaskChainFactory;
import fr.kainovaii.minevote.MineVote;
import fr.kainovaii.minevote.config.ConfigManager;
import org.bukkit.Bukkit;

public class BoostManager
{
    private final MineVote mineVote;
    private final ConfigManager configManager;
    private final TaskChainFactory taskChainFactory;
    private static int globalTimerSeconds = 0;

    public BoostManager() {
        this.mineVote = MineVote.getInstance();
        this.configManager = mineVote.getConfigManager();
        this.taskChainFactory = BukkitTaskChainFactory.create(mineVote);
    }

    public void start() {
        int boostTimeSeconds = configManager.getInt("boost-settings.time");
        configManager.setConfig("boost-settings.status", true);

        for (Object command : configManager.getConfigList("boost-event.start")) {
            mineVote.getServer().dispatchCommand(Bukkit.getConsoleSender(), command.toString());
        }

        TaskChain<?> chain = taskChainFactory.newChain();
        for (int i = boostTimeSeconds; i > 0; i--) {
            final int secondsLeft = i;
            chain = chain.delay(20) // 20 ticks = 1 seconde
            .sync(() -> {
                setGlobalTimerSeconds(secondsLeft);
            });
        }

        chain.sync(this::stop).execute();
    }

    public void stop()
    {
        for (Object command : configManager.getConfigList("boost-event.end")) {
            mineVote.getServer().dispatchCommand(Bukkit.getConsoleSender(), command.toString());
        }
        configManager.setConfig("boost-settings.status", false);
        setGlobalTimerSeconds(0);
    }

    public static int getGlobalTimerSeconds() {
        return globalTimerSeconds;
    }

    public static void setGlobalTimerSeconds(int seconds) {
        globalTimerSeconds = seconds;
    }
}