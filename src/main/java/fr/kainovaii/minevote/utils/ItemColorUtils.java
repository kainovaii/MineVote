package fr.kainovaii.minevote.utils;

import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.Banner;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;

import java.util.List;
import java.util.Map;

/**
 * Utilitaires pour appliquer des couleurs aux objets Minecraft (boucliers, etc.).
 */
public final class ItemColorUtils {

    private static final Map<String, Integer> DYE_COLOR_IDS = Map.ofEntries(
            Map.entry("WHITE", 0), Map.entry("ORANGE", 1), Map.entry("MAGENTA", 2),
            Map.entry("LIGHT_BLUE", 3), Map.entry("YELLOW", 4), Map.entry("LIME", 5),
            Map.entry("PINK", 6), Map.entry("GRAY", 7), Map.entry("LIGHT_GRAY", 8),
            Map.entry("CYAN", 9), Map.entry("PURPLE", 10), Map.entry("BLUE", 11),
            Map.entry("BROWN", 12), Map.entry("GREEN", 13), Map.entry("RED", 14),
            Map.entry("BLACK", 15)
    );

    private ItemColorUtils() {
        // Classe utilitaire, pas d'instanciation
    }

    /**
     * Applique un tag NBT de couleur (Base) au bouclier.
     */
    public static ItemStack applyNbtColor(ItemStack item, String colorName) {
        if (item == null || item.getType() != Material.SHIELD) return item;

        Integer colorId = DYE_COLOR_IDS.get(colorName.toUpperCase());
        if (colorId == null) return item;

        NBTItem nbt = new NBTItem(item);
        nbt.addCompound("BlockEntityTag").setByte("Base", colorId.byteValue());
        return nbt.getItem();
    }

    /**
     * Applique un motif de bannière unie colorée à un bouclier.
     */
    public static ItemStack applyBannerPattern(ItemStack item, String colorName) {
        if (item == null || item.getType() != Material.SHIELD) return item;

        DyeColor dyeColor = getDyeColorFromName(colorName);
        if (dyeColor == null) return item;

        if (item.getItemMeta() instanceof BlockStateMeta blockStateMeta &&
                blockStateMeta.getBlockState() instanceof Banner banner) {

            banner.setPatterns(List.of(new Pattern(dyeColor, PatternType.BASE)));
            blockStateMeta.setBlockState(banner);
            item.setItemMeta(blockStateMeta);
        }

        return item;
    }

    /**
     * Renvoie l'objet Color Bukkit correspondant à un nom de couleur.
     */
    public static Color getColorFromName(String colorName) {
        return switch (colorName.toUpperCase()) {
            case "PINK" -> Color.FUCHSIA;
            case "RED" -> Color.RED;
            case "BLUE" -> Color.BLUE;
            case "GREEN" -> Color.GREEN;
            case "YELLOW" -> Color.YELLOW;
            case "ORANGE" -> Color.ORANGE;
            case "PURPLE" -> Color.PURPLE;
            case "WHITE" -> Color.WHITE;
            case "BLACK" -> Color.BLACK;
            case "GRAY", "GREY" -> Color.GRAY;
            case "LIGHT_BLUE" -> Color.fromRGB(173, 216, 230);
            case "LIME" -> Color.LIME;
            case "CYAN" -> Color.AQUA;
            case "MAGENTA" -> Color.FUCHSIA;
            case "BROWN" -> Color.MAROON;
            default -> null;
        };
    }

    /**
     * Renvoie l'objet DyeColor correspondant à un nom de couleur.
     */
    public static DyeColor getDyeColorFromName(String colorName) {
        return switch (colorName.toUpperCase()) {
            case "PINK" -> DyeColor.PINK;
            case "RED" -> DyeColor.RED;
            case "BLUE" -> DyeColor.BLUE;
            case "GREEN" -> DyeColor.GREEN;
            case "YELLOW" -> DyeColor.YELLOW;
            case "ORANGE" -> DyeColor.ORANGE;
            case "PURPLE" -> DyeColor.PURPLE;
            case "WHITE" -> DyeColor.WHITE;
            case "BLACK" -> DyeColor.BLACK;
            case "GRAY", "GREY" -> DyeColor.GRAY;
            case "LIGHT_BLUE" -> DyeColor.LIGHT_BLUE;
            case "LIME" -> DyeColor.LIME;
            case "CYAN" -> DyeColor.CYAN;
            case "MAGENTA" -> DyeColor.MAGENTA;
            case "BROWN" -> DyeColor.BROWN;
            default -> null;
        };
    }
}
