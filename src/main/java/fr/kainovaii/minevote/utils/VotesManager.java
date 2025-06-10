package fr.kainovaii.minevote.utils;

import fr.kainovaii.minevote.MineVote;
import fr.kainovaii.minevote.config.ConfigManager;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class VotesManager
{
    private final MineVote plugin;
    private final File votesFile;
    private FileConfiguration votesConfig;
    private ConfigManager configManager;

    public VotesManager(MineVote plugin) {
        this.plugin = plugin;
        this.votesFile = new File(plugin.getDataFolder(), "votes.yml");
        loadVotes();
        this.configManager = MineVote.getInstance().getConfigManager();
    }

    private void loadVotes() {
        if (!votesFile.exists()) {
            plugin.saveResource("votes.yml", false);
        }
        votesConfig = YamlConfiguration.loadConfiguration(votesFile);
    }

    private void saveVotes() {
        try {
            votesConfig.save(votesFile);
        } catch (IOException e) {
            plugin.getLogger().severe("Erreur lors de la sauvegarde du fichier votes.yml : " + e.getMessage());
        }
    }

    public long getLastVoteTimestamp(String playerName, String siteId) {
        return votesConfig.getLong("votes." + playerName + "." + siteId, 0);
    }

    public void setLastVoteTimestamp(String playerName, String siteId, long timestamp) {
        votesConfig.set("votes." + playerName + "." + siteId, timestamp);
        saveVotes();
    }

    public boolean canVote(String playerName, String siteId)
    {
        FileConfiguration mainConfig = plugin.getConfig();
        ConfigurationSection providersSection = mainConfig.getConfigurationSection("providers");
        if (providersSection == null || !providersSection.contains(siteId)) {
            return true;
        }

        ConfigurationSection siteSection = providersSection.getConfigurationSection(siteId);
        long cooldown = siteSection.getLong("cooldown", 43200);

        long lastVoteTimestamp = getLastVoteTimestamp(playerName, siteId);
        if (lastVoteTimestamp == 0) {
            return true;
        }

        long now = System.currentTimeMillis() / 1000L;
        return now - lastVoteTimestamp >= cooldown;
    }

    public String getNextVoteDateTime(String playerName, String siteId)
    {
        FileConfiguration mainConfig = plugin.getConfig();
        ConfigurationSection providersSection = mainConfig.getConfigurationSection("providers");
        ConfigurationSection siteSection = providersSection.getConfigurationSection(siteId);
        long cooldown = siteSection.getLong("cooldown", 43200);

        long lastVoteTimestamp = getLastVoteTimestamp(playerName, siteId);
        long nextVoteTimestamp = (lastVoteTimestamp + cooldown) * 1000L; // en ms

        LocalDateTime dateTime = LocalDateTime.ofInstant(
                Instant.ofEpochMilli(nextVoteTimestamp),
                ZoneId.systemDefault()
        );
        if (canVote(playerName, siteId)) return configManager.getMessage("gui.site_page.site_lore.can_vote_yes");
        return configManager.getMessage("gui.site_page.site_lore.can_vote_no")
            .replace("{time}", dateTime.format(DateTimeFormatter.ofPattern("dd-MM HH:mm")));
    }
}
