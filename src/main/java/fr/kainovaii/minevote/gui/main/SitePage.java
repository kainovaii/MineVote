package fr.kainovaii.minevote.gui.main;

import fr.kainovaii.minevote.MineVote;
import fr.kainovaii.minevote.config.ConfigManager;
import fr.kainovaii.minevote.utils.VotesManager;
import fr.kainovaii.minevote.utils.gui.ItemBuilder;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

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

            gui.setItem(8, new ItemBuilder(Material.OAK_SIGN)
                    .name("§6Voir tout les sites")
                    .lore( "§2➤§f Clic gauche pour ouvrir"  )
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

    public Material materialCanVote(Player player, String siteId)
    {
        if (votesManager.canVote(player.getName(), siteId))
        {
            return Material.GREEN_WOOL;
        }
        return Material.RED_WOOL;
    }
}
