package fr.kainovaii.minevote.integration.listeners;

import fr.kainovaii.minevote.domain.voter.Voter;
import fr.kainovaii.minevote.domain.voter.VoterRepository;
import fr.kainovaii.minevote.utils.Prefix;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener
{
    private VoterRepository voterRepo;

    @EventHandler
    public void onJoin(PlayerJoinEvent event)
    {
        voterRepo = new VoterRepository();
        Player player = event.getPlayer();
        String uuid = player.getUniqueId().toString();
        String name = player.getName();

        Voter data = Voter.findFirst("uuid = ?", uuid);

        if (data == null) {
            voterRepo.create(uuid, name);
            player.sendMessage(Prefix.BASE.get() + "Bienvenue ! Tu as été ajouté à la base");
        } else {
            player.sendMessage(Prefix.BASE.get() + "Bienvenue §b" + data.getString("name"));
        }
    }
}