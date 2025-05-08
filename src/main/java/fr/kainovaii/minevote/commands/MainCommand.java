package fr.kainovaii.minevote.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Subcommand;
import com.gamingmesh.jobs.ItemBoostManager;
import fr.kainovaii.minevote.domains.voter.VoterRepository;
import fr.kainovaii.minevote.gui.MainGui;
import fr.kainovaii.minevote.http.api.Voter;
import fr.kainovaii.minevote.utils.ApiClient;
import fr.kainovaii.minevote.utils.Prefix;
import jdk.jfr.Description;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

@CommandAlias("minevote|vote")
@Description("Main command")
public class MainCommand extends BaseCommand
{
    private VoterRepository votingRepo;

    @Default
    public void index(CommandSender sender) {
        Player player = (Player) sender;
        new MainGui(player).open(player);
    }
}