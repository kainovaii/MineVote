package fr.kainovaii.minevote.integration.tasks;

import co.aikar.taskchain.TaskChainFactory;
import fr.kainovaii.minevote.MineVote;
import fr.kainovaii.minevote.integration.api.Voter;
import fr.kainovaii.minevote.utils.ApiClient;
import org.bukkit.Bukkit;

public class VoterTask
{
    private final MineVote plugin;
    private final TaskChainFactory taskFactory;

    public VoterTask(MineVote plugin, TaskChainFactory taskFactory) {
        this.plugin = plugin;
        this.taskFactory = taskFactory;
    }

    public void startRepeatingTask() {
        Bukkit.getScheduler().runTaskTimer(plugin, this::fetchVoters, 0L, 12000L); // 10 minutes
    }

    private void fetchVoters() {
            taskFactory.newChain()
            .asyncFirst(() -> {
                try {
                    return ApiClient.getApi().getVoters().execute().body();
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                } })
            .syncLast(voters -> {
                if (voters != null) {
                    plugin.getLogger().info("Reçus : " + voters.size() + " votants.");
                    for (Voter voter : voters) {
                        plugin.getLogger().info(" - " + voter.name + " (" + voter.uuid + ")");
                    }
                } else {
                    plugin.getLogger().warning("Échec de récupération de la liste des votants.");
                }
            })
            .execute();
    }
}