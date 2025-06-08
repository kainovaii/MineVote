package fr.kainovaii.minevote.listeners;

import com.vexsoftware.votifier.model.Vote;
import com.vexsoftware.votifier.model.VotifierEvent;
import fr.kainovaii.minevote.MineVote;
import fr.kainovaii.minevote.config.ConfigManager;
import fr.kainovaii.minevote.domain.reward.RewardRepository;
import fr.kainovaii.minevote.domain.voter.VoterRepository;
import fr.kainovaii.minevote.utils.BoostManager;
import fr.kainovaii.minevote.utils.Notifier;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.io.File;
import java.util.List;

public class VotifierListener implements Listener
{
    private final MineVote mineVote;
    private final ConfigManager configManager;
    private final BoostManager boostManager;

    private File votesFile;
    private FileConfiguration votesConfig;

    public VotifierListener(MineVote plugin)
    {
        this.mineVote = plugin;
        this.configManager = plugin.getConfigManager();
        this.boostManager = new BoostManager();

        votesFile = new File(plugin.getDataFolder(), "votes.yml");
        if (!votesFile.exists()) {
            plugin.saveResource("votes.yml", false);
        }
        votesConfig = YamlConfiguration.loadConfiguration(votesFile);
    }

    @EventHandler
    public void onVote(VotifierEvent event)
    {
        Vote vote = event.getVote();
        String playerName = vote.getUsername();
        String siteID = vote.getServiceName();

        if (playerName == null || playerName.isEmpty()) {
            return;
        }

        long now = System.currentTimeMillis() / 1000L;
        mineVote.getVotesManager().setLastVoteTimestamp(playerName, siteID, now);

        voteIncrement(playerName);
        playerIncrementVote(playerName);

        Player player = Bukkit.getPlayerExact(playerName);
        boolean isOnline = player != null && player.isOnline();
        List<Object> commands = configManager.getConfigList("rewards");

        if (isOnline) {
            for (Object cmd : commands) {
                String command = cmd.toString().replace("{player}", playerName);
                mineVote.getServer().dispatchCommand(Bukkit.getConsoleSender(), command);
            }
        } else {
            List<String> commandsStr = commands.stream()
                    .map(Object::toString)
                    .toList();
            RewardRepository.create(playerName, commandsStr);
        }
    }

    public void playerIncrementVote(String playerName)
    {
        if (VoterRepository.voterExist(playerName))
        {
            int voting = VoterRepository.getVoting(playerName);
            int bank = VoterRepository.getBank(playerName);
            VoterRepository.updateVoteCount(playerName, voting + 1);
            VoterRepository.updateBank(playerName, bank + 1);
        }
    }

    public void voteIncrement(String playerName)
    {
        int voteCounter = configManager.getInt("boost-settings.counter");
        int voteObjective = configManager.getInt("boost-settings.objective");
        int newCount = voteCounter + 1;

        if (newCount != voteObjective)
        {
            Notifier.broadcast(configManager.getMessage("messages.player_voted")
                    .replace("{vote_counter}", String.valueOf(newCount))
                    .replace("{vote_objective}", String.valueOf(voteObjective))
                    .replace("{player}", playerName));
            configManager.setConfig("boost-settings.counter", newCount);
        }

        if (newCount >= voteObjective)
        {
            Notifier.broadcast(configManager.getMessage("messages.start_boost").replace("{player}", playerName));
            configManager.setConfig("boost-settings.counter", 0);
            boostManager.start();
        }
    }
}
