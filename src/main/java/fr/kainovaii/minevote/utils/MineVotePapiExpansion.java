package fr.kainovaii.minevote.utils;


import fr.kainovaii.minevote.MineVote;
import fr.kainovaii.minevote.config.ConfigManager;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

public class MineVotePapiExpansion  extends PlaceholderExpansion
{
    private final ConfigManager configManager;

    public MineVotePapiExpansion()
    {
        configManager = MineVote.getInstance().getConfigManager();
    }

    @Override
    public String onRequest(OfflinePlayer player, String identifier) {
        switch (identifier) {
            case "counter" -> {
                int voteCount = (int) configManager.getConfig("boost-settings.counter");
                return String.valueOf(voteCount);
            }
            case "objective" -> {
                int voteObjective = (int) configManager.getConfig("boost-settings.objective");
                return String.valueOf(voteObjective);
            }
            case "status" -> {
                if ((boolean) configManager.getConfig("boost-settings.status")) {
                    return configManager.getMessage("messages.boost_on");
                } else {
                    return configManager.getMessage("messages.boost_off");
                }
            }
            case "timeleft" -> {
                int timeLeft = BoostManager.getGlobalTimerSeconds();
                if(timeLeft > 0) {
                    int minutes = timeLeft / 60;
                    int seconds = timeLeft % 60;
                    return String.format("%02d:%02d", minutes, seconds);
                } else {
                    return ""; // ⏰
                }
            }
        }
        return null;
    }

    @Override
    public @NotNull String getIdentifier()
    {
        return "MineVote";
    }

    @Override
    public @NotNull String getAuthor()
    {
        return "KainoVaii";
    }

    @Override
    public @NotNull String getVersion()
    {
        return "0.4";
    }

}