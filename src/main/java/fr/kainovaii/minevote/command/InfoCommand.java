package fr.kainovaii.minevote.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Subcommand;
import fr.kainovaii.minevote.MineVote;
import io.papermc.paper.plugin.configuration.PluginMeta;
import org.bukkit.entity.Player;

@CommandAlias("minevote|vote")
public class InfoCommand extends BaseCommand
{
    @Subcommand("info")
    public void info(Player player)
    {
        PluginMeta minevote = MineVote.getInstance().getPluginMeta();
        String[] messages = new String[]
        {
            "§6§m────────§7[§bMineVote§7]§6§m────────",
            "§7┌ §6Author §7↪ §b" + minevote.getAuthors(),
            "§7├ §6Version §7↪ §b" + minevote.getVersion(),
            "§7├ §6Api Version §7↪ §b" + minevote.getAPIVersion(),
            "§7└ §6Depend §7↪ §b" + minevote.getPluginDependencies(),
            "§6§m──────────────────────"
        };
        player.sendMessage(messages);
    }
}
