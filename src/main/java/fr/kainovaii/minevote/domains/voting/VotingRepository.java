package fr.kainovaii.minevote.domains.voting;

import fr.kainovaii.minevote.MineVote;

public class VotingRepository
{
    private Voting voting;

    public VotingRepository() {
        voting = new Voting();
    }

    public void create(String uuid, String name) {
        voting.set("uuid", uuid, "name", name, "voting", 0).saveIt();
        MineVote.getInstance().getLogger().info("Inséré : " + name);
    }

    public static Integer getVoting(String name) {
        Voting player = Voting.findFirst("name = ?", name);
        return player != null ? player.getInteger("voting") : null;
    }
}
