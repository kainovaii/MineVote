package fr.kainovaii.minevote.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Subcommand;
import fr.kainovaii.minevote.domains.voting.VotingRepository;
import fr.kainovaii.minevote.gui.VotingGui;
import fr.kainovaii.minevote.http.api.Voter;
import fr.kainovaii.minevote.utils.ApiClient;
import fr.kainovaii.minevote.utils.Prefix;
import jdk.jfr.Description;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

@CommandAlias("minevote")
@Description("Main command")
public class MainCommand extends BaseCommand
{
    private VotingRepository votingRepo;

    @Default
    public void index(CommandSender sender) {
        Player player = (Player) sender;
        new VotingGui(player).open(player);
    }

    @Subcommand("fetch")
    public void fetchNewVote(CommandSender sender) {
        Player player = (Player) sender;
        new Thread(() -> {
            try {
                List<Voter> voters = ApiClient.getApi().getVoters().execute().body();
                if (voters != null && !voters.isEmpty()) {
                    player.sendMessage(Prefix.BASE.get() + "Voter reçus: " + voters.size());
                    for (Voter voter : voters) {
                        player.sendMessage("UUID: " + voter.uuid);
                        player.sendMessage("Nom: " + voter.name);
                    }
                } else {
                    player.sendMessage("Réponse vide !");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}