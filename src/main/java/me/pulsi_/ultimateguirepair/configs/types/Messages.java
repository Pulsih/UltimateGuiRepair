package me.pulsi_.ultimateguirepair.configs.types;

import me.pulsi_.ultimateguirepair.configs.ConfigManager;
import org.bukkit.configuration.file.FileConfiguration;

public class Messages {

    private static String noPermission;
    private static String pluginReloaded;
    private static String alreadyRepaired;
    private static String insufficientMoney;
    private static String singularRepair;
    private static String successfullyRepaired;
    private static String specifyPlayer;
    private static String cannotRepair;

    public void loadValues() {

        FileConfiguration messages = ConfigManager.getInstance().getMessages();

        noPermission = messages.getString("NoPermission");
        pluginReloaded = messages.getString("PluginReloaded");
        alreadyRepaired = messages.getString("AlreadyRepaired");
        insufficientMoney = messages.getString("InsufficientMoney");
        singularRepair = messages.getString("SingularRepair");
        successfullyRepaired = messages.getString("SuccessfullyRepaired");
        specifyPlayer = messages.getString("SpecifyPlayer");
        cannotRepair = messages.getString("Cannot-Repair");
    }

    public String getNoPermission() {
        return noPermission;
    }

    public String getPluginReloaded() {
        return pluginReloaded;
    }

    public String getAlreadyRepaired() {
        return alreadyRepaired;
    }

    public String getInsufficientMoney() {
        return insufficientMoney;
    }

    public String getSingularRepair() {
        return singularRepair;
    }

    public String getSuccessfullyRepaired() {
        return successfullyRepaired;
    }

    public String getSpecifyPlayer() {
        return specifyPlayer;
    }

    public String getCannotRepair() {
        return cannotRepair;
    }
}