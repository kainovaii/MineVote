package fr.kainovaii.minevote.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import fr.kainovaii.minevote.MineVote;
import fr.kainovaii.minevote.config.ConfigManager;
import fr.kainovaii.minevote.domain.voter.VoterRepository;
import fr.kainovaii.minevote.gui.ShopGui;
import fr.kainovaii.minevote.gui.main.MainGui;
import fr.kainovaii.minevote.utils.BoostManager;
import fr.kainovaii.minevote.utils.Notifier;
import fr.kainovaii.minevote.utils.Prefix;
import io.papermc.paper.plugin.configuration.PluginMeta;
import jdk.jfr.Description;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;

@CommandAlias("minevote|vote")
@Description("Main command")
public class MainCommand extends BaseCommand
{
    private final ConfigManager configManager;
    private final BoostManager boostManager;

    public MainCommand () {
        this.configManager = MineVote.getInstance().getConfigManager();
        this.boostManager = new BoostManager();
    }

    @Default
    public void index(Player player)
    {
        new MainGui(player).open(player);
    }

    @Subcommand("help")
    public void help(Player player)
    {
        String[] messages = new String[]
        {
            "§6§m────────§7[§bMineVote§7]§6§m────────",
            "§7┌ §6/§bvote reload",
            "§7├ §6/§bvote boost §6<§bon/off§6>",
            "§7├ §6/§bvote shop",
            "§7├ §6/§bvote site",
            "§7└ §6/§bvote ranking",
            "§6§m──────────────────────"
        };
        player.sendMessage(messages);
    }

    @Subcommand("info")
    public void info(Player player)
    {
        PluginMeta meta = MineVote.getInstance().getPluginMeta();
        String[] messages = new String[]
            {
                "§6§m────────§7[§bMineVote§7]§6§m────────",
                "§7┌ §6Autor §7↪ §b" + meta.getAuthors(),
                "§7├ §6Version §7↪ §b" + meta.getVersion(),
                "§7├ §6Api Version §7↪ §b" + meta.getAPIVersion(),
                "§7└ §6Depend §7↪ §b" + meta.getPluginDependencies(),
                "§6§m──────────────────────"
            };
        player.sendMessage(messages);
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
            Notifier.broadcast(configManager.getMessage("messages.force_boost_start")
                    .replace("{player}", player.getName())
            );
        } else {
            boostManager.stop();
            Notifier.broadcast(configManager.getMessage("messages.force_boost_stop")
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