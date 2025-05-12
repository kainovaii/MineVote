package fr.kainovaii.minevote.listeners;

import com.vexsoftware.votifier.model.Vote;
import com.vexsoftware.votifier.model.VotifierEvent;
import fr.kainovaii.minevote.MineVote;
import fr.kainovaii.minevote.config.ConfigManager;
import fr.kainovaii.minevote.domain.voter.VoterRepository;
import fr.kainovaii.minevote.utils.Prefix;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.List;

public class VotifierListener implements Listener
{
    MineVote mineVote;
    private ConfigManager configManager;
    private VoterRepository voterRepository;

    public VotifierListener (MineVote plugin)
    {
        this.configManager = plugin.getConfigManager();
    }

    @EventHandler
    public void onVote(VotifierEvent event)
    {
        mineVote = MineVote.getInstance();
        Vote vote = event.getVote();
        String playerName = vote.getUsername();

        voteIncrement(playerName);
        playerIncrementVote(playerName);

        List<Object> commands = configManager.getConfigList("rewards");
        for (Object command : commands) {
            mineVote.getServer().dispatchCommand(Bukkit.getConsoleSender(), command.toString().replace("{player}", playerName));
        }
    }

    public void playerIncrementVote(String playerName)
    {
        int voting = VoterRepository.getVoting(playerName);
        VoterRepository.updateVoteCount(playerName, voting + 1);
    }

    public void voteIncrement(String playerName)
    {
        int voteCounter = (int) configManager.getConfig("voteCounter");
        int voteObjective = (int) configManager.getConfig("voteObjective");
        int newCount = voteCounter + 1;
        if (newCount != voteObjective)

        {
            mineVote.getServer().broadcastMessage(Prefix.BASE.get() + configManager.getMessage("messages.player_voted")
                    .replace("{vote_counter}", String.valueOf(newCount))
                    .replace("{vote_objective}", String.valueOf(voteObjective))
                    .replace("{player}", playerName)
            );
            configManager.setConfig("voteCounter", newCount);
        }

        if (newCount >= voteObjective)
        {
            mineVote.getServer().broadcastMessage(Prefix.BASE.get() + configManager.getMessage("messages.start_boost")
                    .replace("{player}", playerName)
            );
            configManager.setConfig("voteCounter", 0);

            List<Object> commands = configManager.getConfigList("boost");
            for (Object command : commands) {
                mineVote.getServer().dispatchCommand(Bukkit.getConsoleSender(), command.toString());
            }
        }
    }
}