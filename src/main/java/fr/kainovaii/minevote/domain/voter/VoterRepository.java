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
        Voter voter = new Voter();
        voter.set("uuid", uuid, "name", name, "voting", 0, "bank", 0);
        voter.saveIt();
        MineVote.getInstance().getLogger().info("Inséré : " + name);
    }

    public static LazyList<Model> getVoters()
    {
        return Voter.findAll();
    }

    public static int getBank(String name)
    {
        if (VoterRepository.voterExist(name)) {
            Voter player = Voter.findFirst("name = ?", name);
            return player != null ? player.getInteger("bank") : 0;
        }
        return 0;
    }

    public static int getVoting(String name)
    {
        if (VoterRepository.voterExist(name)) {
            Voter player = Voter.findFirst("name = ?", name);
            return player != null ? player.getInteger("voting") : 0;
        }
        return 0;
    }

    public static void updateVoteCount(String name, int count)
    {
        if (VoterRepository.voterExist(name)) {
            Voter player = Voter.findFirst("name = ?", name);
            player.set("voting", count);
            player.saveIt();
        }
    }

    public static void updateBank(String name, int solde)
    {
        if (VoterRepository.voterExist(name)) {
            Voter player = Voter.findFirst("name = ?", name);
            player.set("bank", solde);
            player.saveIt();
        }
    }

    public static void incrementVoter(String name, int value)
    {
        if (VoterRepository.voterExist(name)) {
            Voter player = Voter.findFirst("name = ?", name);
            player.set("bank",  player.getInteger("bank") + value);
            player.set("voting", player.getInteger("bank") +  value);
            player.saveIt();
        }
    }

    public static boolean voterExist(String name)
    {
        Voter player = Voter.findFirst("name = ?", name);
        return player != null;
    }
}
