package fr.kainovaii.minevote.gui.main;

import fr.kainovaii.minevote.MineVote;
import fr.kainovaii.minevote.config.ConfigManager;
import fr.kainovaii.minevote.utils.HeadUtils;
import fr.kainovaii.minevote.utils.VotesManager;
import fr.kainovaii.minevote.utils.gui.ItemBuilder;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import java.util.Map;

public class SitePage
{
    private ConfigManager configManager;
    private VotesManager votesManager;

    public SitePage ()
    {
        this.configManager = MineVote.getInstance().getConfigManager();
        this.votesManager = MineVote.getInstance().getVotesManager();
    }

    public SitePage(MainGui gui, Player player) {
        this ();
        int slot = 10;
        for (Map.Entry<String, ConfigurationSection> entry : gui.getConfig().getProviders().entrySet()) {
            if (slot > 16) break;

            String siteId = entry.getKey();
            ConfigurationSection provider = entry.getValue();
            String name = provider.getString("title").replace("&", "§");
            String url = provider.getString("url");

            gui.setItem(slot, new ItemBuilder(materialCanVote(player, siteId))
                    .name("§6Vote sur " + name)
                    .lore(
                            "§7┌ §" + configManager.getMessage("gui.site_page.site_lore.site_url").replace("{site_url}", url),
                            "§7├ §f" + votesManager.getNextVoteDateTime(player.getName(), siteId),
                            "§7│",
                            "§7└ §f" + configManager.getMessage("gui.site_page.site_lore.footer")
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

            gui.setItem(8, new ItemBuilder(Material.OAK_SIGN)
                    .name(configManager.getMessage("gui.site_page.sign.name"))
                    .lore(configManager.getMessage("gui.site_page.sign.lore"))
                    .build(), event -> {
                player.closeInventory();

                for (Map.Entry<String, ConfigurationSection> e : gui.getConfig().getProviders().entrySet()) {
                    String tName = e.getValue().getString("title").replace("&", "§");
                    String tUrl = e.getValue().getString("url");

                    TextComponent message = new TextComponent("§6" + tName + ": ");
                    TextComponent link = new TextComponent(tUrl);
                    link.setColor(net.md_5.bungee.api.ChatColor.AQUA);
                    link.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, tUrl));
                    message.addExtra(link);

                    player.spigot().sendMessage(message);
                }
            });
        }
        GuiUtils.arrowBack(player, gui, 0);
    }

    public ItemStack materialCanVote(Player player, String siteId)
    {
        if (votesManager.canVote(player.getName(), siteId))
        {
            // Green ball
            return HeadUtils.getCustomHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjFlOTc0YTI2MDhiZDZlZTU3ZjMzNDg1NjQ1ZGQ5MjJkMTZiNGEzOTc0NGViYWI0NzUzZjRkZWI0ZWY3ODIifX19");
        }
        // Red ball
        return  HeadUtils.getCustomHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2U2MjMyMTFiZGE1ZGQ1YWI5ZTc2MDMwZjg2YjFjNDczMGI5ODg3MjZlZWY2YTNhYjI4YWExYzFmN2Q4NTAifX19");
    }
}
