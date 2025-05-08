package fr.kainovaii.minevote.gui;

import fr.kainovaii.minevote.utils.gui.InventoryAPI;
import fr.kainovaii.minevote.utils.gui.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class StoreGui extends InventoryAPI {

    private final Player player;
    private final int page;

    public StoreGui(Player player) {
        this(player, 0);
    }

    public StoreGui(Player player, int page) {
        super(27, "§8Vote shop");
        this.player = player;
        this.page = page;
        setupMenu();
    }

    private void setupMenu() {
        for (int slot : Arrays.asList(0, 1, 2, 3, 5, 6, 7, 8, 9, 17, 18, 19, 20, 21, 22, 23, 24, 25)) {
            setItem(slot, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).name("§f").build());
        }

        setItem(4, MainGui.playerHead(player));
        setItem(26, new ItemBuilder(Material.ARROW).name("§cRetour").build(),event -> {
            if (event.isLeftClick()) {
                player.closeInventory();
                new MainGui(player).open(player);
            }
        });
    }
}
