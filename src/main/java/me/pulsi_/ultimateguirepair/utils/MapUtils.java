package me.pulsi_.ultimateguirepair.utils;

import org.bukkit.entity.Item;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class MapUtils {

    public static Map<String, Item> itemsOnAnvil = new HashMap<>();

    public static boolean hasItemOnAnvil(Player p) {
        Item item = itemsOnAnvil.get(p.getName());
        return item != null;
    }

    public static void removeItemOnAnvil(Player p) {
        Item itemToRemove = MapUtils.itemsOnAnvil.get(p.getName());
        if (itemToRemove == null) return;
        itemToRemove.remove();
        MapUtils.itemsOnAnvil.remove(p.getName());
    }
}