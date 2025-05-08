package fr.kainovaii.minevote.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class ConfigManager {

    private JavaPlugin plugin;
    private FileConfiguration config;
    private FileConfiguration messages;
    private File configFile;

    public ConfigManager(JavaPlugin plugin) {
        this.plugin = plugin;
        this.configFile = new File(plugin.getDataFolder(), "config.yml");
    }

    public void loadConfigs() {
        if (!configFile.exists()) {
            plugin.saveResource("config.yml", false);
        }
        this.config = YamlConfiguration.loadConfiguration(configFile);
    }

    public Object getConfigValue(String path) {
        return config.get(path);
    }

    public String getMessage(String path) {
        return messages.getString(path, "Message non trouvé.");
    }

    public void setConfigValue(String path, Object value) {
        config.set(path, value);
        saveConfig();
    }

    public void saveConfig() {
        try {
            config.save(configFile);
        } catch (IOException e) {
            plugin.getLogger().severe("Impossible de sauvegarder le fichier config.yml");
        }
    }

    public void reloadConfigs() {
        loadConfigs();
        plugin.reloadConfig();
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public FileConfiguration getMessages() {
        return messages;
    }
}
