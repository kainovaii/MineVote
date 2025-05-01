package fr.kainovaii.minevote.domains;

import fr.kainovaii.minevote.MineVote;

public class VotingRepository
{
    private Voting voting;

    public void create(String uuid, String name) {
        voting = new Voting();
        voting.set("uuid", uuid, "name", name, "voting", 0).saveIt();
        MineVote.getInstance().getLogger().info("Inséré : " + name);
    }
}
