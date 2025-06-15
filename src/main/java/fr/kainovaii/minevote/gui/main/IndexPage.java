package fr.kainovaii.minevote.gui.main;

import fr.kainovaii.minevote.gui.ShopGui;
import fr.kainovaii.minevote.utils.HeadUtils;
import fr.kainovaii.minevote.utils.gui.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class IndexPage
{
    public IndexPage(MainGui gui, Player player)
    {
        ItemStack shopHead = HeadUtils.getCustomHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTFiZTRiNWI1OTJmZWUyMWE2NWUwZjkwMzAzOGM1MzMzYmUzODgyMzRhNDM3MzFkNGFkZmU1ZDU3ZDM2NDRlNSJ9fX0=");
        ItemStack rankingHead = HeadUtils.getCustomHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWQzMDhhZTI3YjU4YjY5NjQ1NDk3ZjlkYTg2NTk3ZWRhOTQ3ZWFjZDEwYzI5ZTNkNGJiZjNiYzc2Y2ViMWVhYiJ9fX0=");
        ItemStack siteHead = HeadUtils.getCustomHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmZhN2RjYzMyYTQzMzJmMDQ4ZWZlYzkzZWY1NDY2ZjM0NDBkYjQ5YzI1ODJmOTIwZTYyODRhNTRhY2UzZGJlIn19fQ==");

        gui.setItem(11, new ItemBuilder(siteHead).name(gui.getConfig().getMessage("gui.items.site")).build(), event -> {
            if (event.isLeftClick()) {
                new MainGui(player, 1).open(player);
            }
        });
        gui.setItem(13, new ItemBuilder(shopHead).name(gui.getConfig().getMessage("gui.items.shop")).build(), event -> {
            if (event.isLeftClick()) {
                new ShopGui(player).open(player);
            }
        });
        gui.setItem(15, new ItemBuilder(rankingHead).name(gui.getConfig().getMessage("gui.items.ranking")).build(), event -> {
            if (event.isLeftClick()) {
                new MainGui(player, 2).open(player);
            }
        });
    }
}
