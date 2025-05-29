package fr.kainovaii.minevote.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import fr.kainovaii.minevote.MineVote;
import fr.kainovaii.minevote.config.ConfigManager;
import fr.kainovaii.minevote.domain.voter.VoterRepository;
import fr.kainovaii.minevote.gui.ShopGui;
import fr.kainovaii.minevote.gui.main.MainGui;
import fr.kainovaii.minevote.utils.BoostManager;
import fr.kainovaii.minevote.utils.MineVoteNotifier;
import fr.kainovaii.minevote.utils.Prefix;
import jdk.jfr.Description;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("minevote|vote")
@Description("Main command")
public class MainCommand extends BaseCommand
{
    private ConfigManager configManager;
    private BoostManager boostManager;

    public MainCommand () {
        this.configManager = MineVote.getInstance().getConfigManager();
        this.boostManager = new BoostManager();
    }

    @Default
    public void index(Player player)
    {
        new MainGui(player).open(player);
    }

    @Subcommand("shop")
    public void shop(Player player)
    {
        new ShopGui(player).open(player);
    }

    @Subcommand("site")
    public void site(Player player)
    {
        new MainGui(player, 1).open(player);
    }

    @Subcommand("ranking")
    public void ranking(Player player)
    {
        new MainGui(player, 2).open(player);
    }

    @Subcommand("give")
    @CommandPermission("minevote.give")
    @Syntax("<player> <value>")
    @CommandCompletion("@players")
    public void give(Player player, String target, int value)
    {
        VoterRepository.incrementVoter(target, value);
        player.sendMessage(Prefix.BASE.get() + "le joueur {player} a reçu {value} votes supplémentaires."
            .replace("{player}", target)
            .replace("{value}", String.valueOf(value))
        );
    }

    @Subcommand("boost")
    @CommandPermission("minevote.boost")
    @Syntax("set <on/off>")
    public void boost(Player player, boolean value)
    {
        if (value)
        {
            boostManager.start();
            MineVoteNotifier.broadcast(configManager.getMessage("messages.force_boost_start")
                    .replace("{player}", player.getName())
            );
        } else {
            // Logic boost stop
            MineVoteNotifier.broadcast(configManager.getMessage("messages.force_boost_stop")
                    .replace("{player}", player.getName())
            );
        }
    }

    @Subcommand("reload")
    @CommandPermission("minevote.reload")
    public void reload(Player player) {
        configManager.reloadConfigs();
        player.sendMessage(Prefix.BASE.get() + "the config has been reloaded");
    }

}