package me.pulsi_.ultimateguirepair.utils;

import me.pulsi_.ultimateguirepair.UltimateGuiRepair;
import me.pulsi_.ultimateguirepair.configs.Values;
import me.pulsi_.ultimateguirepair.managers.MessageManager;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.text.NumberFormat;
import java.util.Locale;

public class Methods {

    public static void repair(ItemStack item, Player p) {
        if (item == null) return;

        long itemDamage = item.getDurability();
        UltimateGuiRepair plugin = JavaPlugin.getPlugin(UltimateGuiRepair.class);

        if (item.getAmount() > 1 && Values.CONFIG().isSingularRepair()) {
            MessageManager.singularRepair(p);
            return;
        }

        if (itemDamage < 1) {
            MessageManager.alreadyRepaired(p);
            return;
        }

        double playerMoney = plugin.getEconomy().getBalance(p);
        double cost = getRepairCost(item);
        if (playerMoney < cost) {
            MessageManager.insufficientMoney(p, cost - playerMoney);
            return;
        }

        plugin.getEconomy().withdrawPlayer(p, cost);
        if (MapUtils.hasItemQueued(p)) {
            ItemStack itemOnAnvilRepaired = MapUtils.getPlayerItemQueued(p);
            itemOnAnvilRepaired.setDurability((short) 0);
            p.setItemInHand(itemOnAnvilRepaired);

            MapUtils.removeItemOnAnvil(p);
            MapUtils.removeItemQueued(p);
        } else {
            item.setDurability((short) 0);
        }
        MessageManager.successfullyRepaired(p, cost);
        playSound(Values.CONFIG().getRepairSound(), p);
        p.closeInventory();
    }

    public static double getRepairCost(ItemStack item) {
        if (item.getDurability() < 0) return 0;
        long itemDamage = item.getDurability();
        return (double) itemDamage * Values.CONFIG().getRepairCost();
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
            UGRLogger.logWarn("&8[&a&lUltimate&c&lGui&9&lRepair&8] &cCannot recognize \"" + sound + "\" as a minecraft sound.");
        }
    }
}
