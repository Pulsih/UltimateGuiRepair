package me.pulsi_.ultimateguirepair.managers;

import me.pulsi_.ultimateguirepair.UltimateGuiRepair;
import me.pulsi_.ultimateguirepair.commands.Commands;
import me.pulsi_.ultimateguirepair.configs.ConfigManager;
import me.pulsi_.ultimateguirepair.configs.Values;
import me.pulsi_.ultimateguirepair.listeners.GuiListener;
import me.pulsi_.ultimateguirepair.listeners.BlockInteraction;
import me.pulsi_.ultimateguirepair.listeners.OnQuit;
import me.pulsi_.ultimateguirepair.utils.ChatUtils;
import me.pulsi_.ultimateguirepair.utils.MapUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class DataManager {

    private final UltimateGuiRepair plugin;

    public DataManager(UltimateGuiRepair plugin) {
        this.plugin = plugin;
    }

    public static DataManager getInstance() {
        return new DataManager(JavaPlugin.getPlugin(UltimateGuiRepair.class));
    }

    public void enablePlugin() {
        long startTime = System.currentTimeMillis();

        ChatUtils.consoleMessage("");
        ChatUtils.consoleMessage("    &8[&a&lUltimate&c&lGui&9&lRepair&8] &7v" + plugin.getDescription().getVersion());
        ChatUtils.consoleMessage("    &2Enabling plugin...");
        ConfigManager.getInstance().createConfigs();
        ChatUtils.consoleMessage("    &7Loaded config files! &f(" + (System.currentTimeMillis() - startTime) + "ms)");
        reloadPlugin();
        ChatUtils.consoleMessage("    &7Loaded values! &f(" + (System.currentTimeMillis() - startTime) + "ms)");
        registerCommands();
        ChatUtils.consoleMessage("    &7Registered commands! &f(" + (System.currentTimeMillis() - startTime) + "ms)");
        registerEvents();
        ChatUtils.consoleMessage("    &7Registered events! &f(" + (System.currentTimeMillis() - startTime) + "ms)");
        ChatUtils.consoleMessage("    &aDone! &f(" + (System.currentTimeMillis() - startTime) + " total ms)");
        ChatUtils.consoleMessage("");
    }

    public void shutdownPlugin() {
        ChatUtils.consoleMessage("");
        ChatUtils.consoleMessage("    &8[&a&lUltimate&c&lGui&9&lRepair&8] &cis shutting down...");
        ChatUtils.consoleMessage("");

        for (Player p : Bukkit.getOnlinePlayers())
            if (MapUtils.hasItemOnAnvil(p)) MapUtils.removeItemOnAnvil(p);
    }

    public void reloadPlugin() {
        ConfigManager.getInstance().reloadConfigs();
        Values.CONFIG().loadValues();
        Values.MESSAGES().loadValues();
    }

    private void registerCommands() {
        plugin.getCommand("ultimateguirepair").setExecutor(new Commands(plugin));
        plugin.getCommand("ultimateguirepair").setTabCompleter(new Commands(plugin));
    }

    private void registerEvents() {
        Bukkit.getPluginManager().registerEvents(new GuiListener(), plugin);
        Bukkit.getPluginManager().registerEvents(new BlockInteraction(), plugin);
        Bukkit.getPluginManager().registerEvents(new OnQuit(), plugin);
    }
}