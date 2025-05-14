package fr.kainovaii.minevote.domain.voter;

import fr.kainovaii.minevote.MineVote;
import org.javalite.activejdbc.LazyList;
import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.Model;

public class VoterRepository
{
    private final Voter voter;

    public VoterRepository() {
        voter = new Voter();
    }

    public void create(String uuid, String name)
    {
        voter.set("uuid", uuid, "name", name, "voting", 0, "bank", 0).saveIt();
        MineVote.getInstance().getLogger().info("Inséré : " + name);
    }

    public static LazyList<Model> getVoters()
    {
        return Voter.findAll();
    }

    public static int getBank(String name)
    {
        Voter player = Voter.findFirst("name = ?", name);
        return player != null ? player.getInteger("bank") : null;
    }

    public static int getVoting(String name)
    {
        Voter player = Voter.findFirst("name = ?", name);
        return player != null ? player.getInteger("voting") : null;
    }

    public static void updateVoteCount(String name, int count)
    {
        Voter player = Voter.findFirst("name = ?", name);
        player.set("voting", count);
        player.saveIt();
    }

    public static void updateBank(String name, int solde)
    {
        Voter player = Voter.findFirst("name = ?", name);
        player.set("bank", solde);
        player.saveIt();
    }
}
