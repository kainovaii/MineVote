package fr.kainovaii.minevote.listeners;

import fr.kainovaii.minevote.MineVote;
import fr.kainovaii.minevote.config.ConfigManager;
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
    private ConfigManager configManager;

    public PlayerJoinListener(MineVote plugin)
    {
        this.configManager = plugin.getConfigManager();
        this.voterRepo = new VoterRepository();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();
        String uuid = player.getUniqueId().toString();
        String playerName = player.getName();

        Voter data = Voter.findFirst("uuid = ?", uuid);

        if (data == null) {
            voterRepo.create(uuid, playerName);
            player.sendMessage(Prefix.BASE.get() + configManager.getMessage("messages.welcome_unregistered"));
        } else {
            player.sendMessage(Prefix.BASE.get() + configManager.getMessage("messages.welcome_registered")
                    .replace("{player}", playerName)
            );
        }
    }
}