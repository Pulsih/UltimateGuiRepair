package me.pulsi_.ultimateguirepair.listeners;

import me.pulsi_.ultimateguirepair.configs.Values;
import me.pulsi_.ultimateguirepair.gui.GuiHolder;
import me.pulsi_.ultimateguirepair.utils.MapUtils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class BlockInteraction implements Listener {

    /**
     * Simple listener to check if a player clicks on an anvil.
     */
    @EventHandler
    public void noAnvilClick(PlayerInteractEvent e) {
        Block block = e.getClickedBlock();
        if (!e.getAction().equals(Action.RIGHT_CLICK_BLOCK) || block.getType() != Material.ANVIL) return;
        e.setCancelled(true);

        Player p = e.getPlayer();
        GuiHolder.getInstance().openBank(p);

        ItemStack handItem = p.getItemInHand();
        if (!Values.CONFIG().isItemOnAnvilEffect() || handItem.getType() == Material.AIR) return;

        Item itemToRepair = p.getWorld().dropItem(block.getLocation().add(0.500, 1, 0.500), handItem);
        // Applying the velocity to make the item stay at his location once spawned.
        itemToRepair.setVelocity(p.getLocation().getDirection().setX(0).setY(-1).setZ(0));
        itemToRepair.setPickupDelay(999999999);

        MapUtils.itemsOnAnvil.put(p.getName(), itemToRepair);
        MapUtils.playerItems.put(p.getName(), handItem);

        p.setItemInHand(null);
    }
}