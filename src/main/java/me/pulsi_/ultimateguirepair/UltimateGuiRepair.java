package me.pulsi_.ultimateguirepair;

import me.pulsi_.ultimateguirepair.managers.DataManager;
import me.pulsi_.ultimateguirepair.utils.UGRLogger;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class UltimateGuiRepair extends JavaPlugin {

    private static Economy econ;

    private boolean placeholderApiHooked = false;

    @Override
    public void onEnable() {

        if (Bukkit.getPluginManager().getPlugin("Vault") == null) {
            UGRLogger.logError("Cannot load UltimateGuiRepair plugin, the Vault plugin has not been installed, please install it to use this plugin!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        if (!setupEconomy()) {
            UGRLogger.logError("Cannot load UltimateGuiRepair plugin, no economy plugin has been found!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            placeholderApiHooked = true;
        }

        DataManager.getInstance().enablePlugin();
    }

    @Override
    public void onDisable() {
        DataManager.getInstance().shutdownPlugin();
    }

    public boolean isPlaceholderApiHooked() {
        return placeholderApiHooked;
    }

    public Economy getEconomy() {
        return econ;
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) return false;
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp != null) econ = rsp.getProvider();
        return true;
    }
}