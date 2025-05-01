package fr.kainovaii.minevote.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import fr.kainovaii.minevote.domains.Voting;
import fr.kainovaii.minevote.domains.VotingRepository;
import fr.kainovaii.minevote.gui.VotingGui;
import jdk.jfr.Description;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
}