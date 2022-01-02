package me.pulsi_.ultimateguirepair.utils;

import me.clip.placeholderapi.PlaceholderAPI;
import me.pulsi_.ultimateguirepair.UltimateGuiRepair;
import me.pulsi_.ultimateguirepair.configs.Values;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class ItemUtils {

    private static final ItemStack UNKNOWN_MATERIAL = new ItemStack(Material.BARRIER);

    public static ItemStack createItemStack(ConfigurationSection c, Player p) {
        UltimateGuiRepair plugin = JavaPlugin.getPlugin(UltimateGuiRepair.class);

        String itemType = c.getString("Type");
        if (itemType != null && itemType.equals("CUSTOM")) {
            String customItemType = c.getString("Custom-Item");
            if (customItemType.equals("ITEM-DISPLAYER")) return p.getItemInHand();
        }

        String material = c.getString("Material");
        ItemStack item = getItem(material, c);
        ItemMeta meta = item.getItemMeta();

        String displayName = c.getString("Displayname");
        setDisplayname(displayName, meta, plugin.isPlaceholderApiHooked(), p);

        setLore(c, plugin.isPlaceholderApiHooked(), meta, p);

        if (c.getBoolean("Glowing")) {
            meta.addEnchant(Enchantment.DURABILITY, 1, false);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }

        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack guiFiller() {
        ItemStack filler;

        String material = Values.CONFIG().getGuiFillerMaterial();
        if (material == null) return UNKNOWN_MATERIAL;

        try {
            if (material.contains(":")) {
                String[] itemData = material.split(":");
                filler = new ItemStack(Material.valueOf(itemData[0]), 1, Byte.parseByte(itemData[1]));
            } else {
                filler = new ItemStack(Material.valueOf(material));
            }
        } catch (IllegalArgumentException e) {
            filler = UNKNOWN_MATERIAL;
        }

        ItemMeta fillerMeta = filler.getItemMeta();

        String displayName = Values.CONFIG().getGuiFillerDisplayname();
        if (displayName == null) {
            fillerMeta.setDisplayName(ChatUtils.color("&c&l*CANNOT FIND DISPLAYNAME*"));
        } else {
            fillerMeta.setDisplayName(ChatUtils.color(displayName));
        }

        if (Values.CONFIG().isGuiFillerGlowing()) {
            fillerMeta.addEnchant(Enchantment.DURABILITY, 1, false);
            filler.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }

        filler.setItemMeta(fillerMeta);
        return filler;
    }

    private static ItemStack getItem(String material, ConfigurationSection c) {
        ItemStack item;
        if (material == null) return UNKNOWN_MATERIAL;

        try {
            if (material.contains(":")) {
                String[] itemData = material.split(":");
                item = new ItemStack(Material.valueOf(itemData[0]), 1, Byte.parseByte(itemData[1]));
            } else {
                item = new ItemStack(Material.valueOf(material));
            }
        } catch (IllegalArgumentException e) {
            item = UNKNOWN_MATERIAL;
        }

        int amount = c.getInt("Amount");
        if (amount > 1) item.setAmount(amount);

        return item;
    }

    private static void setDisplayname(String displayName, ItemMeta meta, boolean isPlaceholderApiHooked, Player p) {
        if (displayName == null) {
            meta.setDisplayName(ChatUtils.color("&c&l*CANNOT FIND DISPLAYNAME*"));
            return;
        }
        if (isPlaceholderApiHooked) {
            meta.setDisplayName(ChatUtils.color(PlaceholderAPI.setPlaceholders(p, displayName)));
        } else {
            meta.setDisplayName(ChatUtils.color(displayName));
        }
    }

    private static void setLore(ConfigurationSection c, boolean isPlaceholderApiHooked, ItemMeta meta, Player p) {
        List<String> lore = new ArrayList<>();

        for (String lines : c.getStringList("Lore")) {
            lore.add(ChatUtils.color(lines
                    .replace("%cost_formatted%", "" + Methods.format(Methods.getRepairCost(p.getItemInHand())))
                    .replace("%cost%", "" + Methods.getRepairCost(p.getItemInHand()))
            ));
        }

        if (isPlaceholderApiHooked) {
            meta.setLore(PlaceholderAPI.setPlaceholders(p, lore));
        } else {
            meta.setLore(lore);
        }
    }
}