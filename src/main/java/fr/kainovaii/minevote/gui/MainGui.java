package fr.kainovaii.minevote.gui;

import fr.kainovaii.minevote.domain.voter.VoterRepository;
import fr.kainovaii.minevote.utils.gui.InventoryAPI;
import fr.kainovaii.minevote.utils.gui.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;

public class MainGui extends InventoryAPI {

    private final Player player;
    private final int page;

    public MainGui(Player player) {
        this(player, 0);
    }

    public MainGui(Player player, int page) {
        super(27, "§8MineVote");
        this.player = player;
        this.page = page;
        setupMenu();
    }

    private void setupMenu() {
        for (int slot : Arrays.asList(0, 1, 2, 3, 5, 6, 7, 8, 9, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 26)) {
            setItem(slot, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).name("§f").build());
        }

        setItem(4, this.playerHead(player));
        setItem(13, new ItemBuilder(Material.CHEST).name("§6Vote Store").build(), event -> {
            if (event.isLeftClick()) {
                player.closeInventory();
                new StoreGui(player).open(player);
            }
        });
    }

    public static ItemStack playerHead(Player player)
    {
        int voting = VoterRepository.getVoting(player.getName());
        double bank = VoterRepository.getBank(player.getName());

        ItemStack skull = new ItemBuilder(Material.PLAYER_HEAD)
                .name("§6" + player.getName())
                .addLore(
                        "§8§l→ §7Balance: §b" + bank + " $",
                        "§8§l→ §7Vote: §b" + voting
                ).build();
        SkullMeta meta = (SkullMeta) skull.getItemMeta();
        if (meta != null) {
            meta.setOwningPlayer(player);
            skull.setItemMeta(meta);
        }
        return skull;
    }
}
