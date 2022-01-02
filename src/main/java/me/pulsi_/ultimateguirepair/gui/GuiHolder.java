package me.pulsi_.ultimateguirepair.gui;

import me.pulsi_.ultimateguirepair.UltimateGuiRepair;
import me.pulsi_.ultimateguirepair.configs.Values;
import me.pulsi_.ultimateguirepair.utils.ChatUtils;
import me.pulsi_.ultimateguirepair.utils.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.plugin.java.JavaPlugin;

public class GuiHolder implements InventoryHolder {

    private final Inventory guiBank;

    public GuiHolder(String title) {
        this.guiBank = Bukkit.createInventory(this, InventoryType.HOPPER, ChatUtils.color(title));
    }

    public static GuiHolder getInstance() {
        return new GuiHolder(Values.CONFIG().getGuiTitle());
    }

    public void openBank(Player p) {
        GuiHolder gui = getInstance();
        gui.buildBank(p);
        p.openInventory(gui.getInventory());
    }

    private void buildBank(Player p) {
        ConfigurationSection c = Values.CONFIG().getGuiItems();
        Bukkit.getScheduler().runTaskAsynchronously(JavaPlugin.getPlugin(UltimateGuiRepair.class), () -> {
            for (String items : c.getKeys(false)) {
                try {
                    guiBank.setItem(c.getConfigurationSection(items).getInt("Slot") - 1,
                            ItemUtils.createItemStack(c.getConfigurationSection(items), p));
                } catch (ArrayIndexOutOfBoundsException ex) {
                    ChatUtils.consoleMessage("&8[&a&lAdvanced&c&lGui&9&lRepair&8] &cSome items arent set properly in the GUI! Please fix as soon as possible!");
                    guiBank.addItem(ItemUtils.createItemStack(c.getConfigurationSection(items), p));
                }
            }
            if (Values.CONFIG().isGuiFillerEnabled())
                for (int i = 0; i < 4; i++)
                    if (guiBank.getItem(i) == null) guiBank.setItem(i, ItemUtils.guiFiller());
        });
    }

    @Override
    public Inventory getInventory() {
        return guiBank;
    }
}