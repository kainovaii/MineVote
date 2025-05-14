package fr.kainovaii.minevote.gui;

import fr.kainovaii.minevote.MineVote;
import fr.kainovaii.minevote.config.ConfigManager;
import fr.kainovaii.minevote.domain.voter.Voter;
import fr.kainovaii.minevote.domain.voter.VoterRepository;
import fr.kainovaii.minevote.utils.gui.InventoryAPI;
import fr.kainovaii.minevote.utils.gui.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.javalite.activejdbc.LazyList;
import org.javalite.activejdbc.Model;

import java.awt.*;
import java.net.URI;
import java.util.ArrayList;
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
        String startMaterial = configManager.getConfig("customize-gui.borderMaterial").toString();
        Material borderMaterial = Material.matchMaterial(startMaterial);

        for (int slot : Arrays.asList(1, 2, 3, 5, 6, 7, 8, 9, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 26)) {
            setItem(slot, new ItemBuilder(borderMaterial).name("§f").build());
        }
        setItem(0, this.voteCompass());
        setItem(4, playerHead(player));
        if (page == 0) {this.GuiIndex();}
        if (page == 1) {this.GuiSite();}
        if (page == 2) {this.GuiRanking();}
    }

    public void GuiIndex()
    {
        setItem(11, new ItemBuilder(Material.BOOK).name(configManager.getMessage("gui.items.site")).build(), event -> {
            if (event.isLeftClick()) {
                player.closeInventory();
                new MainGui(player, 1).open(player);
            }
        });
        setItem(13, new ItemBuilder(Material.CHEST).name(configManager.getMessage("gui.items.shop")).build(), event -> {
            if (event.isLeftClick()) {
                player.closeInventory();
                new ShopGui(player).open(player);
            }
        });
        setItem(15, new ItemBuilder(Material.PAPER).name(configManager.getMessage("gui.items.ranking")).build(), event -> {
            if (event.isLeftClick()) {
                player.closeInventory();
                new MainGui(player, 2).open(player);
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

        arrowBack();
    }

    public void GuiRanking() {
        LazyList<Model> voters = VoterRepository.getVoters();
        List<Model> sortedVoters = new ArrayList<>(voters);
        sortedVoters.sort((a, b) -> {
            int voteA = a.getInteger("voting");
            int voteB = b.getInteger("voting");
            if (voteA != voteB) {
                return Integer.compare(voteB, voteA);
            }
            int bankA = a.getInteger("bank");
            int bankB = b.getInteger("bank");
            return Integer.compare(bankB, bankA);
        });

        int slot = 10;
        for (Model voter : sortedVoters) {
            if (slot > 16) break;

            String name = voter.getString("name");
            OfflinePlayer offline = Bukkit.getOfflinePlayer(name);

            setItem(slot, new ItemBuilder(playerHead(offline))
                    .name("§6" + offline.getName())
                    .build(), event -> {
                if (event.isLeftClick()) {
                    // Logic
                }
            });

            slot++;
        }

        arrowBack();
    }

    public static ItemStack playerHead(OfflinePlayer player) {
        int voting = VoterRepository.getVoting(player.getName());
        int bank = VoterRepository.getBank(player.getName());

        ItemStack skull = new ItemBuilder(Material.PLAYER_HEAD)
                .name("§6" + player.getName())
                .addLore(
                        "§8§l→ §7Votes: §b" + voting,
                        "§8§l→ §7Points: §b" + bank
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
                .name(configManager.getMessage("gui.compass.name"))
                .addLore(configManager.getMessage("gui.compass.text")
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
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void arrowBack()
    {
        setItem(26, new ItemBuilder(Material.ARROW).name(configManager.getMessage("gui.arrows.back")).build(),event -> {
            if (event.isLeftClick()) {
                player.closeInventory();
                new MainGui(player).open(player);
            }
        });
    }
}