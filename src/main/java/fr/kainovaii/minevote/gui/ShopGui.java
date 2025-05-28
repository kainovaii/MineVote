package fr.kainovaii.minevote.gui;

import fr.kainovaii.minevote.MineVote;
import fr.kainovaii.minevote.config.ConfigManager;
import fr.kainovaii.minevote.domain.voter.VoterRepository;
import fr.kainovaii.minevote.gui.main.GuiUtils;
import fr.kainovaii.minevote.gui.main.MainGui;
import fr.kainovaii.minevote.utils.Prefix;
import fr.kainovaii.minevote.utils.gui.InventoryAPI;
import fr.kainovaii.minevote.utils.gui.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.ShulkerBox;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ShopGui extends InventoryAPI {

    private final Player player;
    private final int page;
    private final ConfigManager configManager;

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
        String startMaterial = configManager.getConfig("customize-gui.borderMaterial").toString();
        Material borderMaterial = Material.matchMaterial(startMaterial);

        for (int slot : Arrays.asList(0, 1, 2, 3, 5, 6, 7, 8, 9, 17, 18, 19, 20, 21, 22, 23, 24, 25)) {
            setItem(slot, new ItemBuilder(borderMaterial).name("§f").build());
        }
        setupItems();
        setItem(4, GuiUtils.playerHead(player));
        setItem(26, new ItemBuilder(Material.ARROW).name("§cRetour").build(), event -> {
            if (event.isLeftClick()) {
                player.closeInventory();
                new MainGui(player).open(player);
            }
        });
    }

    public void setupItems() {
        int slot = 10;
        Map<String, ConfigurationSection> items = configManager.getShopItems();
        Map<String, ConfigurationSection> shulkers = configManager.getShopShulkers();

        for (Map.Entry<String, ConfigurationSection> entry : items.entrySet()) {
            if (slot > 16) break;

            ConfigurationSection itemSection = entry.getValue();
            ItemStack displayItem = buildDisplayItem(itemSection);
            ItemStack giveItem = buildGiveItem(itemSection);

            double price = itemSection.getDouble("price", -1);
            String name = itemSection.getString("name", "§fItem inconnu").replace("&", "§");

            final ItemStack finalGiveItem = giveItem;
            final int finalPrice = (int) price;
            final String finalName = name;

            setItem(slot, displayItem, event -> {
                if (event.getWhoClicked() instanceof Player p) {
                    if (playerBuyItem(p, finalPrice)) {
                        p.getInventory().addItem(finalGiveItem);
                        p.sendMessage(Prefix.BASE.get() + configManager.getMessage("messages.shop_buyed")
                                .replace("&", "§")
                                .replace("{item}", finalName)
                        );
                    }
                    p.closeInventory();
                }
            });

            slot++;
        }

        for (Map.Entry<String, ConfigurationSection> entry : shulkers.entrySet()) {
            if (slot > 16) break;

            ConfigurationSection shulkerSection = entry.getValue();

            ItemStack displayItem = buildShulkerDisplayItem(shulkerSection);
            ItemStack giveItem = buildShulkerGiveItem(shulkerSection);

            double price = shulkerSection.getDouble("price", -1);
            String name = shulkerSection.getString("name", "§fShulker inconnu").replace("&", "§");

            final ItemStack finalGiveItem = giveItem;
            final int finalPrice = (int) price;
            final String finalName = name;

            setItem(slot, displayItem, event -> {
                if (event.getWhoClicked() instanceof Player p) {
                    if (playerBuyItem(p, finalPrice)) {
                        p.getInventory().addItem(finalGiveItem);
                        p.sendMessage(Prefix.BASE.get() + configManager.getMessage("messages.shop_buyed")
                                .replace("&", "§")
                                .replace("{item}", finalName)
                        );
                    }
                    p.closeInventory();
                }
            });

            slot++;
        }
    }

    private ItemStack buildDisplayItem(ConfigurationSection section) {
        String name = section.getString("name", "§fItem inconnu").replace("&", "§");
        String materialName = section.getString("material", "STONE");
        List<String> lore = section.getStringList("lore");
        List<String> formattedLore = new ArrayList<>();
        for (String line : lore) {
            formattedLore.add(line.replace("&", "§"));
        }
        double price = section.getDouble("price", -1);
        if (price >= 0) {
            formattedLore.add("§7Prix: §e" + price + "⛃");
        }

        Material material = Material.matchMaterial(materialName);
        if (material == null) material = Material.STONE;

        ItemBuilder builder = new ItemBuilder(material).name(name).lore(formattedLore);

        List<String> enchantments = section.getStringList("enchant");
        for (String enchant : enchantments) {
            try {
                String[] parts = enchant.split(":");
                Enchantment enchantment = Enchantment.getByName(parts[0].toUpperCase());
                int level = parts.length == 2 ? Integer.parseInt(parts[1]) : 1;
                if (enchantment != null) {
                    builder.enchant(enchantment, level);
                }
            } catch (Exception ignored) {
            }
        }

        return builder.build();
    }

    private ItemStack buildGiveItem(ConfigurationSection section) {
        String name = section.getString("name", "§fItem inconnu").replace("&", "§");
        String materialName = section.getString("material", "STONE");
        List<String> lore = section.getStringList("lore");
        List<String> formattedLore = new ArrayList<>();
        for (String line : lore) {
            formattedLore.add(line.replace("&", "§"));
        }

        Material material = Material.matchMaterial(materialName);
        if (material == null) material = Material.STONE;

        ItemBuilder builder = new ItemBuilder(material).name(name).lore(formattedLore);

        List<String> enchantments = section.getStringList("enchant");
        for (String enchant : enchantments) {
            try {
                String[] parts = enchant.split(":");
                Enchantment enchantment = Enchantment.getByName(parts[0].toUpperCase());
                int level = parts.length == 2 ? Integer.parseInt(parts[1]) : 1;
                if (enchantment != null) {
                    builder.enchant(enchantment, level);
                }
            } catch (Exception ignored) {
            }
        }

        return builder.build();
    }

    private ItemStack buildShulkerDisplayItem(ConfigurationSection section) {
        String name = section.getString("name", "§fShulker inconnu").replace("&", "§");
        String materialName = section.getString("material", "SHULKER_BOX");
        List<String> lore = section.getStringList("lore");
        List<String> formattedLore = new ArrayList<>();
        for (String line : lore) {
            formattedLore.add(line.replace("&", "§"));
        }
        double price = section.getDouble("price", -1);
        if (price >= 0) {
            formattedLore.add("§7Prix: §e" + price + "⛃");
        }

        Material material = Material.matchMaterial(materialName);
        if (material == null) material = Material.SHULKER_BOX;

        ItemBuilder displayBuilder = new ItemBuilder(material).name(name).lore(formattedLore);

        ItemStack shulkerDisplay = displayBuilder.build();
        BlockStateMeta meta = (BlockStateMeta) shulkerDisplay.getItemMeta();

        if (meta != null && meta.getBlockState() instanceof ShulkerBox shulkerBox) {
            Inventory inv = Bukkit.createInventory(null, 9, "Shulker Box Inventory");

            ConfigurationSection itemsInside = section.getConfigurationSection("item_inside");
            if (itemsInside != null) {
                int i = 0;
                for (String key : itemsInside.getKeys(false)) {
                    if (i >= 9) break;
                    ConfigurationSection itemSection = itemsInside.getConfigurationSection(key);
                    if (itemSection != null) {
                        ItemStack insideItem = buildGiveItem(itemSection);
                        inv.setItem(i, insideItem);
                        i++;
                    }
                }
            }
            shulkerBox.getInventory().setContents(inv.getContents());
            meta.setBlockState(shulkerBox);
            shulkerDisplay.setItemMeta(meta);
        }

        return shulkerDisplay;
    }

    private ItemStack buildShulkerGiveItem(ConfigurationSection section) {
        String name = section.getString("name", "§fShulker inconnu").replace("&", "§");
        String materialName = section.getString("material", "SHULKER_BOX");
        List<String> lore = section.getStringList("lore");
        List<String> formattedLore = new ArrayList<>();
        for (String line : lore) {
            formattedLore.add(line.replace("&", "§"));
        }

        Material material = Material.matchMaterial(materialName);
        if (material == null) material = Material.SHULKER_BOX;

        ItemBuilder giveBuilder = new ItemBuilder(material).name(name).lore(formattedLore);

        ItemStack shulkerGive = giveBuilder.build();
        BlockStateMeta meta = (BlockStateMeta) shulkerGive.getItemMeta();

        if (meta != null && meta.getBlockState() instanceof ShulkerBox shulkerBox) {
            Inventory inv = Bukkit.createInventory(null, 9, "Shulker Box Inventory");

            ConfigurationSection itemsInside = section.getConfigurationSection("item_inside");
            if (itemsInside != null) {
                int i = 0;
                for (String key : itemsInside.getKeys(false)) {
                    if (i >= 9) break;
                    ConfigurationSection itemSection = itemsInside.getConfigurationSection(key);
                    if (itemSection != null) {
                        ItemStack insideItem = buildGiveItem(itemSection);
                        inv.setItem(i, insideItem);
                        i++;
                    }
                }
            }
            shulkerBox.getInventory().setContents(inv.getContents());
            meta.setBlockState(shulkerBox);
            shulkerGive.setItemMeta(meta);
        }

        return shulkerGive;
    }

    public boolean playerBuyItem(Player player, int price) {
        //PlayerBuyItemEvent buyEvent = new PlayerBuyItemEvent(player, "0x40f545dQ545", price);
        //Bukkit.getPluginManager().callEvent(buyEvent);
        int bank = VoterRepository.getBank(player.getName());

        if (bank >= price) {
            VoterRepository.updateBank(player.getName(), bank - price);
            return true;
        } else {
            player.sendMessage(Prefix.BASE.get() + configManager.getMessage("messages.shop_no_money").replace("&", "§"));
            return false;
        }
    }

}
