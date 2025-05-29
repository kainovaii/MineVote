package fr.kainovaii.minevote.gui.main;

import fr.kainovaii.minevote.MineVote;
import fr.kainovaii.minevote.config.ConfigManager;
import fr.kainovaii.minevote.domain.voter.VoterRepository;
import fr.kainovaii.minevote.utils.gui.InventoryAPI;
import fr.kainovaii.minevote.utils.gui.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.List;
import java.util.stream.Collectors;

public class GuiUtils
{
    private ConfigManager configManager;

    public GuiUtils(Player player, int page)
    {
        this.configManager = MineVote.getInstance().getConfigManager();
    }

    public static ItemStack playerHead(OfflinePlayer player)
    {
        ConfigManager configManager = MineVote.getInstance().getConfigManager();

        int voting = VoterRepository.getVoting(player.getName());
        int bank = VoterRepository.getBank(player.getName());

        List<String> loreLines = configManager.getMessageList("gui.player_head");
        List<String> lore = loreLines.stream()
        .map(line -> line
        .replace("&", "§")
        .replace("{player}", player.getName())
        .replace("{votes}", String.valueOf(voting))
        .replace("{points}", String.valueOf(bank)))
        .collect(Collectors.toList());

        ItemStack skull = new ItemBuilder(Material.PLAYER_HEAD)
        .name("§6" + player.getName())
        .addLore(lore).build();

        SkullMeta meta = (SkullMeta) skull.getItemMeta();
        if (meta != null) {
            meta.setOwningPlayer(player);
            skull.setItemMeta(meta);
        }
        return skull;
    }

    public static void borderGui(MainGui gui)
    {
        String startMaterial = gui.getConfig().getConfig("customize-gui.borderMaterial").toString();
        Material borderMaterial = Material.matchMaterial(startMaterial);

        int[] borderSlots = {
                1, 2, 3, 5, 6, 7, 8, 9, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 26
        };
        for (int slot : borderSlots) {
            gui.setItem(slot, new ItemBuilder(borderMaterial).name("§f").build());
        }
    }

    public static void arrowBack(Player player, InventoryAPI gui, int page) {
        gui.setItem(26, new ItemBuilder(Material.ARROW).name("§cRetour").build(), event -> {
            if (event.isLeftClick()) {
                player.closeInventory();
                new MainGui(player, page).open(player);
            }
        });
    }

    public static ItemStack voteCompass(MainGui gui)
    {
        int voteCounter = (int) gui.getConfig().getConfig("boost-settings.counter");
        int voteObjective = (int) gui.getConfig().getConfig("boost-settings.objective");

        ItemStack compass = new ItemBuilder(Material.COMPASS)
                .name(gui.getConfig().getMessage("gui.compass.name"))
                .addLore(gui.getConfig().getMessage("gui.compass.text")
                        .replace("{voteCounter}", String.valueOf(voteCounter))
                        .replace("{voteObjective}", String.valueOf(voteObjective))
                ).build();
        return compass;
    }
}