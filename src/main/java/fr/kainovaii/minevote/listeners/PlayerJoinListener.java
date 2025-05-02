package fr.kainovaii.minevote.listeners;

import fr.kainovaii.minevote.domains.voter.Voter;
import fr.kainovaii.minevote.domains.voter.VoterRepository;
import fr.kainovaii.minevote.utils.Prefix;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener
{
    private Voter voting;
    private VoterRepository votingRepo;

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        voting = new Voter();
        votingRepo = new VoterRepository();

        Player player = event.getPlayer();
        String uuid = player.getUniqueId().toString();
        String name = player.getName();

        Voter data = Voter.findFirst("uuid = ?", uuid);

        if (data == null) {
            votingRepo.create(uuid, name);
            player.sendMessage(Prefix.BASE.get() + "Bienvenue ! Tu as été ajouté à la base");
        } else {
            player.sendMessage(Prefix.BASE.get() + "Bienvenue §b" + data.getString("name"));
        }
    }
}
