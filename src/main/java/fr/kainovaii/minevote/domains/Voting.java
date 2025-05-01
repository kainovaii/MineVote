package fr.kainovaii.minevote.domains;

import org.javalite.activejdbc.Model;

public class Voting extends Model
{
    public static Integer getVoting(String name) {
        Voting player = Voting.findFirst("name = ?", name);
        return player != null ? player.getInteger("voting") : null;
    }
}