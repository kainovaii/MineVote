package fr.kainovaii.minevote.gui;

import fr.kainovaii.minevote.MineVote;
import fr.kainovaii.minevote.config.ConfigManager;
import fr.kainovaii.minevote.utils.gui.InventoryAPI;
import fr.kainovaii.minevote.utils.gui.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.ShulkerBox;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ShopGui extends InventoryAPI {

    private final Player player;
    private final int page;
    private ConfigManager configManager;

    public ShopGui(Player player) {
        this(player, 0);
    }

    public ShopGui(Player player, int page) {
        super(27, "§8Vote shop");
        this.player = player;
        this.page = page;
        this.configManager = MineVote.getInstance().getConfigManager();
        setupMenu();
    }

    private void setupMenu() {
        for (int slot : Arrays.asList(0, 1, 2, 3, 5, 6, 7, 8, 9, 17, 18, 19, 20, 21, 22, 23, 24, 25)) {
            setItem(slot, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).name("§f").build());
        }
        setupItems();
        setItem(4, MainGui.playerHead(player));
        setItem(26, new ItemBuilder(Material.ARROW).name("§cRetour").build(),event -> {
            if (event.isLeftClick()) {
                player.closeInventory();
                new MainGui(player).open(player);
            }
        });
    }

    public void setupItems() {
        int slot = 10;
        for (Map.Entry<String, ConfigurationSection> entry : configManager.getShopItems().entrySet()) {
            if (slot > 16) break;

            ConfigurationSection itemSection = entry.getValue();
            String name = itemSection.getString("name", "§fItem inconnu").replace("&", "§");
            String materialName = itemSection.getString("material", "STONE");
            List<String> lore = itemSection.getStringList("lore");
            List<String> formattedLore = new ArrayList<>();
            for (String line : lore) {
                formattedLore.add(line.replace("&", "§"));
            }

            double price = itemSection.getDouble("price", -1);
            Material material = Material.matchMaterial(materialName);
            if (material == null) material = Material.STONE;

            String type = itemSection.getString("type", "").trim().toLowerCase();

            ItemStack displayItem = null;
            ItemStack giveItem = null;

            if (type.equals("shulker")) {
                List<String> displayedLore = new ArrayList<>(formattedLore);
                if (price >= 0) {
                    displayedLore.add("§7Prix: §e" + price + "⛃");
                }

                ItemBuilder displayBuilder = new ItemBuilder(Material.SHULKER_BOX)
                        .name(name)
                        .lore(displayedLore);

                ItemBuilder giveBuilder = new ItemBuilder(Material.SHULKER_BOX)
                        .name(name)
                        .lore(formattedLore);

                ConfigurationSection itemInside = itemSection.getConfigurationSection("item_inside");
                if (itemInside != null) {
                    String itemName = itemInside.getString("name", "§fObjet inconnu").replace("&", "§");
                    String itemMaterial = itemInside.getString("material", "STONE");
                    List<String> itemLore = itemInside.getStringList("lore");
                    List<String> formattedItemLore = new ArrayList<>();
                    for (String line : itemLore) {
                        formattedItemLore.add(line.replace("&", "§"));
                    }

                    Material itemMaterialType = Material.matchMaterial(itemMaterial);
                    if (itemMaterialType == null) itemMaterialType = Material.STONE;

                    ItemStack itemInsideStack = new ItemStack(itemMaterialType);
                    ItemMeta itemMeta = itemInsideStack.getItemMeta();
                    if (itemMeta != null) {
                        itemMeta.setDisplayName(itemName);
                        itemMeta.setLore(formattedItemLore);
                        List<String> enchantments = itemInside.getStringList("enchant");
                        for (String enchant : enchantments) {
                            String[] enchantData = enchant.split(":");
                            if (enchantData.length == 2) {
                                Enchantment enchantment = Enchantment.getByName(enchantData[0].toUpperCase());
                                if (enchantment != null) {
                                    int level = Integer.parseInt(enchantData[1]);
                                    itemMeta.addEnchant(enchantment, level, true);
                                }
                            }
                        }
                        itemInsideStack.setItemMeta(itemMeta);
                    }

                    Inventory inventory = Bukkit.createInventory(null, 9, "Shulker Box Inventory");
                    inventory.addItem(itemInsideStack);

                    // Création du displayItem (avec prix)
                    ItemStack shulkerDisplay = displayBuilder.build();
                    BlockStateMeta displayMeta = (BlockStateMeta) shulkerDisplay.getItemMeta();
                    if (displayMeta != null && displayMeta.getBlockState() instanceof ShulkerBox shulkerState) {
                        shulkerState.getInventory().setContents(inventory.getContents());
                        displayMeta.setBlockState(shulkerState);
                        shulkerDisplay.setItemMeta(displayMeta);
                        displayItem = shulkerDisplay;
                    }

                    ItemStack shulkerGive = giveBuilder.build();
                    BlockStateMeta giveMeta = (BlockStateMeta) shulkerGive.getItemMeta();
                    if (giveMeta != null && giveMeta.getBlockState() instanceof ShulkerBox shulkerState) {
                        shulkerState.getInventory().setContents(inventory.getContents());
                        giveMeta.setBlockState(shulkerState);
                        shulkerGive.setItemMeta(giveMeta);
                        giveItem = shulkerGive;
                    }

                } else {
                    displayItem = displayBuilder.build();
                    giveItem = giveBuilder.build();
                }

            } else {
                List<String> displayedLore = new ArrayList<>(formattedLore);
                if (price >= 0) {
                    displayedLore.add("§7Prix: §e" + price + "⛃");
                }

                ItemBuilder displayBuilder = new ItemBuilder(material)
                        .name(name)
                        .lore(displayedLore);

                ItemBuilder giveBuilder = new ItemBuilder(material)
                        .name(name)
                        .lore(formattedLore);

                List<String> enchantments = itemSection.getStringList("enchant");
                for (String enchant : enchantments) {
                    try {
                        Enchantment enchantment = Enchantment.getByName(enchant.toUpperCase());
                        if (enchantment != null) {
                            displayBuilder.enchant(enchantment, 1);
                            giveBuilder.enchant(enchantment, 1);
                        }
                    } catch (Exception ignored) {}
                }

                displayItem = displayBuilder.build();
                giveItem = giveBuilder.build();
            }

            final ItemStack itemToGive = giveItem;
            final String itemName = name;

            setItem(slot, displayItem, event -> {
                if (event.getWhoClicked() instanceof Player player) {
                    player.getInventory().addItem(itemToGive);
                    player.sendMessage("§aVous avez reçu l'objet: §f" + itemName);
                    player.closeInventory();
                }
            });

            slot++;
        }
    }

}
