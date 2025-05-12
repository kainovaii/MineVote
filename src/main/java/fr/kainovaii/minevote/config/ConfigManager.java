package fr.kainovaii.minevote.config;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class ConfigManager {

    private final JavaPlugin plugin;
    private FileConfiguration message;
    private FileConfiguration shop;
    private final File messageFile;
    private final File shopFile;

    public ConfigManager(JavaPlugin plugin) {
        this.plugin = plugin;
        this.messageFile = new File(plugin.getDataFolder(), "message.yml");
        this.shopFile = new File(plugin.getDataFolder(), "shop.yml");
    }

    public void loadConfigs() {
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdirs();
        }

        if (!new File(plugin.getDataFolder(), "config.yml").exists()) {
            plugin.saveResource("config.yml", false);
            plugin.getLogger().info("Le fichier config.yml a été copié.");
        }

        if (!messageFile.exists()) {
            plugin.saveResource("message.yml", false);
            plugin.getLogger().info("Le fichier message.yml a été copié.");
        }

        if (!shopFile.exists()) {
            plugin.saveResource("shop.yml", false);
            plugin.getLogger().info("Le fichier shop.yml a été copié.");
        }

        this.message = YamlConfiguration.loadConfiguration(messageFile);
        this.shop = YamlConfiguration.loadConfiguration(shopFile);
    }

    public Object getConfig(String path) {
        return plugin.getConfig().get(path);
    }

    public String getConfigString(String path) {
        return plugin.getConfig().getString(path);
    }

    public List<String> getConfigStringList(String path) {
        return plugin.getConfig().getStringList(path);
    }

    public List<Object> getConfigList(String path) {
        List<?> list = plugin.getConfig().getList(path);
        return list != null ? new ArrayList<>(list) : new ArrayList<>();
    }

    public void setConfig(String path, Object value) {
        plugin.getConfig().set(path, value);
        plugin.saveConfig();
    }

    public void reloadConfig() {
        plugin.reloadConfig();
    }

    public String getMessage(String path) {
        if (message.contains(path)) {
            return message.getString(path).replace("&", "§");
        } else {
            plugin.getLogger().warning("La clé '" + path + "' n'a pas été trouvée dans message.yml.");
            return "Message non trouvé.";
        }
    }

    public FileConfiguration getMessages() {
        return this.message;
    }

    public FileConfiguration getShop() {
        return this.shop;
    }

    public void saveConfigs() {
        try {
            plugin.saveConfig();
            message.save(messageFile);
            shop.save(shopFile);
        } catch (IOException e) {
            plugin.getLogger().severe("Erreur lors de la sauvegarde des fichiers de configuration : " + e.getMessage());
        }
    }

    public Map<String, ConfigurationSection> getProviders() {
        Map<String, ConfigurationSection> providersMap = new HashMap<>();
        ConfigurationSection section = plugin.getConfig().getConfigurationSection("providers");

        if (section != null) {
            for (String key : section.getKeys(false)) {
                ConfigurationSection providerSection = section.getConfigurationSection(key);
                if (providerSection != null) {
                    providersMap.put(key, providerSection);
                }
            }
        }
        return providersMap;
    }

    public void reloadConfigs() {
        plugin.reloadConfig();
        this.message = YamlConfiguration.loadConfiguration(messageFile);
        this.shop = YamlConfiguration.loadConfiguration(shopFile);
    }

}
