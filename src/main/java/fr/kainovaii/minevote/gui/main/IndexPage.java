package fr.kainovaii.minevote.gui.main;

import fr.kainovaii.minevote.gui.ShopGui;
import fr.kainovaii.minevote.utils.gui.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class IndexPage
{
    public IndexPage(MainGui gui, Player player)
    {
        gui.setItem(11, new ItemBuilder(Material.BOOK).name(gui.getConfig().getMessage("gui.items.site")).build(), event -> {
            if (event.isLeftClick()) {
                player.closeInventory();
                new MainGui(player, 1).open(player);
            }
        });
        gui.setItem(13, new ItemBuilder(Material.CHEST).name(gui.getConfig().getMessage("gui.items.shop")).build(), event -> {
            if (event.isLeftClick()) {
                player.closeInventory();
                new ShopGui(player).open(player);
            }
        });
        gui.setItem(15, new ItemBuilder(Material.PAPER).name(gui.getConfig().getMessage("gui.items.ranking")).build(), event -> {
            if (event.isLeftClick()) {
                player.closeInventory();
                new MainGui(player, 2).open(player);
            }
        });
    }
}
