package fr.kainovaii.minevote.listeners;

import com.vexsoftware.votifier.model.Vote;
import com.vexsoftware.votifier.model.VotifierEvent;
import fr.kainovaii.minevote.MineVote;
import fr.kainovaii.minevote.utils.Prefix;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class VotifierListener implements Listener
{
    MineVote mineVote;

    @EventHandler
    public void onVote(VotifierEvent event) {
        mineVote = MineVote.getInstance();
        Vote vote = event.getVote();
        String playerName = vote.getUsername();
        voteIncrement(playerName);
    }

    public void voteIncrement(String playerName)
    {
        int voteMax = mineVote.getConfig().getInt("voteMax");
        int voteCount = mineVote.getConfig().getInt("voteCount");
        int newCount = voteCount + 1;
        if (voteCount != voteMax)
        {
            mineVote.getServer().broadcastMessage(Prefix.BASE.get() + "§b" + playerName + " §fa voter ! §b" + voteCount + "/" + voteMax);
            mineVote.getConfig().set("voteCount",  + newCount);
            mineVote.saveConfig();
        }

        if (voteCount >= voteMax)
        {
            mineVote.getServer().broadcastMessage(Prefix.BASE.get() + "§b" + playerName + " §fa voter ! §fBoost actif");
            mineVote.getConfig().set("voteCount", 1);
        }
    }
}