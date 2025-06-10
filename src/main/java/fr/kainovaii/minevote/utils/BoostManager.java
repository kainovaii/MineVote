package fr.kainovaii.minevote.utils;

import fr.kainovaii.minevote.MineVote;
import fr.kainovaii.minevote.config.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class BoostManager
{
    private final MineVote mineVote;
    private final ConfigManager configManager;
    private static int boostTimer = 0;
    private static boolean boostActive;
    private BukkitTask runningTask = null;

    public BoostManager() {
        this.mineVote = MineVote.getInstance();
        this.configManager = mineVote.getConfigManager();
    }

    public static void setBoostActive(boolean boostActive) { BoostManager.boostActive = boostActive; }

    public void start()
    {
        if (runningTask != null && !runningTask.isCancelled())
        {
            runningTask.cancel();
        }

        boostActive = true;
        int boostTimeSeconds = configManager.getInt("boost-settings.time");
        configManager.setConfig("boost-settings.status", true);

        for (Object command : configManager.getConfigList("boost-event.start"))
        {
            mineVote.getServer().dispatchCommand(Bukkit.getConsoleSender(), command.toString());
        }

        runningTask = new BukkitRunnable()
        {
            int secondsLeft = boostTimeSeconds;

            @Override
            public void run() {
                if (secondsLeft <= 0) {
                    setBoostTimer(0);
                    stop();
                    cancel();
                    return;
                }
                setBoostTimer(secondsLeft);
                secondsLeft--;
            }
        }.runTaskTimer(mineVote, 0L, 20L);
    }

    public void stop()
    {
        boostActive = false;

        for (Object command : configManager.getConfigList("boost-event.end")) {
            mineVote.getServer().dispatchCommand(Bukkit.getConsoleSender(), command.toString());
        }

        configManager.setConfig("boost-settings.status", false);
        setBoostTimer(0);

        if (runningTask != null && !runningTask.isCancelled()) {
            runningTask.cancel();
            runningTask = null;
        }
    }

    public static boolean getBoostActive() { return boostActive; }

    public static int getBoostTimer() { return boostTimer; }

    public static void setBoostTimer(int seconds) { boostTimer = seconds; }
}
