package fr.kainovaii.minevote.config;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigManager {

    private JavaPlugin plugin;
    private FileConfiguration config;
    private FileConfiguration message;
    private FileConfiguration shop;
    private File configFile;
    private File messageFile;
    private File shopFile;
    
    public ConfigManager(JavaPlugin plugin) {
        this.plugin = plugin;
        this.configFile = new File(plugin.getDataFolder(), "config.yml");
        this.messageFile = new File(plugin.getDataFolder(), "message.yml");
        this.shopFile = new File(plugin.getDataFolder(), "shop.yml");
    }

    public void loadConfigs() {
        if (!configFile.exists()) {
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

        this.config = YamlConfiguration.loadConfiguration(configFile);
        this.message = YamlConfiguration.loadConfiguration(messageFile);
        this.shop = YamlConfiguration.loadConfiguration(shopFile);
    }

    public Object getConfig(String path) {
        return config.get(path);
    }

    public void setConfig(String path, Object value) {
        config.set(path, value);
        saveConfig();
    }

    public String getMessage(String path) {
        if (message.contains(path)) {
            return message.getString(path);
        } else {
            plugin.getLogger().warning("La clé '" + path + "' n'a pas été trouvée dans message.yml.");
            return "Message non trouvé.";
        }
    }

    public List<Object> getConfigList(String path) {
        List<?> rawList = config.getList(path);
        if (rawList == null) return new ArrayList<>();
        return new ArrayList<>(rawList);
    }

    public void saveConfig() {
        try {
            config.save(configFile);
            message.save(messageFile);
            shop.save(shopFile);
        } catch (IOException e) {
            plugin.getLogger().severe("Impossible de sauvegarder le fichier config.yml");
        }
    }

    public Map<String, ConfigurationSection> getProviders() {
        Map<String, ConfigurationSection> providersMap = new HashMap<>();
        ConfigurationSection section = config.getConfigurationSection("providers");

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
        loadConfigs();
        plugin.reloadConfig();
    }

    public FileConfiguration getConfig() {
        return this.config;
    }

    public FileConfiguration getMessages() {
        return this.message;
    }
}
