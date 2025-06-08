package fr.kainovaii.minevote.utils;

import fr.kainovaii.minevote.MineVote;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class VotesManager
{
    private final MineVote plugin;
    private final File votesFile;
    private FileConfiguration votesConfig;

    public VotesManager(MineVote plugin) {
        this.plugin = plugin;
        this.votesFile = new File(plugin.getDataFolder(), "votes.yml");
        loadVotes();
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

    public boolean canVote(String playerName, String siteId) {
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
}
