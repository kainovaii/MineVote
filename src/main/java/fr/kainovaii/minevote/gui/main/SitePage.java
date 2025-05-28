package fr.kainovaii.minevote.gui.main;

import fr.kainovaii.minevote.utils.gui.ItemBuilder;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.Map;

public class SitePage
{
    public SitePage(MainGui gui, Player player) {
        int slot = 10;
        for (Map.Entry<String, ConfigurationSection> entry : gui.getConfig().getProviders().entrySet()) {
            if (slot > 16) break;
            ConfigurationSection provider = entry.getValue();
            String name = provider.getString("title").replace("&", "§");
            String url = provider.getString("url");

            gui.setItem(slot, new ItemBuilder(Material.GREEN_WOOL)
                    .name("§6Vote sur " + name)
                    .lore(
                            "§7" + url,
                            "§2➤§f Clic gauche pour ouvrir"
                    )
                    .build(), event -> {
                player.closeInventory();
                TextComponent message = new TextComponent(gui.getConfig().getMessage("messages.open_site_url"));
                TextComponent link = new TextComponent(url);
                link.setColor(net.md_5.bungee.api.ChatColor.AQUA);
                link.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, url));
                message.addExtra(link);

                player.spigot().sendMessage(message);
            });
            slot++;
        }

        GuiUtils.arrowBack(player, gui, 0);
    }
}