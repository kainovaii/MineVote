package fr.kainovaii.minevote.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Subcommand;
import fr.kainovaii.minevote.MineVote;
import fr.kainovaii.minevote.config.ConfigManager;
import fr.kainovaii.minevote.domain.voter.VoterRepository;
import fr.kainovaii.minevote.gui.ShopGui;
import fr.kainovaii.minevote.gui.main.MainGui;
import fr.kainovaii.minevote.utils.Prefix;
import jdk.jfr.Description;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("minevote|vote")
@Description("Main command")
public class MainCommand extends BaseCommand
{
    private ConfigManager configManager;

    public MainCommand () {this.configManager = MineVote.getInstance().getConfigManager(); }

    @Default
    public void index(CommandSender sender) {
        Player player = (Player) sender;
        new MainGui(player).open(player);
    }

    @Subcommand("shop")
    public void shop(CommandSender sender) {
        Player player = (Player) sender;
        new ShopGui(player).open(player);
    }

    @Subcommand("give")
    @CommandPermission("minevote.give")
    public void give(CommandSender sender, String target, int value) {
        Player player = (Player) sender;

        VoterRepository.incrementVoter(target, value);
        player.sendMessage(Prefix.BASE.get() + "le joueur {player} a reçu {value} votes supplémentaires."
                .replace("{player}", target)
                .replace("{value}", String.valueOf(value))
        );
    }

    @Subcommand("reload")
    @CommandPermission("minevote.reload")
    public void reload(CommandSender sender) {
        Player player = (Player) sender;
        configManager.reloadConfigs();
        player.sendMessage(Prefix.BASE.get() + "the config has been reloaded");
    }
}