package fr.kainovaii.minevote.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class ConfigManager {

    private JavaPlugin plugin;
    private FileConfiguration config;
    private FileConfiguration message;
    private File configFile;
    private File messageFile;
    
    public ConfigManager(JavaPlugin plugin) {
        this.plugin = plugin;
        this.configFile = new File(plugin.getDataFolder(), "config.yml");
        this.messageFile = new File(plugin.getDataFolder(), "message.yml");
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

        this.config = YamlConfiguration.loadConfiguration(configFile);
        this.message = YamlConfiguration.loadConfiguration(messageFile);
    }

    public Object getConfigValue(String path) {
        return config.get(path);
    }

    public void setConfigValue(String path, Object value) {
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
    public void saveConfig() {
        try {
            config.save(configFile);
            config.save(messageFile);
        } catch (IOException e) {
            plugin.getLogger().severe("Impossible de sauvegarder le fichier config.yml");
        }
    }

    public void reloadConfigs() {
        loadConfigs();
        plugin.reloadConfig();
    }

    public FileConfiguration getConfig() {
        return this.config;
    }

    /*
    public FileConfiguration getMessages() {
        return this.message;
    }
    */
}
