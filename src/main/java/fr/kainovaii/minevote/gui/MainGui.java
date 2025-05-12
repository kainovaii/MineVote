package fr.kainovaii.minevote.gui;

import fr.kainovaii.minevote.MineVote;
import fr.kainovaii.minevote.config.ConfigManager;
import fr.kainovaii.minevote.domain.voter.VoterRepository;
import fr.kainovaii.minevote.utils.gui.InventoryAPI;
import fr.kainovaii.minevote.utils.gui.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.awt.*;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class MainGui extends InventoryAPI {

    private final Player player;
    private final int page;
    private ConfigManager configManager;

    public MainGui(Player player) {
        this(player, 0);
    }

    public MainGui(Player player, int page) {
        super(27, "§8MineVote");
        this.player = player;
        this.page = page;
        this.configManager = MineVote.getInstance().getConfigManager();
        setupMenu();
    }

    private void setupMenu() {
        for (int slot : Arrays.asList(1, 2, 3, 5, 6, 7, 8, 9, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 26)) {
            setItem(slot, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).name("§f").build());
        }
        setItem(0, this.voteCompass());
        setItem(4, playerHead(player));
        if (page == 0) {this.GuiIndex();}
        if (page == 1) {this.GuiSite();}
    }

    public void GuiIndex()
    {
        setItem(11, new ItemBuilder(Material.BOOK).name("§6Vote site").build(), event -> {
            if (event.isLeftClick()) {
                player.closeInventory();
                new MainGui(player, 1).open(player);
            }
        });

        setItem(13, new ItemBuilder(Material.CHEST).name("§6Vote Shop §8(§cA venir§8)").build(), event -> {
            if (event.isLeftClick()) {
                //player.closeInventory();
                //new ShopGui(player).open(player);
            }
        });
    }

    private void GuiSite()
    {
        int slot = 10;
        for (Map.Entry<String, ConfigurationSection> entry : configManager.getProviders().entrySet()) {
            if (slot > 16) break;
            String name = entry.getKey();
            ConfigurationSection provider = entry.getValue();
            String url = provider.getString("url");

            setItem(slot, new ItemBuilder(Material.GREEN_WOOL)
                    .name("§6Vote sur " + name)
                    .lore(
                        "§7" + url,
                        "§2➤§f Clic gauche pour ouvrir"
                    )
                    .build(), event -> {
                openWebUrl(url);
            });
            slot++;
        }

        setItem(26, new ItemBuilder(Material.ARROW).name("§cRetour").build(),event -> {
            if (event.isLeftClick()) {
                player.closeInventory();
                new MainGui(player).open(player);
            }
        });
    }

    public static ItemStack playerHead(Player player)
    {
        int voting = VoterRepository.getVoting(player.getName());
        ItemStack skull = new ItemBuilder(Material.PLAYER_HEAD)
                .name("§6" + player.getName())
                .addLore(
                        "§8§l→ §7Vote: §b{voting}".replace("{voting}", String.valueOf(voting))
                ).build();

        SkullMeta meta = (SkullMeta) skull.getItemMeta();
        if (meta != null) {
            meta.setOwningPlayer(player);
            skull.setItemMeta(meta);
        }
        return skull;
    }

    private ItemStack voteCompass()
    {
        int voteCounter = (int) configManager.getConfig("voteCounter");
        int voteObjective = (int) configManager.getConfig("voteObjective");

        ItemStack compass = new ItemBuilder(Material.COMPASS)
                .name("§6Etat du boost")
                .addLore(
                        "§8§l→ §b{voteCounter}/{voteObjective}"
                                .replace("{voteCounter}", String.valueOf(voteCounter))
                                .replace("{voteObjective}", String.valueOf(voteObjective))
                ).build();
        return compass;
    }

    public void openWebUrl(String url)
    {
        try {
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                URI uri = new URI(url);
                desktop.browse(uri);
            } else {
                System.out.println("L'ouverture d'URL n'est pas supportée sur cette plateforme.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}