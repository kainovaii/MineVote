package fr.kainovaii.minevote.gui;

import fr.kainovaii.minevote.domains.Voting;
import fr.kainovaii.minevote.gui.reflection.InventoryAPI;
import fr.kainovaii.minevote.gui.reflection.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;

public class VotingGui extends InventoryAPI {

    private final Player player;
    private final int page;

    public VotingGui(Player player) {
        this(player, 0);
    }

    public VotingGui(Player player, int page) {
        super(27, "§8§bShop");
        this.player = player;
        this.page = page;
        setupMenu();
    }

    private void setupMenu() {
        for (int slot : Arrays.asList(0, 1, 2, 3, 5, 6, 7, 8, 9, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 26)) {
            setItem(slot, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).name("§f").build());
        }

        setItem(4, this.playerHead());
        setItem(13, new ItemBuilder(Material.DRAGON_EGG).name("§6Page 0").build(), event -> {
            if (event.isLeftClick()) {
                player.closeInventory();
            }
        });
    }

    public ItemStack playerHead()
    {
        int voting = Voting.getVoting(player.getName());
        ItemStack skull = new ItemBuilder(Material.PLAYER_HEAD)
                .name("§6" + player.getName())
                .addLore(
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
