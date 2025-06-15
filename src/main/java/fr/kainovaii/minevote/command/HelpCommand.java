package fr.kainovaii.minevote.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Subcommand;
import fr.kainovaii.minevote.MineVote;
import io.papermc.paper.plugin.configuration.PluginMeta;
import org.bukkit.entity.Player;

@CommandAlias("minevote|vote")
public class HelpCommand extends BaseCommand
{
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
}
