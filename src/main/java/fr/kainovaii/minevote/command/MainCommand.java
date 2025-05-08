package fr.kainovaii.minevote.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import fr.kainovaii.minevote.domain.voter.VoterRepository;
import fr.kainovaii.minevote.gui.MainGui;
import jdk.jfr.Description;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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