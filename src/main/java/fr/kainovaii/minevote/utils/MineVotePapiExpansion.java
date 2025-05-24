package fr.kainovaii.minevote.utils;


import fr.kainovaii.minevote.MineVote;
import fr.kainovaii.minevote.config.ConfigManager;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

public class MineVotePapiExpansion  extends PlaceholderExpansion
{
    private ConfigManager configManager;

    public MineVotePapiExpansion()
    {
        configManager = MineVote.getInstance().getConfigManager();
    }

    @Override
    public String onRequest(OfflinePlayer player, String identifier) {
        switch (identifier) {
            case "counter" -> {
                int voteCount = (int) configManager.getConfig("voteCounter");
                return String.valueOf(voteCount);
            }
            case "objective" -> {
                int voteObjective = (int) configManager.getConfig("voteObjective");
                return String.valueOf(voteObjective);
            }
            case "status" -> {
                if ((boolean) configManager.getConfig("boost-settings.status")) {
                    return configManager.getMessage("messages.boost_on");
                } else {
                    return configManager.getMessage("messages.boost_off");
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
        return "0.1";
    }

}