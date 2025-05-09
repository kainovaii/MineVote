package fr.kainovaii.minevote.integration.listeners;

import com.vexsoftware.votifier.model.Vote;
import com.vexsoftware.votifier.model.VotifierEvent;
import fr.kainovaii.minevote.MineVote;
import fr.kainovaii.minevote.config.ConfigManager;
import fr.kainovaii.minevote.utils.Prefix;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class VotifierListener implements Listener
{
    MineVote mineVote;
    private ConfigManager configManager;

    public VotifierListener (MineVote plugin)
    {
        this.configManager = plugin.getConfigManager();
    }

    @EventHandler
    public void onVote(VotifierEvent event) {
        mineVote = MineVote.getInstance();
        Vote vote = event.getVote();
        String playerName = vote.getUsername();
        voteIncrement(playerName);
    }

    public void voteIncrement(String playerName)
    {
        int voteObjective = mineVote.getConfig().getInt("voteMax");
        int voteCounter = mineVote.getConfig().getInt("voteCount");
        int newCount = voteCounter + 1;
        if (voteCounter != voteObjective)
        {
            mineVote.getServer().broadcastMessage(Prefix.BASE.get() + configManager.getMessage("messages.player_voted")
                    .replace("{vote_counter}", String.valueOf(voteCounter))
                    .replace("{vote_objective}", String.valueOf(voteObjective))
                    .replace("{player}", playerName)
            );
            mineVote.getConfig().set("voteCount",  + newCount);
            mineVote.saveConfig();
        }

        if (voteCounter >= voteObjective)
        {
            mineVote.getServer().broadcastMessage(Prefix.BASE.get() + configManager.getMessage("messages.start_boost")
                    .replace("{player}", playerName)
            );
            mineVote.getConfig().set("voteCount", 1);
        }
    }
}