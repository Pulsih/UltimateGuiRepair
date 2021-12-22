package me.pulsi_.ultimateguirepair.configs.types;

import me.pulsi_.ultimateguirepair.configs.ConfigManager;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

public class MainConfig {

    private static String prefix;
    private static boolean guiFillerEnabled;
    private static String guiFillerDisplayname;
    private static String guiFillerMaterial;
    private static boolean guiFillerGlowing;
    private static ConfigurationSection guiItems;
    private static String guiTitle;
    private static int guiLines;
    private static double repairCost;
    private static boolean singularRepair;
    private static String repairSound;
    private static boolean itemOnAnvilEffect;

    public void loadValues() {

        FileConfiguration config = ConfigManager.getInstance().getConfig();

        prefix = config.getString("Settings.Prefix");
        guiFillerEnabled = config.getBoolean("Gui.Gui-Filler.Enabled");
        guiFillerDisplayname = config.getString("Gui.Gui-Filler.Displayname");
        guiFillerMaterial = config.getString("Gui.Gui-Filler.Material");
        guiFillerGlowing = config.getBoolean("Gui.Gui-Filler.Glowing");
        guiItems = config.getConfigurationSection("Gui.Items");
        guiTitle = config.getString("Gui.Title");
        guiLines = config.getInt("Gui.Lines");
        repairCost = config.getDouble("Settings.Repair-Cost");
        singularRepair = config.getBoolean("Settings.Singular-Repair");
        repairSound = config.getString("Settings.Repair-Sound");
        itemOnAnvilEffect = config.getBoolean("Settings.Item-On-Anvil-Effect");
    }

    public String getPrefix() {
        return prefix;
    }

    public boolean isGuiFillerEnabled() {
        return guiFillerEnabled;
    }

    public String getGuiFillerDisplayname() {
        return guiFillerDisplayname;
    }

    public String getGuiFillerMaterial() {
        return guiFillerMaterial;
    }

    public boolean isGuiFillerGlowing() {
        return guiFillerGlowing;
    }

    public ConfigurationSection getGuiItems() {
        return guiItems;
    }

    public String getGuiTitle() {
        return guiTitle;
    }

    public int getGuiLines() {
        return guiLines;
    }

    public double getRepairCost() {
        return repairCost;
    }

    public boolean isSingularRepair() {
        return singularRepair;
    }

    public String getRepairSound() {
        return repairSound;
    }

    public boolean isItemOnAnvilEffect() {
        return itemOnAnvilEffect;
    }
}