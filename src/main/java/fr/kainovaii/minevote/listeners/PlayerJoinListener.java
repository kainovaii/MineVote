package fr.kainovaii.minevote.listeners;

import com.google.gson.Gson;
import fr.kainovaii.minevote.MineVote;
import fr.kainovaii.minevote.config.ConfigManager;
import fr.kainovaii.minevote.domain.reward.Reward;
import fr.kainovaii.minevote.domain.reward.RewardRepository;
import fr.kainovaii.minevote.domain.voter.Voter;
import fr.kainovaii.minevote.domain.voter.VoterRepository;
import fr.kainovaii.minevote.utils.Prefix;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

public class PlayerJoinListener implements Listener
{
    private final VoterRepository voterRepo;
    private final ConfigManager configManager;

    public PlayerJoinListener(MineVote plugin)
    {
        this.configManager = plugin.getConfigManager();
        this.voterRepo = new VoterRepository();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();
        String playerName = player.getName();

        UUID offlineUUID = UUID.nameUUIDFromBytes(("OfflinePlayer:" + playerName).getBytes(StandardCharsets.UTF_8));
        String uuid = offlineUUID.toString();

        System.out.println("Player " + playerName + " joined with offline UUID: " + uuid);

        Voter data = Voter.findFirst("uuid = ?", uuid);

        if (data == null) {
            voterRepo.create(uuid, playerName);
            player.sendMessage(Prefix.BASE.get() + configManager.getMessage("messages.welcome_unregistered"));
        } else {
            player.sendMessage(Prefix.BASE.get() + configManager.getMessage("messages.welcome_registered")
                    .replace("{player}", playerName)
            );
            RewardRepository.getCommandsFrom(playerName);
        }

    }
}