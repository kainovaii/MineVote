package fr.kainovaii.minevote.domain.voter;

import fr.kainovaii.minevote.MineVote;

public class VoterRepository
{
    private Voter voter;

    public VoterRepository() {
        voter = new Voter();
    }

    public void create(String uuid, String name)
    {
        voter.set("uuid", uuid, "name", name, "voting", 0, "bank", 0).saveIt();
        MineVote.getInstance().getLogger().info("Inséré : " + name);
    }

    public static int getVoting(String name)
    {
        Voter player = Voter.findFirst("name = ?", name);
        return player != null ? player.getInteger("voting") : null;
    }

    public static double getBank(String name)
    {
        Voter player = Voter.findFirst("name = ?", name);
        return player != null ? player.getDouble("bank") : null;
    }
}
