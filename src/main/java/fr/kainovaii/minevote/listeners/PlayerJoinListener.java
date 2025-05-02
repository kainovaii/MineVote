package fr.kainovaii.minevote.listeners;

import fr.kainovaii.minevote.domains.voting.Voting;
import fr.kainovaii.minevote.domains.voting.VotingRepository;
import fr.kainovaii.minevote.utils.Prefix;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener
{
    private Voting voting;
    private VotingRepository votingRepo;

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        voting = new Voting();
        votingRepo = new VotingRepository();

        Player player = event.getPlayer();
        String uuid = player.getUniqueId().toString();
        String name = player.getName();

        Voting data = Voting.findFirst("uuid = ?", uuid);

        if (data == null) {
            votingRepo.create(uuid, name);
            player.sendMessage(Prefix.BASE.get() + "Bienvenue ! Tu as été ajouté à la base.e §b");
        } else {
            player.sendMessage(Prefix.BASE.get() + "Bienvenue §b" + data.getString("name"));
        }
    }
}
