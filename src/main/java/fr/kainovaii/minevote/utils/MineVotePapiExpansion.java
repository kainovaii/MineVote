package fr.kainovaii.minevote.utils;


import fr.kainovaii.minevote.MineVote;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

public class MineVotePapiExpansion  extends PlaceholderExpansion
{
    private int voteCount;
    private int votebjective;

    public MineVotePapiExpansion()
    {
        voteCount = MineVote.getInstance().getConfig().getInt("voteCount");
        votebjective = MineVote.getInstance().getConfig().getInt("voteMax");
    }

    @Override
    public String onRequest(OfflinePlayer player, String identifier)
    {
        if(identifier.equals("counter")){
            return String.valueOf(voteCount);
        }
        if(identifier.equals("objective")){
            return String.valueOf(votebjective);
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