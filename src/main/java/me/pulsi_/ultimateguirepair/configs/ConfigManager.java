package me.pulsi_.ultimateguirepair.configs;

import me.pulsi_.ultimateguirepair.UltimateGuiRepair;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class ConfigManager {

    private final UltimateGuiRepair plugin;
    private static File configFile, messagesFile;
    private static FileConfiguration config, messages;

    public ConfigManager(UltimateGuiRepair plugin) {
        this.plugin = plugin;
    }

    public static ConfigManager getInstance() {
        return new ConfigManager(JavaPlugin.getPlugin(UltimateGuiRepair.class));
    }

    public void createConfigs() {
        configFile = new File(plugin.getDataFolder(), "config.yml");
        messagesFile = new File(plugin.getDataFolder(), "messages.yml");

        if (!configFile.exists()) plugin.saveResource("config.yml", false);
        if (!messagesFile.exists()) plugin.saveResource("messages.yml", false);

        config = new YamlConfiguration();
        messages = new YamlConfiguration();

        config = YamlConfiguration.loadConfiguration(configFile);
        messages = YamlConfiguration.loadConfiguration(messagesFile);
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public FileConfiguration getMessages() {
        return messages;
    }

    public void reloadConfigs() {
        config = YamlConfiguration.loadConfiguration(configFile);
        messages = YamlConfiguration.loadConfiguration(messagesFile);
    }
}
