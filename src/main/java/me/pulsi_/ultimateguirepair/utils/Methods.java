package me.pulsi_.ultimateguirepair.utils;

import me.pulsi_.ultimateguirepair.UltimateGuiRepair;
import me.pulsi_.ultimateguirepair.configs.Values;
import me.pulsi_.ultimateguirepair.managers.MessageManager;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Methods {

    public static void repair(ItemStack item, Player p) {
        if (item == null) return;
        UltimateGuiRepair plugin = JavaPlugin.getPlugin(UltimateGuiRepair.class);

        double playerMoney = plugin.getEconomy().getBalance(p);
        double cost = getRepairCost(item);
        if (playerMoney < cost) {
            MessageManager.insufficientMoney(p, cost - playerMoney);
            return;
        }

        plugin.getEconomy().withdrawPlayer(p, cost);
        item.setDurability((short) 0);
        MapUtils.removeItemOnAnvil(p);

        playSound(Values.CONFIG().getRepairSound(), p);
        MessageManager.successfullyRepaired(p, cost);
        p.closeInventory();
    }

    public static double getRepairCost(ItemStack item) {
        if (item.getDurability() < 0) return 0;

        double cost;
        long itemDamage = item.getDurability();
        if ("CONSTANT".equals(Values.CONFIG().getRepairType())) {
            cost = Values.CONFIG().getRepairCost();
        } else {
            cost = (double) itemDamage * Values.CONFIG().getRepairCost();
        }

        if (Values.CONFIG().isEnchantIncreasePrice()) {
            List<Enchantment> enchants = new ArrayList<>(item.getEnchantments().keySet());
            for (int i = 0; i < enchants.size(); i++)
                cost = cost + ((cost / 100) * Values.CONFIG().getEnchantPricePercentage());
        }

        return cost;
    }

    public static String format(double amount) {
        if (amount < 1000L) return formatString(amount);
        if (amount < 1000000L) return formatString(amount / 1000L) + "K";
        if (amount < 1000000000L) return formatString(amount / 1000000L) + "M";
        if (amount < 1000000000000L) return formatString(amount / 1000000000L) + "B";
        if (amount < 1000000000000000L) return formatString(amount / 1000000000000L) + "T";
        if (amount < 1000000000000000000L) return formatString(amount / 1000000000000000L) + "Q";
        return "0";
    }

    private static String formatString(double amount) {
        NumberFormat format = NumberFormat.getInstance(Locale.ENGLISH);
        format.setMaximumFractionDigits(2);
        format.setMinimumFractionDigits(0);
        return format.format(amount);
    }

    public static void playSound(String sound, Player p) {
        try {
            String[] pathSlitted = sound.split(" ");
            String soundType = pathSlitted[0];
            int volume = Integer.parseInt(pathSlitted[1]);
            int pitch = Integer.parseInt(pathSlitted[2]);
            p.playSound(p.getLocation(), Sound.valueOf(soundType), volume, pitch);
        } catch (NullPointerException | IllegalArgumentException exception) {
            UGRLogger.logWarn("&cCannot recognize \"" + sound + "\" as a minecraft sound.");
        }
    }

    public static void fireItemAnimation(Player p, Location location) {
        Item itemToRepair = p.getWorld().dropItem(location.add(0.500, 1, 0.500), p.getItemInHand());
        itemToRepair.setVelocity(p.getLocation().getDirection().setX(0).setY(-1).setZ(0));
        itemToRepair.setPickupDelay(999999999);

        MapUtils.itemsOnAnvil.put(p.getName(), itemToRepair);
    }
}
