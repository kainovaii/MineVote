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
    private final MineVote mineVote;
    private final ConfigManager configManager;

    public VotifierListener(MineVote plugin)
    {
        this.mineVote = plugin;
        this.configManager = plugin.getConfigManager();
    }

    @EventHandler
    public void onVote(VotifierEvent event)
    {
        Vote vote = event.getVote();
        String playerName = vote.getUsername();

        if (playerName == null || playerName.isEmpty()) {
            return;
        }

        voteIncrement(playerName);
        playerIncrementVote(playerName);

        for (Object command : configManager.getConfigList("rewards"))
        {
            mineVote.getServer().dispatchCommand(Bukkit.getConsoleSender(), command.toString().replace("{player}", playerName));
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
        int voteCounter = Integer.parseInt(configManager.getConfig("voteCounter").toString());
        int voteObjective = Integer.parseInt(configManager.getConfig("voteObjective").toString());
        int newCount = voteCounter + 1;

        if (newCount != voteObjective)
        {
            Bukkit.getServer().broadcastMessage(Prefix.BASE.get() + configManager.getMessage("messages.player_voted")
                    .replace("{vote_counter}", String.valueOf(newCount))
                    .replace("{vote_objective}", String.valueOf(voteObjective))
                    .replace("{player}", playerName)
            );
            configManager.setConfig("voteCounter", newCount);
        }

        if (newCount >= voteObjective)
        {
            Bukkit.getServer().broadcastMessage(Prefix.BASE.get() + configManager.getMessage("messages.start_boost")
                    .replace("{player}", playerName)
            );
            configManager.setConfig("voteCounter", 0);

            for (Object command : configManager.getConfigList("boost")) {
                mineVote.getServer().dispatchCommand(Bukkit.getConsoleSender(), command.toString());
            }
        }
    }
}
