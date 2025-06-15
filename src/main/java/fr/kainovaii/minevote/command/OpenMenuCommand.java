package fr.kainovaii.minevote.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import fr.kainovaii.minevote.gui.ConfigGui;
import fr.kainovaii.minevote.gui.ShopGui;
import fr.kainovaii.minevote.gui.main.MainGui;
import org.bukkit.entity.Player;

@CommandAlias("minevote|vote")
public class OpenMenuCommand extends BaseCommand
{
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

    @Subcommand("gui-config")
    @CommandPermission("minevote.admin")
    public void config(Player player)
    {
        new ConfigGui(player, 2).open(player);
    }
}