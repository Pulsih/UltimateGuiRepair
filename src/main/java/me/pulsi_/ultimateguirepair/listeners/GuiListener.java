package me.pulsi_.ultimateguirepair.listeners;

import me.pulsi_.ultimateguirepair.configs.ConfigManager;
import me.pulsi_.ultimateguirepair.configs.Values;
import me.pulsi_.ultimateguirepair.gui.GuiHolder;
import me.pulsi_.ultimateguirepair.utils.MapUtils;
import me.pulsi_.ultimateguirepair.utils.Methods;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

import java.util.List;

public class GuiListener implements Listener {

    /**
     * Simple listener for the gui.
     */
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();

        if (!(p.getOpenInventory().getTopInventory().getHolder() instanceof GuiHolder)) return;
        e.setCancelled(true);

        for (String key : Values.CONFIG().getGuiItems().getKeys(false)) {
            ConfigurationSection items = ConfigManager.getInstance().getConfig().getConfigurationSection("Gui.Items." + key);

            if (e.getSlot() + 1 != items.getInt("Slot") || items.getStringList("Actions").isEmpty()) continue;

            List<String> actions = items.getStringList("Actions");
            for (String action : actions) {
                switch (action) {
                    case "[CLOSE]":
                        p.closeInventory();
                        break;

                    case "[REPAIR]":
                        Methods.repair(p.getItemInHand(), p);
                        break;
                }
            }
        }
    }

    /**
     * Used to not losing the item when closing the inventory while having the item queued on the anvil.
     */
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        if (!(e.getPlayer() instanceof Player)) return;

        Player p = (Player) e.getPlayer();
        if (MapUtils.hasItemOnAnvil(p)) MapUtils.removeItemOnAnvil(p);
    }
}