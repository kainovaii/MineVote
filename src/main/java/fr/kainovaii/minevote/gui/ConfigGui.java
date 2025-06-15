package fr.kainovaii.minevote.gui;

import fr.kainovaii.minevote.MineVote;
import fr.kainovaii.minevote.config.ConfigManager;
import fr.kainovaii.minevote.gui.main.GuiUtils;
import fr.kainovaii.minevote.gui.main.MainGui;
import fr.kainovaii.minevote.utils.HeadUtils;
import fr.kainovaii.minevote.utils.gui.InventoryAPI;
import fr.kainovaii.minevote.utils.gui.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class ConfigGui extends InventoryAPI
{
    private final Player player;
    private final int page;
    private final ConfigManager configManager;

    public ConfigGui(Player player) { this(player, 0); }

    public ConfigGui(Player player, int page)
    {
        super(27, "§8MineVote");
        this.player = player;
        this.page = page;
        this.configManager = MineVote.getInstance().getConfigManager();
        setupMenu();
    }

    private void setupMenu()
    {
        GuiUtils.borderGui(this);
        String itemName = "§6Chosir §7(§bClic gauche§7)";

        this.setItem(10, new ItemBuilder(HeadUtils.getCustomHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjcyYjVjZjRmYjJlM2Q5MTU4YTRmNWVjZTcxNDk3MGJjYzEzNzFjYTAyOWU2NDU1NTU5MjdmZDE1NmUxODQifX19")).name(itemName).build(), event -> {
            Bukkit.getScheduler().runTaskLater(MineVote.getInstance(), () -> {
                configManager.setConfig("customize-gui.borderMaterial", "RED_STAINED_GLASS_PANE");
                new ConfigGui(player).open(player);
            }, 2L);
        });

        this.setItem(11, new ItemBuilder(HeadUtils.getCustomHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzA5NTEzODlmMTczMzI4ZThiMjZjMDhmNzExNmE0NTUxMThkYTNmMTdkOTg1YzRmNGI1YTVjN2RhNzZkZjEifX19")).name(itemName).build(), event -> {
            if (event.isLeftClick()) {
                Bukkit.getScheduler().runTaskLater(MineVote.getInstance(), () -> {
                    configManager.setConfig("customize-gui.borderMaterial", "LIME_STAINED_GLASS_PANE");
                    new ConfigGui(player).open(player);
                }, 2L);
            }
        });

        this.setItem(12, new ItemBuilder(HeadUtils.getCustomHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDNjNTBjYzc1ZWFkYTdhZDFkY2YwMjM4YTdmMzBlZTI3ZGM4NjFkNTFkMDU3ODJlMDJjN2U1NmU4NDE0YzZjIn19fQ==")).name(itemName).build(), event -> {
            Bukkit.getScheduler().runTaskLater(MineVote.getInstance(), () -> {
                configManager.setConfig("customize-gui.borderMaterial", "GREEN_STAINED_GLASS_PANE");
                new ConfigGui(player).open(player);
            }, 2L);
        });

        this.setItem(13, new ItemBuilder(HeadUtils.getCustomHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjIzYzRiNjcxZjI0NzNlMzgyYzYzMWRmMTcxZTI3MWYxY2FmNDlhZmMxNTQ4ZWU2NzUzY2Y2MjdiZTBmZDcyIn19fQ==")).name(itemName).build(), event -> {
            if (event.isLeftClick()) {
                Bukkit.getScheduler().runTaskLater(MineVote.getInstance(), () -> {
                    configManager.setConfig("customize-gui.borderMaterial", "ORANGE_STAINED_GLASS_PANE");
                    new ConfigGui(player).open(player);
                }, 2L);
            }
        });

        this.setItem(14, new ItemBuilder(HeadUtils.getCustomHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzUyYTRiMWIyY2M1NTQwNzJhMDE5ZjkyNDI0ODE0YWI0Y2UyZmNiYTVmZDY1ZWM5NjY5YzZhZWE4Y2IifX19")).name(itemName).build(), event -> {
            if (event.isLeftClick()) {
                Bukkit.getScheduler().runTaskLater(MineVote.getInstance(), () -> {
                    configManager.setConfig("customize-gui.borderMaterial", "PURPLE_STAINED_GLASS_PANE");
                    new ConfigGui(player).open(player);
                }, 2L);
            }
        });

        this.setItem(15, new ItemBuilder(HeadUtils.getCustomHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2RiMjU4YzcxZjE0NDFmN2E4YmM2NDZkNzRlZmYyNDgyMTI1YmY2OTg1ZDU4NjMxMzA2YzIzOTJmZGY2NTE1NyJ9fX0=")).name(itemName).build(), event -> {
            if (event.isLeftClick()) {
                Bukkit.getScheduler().runTaskLater(MineVote.getInstance(), () -> {
                    configManager.setConfig("customize-gui.borderMaterial", "BLUE_STAINED_GLASS_PANE");
                    new ConfigGui(player).open(player);
                }, 2L);
            }
        });

        this.setItem(16, new ItemBuilder(HeadUtils.getCustomHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmY5ZGMxYTRjZTE5YThmOTliN2YxMjE1YzQ4NGE1NjU3N2M1MDFmODYzNDRmZTIyYzZlNWIxYmExYWQwIn19fQ==")).name(itemName).build(), event -> {
            if (event.isLeftClick()) {
                Bukkit.getScheduler().runTaskLater(MineVote.getInstance(), () -> {
                    configManager.setConfig("customize-gui.borderMaterial", "BLACK_STAINED_GLASS_PANE");
                    new ConfigGui(player).open(player);
                }, 2L);
            }
        });
    }

    public Player getPlayer()
    {
        return player;
    }

    public int getPage() {
        return page;
    }

    public ConfigManager getConfig() {
        return configManager;
    }
}
