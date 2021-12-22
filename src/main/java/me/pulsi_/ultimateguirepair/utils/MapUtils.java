package me.pulsi_.ultimateguirepair.utils;

import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class MapUtils {

    public static Map<String, ItemStack> playerItems = new HashMap<>();

    public static boolean hasItemQueued(Player p) {
        ItemStack item = playerItems.get(p.getName());
        return item != null;
    }

    public static ItemStack getPlayerItemQueued(Player p) {
        return playerItems.get(p.getName());
    }

    public static void removeItemQueued(Player p) {
        playerItems.remove(p.getName());
    }


    public static Map<String, Item> itemsOnAnvil = new HashMap<>();

    public static boolean hasItemOnAnvil(Player p) {
        Item item = itemsOnAnvil.get(p.getName());
        return item != null;
    }

    public static Item getPlayerItemOnAnvil(Player p) {
        return itemsOnAnvil.get(p.getName());
    }

    public static void removeItemOnAnvil(Player p) {
        Item itemToRemove = MapUtils.itemsOnAnvil.get(p.getName());
        itemToRemove.remove();
        MapUtils.itemsOnAnvil.remove(p.getName());
    }
}